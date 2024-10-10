package com.javenock.notification_service.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.notification_service.docs.Examples;
import com.javenock.notification_service.model.AuditForm;
import com.javenock.notification_service.model.dto.EntityType;
import com.javenock.notification_service.service.AuditService;
import com.javenock.notification_service.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/auditReport")
@RequiredArgsConstructor
public class AuditReportRestController {

    private final AuditService auditService;

    @PreAuthorize("hasAuthority('READ_AUDIT_RECORDS')")
    @GetMapping
    @JsonView(BaseView.AuditView.class)
    @Operation(summary = "Get Audit Report", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.AUDIT_REPORT_RESPONSE)))})
    public Page<AuditForm> retrieveAllAudits(@RequestParam(name = "entityType", required = false) EntityType entityType,
                                             @RequestParam(name = "actionedById", required = false) UUID actionedById,
                                             @RequestParam(name = "action", required = false) String action,
                                             @RequestParam(name = "eventDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") @Parameter(example = "dd-MM-yyyy") LocalDate eventDate,
                                             Pageable pageable) {
        return auditService.fetchAuditReport(actionedById, entityType, action, eventDate, pageable);
    }

}
