package johan.spekman.novibeie.module_invoice.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_invoice.model.Payment;
import johan.spekman.novibeie.module_invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_invoice.repository.PaymentRepository;
import johan.spekman.novibeie.module_invoice.repository.SalesInvoiceRepository;
import johan.spekman.novibeie.module_orders.model.SalesOrder;
import johan.spekman.novibeie.module_orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class SalesInvoiceServiceTest {
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private CreateTimeStamp createTimeStamp;
    @Autowired
    private SalesInvoiceRepository salesInvoiceRepository;
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private SalesInvoiceServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @Mock
    Payment payment;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new SalesInvoiceServiceImpl(salesOrderRepository, paymentRepository, createTimeStamp,
                salesInvoiceRepository, customerAddressRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void shouldCreateNewInvoice() throws ParseException {
        Customer customer = new Customer(
                1L,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setCustomer(customer);
        customerRepository.save(customer);

        underTest.createInvoice(payment, salesOrder, customer);
        SalesInvoice savedSalesInvoice = salesInvoiceRepository.getById(1L);
        assertThat(savedSalesInvoice.getCustomer().getEmailAddress()).isEqualTo(customer.getEmailAddress());

    }

    @Test
    public void shouldCreateNewPayment() {
        SalesOrder salesOrder = new SalesOrder();
        Payment payment = new Payment();
        payment.setSalesOrder(salesOrder);
        salesOrder.setTotalItems(3);

        underTest.createPayment(payment, salesOrder);
        Payment savedPayment = paymentRepository.getById(1L);
        assertThat(savedPayment.getSalesOrder().getTotalItems()).isEqualTo(savedPayment.getSalesOrder().getTotalItems());
    }

    @Test
    public void shouldProcessPaymentSuccessfully() {

    }

}
