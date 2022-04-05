package johan.spekman.novibeie.module_orders.repository;

import johan.spekman.novibeie.module_orders.model.SalesOrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderItemRepository extends JpaRepository<SalesOrderItem, Long> {
}
