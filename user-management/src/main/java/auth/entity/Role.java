package auth.entity;

import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@SQLRestriction(value = "deleted = false")
@Table(name = "roles", schema = "public")
public class Role extends AbstractAuditableEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView(BaseView.RoleView.class)
    private Long id;

    @Column(name = "name")
    @JsonView({BaseView.RoleView.class, BaseView.UserDetailedView.class})
    private String name;

    @Column(name = "description")
    @JsonView({BaseView.RoleView.class, BaseView.UserDetailedView.class})
    private String description;

    @Deprecated
    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonView({BaseView.RoleView.class, BaseView.UserDetailedView.class})
    private List<Privilege> privileges = new ArrayList<>();

    @JsonView({BaseView.RoleView.class, BaseView.UserDetailedView.class})
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "role_privileges", schema = "public",
            joinColumns = @JoinColumn(name = "role_id_fk"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id_fk"))
    private List<Privilege> privilegeList = new ArrayList<>();

    public void addPrivilege(Privilege privilege) {
        if(privilegeList == null) {
            privilegeList = new ArrayList<>();
        }
        Optional<Privilege> optionalPrivilege = privilegeList.stream()
                .filter(existingPrivilege -> existingPrivilege.getId().equals(privilege.getId()))
                .findFirst();
        if (!optionalPrivilege.isPresent()) {
            privilegeList.add(privilege);
        }
    }

    public void updatePrivileges(Set<Privilege> updatedPrivilegeList) {
        if (this.privileges != null) {
            if (updatedPrivilegeList != null) {
                List<Privilege> deleteList = this.privileges.stream().filter(existingDivision -> {
                    for (Privilege privs : updatedPrivilegeList) {
                        if (ObjectUtils.nullSafeEquals(privs.getPublicId(), existingDivision.getPublicId())) {
                            return false;
                        }
                    }
                    return true;
                }).collect(Collectors.toList());

                deleteList.forEach(this.privileges::remove);

                updatedPrivilegeList.forEach(divisions -> {
                    if (divisions != null) {
                        this.privileges.add(divisions);
                    }
                });

            } else {
                this.privileges.clear();
            }
        }
    }

}
