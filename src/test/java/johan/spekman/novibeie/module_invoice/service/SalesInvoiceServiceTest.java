package johan.spekman.novibeie.module_invoice.service;

import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_invoice.repository.PaymentRepository;
import johan.spekman.novibeie.module_invoice.repository.SalesInvoiceRepository;
import johan.spekman.novibeie.module_orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import johan.spekman.novibeie.utililies.InputValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.transaction.Transactional;

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

    @MockBean
    private SalesInvoiceServiceImpl underTest;
    private AutoCloseable autoCloseable;

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
    public void shouldCreateNewInvoice() {

    }

    @Test
    public void shouldCreateNewPayment() {

    }

    @Test
    public void shouldProcessPaymentSuccessfully() {

    }

}
