package johan.spekman.novibeie.module_product.product_media.repository;

import johan.spekman.novibeie.module_product.product_media.model.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {
    @Query(value = "SELECT * FROM `NOVI-BE-IE`.product_media_gallery WHERE parent_id = ?1", nativeQuery = true)
    List<ProductMedia> findByParentId(Long parentId);
}
