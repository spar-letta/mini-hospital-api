package auth.web;

import auth.application.doc.Examples;
import auth.dto.request.RoleDto;
import auth.service.UserService;
import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import auth.dto.request.CreateUserDTO;
import auth.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "users")
@RequiredArgsConstructor
@Tag(name = "users")
public class UserRestController {

    private final UserService userService;

    @PostMapping
    @JsonView(BaseView.UserCreatedDetailedView.class)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USER_OK_RESPONSE)))})
    public User createUser(@RequestBody @Valid CreateUserDTO createUserDTO) {
        return userService.createUser(createUserDTO);
    }

    @GetMapping("/{publicId}")
    @JsonView(BaseView.UserDetailedView.class)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USER_OK_RESPONSE)))})
    public User getUserByPublicId(@PathVariable UUID publicId) {
        return userService.getUserByPublicId(publicId);
    }

    @GetMapping
    @JsonView(BaseView.UserDetailedView.class)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USERS_OK_RESPONSE)))})
    @PageableAsQueryParam
    public List<User> getUsers(@Parameter(hidden = true) @PageableDefault(size = 20) Pageable pageable) {
        return userService.getUsers(pageable).getContent();
    }

    @PutMapping("/{publicId}")
    @JsonView(BaseView.UserDetailedView.class)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USER_OK_RESPONSE)))})
    public User updateUser(@PathVariable UUID publicId, @RequestBody @Valid CreateUserDTO createUserDTO) {
        return userService.updateUser(createUserDTO, publicId);
    }

    @PutMapping("/assignRole/{publicId}")
    @JsonView(BaseView.UserDetailedView.class)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USER_OK_RESPONSE)))})
    public User assignRoleToUser(@PathVariable UUID publicId, @RequestBody @Valid RoleDto request) {
        return userService.assignRoleToUser(request, publicId);
    }
}
