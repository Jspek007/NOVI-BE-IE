package johan.spekman.novibeie.module_sales.invoice.repository;

import johan.spekman.novibeie.module_sales.invoice.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
}
