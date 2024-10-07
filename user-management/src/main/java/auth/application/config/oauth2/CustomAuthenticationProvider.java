package auth.application.config.oauth2;

import auth.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2AccessTokenAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.context.AuthorizationServerContextHolder;
import org.springframework.security.oauth2.server.authorization.token.DefaultOAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.security.Principal;
import java.util.*;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private static final String ERROR_URI = "https://datatracker.ietf.org/doc/html/rfc6749#section-5.2";
    private final OAuth2AuthorizationService authorizationService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator;

    public CustomAuthenticationProvider(OAuth2AuthorizationService authorizationService,
                                        OAuth2TokenGenerator<? extends OAuth2Token> tokenGenerator,
                                        UserDetailService userDetailService, PasswordEncoder passwordEncoder) {

        Assert.notNull(authorizationService, "authorizationService cannot be null");
        Assert.notNull(tokenGenerator, "TokenGenerator cannot be null");
        Assert.notNull(userDetailService, "UserDetailsService cannot be null");
        Assert.notNull(passwordEncoder, "PasswordEncoder cannot be null");
        this.authorizationService = authorizationService;
        this.tokenGenerator = tokenGenerator;
        this.userDetailsService = userDetailService.userDetailsService();
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CustomAuthorizationGrantAuthenticationToken customAuthorizationGrantAuthenticationToken = (CustomAuthorizationGrantAuthenticationToken) authentication;
        OAuth2ClientAuthenticationToken clientPrincipal = getAuthenticatedClientElseThrowInvalidClient(customAuthorizationGrantAuthenticationToken);
        RegisteredClient registeredClient = clientPrincipal.getRegisteredClient();
        String username = customAuthorizationGrantAuthenticationToken.getUsername();
        String password = customAuthorizationGrantAuthenticationToken.getPassword();

        User user;
        Collection<GrantedAuthority> authorities;
        AuthorizationGrantType authorizationGrantType;
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            try {
                user = (User) userDetailsService.loadUserByUsername(username);
            } catch (UsernameNotFoundException e) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.ACCESS_DENIED);
            }

            if (!passwordEncoder.matches(password, user.getPassword())) {
                throw new OAuth2AuthenticationException(OAuth2ErrorCodes.ACCESS_DENIED);
            }
            authorities = user.getAuthorities();
            authorizationGrantType = new AuthorizationGrantType(Oauth2Settings.GRANT_TYPE_PASSWORD);
        } else {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
            throw new OAuth2AuthenticationException(error);
        }

        Set<String> authorizedScopes = registeredClient.getScopes();

        //-----------Create a new Security Context Holder Context----------
        OAuth2ClientAuthenticationToken oAuth2ClientAuthenticationToken = (OAuth2ClientAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        CustomUserDetails customUserDetails = new CustomUserDetails(user.getPublicId(), user.getUsername(), authorities);
        oAuth2ClientAuthenticationToken.setDetails(customUserDetails);

        var newcontext = SecurityContextHolder.createEmptyContext();
        newcontext.setAuthentication(oAuth2ClientAuthenticationToken);
        SecurityContextHolder.setContext(newcontext);

        //-----------TOKEN BUILDERS----------
        DefaultOAuth2TokenContext.Builder tokenContextBuilder = DefaultOAuth2TokenContext.builder()
                .registeredClient(registeredClient)
                .principal(clientPrincipal)
                .authorizationServerContext(AuthorizationServerContextHolder.getContext())
                .authorizedScopes(authorizedScopes)
                .authorizationGrantType(authorizationGrantType)
                .authorizationGrant(customAuthorizationGrantAuthenticationToken);

        OAuth2Authorization.Builder authorizationBuilder = OAuth2Authorization.withRegisteredClient(registeredClient)
                .attribute(Principal.class.getName(), clientPrincipal)
                .principalName(clientPrincipal.getName())
                .authorizationGrantType(authorizationGrantType)
                .authorizedScopes(authorizedScopes);

        //-----------ACCESS TOKEN----------
        OAuth2TokenContext tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.ACCESS_TOKEN).build();
        OAuth2Token generatedAccessToken = this.tokenGenerator.generate(tokenContext);
        if (generatedAccessToken == null) {
            OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                    "The token generator failed to generate the access token.", ERROR_URI);
            throw new OAuth2AuthenticationException(error);
        }

        OAuth2AccessToken accessToken = new OAuth2AccessToken(OAuth2AccessToken.TokenType.BEARER,
                generatedAccessToken.getTokenValue(), generatedAccessToken.getIssuedAt(),
                generatedAccessToken.getExpiresAt(), tokenContext.getAuthorizedScopes());
        if (generatedAccessToken instanceof ClaimAccessor) {
            authorizationBuilder.token(accessToken, (metadata) ->
                    metadata.put(OAuth2Authorization.Token.CLAIMS_METADATA_NAME, ((ClaimAccessor) generatedAccessToken).getClaims()));
        } else {
            authorizationBuilder.accessToken(accessToken);
        }

        //-----------REFRESH TOKEN----------
        OAuth2RefreshToken refreshToken = null;
        if (registeredClient.getAuthorizationGrantTypes().contains(AuthorizationGrantType.REFRESH_TOKEN) &&
                !clientPrincipal.getClientAuthenticationMethod().equals(ClientAuthenticationMethod.NONE)) {

            tokenContext = tokenContextBuilder.tokenType(OAuth2TokenType.REFRESH_TOKEN).build();
            OAuth2Token generatedRefreshToken = this.tokenGenerator.generate(tokenContext);
            if (!(generatedRefreshToken instanceof OAuth2RefreshToken)) {
                OAuth2Error error = new OAuth2Error(OAuth2ErrorCodes.SERVER_ERROR,
                        "The token generator failed to generate the refresh token.", ERROR_URI);
                throw new OAuth2AuthenticationException(error);
            }
            refreshToken = (OAuth2RefreshToken) generatedRefreshToken;
            authorizationBuilder.refreshToken(refreshToken);
        }

        OAuth2Authorization authorization = authorizationBuilder.build();
        this.authorizationService.save(authorization);

        Map<String, Object> additionalInfo = new HashMap<>();

        return new OAuth2AccessTokenAuthenticationToken(registeredClient, clientPrincipal, accessToken, refreshToken, additionalInfo);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthorizationGrantAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private static OAuth2ClientAuthenticationToken getAuthenticatedClientElseThrowInvalidClient(Authentication authentication) {
        OAuth2ClientAuthenticationToken clientPrincipal = null;
        if (OAuth2ClientAuthenticationToken.class.isAssignableFrom(authentication.getPrincipal().getClass())) {
            clientPrincipal = (OAuth2ClientAuthenticationToken) authentication.getPrincipal();
        }
        if (clientPrincipal != null && clientPrincipal.isAuthenticated()) {
            return clientPrincipal;
        }
        throw new OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_CLIENT);
    }

}
