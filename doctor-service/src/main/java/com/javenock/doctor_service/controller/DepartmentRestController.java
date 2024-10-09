package com.javenock.doctor_service.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.docs.Examples;
import com.javenock.doctor_service.model.Department;
import com.javenock.doctor_service.service.DepartmentService;
import com.javenock.doctor_service.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentRestController {
    private final DepartmentService departmentService;

    @GetMapping
    @Operation(summary = "Get Department Response", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.DEPARTMENT_RESPONSE)))})
    @JsonView(BaseView.DepartmentView.class)
    public Page<Department> findNaSearch(@RequestParam(name = "searchParam", required = false) String searchParam,
                                         @Parameter(hidden = true) Pageable pageable) {
        return departmentService.fetchAllDepartments(searchParam, pageable);
    }
}
