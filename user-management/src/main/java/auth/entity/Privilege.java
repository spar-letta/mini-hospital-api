package auth.entity;

import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "privileges", schema = "public")
public class Privilege extends AbstractAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonView({BaseView.RoleView.class, BaseView.privilegeView.class})
    private Long id;

    @Column(name = "name")
    @JsonView({BaseView.RoleView.class, BaseView.UserDetailedView.class, BaseView.privilegeView.class})
    private String name;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private Role role;
}
