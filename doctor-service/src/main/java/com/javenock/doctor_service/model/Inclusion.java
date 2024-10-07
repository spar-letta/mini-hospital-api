package com.javenock.doctor_service.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.views.BaseView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "inclusion", schema = "hospital_v1")
public class Inclusion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @JsonView({BaseView.InclusionView.class, BaseView.DepartmentView.class})
    private String name;

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;
}
