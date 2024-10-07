package com.javenock.member_service.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientAddressDto {
    private String postalCode;
    private String physicalAddress;
    private String country;
}
