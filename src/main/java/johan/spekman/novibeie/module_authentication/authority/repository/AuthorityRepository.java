package johan.spekman.novibeie.module_authentication.authority.repository;

import johan.spekman.novibeie.module_authentication.authority.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);
}
