package johan.spekman.novibeie.module_sales.invoice.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_sales.invoice.model.Payment;
import johan.spekman.novibeie.module_sales.invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.List;

public interface SalesInvoiceService {
    List<SalesInvoice> getAllInvoices();

    SalesInvoice processPayment(@PathVariable("orderId") Long orderId,
                                @RequestBody Payment payment);

    SalesInvoice createInvoice(Payment payment, SalesOrder salesOrder, Customer customer) throws ParseException;

    Payment createPayment(Payment payment, SalesOrder salesOrder);
}
