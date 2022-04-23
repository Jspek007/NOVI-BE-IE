package johan.spekman.novibeie.module_sales.orders.repository;

import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    List<SalesOrder> findByCustomerEmail(String email);
}
