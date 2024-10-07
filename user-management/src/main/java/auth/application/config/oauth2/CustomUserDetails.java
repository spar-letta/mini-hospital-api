package auth.application.config.oauth2;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.UUID;

public record CustomUserDetails (UUID publicId,
                                 String username,
                                 Collection<GrantedAuthority> authorities) {
}
