package uz.pdp.springsecurityrealrolepermission.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.springsecurityrealrolepermission.entity.Users;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    boolean existsByUsername(String username);
    Optional<Users> findByUsernameAndEmailCode(String username, Integer emailCode);

    Optional<Users> findByUsername(String username);
}
