package uz.pdp.springsecurityrealrolepermission.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.springsecurityrealrolepermission.entity.Role;

import java.util.Optional;

@Repository
public interface RoleRepositry extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
