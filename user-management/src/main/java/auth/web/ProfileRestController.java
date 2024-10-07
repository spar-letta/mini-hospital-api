package auth.web;

import auth.application.doc.Examples;
import auth.service.UserService;
import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import auth.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
@Tag(name = "profile")
@RequiredArgsConstructor
public class ProfileRestController {

    private final UserService userService;

    @GetMapping
    @JsonView(BaseView.UserProfileView.class)
    @Operation(description = "user profile", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USERS_OK_RESPONSE)))
    })
    public User getUserProfile() {
        return userService.getUserProfile();
    }
}
