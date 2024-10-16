package com.javenock.member_service.model.vo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.javenock.member_service.views.BaseView;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@Entity
@ToString
@Table(schema = "public", name = "users")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private Long id;

    @Column(name = "public_id", updatable = false, insertable = false)
    @JsonView({BaseView.UserView.class, BaseView.MemberView.class})
    private UUID publicId;

    @Column(name = "deleted", updatable = false, insertable = false)
    private boolean deleted;

    @Column(name = "first_name", updatable = false, insertable = false)
    @JsonView({BaseView.UserView.class, BaseView.MemberView.class})
    private String firstName;

    @Column(name = "contact_email", updatable = false, insertable = false)
    private String contactEmail;

    @Column(name = "user_name", updatable = false, insertable = false)
    private String userName;
}
