package johan.spekman.novibeie.module_order.service;

import javax.transaction.Transactional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_orders.repository.SalesOrderItemRepository;
import johan.spekman.novibeie.module_orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_orders.service.SalesOrderServiceImpl;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import johan.spekman.novibeie.utililies.InputValidation;

@SpringBootTest
@Transactional
public class SalesOrderServiceImplTest {
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    SalesOrderItemRepository salesOrderItemRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    InputValidation inputValidation;
    @Autowired
    CreateTimeStamp createTimeStamp;
    @Autowired
    CustomerAddressRepository customerAddressRepository;
    @Autowired
    ProductRepository productRepository;

    @MockBean
    private SalesOrderServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new SalesOrderServiceImpl(customerRepository, inputValidation, createTimeStamp,
                salesOrderItemRepository, customerAddressRepository, salesOrderRepository, productRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

}
