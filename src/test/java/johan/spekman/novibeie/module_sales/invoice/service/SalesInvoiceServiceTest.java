package johan.spekman.novibeie.module_sales.invoice.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_sales.invoice.model.Payment;
import johan.spekman.novibeie.module_sales.invoice.model.SalesInvoice;
import johan.spekman.novibeie.module_sales.invoice.repository.PaymentRepository;
import johan.spekman.novibeie.module_sales.invoice.repository.SalesInvoiceRepository;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_sales.service.SalesResourceService;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class SalesInvoiceServiceTest {
    @Mock
    private SalesOrderRepository salesOrderRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @MockBean
    private CreateTimeStamp createTimeStamp;
    @MockBean
    private SalesResourceService salesResourceService;
    @Mock
    private SalesInvoiceRepository salesInvoiceRepository;
    @Mock
    private CustomerAddressRepository customerAddressRepository;
    @Mock
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
                salesInvoiceRepository, salesResourceService);
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
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(1L);
        customerAddress.setCustomer(customer);
        customerAddress.setStreetName("Teststreet");
        customerAddress.setAddition("");
        customerAddress.setPostalCode("1111AA");
        customerAddress.setHouseNumber(11);

        when(customerAddressRepository.getCustomerAddressByCustomerAndType(any(), anyString())).thenReturn(customerAddress);

        SalesInvoice savedSalesInvoice = underTest.createInvoice(payment, salesOrder, customer);
        assertThat(savedSalesInvoice.getSalesOrder().getCustomer().getEmailAddress()).isEqualTo(customer.getEmailAddress());

    }

    @Test
    public void shouldCreateNewPayment() {
        SalesOrder salesOrder = new SalesOrder();
        Payment payment = new Payment();
        payment.setSalesOrder(salesOrder);
        salesOrder.setTotalItems(3);

        Payment savedPayment = underTest.createPayment(payment, salesOrder);
        assertThat(savedPayment.getSalesOrder().getTotalItems()).isEqualTo(savedPayment.getSalesOrder().getTotalItems());
    }

    @Test
    public void shouldProcessPaymentSuccessfully() {
        Customer customer = new Customer(
                1L,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setCustomerId(1L);
        customerAddress.setCustomer(customer);
        customerAddress.setStreetName("Teststreet");
        customerAddress.setAddition("");
        customerAddress.setPostalCode("1111AA");
        customerAddress.setHouseNumber(11);
        customerAddress.setCustomerAddressType(CustomerAddressType.billing);
        customerAddress.setCustomerAddressType(CustomerAddressType.shipping);
        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setEntityId(1L);
        salesOrder.setGrandTotal(11.99);
        salesOrder.setCustomer(customer);
        salesOrderRepository.save(salesOrder);
        Payment payment = new Payment();
        payment.setPaymentAmount(11.99);
        payment.setSalesOrder(salesOrder);

        when(salesOrderRepository.getById(anyLong())).thenReturn(salesOrder);
        when(customerAddressRepository.getCustomerAddressByCustomerAndType(1L, "shipping")).thenReturn(customerAddress);
        when(customerAddressRepository.getCustomerAddressByCustomerAndType(1L, "billing")).thenReturn(customerAddress);

        SalesInvoice capturedSalesInvoice = underTest.processPayment(1L, payment);
        assertThat(capturedSalesInvoice.getGrandTotal()).isEqualTo(salesOrder.getGrandTotal());
    }
}
