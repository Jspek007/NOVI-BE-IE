package johan.spekman.novibeie.module_authentication.role.repository;

import johan.spekman.novibeie.module_authentication.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
