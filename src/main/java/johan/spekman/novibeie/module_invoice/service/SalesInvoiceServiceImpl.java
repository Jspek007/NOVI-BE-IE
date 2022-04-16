package johan.spekman.novibeie.module_invoice.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_invoice.model.Payment;
import johan.spekman.novibeie.module_invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_invoice.repository.PaymentRepository;
import johan.spekman.novibeie.module_invoice.repository.SalesInvoiceRepository;
import johan.spekman.novibeie.module_orders.model.SalesOrder;
import johan.spekman.novibeie.module_orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private final CustomerAddressRepository customerAddressRepository;

    public SalesInvoiceServiceImpl(SalesOrderRepository salesOrderRepository,
                                   PaymentRepository paymentRepository,
                                   CreateTimeStamp createTimeStamp,
                                   SalesInvoiceRepository salesInvoiceRepository,
                                   CustomerAddressRepository customerAddressRepository) {
        this.salesOrderRepository = salesOrderRepository;
        this.paymentRepository = paymentRepository;
        this.createTimeStamp = createTimeStamp;
        this.salesInvoiceRepository = salesInvoiceRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public ResponseEntity<Object> processPayment(@PathVariable("orderId") Long orderId,
                                                 @RequestBody Payment request) {
        try {
            SalesOrder salesOrder = salesOrderRepository.getById(orderId);
            Customer customer = salesOrder.getCustomer();

            if (salesOrder.getAmountPaid() != 0) {
                throw new ApiRequestException("Order is already paid.");
            }

            if (salesOrder.getGrandTotal() != request.getPaymentAmount()) {
                throw new ApiRequestException("Paid amount is not sufficient for orders grand total.");
            } else {
                createPayment(request, salesOrder);
                salesOrder.setAmountPaid(request.getPaymentAmount());
                return new ResponseEntity<>(createInvoice(request, salesOrder, customer), HttpStatus.CREATED);
            }
        } catch (Exception exception) {
            throw new ApiRequestException("Payment could not be processed successfully: " + exception.getMessage());
        }
    }

    @Override
    public SalesInvoice createInvoice(Payment payment, SalesOrder salesOrder, Customer customer) throws ParseException {
        SalesInvoice salesInvoice = new SalesInvoice();
        salesInvoice.setCustomer(salesOrder.getCustomer());
        salesInvoice.setSalesOrder(salesOrder);
        salesInvoice.setCreatedAtDate(createTimeStamp.createTimeStamp());
        salesInvoice.setGrandTotal(payment.getPaymentAmount());
        salesInvoice.setBillingAddress(customerAddressRepository.getCustomerAddressByCustomerAndType(
                        salesOrder.getCustomer().getId(), "billing"
                )
        );
        salesInvoice.setShippingAddress(customerAddressRepository.getCustomerAddressByCustomerAndType(
                        salesOrder.getCustomer().getId(), "shipping"
                )
        );
        return salesInvoiceRepository.save(salesInvoice);
    }

    @Override
    public void createPayment(Payment payment, SalesOrder salesOrder) {
        payment.setSalesOrder(salesOrder);
        payment.setCustomer(salesOrder.getCustomer());
        payment.setPaymentAmount(salesOrder.getGrandTotal());
        paymentRepository.save(payment);
    }
}
