package johan.spekman.novibeie.module_invoice.repository;

import johan.spekman.novibeie.module_invoice.model.SalesInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesInvoiceRepository extends JpaRepository<SalesInvoice, Long> {
}
