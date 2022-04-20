package johan.spekman.novibeie.module_sales.invoice.repository;

import johan.spekman.novibeie.module_sales.invoice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
