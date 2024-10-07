package com.javenock.doctor_service.utils.feign;

import com.javenock.doctor_service.model.vo.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Optional;

@FeignClient(name = "member-service")
public interface PatientServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/internal/patient/{patientId}")
    Optional<Patient> fetchUserPatient(@PathVariable("patientId") String patientId);
}
