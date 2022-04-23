package johan.spekman.novibeie.module_sales.invoice.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_sales.invoice.model.Payment;
import johan.spekman.novibeie.module_sales.invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_sales.invoice.repository.PaymentRepository;
import johan.spekman.novibeie.module_sales.invoice.repository.SalesInvoiceRepository;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_sales.service.SalesResourceService;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.text.ParseException;

@Service
@Transactional
public class SalesInvoiceServiceImpl implements SalesInvoiceService {
    private final SalesOrderRepository salesOrderRepository;
    private final PaymentRepository paymentRepository;
    private final CreateTimeStamp createTimeStamp;
    private final SalesInvoiceRepository salesInvoiceRepository;
    private final SalesResourceService salesResourceService;
    private final CustomerRepository customerRepository;

    public SalesInvoiceServiceImpl(SalesOrderRepository salesOrderRepository,
                                   PaymentRepository paymentRepository,
                                   CreateTimeStamp createTimeStamp,
                                   SalesInvoiceRepository salesInvoiceRepository,
                                   SalesResourceService salesResourceService,
                                   CustomerRepository customerRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.paymentRepository = paymentRepository;
        this.createTimeStamp = createTimeStamp;
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.salesResourceService = salesResourceService;
        this.customerRepository = customerRepository;
    }

    @Override
    public SalesInvoice processPayment(@PathVariable("orderId") Long orderId,
                                       @RequestBody Payment request) {
        try {
            SalesOrder salesOrder = salesOrderRepository.getById(orderId);
            Customer customer = customerRepository.findByEmailAddress(salesOrder.getCustomerEmail());

            if (salesOrder.getAmountPaid() != 0) {
                throw new ApiRequestException("Order is already paid.");
            }

            if (salesOrder.getGrandTotal() != request.getPaymentAmount()) {
                throw new ApiRequestException("Paid amount is not sufficient for orders grand total.");
            } else {
                createPayment(request, salesOrder);
                salesOrder.setAmountPaid(request.getPaymentAmount());
                return createInvoice(request, salesOrder, customer);
            }
        } catch (Exception exception) {
            throw new ApiRequestException("Payment could not be processed successfully: " + exception.getMessage());
        }
    }

    @Override
    public SalesInvoice createInvoice(Payment payment, SalesOrder salesOrder, Customer customer) {
        SalesInvoice salesInvoice = new SalesInvoice();
        try {
            salesResourceService.prepareCustomerData(salesInvoice, customer);
            salesInvoice.setSalesOrder(salesOrder);
            salesInvoice.setCreatedAtDate(createTimeStamp.createTimeStamp());
            salesInvoice.setGrandTotal(payment.getPaymentAmount());
            salesResourceService.prepareCustomerBillingAddress(salesInvoice, customer);
            salesResourceService.prepareCustomerShippingAddress(salesInvoice, customer);
            salesInvoiceRepository.save(salesInvoice);
            return salesInvoice;
        } catch (Exception exception) {
            throw new ApiRequestException("Invoice could not be created: " + exception.getMessage());
        }
    }

    @Override
    public Payment createPayment(Payment payment, SalesOrder salesOrder) {
        payment.setSalesOrder(salesOrder);
        payment.setPaymentAmount(salesOrder.getGrandTotal());
        paymentRepository.save(payment);
        return payment;
    }
}
