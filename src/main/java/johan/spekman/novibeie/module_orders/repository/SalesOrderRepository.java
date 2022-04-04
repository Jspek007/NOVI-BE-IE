package johan.spekman.novibeie.module_orders.repository;

import johan.spekman.novibeie.module_orders.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
}
