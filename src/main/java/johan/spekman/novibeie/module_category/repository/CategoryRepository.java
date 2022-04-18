package johan.spekman.novibeie.module_category.repository;

import johan.spekman.novibeie.module_category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
