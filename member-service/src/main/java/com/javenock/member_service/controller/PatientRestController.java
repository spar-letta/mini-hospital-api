package com.javenock.member_service.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.member_service.docs.Examples;
import com.javenock.member_service.exceptions.BadRequestRestApiException;
import com.javenock.member_service.model.Patient;
import com.javenock.member_service.model.dataType.ActionType;
import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import com.javenock.member_service.model.dto.PatientCreateRequest;
import com.javenock.member_service.service.PatientService;
import com.javenock.member_service.views.BaseView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@RequestMapping("/patients")
@RestController
@Tag(name = "patients")
public class PatientRestController {

    private PatientService patientService;

    public PatientRestController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    @Operation(summary = "Create Patient", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.PATIENT_RESPONSE)))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {@ExampleObject(name = "Patient creation request", value = Examples.CREATE_PATIENT_REQUEST)})))
    @JsonView(BaseView.MemberView.class)
    public Patient createPatient(@RequestBody @Valid PatientCreateRequest patientCreateRequest) throws BadRequestRestApiException {
        return patientService.createPatient(patientCreateRequest);
    }

    @GetMapping("/{publicId}")
    @Operation(summary = "Get Single Patient", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.SINGLE_PATIENT_RESPONSE)))})
    @JsonView(BaseView.MemberView.class)
    public Patient getAllPatients(@PathVariable UUID publicId) throws BadRequestRestApiException {
        return patientService.getPatient(publicId);
    }

    @GetMapping
    @Operation(summary = "Get Single Patient", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.PATIENTS_RESPONSE)))})
    @JsonView(BaseView.MemberView.class)
    public Page<Patient> getAllPatients(
            @RequestParam(name = "nationalId", required = false) Long nationalId,
            @RequestParam(name = "searchParam", required = false) String searchParam,
            @RequestParam(name = "gender", required = false) Gender gender,
            @RequestParam(name = "maritalStatus", required = false) MaritalStatus maritalStatus,
            @RequestParam(name = "age", required = false) Integer age,
            @RequestParam(value = "dob", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") @Parameter(example = "dd-MM-yyyy") LocalDate dob,
            @Parameter(hidden = true) Pageable pageable) {
        return patientService.getPatients(nationalId, searchParam, gender, maritalStatus, age, dob, pageable);
    }

    @PutMapping("/{publicId}")
    @Operation(summary = "Update Patient", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = "application/json", schema = @Schema(example = Examples.UPDATE_PATIENT_RESPONSE)))
    }, requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true, content = @Content(examples = {
            @ExampleObject(name = "Basic Details Action request", value = Examples.UPDATE_PATIENT_BASIC_DETAILS_REQUEST),
            @ExampleObject(name = "Address Details Action request", value = Examples.UPDATE_PATIENT_ADDRESS_DETAILS_REQUEST),
    })))
    @JsonView(BaseView.MemberView.class)
    public Patient updatePatient(
            @PathVariable UUID publicId,
            @RequestParam(name = "actionType") ActionType actionType,
            @RequestBody @Valid PatientCreateRequest patientCreateRequest) throws BadRequestRestApiException {
        return patientService.updatePatientDetails(publicId, actionType, patientCreateRequest);
    }
}
