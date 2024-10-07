package com.javenock.member_service.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PatientCreateRequest {
    private Long nationalId;

    @NotNull
    private String firstName;

    private String lastName;

    private String otherName;

    @NotNull
    private Gender gender;

    private String emailAddress;

    private String phoneNumber;

    @JsonFormat(pattern = "dd-MM-yyyy", shape = JsonFormat.Shape.STRING)
    private LocalDate dateOfBirth;

    private MaritalStatus maritalStatus;

    private PatientAddressDto patientAddress;
}
