package auth.repository;

import auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPublicId(UUID publicId);

    Optional<User> findByUserNameIgnoreCase(String userName);

}
