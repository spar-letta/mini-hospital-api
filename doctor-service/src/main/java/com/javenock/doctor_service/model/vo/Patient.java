package com.javenock.doctor_service.model.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.views.BaseView;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(schema = "hospital_v1", name = "patient")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Patient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "public_id", updatable = false, insertable = false)
    @JsonView({BaseView.PatientView.class, BaseView.DoctorView.class})
    private UUID publicId;

    @Column(name = "deleted", updatable = false, insertable = false)
    private boolean deleted;

    @Column(name = "national_id")
    @JsonView({BaseView.PatientView.class, BaseView.DoctorView.class})
    private Long nationalId;

    @Column(name = "first_name")
    @JsonView({BaseView.PatientView.class, BaseView.DoctorView.class})
    private String firstName;

    @Column(name = "last_name")
    @JsonView({BaseView.PatientView.class, BaseView.DoctorView.class})
    private String lastName;

    @Column(name = "email_address")
    @JsonView({BaseView.PatientView.class, BaseView.DoctorView.class})
    private String emailAddress;
}
