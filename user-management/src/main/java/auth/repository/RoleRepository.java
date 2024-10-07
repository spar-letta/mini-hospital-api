package auth.repository;

import auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNameIgnoreCase(String name);

    @Query("select r from Role r where r.deleted = false")
    List<Role> findAllAndDeletedIsFalse();

    Optional<Role> findByPublicIdAndDeletedIsFalse(UUID rolePublicId);
}
