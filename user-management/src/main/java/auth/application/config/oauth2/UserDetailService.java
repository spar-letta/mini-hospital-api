package auth.application.config.oauth2;

import auth.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;


public interface UserDetailService {
    UserDetailsService userDetailsService();

    User loadUser(String username);
}
