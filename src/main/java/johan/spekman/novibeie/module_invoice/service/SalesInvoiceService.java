package johan.spekman.novibeie.module_invoice.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_invoice.model.Payment;
import johan.spekman.novibeie.module_invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_orders.model.SalesOrder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;

public interface SalesInvoiceService {
    SalesInvoice processPayment(@PathVariable("orderId") Long orderId,
                                @RequestBody Payment payment);

    SalesInvoice createInvoice(Payment payment, SalesOrder salesOrder, Customer customer) throws ParseException;

    Payment createPayment(Payment payment, SalesOrder salesOrder);

    void prepareCustomer(SalesOrder salesOrder, SalesInvoice salesInvoice);

    void prepareShippingAddress(SalesOrder salesOrder, SalesInvoice salesInvoice);
    void prepareBillingAddress(SalesOrder salesOrder, SalesInvoice salesInvoice);
}
