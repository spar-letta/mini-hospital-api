package auth.service;

import auth.application.exception.ApplicationOperationException;
import auth.entity.User;
import auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipleService {

    private final UserRepository userRepository;

    public User getLoggedInUser() {
        Optional<String> optionalUsername;
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            if (SecurityContextHolder.getContext().getAuthentication() instanceof JwtAuthenticationToken auth) {
                Object principal = auth.getPrincipal();
                if (principal instanceof Jwt userDetails) {
                    optionalUsername = Optional.of((String) userDetails.getClaims().getOrDefault("username", ""));
                } else {
                    optionalUsername = Optional.of("..");
                }
            } else if (SecurityContextHolder.getContext().getAuthentication() instanceof UsernamePasswordAuthenticationToken auth) {
                Object principal = auth.getPrincipal();
                UserDetails userDetails = (UserDetails) principal;
                optionalUsername = Optional.of(userDetails.getUsername());
            } else {
                optionalUsername = Optional.empty();
            }
        } else {
            optionalUsername = Optional.empty();
        }
        String username = optionalUsername.orElseThrow(() -> new ApplicationOperationException("user.not.found"));
        return userRepository.findByUserNameIgnoreCase(username).orElseThrow(() -> new ApplicationOperationException("user.not.found"));
    }
}
