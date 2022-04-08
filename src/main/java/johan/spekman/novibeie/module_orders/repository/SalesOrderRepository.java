package johan.spekman.novibeie.module_orders.repository;

import johan.spekman.novibeie.module_orders.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findByCustomerId(Long customerId);
}
