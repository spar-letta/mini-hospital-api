package com.javenock.member_service.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.javenock.member_service.model.dataType.Gender;
import com.javenock.member_service.model.dataType.MaritalStatus;
import com.javenock.member_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Where;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(schema = "hospital_v1", name = "patient")
@Where(clause = "deleted = false")
public class Patient extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long id;

    @Column(name = "national_id")
    @JsonView(BaseView.MemberView.class)
    private Long nationalId;

    @Column(name = "first_name")
    @JsonView(BaseView.MemberView.class)
    private String firstName;

    @Column(name = "last_name")
    @JsonView(BaseView.MemberView.class)
    private String lastName;

    @Column(name = "other_name")
    @JsonView(BaseView.MemberView.class)
    private String otherName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    @JsonView(BaseView.MemberView.class)
    private Gender gender;

    @Column(name = "email_address")
    @JsonView(BaseView.MemberView.class)
    private String emailAddress;

    @Column(name = "phone_number")
    @JsonView(BaseView.MemberView.class)
    private String phoneNumber;

    @Column(name = "date_of_birth")
    @JsonFormat(pattern = "dd/MM/yyyy", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonView(BaseView.MemberView.class)
    private LocalDate dateOfBirth;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    @JsonView(BaseView.MemberView.class)
    private MaritalStatus maritalStatus;

    @OneToMany(mappedBy = "patient")
    @JsonView(BaseView.MemberView.class)
    private Set<PatientAddress> address = new HashSet<>();

    @Transient
    @JsonView(BaseView.MemberView.class)
    private String fullName;

    @Transient
    @JsonView({BaseView.MemberView.class})
    private int age;

    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        sb.append(firstName).append(" ");
        sb.append(lastName);
        if (otherName != null)
            sb.append(" ").append(otherName);
        return sb.toString();
    }

    public int getAge() {
        if (dateOfBirth != null) {
            Period period = Period.between(dateOfBirth, LocalDate.now());
            return period.getYears();
        } else {
            return 0;
        }
    }
}
