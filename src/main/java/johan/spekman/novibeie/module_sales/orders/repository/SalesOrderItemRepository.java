package johan.spekman.novibeie.module_sales.orders.repository;

import johan.spekman.novibeie.module_sales.orders.model.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
}
