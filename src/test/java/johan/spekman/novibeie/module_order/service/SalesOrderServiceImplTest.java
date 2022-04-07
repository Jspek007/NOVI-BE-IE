package johan.spekman.novibeie.module_order.service;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.validation.Valid;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;
import johan.spekman.novibeie.module_orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_orders.model.SalesOrder;
import johan.spekman.novibeie.module_orders.model.SalesOrderItem;
import johan.spekman.novibeie.module_orders.service.SalesOrderService;
import johan.spekman.novibeie.module_product.product.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
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
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class SalesOrderServiceImplTest {
    @Autowired
    private SalesOrderRepository salesOrderRepository;
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

    @Mock
    SalesOrderItemRepository salesOrderItemRepository;

    @Mock
    SalesOrder salesOrder;

    @Mock
    Product product;

    @Mock
    Customer customer;

    @Mock
    CustomerAddress customerAddress;



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

        List<SalesOrderItem> salesOrderItemList =  underTest.saveOrderItems(salesOrderItems, salesOrder, customer);

        int actual = salesOrderItemList.size();
        int expected = 1;

        assertEquals(actual, expected);
    }

    @Test
    public void shouldThrowErrorOnOrderItemsSave() {
        Product product = new Product();
        List<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);

        assertThrows(ApiRequestException.class, () -> underTest.saveOrderItems(salesOrderItems, salesOrder, customer));
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
