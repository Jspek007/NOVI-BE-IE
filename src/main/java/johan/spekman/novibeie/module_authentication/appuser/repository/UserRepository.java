package johan.spekman.novibeie.module_authentication.appuser.repository;

import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);
}
