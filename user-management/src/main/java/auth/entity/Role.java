package auth.entity;

import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @Setter(AccessLevel.NONE)
    @JsonView({BaseView.RoleView.class, BaseView.UserDetailedView.class})
    private List<Privilege> privileges = new ArrayList<>();
}
