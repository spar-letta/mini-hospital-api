package auth.web;

import auth.application.doc.Examples;
import auth.dto.request.PrivilegeDto;
import auth.entity.Privilege;
import auth.service.PrivilegeService;
import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.converters.models.PageableAsQueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/privileges")
@Tag(name = "privilegs")
@RequiredArgsConstructor
@SecurityRequirement(name = "Jwt")
public class PrivilegeRestController {
    private final PrivilegeService privilegeService;

    @PreAuthorize("hasAuthority('CREATE_PRIVILEGE')")
    @PostMapping
    @Operation(summary = "Create Privile", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.CREATE_PRIVILEGE_OK_RESPONSE)))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {@ExampleObject(name = "Create Privilege Request", value = Examples.CREATE_PRIVILEGE_REQUEST)})))
    @JsonView(BaseView.privilegeView.class)
    public Privilege createPrivilege(@RequestBody @Valid PrivilegeDto privilegeDto) {
        return privilegeService.createPrivilege(privilegeDto);
    }

    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    @GetMapping
    @Operation(summary = "Read all privilegs", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.READ_PRIVILEGES_OK_RESPONSE)))})
    @PageableAsQueryParam
    @JsonView(BaseView.privilegeView.class)
    public Page<Privilege> getAllPrivileges(@Parameter(hidden = true) @PageableDefault(sort = "name", size = 10, direction = Sort.Direction.ASC) Pageable pageable,
                                            Sort sort) {
        return privilegeService.fetchAllPrivileges(pageable);
    }

}
