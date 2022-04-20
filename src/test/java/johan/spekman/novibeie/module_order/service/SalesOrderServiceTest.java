package johan.spekman.novibeie.module_order.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_sales.orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrderItem;
import johan.spekman.novibeie.module_sales.orders.repository.SalesOrderItemRepository;
import johan.spekman.novibeie.module_sales.orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_sales.orders.service.SalesOrderServiceImpl;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import johan.spekman.novibeie.utililies.InputValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class SalesOrderServiceTest {
    @Autowired
    private SalesOrderRepository salesOrderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InputValidation inputValidation;
    @Autowired
    private CreateTimeStamp createTimeStamp;
    @Autowired
    private CustomerAddressRepository customerAddressRepository;
    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private SalesOrderServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @Mock
    SalesOrderItemRepository salesOrderItemRepository;

    @Mock
    SalesOrder salesOrder;

    @Mock
    Customer customer;


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

    @Test
    public void shouldSaveOrderItems() {
        Product product = new Product(
                1L,
                "Sku_111111",
                "Test product",
                "This is a test",
                11.99,
                21,
                new Date(),
                true
        );
        List<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);
        productRepository.save(product);

        List<SalesOrderItem> salesOrderItemList = underTest.saveOrderItems(salesOrderItems, salesOrder);

        int actual = salesOrderItemList.size();
        int expected = 1;

        assertEquals(actual, expected);
    }

    @Test
    public void shouldThrowErrorOnOrderItemsSave() {
        Product product = new Product();
        List<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);

        assertThrows(ApiRequestException.class, () -> underTest.saveOrderItems(salesOrderItems, salesOrder));
    }

    @Test
    public void shouldSaveSalesOrder() {
        Product product = new Product(
                1L,
                "Sku_111111",
                "Test product",
                "This is a test",
                11.99,
                21,
                new Date(),
                true
        );
        Customer customer = new Customer(
                1L,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        customerRepository.save(customer);
        List<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);
        productRepository.save(product);

        underTest.saveSalesOrder(salesOrderItems, salesOrder, customer);

        int expected = 1;
        int actual = salesOrderRepository.findAll().size();

        assertEquals(expected, actual);
    }

    @Test
    public void shouldCreateCompleteSalesOrder() {
        Product product = new Product(
                1L,
                "Sku_111111",
                "Test product",
                "This is a test",
                11.99,
                21,
                new Date(),
                true
        );
        ArrayList<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);
        productRepository.save(product);
        Customer customer = new Customer(
                1L,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        customerRepository.save(customer);

        SalesOrderItemDto salesOrderItemDto = new SalesOrderItemDto(
                customer,
                salesOrderItems
        );
        BindingResult bindingResult = new BindException(salesOrderItemDto, "salesOrder");

        underTest.createOrder(salesOrderItemDto, bindingResult);
        int expected = 1;
        int actual = salesOrderRepository.findAll().size();

        assertEquals(expected, actual);
    }
}
