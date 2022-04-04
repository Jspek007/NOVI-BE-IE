package johan.spekman.novibeie.module_catalog.repository;

import johan.spekman.novibeie.module_catalog.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
