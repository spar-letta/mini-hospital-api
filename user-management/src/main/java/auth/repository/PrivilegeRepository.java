package auth.repository;

import auth.entity.Privilege;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Optional<Privilege> findByNameIgnoreCaseAndDeletedIsFalse(String name);

    @Query("select p from Privilege p where p.deleted = false")
    List<Privilege> findAllPrivileges();

    Privilege findByPublicId(UUID itemId);
}
