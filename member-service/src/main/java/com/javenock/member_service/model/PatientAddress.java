package com.javenock.member_service.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.member_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(schema = "hospital_v1", name = "patient_address")
//@Where(clause = "deleted = false")
public class PatientAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "postal_code")
    @JsonView(BaseView.MemberView.class)
    private String postalCode;

    @Column(name = "physical_address")
    @JsonView(BaseView.MemberView.class)
    private String physicalAddress;

    @Column(name = "country")
    @JsonView(BaseView.MemberView.class)
    private String country;

    @ManyToOne
    @JoinColumn(name = "address_patient_id", referencedColumnName = "patient_id")
    private Patient patient;

}
