package auth.web;

import auth.entity.User;
import auth.service.UserService;
import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalRestController {

    private final UserService userService;

    @GetMapping("/user")
    @JsonView(BaseView.internalView.class)
    public User user(@RequestParam String username) {
        return userService.getOneUser(username);
    }
}
