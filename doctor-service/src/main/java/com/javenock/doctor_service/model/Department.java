package com.javenock.doctor_service.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.doctor_service.views.BaseView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments", schema = "hospital_v1")
public class Department extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @JsonView({BaseView.DepartmentView.class, BaseView.DoctorView.class})
    private String name;

    @Column(name = "description")
    @JsonView({BaseView.DepartmentView.class, BaseView.DoctorView.class})
    private String description;

    @OneToMany(mappedBy = "department")
    @JsonView(BaseView.DepartmentView.class)
    private Set<Inclusion> inclusions = new HashSet<>();

    @Transient
    private List<String> inclusionName= new ArrayList();

    public Department(String name, String description, List<String> inclusionName) {
        this.name = name;
        this.description = description;
        this.inclusionName = inclusionName;
    }
}
