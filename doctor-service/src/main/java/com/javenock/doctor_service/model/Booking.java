package com.javenock.doctor_service.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.model.dataType.BookingStatus;
import com.javenock.doctor_service.model.dataType.VisitType;
import com.javenock.doctor_service.model.vo.Patient;
import com.javenock.doctor_service.model.vo.User;
import com.javenock.doctor_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings", schema = "hospital_v1")
@SQLRestriction(value = "deleted = false")
public class Booking extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "visit_type")
    @JsonView(BaseView.DoctorView.class)
    private VisitType visitType;

    @ElementCollection
    @CollectionTable(name = "booking_reasons", schema = "hospital_v1",
            joinColumns = @JoinColumn(name = "booking_id", referencedColumnName = "id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"booking_id", "reason"}))
    @Column(name = "reason")
    @JsonView(BaseView.DoctorView.class)
    private List<String> reason = new ArrayList<>();

    @Column(name = "visit_date")
    @JsonView(BaseView.DoctorView.class)
    private LocalDateTime visitDate;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonView(BaseView.DoctorView.class)
    private Department department;

    @ManyToMany
    @JoinTable(name = "book_doctor", schema = "hospital_v1",
    joinColumns = {@JoinColumn(name = "booking_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "doctor_id", referencedColumnName = "id")},
    uniqueConstraints = @UniqueConstraint(columnNames = {"booking_id", "doctor_id"}))
    @JsonView(BaseView.DoctorView.class)
    private List<User> doctor = new ArrayList<>();

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @JsonView(BaseView.DoctorView.class)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "patient_booking_id", referencedColumnName = "patient_id")
    @JsonView(BaseView.DoctorView.class)
    private Patient patient;

    @Column(name = "booking_number")
    @JsonView(BaseView.DoctorView.class)
    private String bookingNumber;
}
