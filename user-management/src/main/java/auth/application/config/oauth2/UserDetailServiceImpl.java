package auth.application.config.oauth2;

import auth.entity.User;
import auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> {
            log.info("============================================================================");
            log.info("username {}", username);
            log.info("============================================================================");
            return userRepository.findByUserNameIgnoreCase(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        };
    }

    @Override
    public User loadUser(String username) {
        return userRepository.findByUserNameIgnoreCase(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}
