package auth.web;

import auth.application.doc.Examples;
import auth.dto.request.CreateRoleDTO;
import auth.dto.request.RolePrivilegeDto;
import auth.entity.Role;
import auth.service.RoleService;
import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/roles")
@Tag(name = "roles")
@RequiredArgsConstructor
@SecurityScheme(
        name = "Jwt",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER,
        bearerFormat = "JWT"
)
@Tag(name = "roles")
public class RoleRestController {
    private static final Logger log = LoggerFactory.getLogger(RoleRestController.class);
    private final RoleService roleService;


    @PreAuthorize("hasAuthority('CREATE_ROLE')")
    @PostMapping
    @JsonView(BaseView.RoleView.class)
    @Operation(responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.USER_OK_RESPONSE)))})
    public Role createUser(@RequestBody @Valid CreateRoleDTO createRoleDTO) throws Exception {
        return roleService.createRole(createRoleDTO);
    }

    @PreAuthorize("hasAuthority('READ_ROLES')")
    @GetMapping
    @JsonView(BaseView.RoleView.class)
    @PageableAsQueryParam
    public Page<Role> getAllRoles(@PageableDefault Pageable pageable) throws Exception {
        return roleService.getAllRoles(pageable);
    }

    @PatchMapping("/assignPrivilegeToRole/{rolePublicId}")
    @PreAuthorize("hasAuthority('ASSIGN_PRIVILEGE_TO_ROLE')")
    @JsonView(BaseView.RoleView.class)
    public Role assignPrivilegeToRole(@PathVariable UUID rolePublicId, @RequestBody @Valid RolePrivilegeDto request) throws Exception {
        return roleService.assignPrivilegeToRole(rolePublicId, request);
    }
}