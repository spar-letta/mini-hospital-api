package auth.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.util.Optional;

@EnableJpaAuditing(auditorAwareRef = "createAuditorProvider")
@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> createAuditorProvider() {
        return () -> {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken auth) {
                    Object principal = auth.getPrincipal();
                    if (principal instanceof Jwt userDetails) {
                        if (userDetails.hasClaim("username")) {
                            return Optional.ofNullable((String) userDetails.getClaims().getOrDefault("username", ""));
                        } else {
                            return Optional.ofNullable((String) userDetails.getClaims().getOrDefault("userPublicId", ""));
                        }
                    } else {
                        return Optional.of("..");
                    }
                } else if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken auth) {
                    Object principal = auth.getPrincipal();
                    UserDetails userDetails = (UserDetails) principal;
                    return Optional.of(userDetails.getUsername());
                } else if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken auth) {
                    Object principal = auth.getPrincipal();
                    String userDetails = (String) principal;
                    return Optional.of(userDetails);
                } else {
                    return Optional.of("..");
                }
            } else {
                return Optional.of("..");
            }
        };
    }
}
