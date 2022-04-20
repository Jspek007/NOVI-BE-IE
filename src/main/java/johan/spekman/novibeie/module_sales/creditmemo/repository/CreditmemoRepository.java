package johan.spekman.novibeie.module_sales.creditmemo.repository;

import johan.spekman.novibeie.module_sales.creditmemo.model.Creditmemo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditmemoRepository extends JpaRepository<Creditmemo, Long> {
}
