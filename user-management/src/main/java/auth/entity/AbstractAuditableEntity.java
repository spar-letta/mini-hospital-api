package auth.entity;

import auth.utils.views.BaseView;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditableEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "public_id", updatable = false)
    @JsonView({BaseView.BaseEntityListView.class, BaseView.ProfileView.class, BaseView.RoleView.class, BaseView.privilegeView.class, BaseView.internalView.class})
    private UUID publicId;

    @CreatedDate
    @Column(name = "date_created", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView({BaseView.BaseEntityListView.class, BaseView.RoleView.class})
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonView({BaseView.BaseEntityListView.class})
    private LocalDateTime dateModified;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    @JsonView({BaseView.BaseEntityDetailedView.class})
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by")
    @JsonView({BaseView.BaseEntityDetailedView.class})
    private String modifiedBy;

    @Column(name = "deleted")
    private boolean deleted;

    public void touch() {
        this.dateModified = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        this.publicId = UUID.randomUUID();
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
        this.deleted = false;
    }

    @PreUpdate
    public void preUpdate() {
        this.dateModified = LocalDateTime.ofInstant(Instant.now(), ZoneOffset.UTC);
    }
}
