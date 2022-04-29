package johan.spekman.novibeie.module_sales.order.service;

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
import johan.spekman.novibeie.module_sales.service.SalesResourceService;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import johan.spekman.novibeie.utililies.InputValidation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class SalesOrderServiceTest {
    @Mock
    private SalesOrderRepository salesOrderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerAddressRepository customerAddressRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SalesResourceService salesResourceService;
    @Mock
    SalesOrderItemRepository salesOrderItemRepository;

    @MockBean
    private SalesOrderServiceImpl underTest;
    @MockBean
    private InputValidation inputValidation;
    @MockBean
    private CreateTimeStamp createTimeStamp;

    private AutoCloseable autoCloseable;


    @Mock
    Customer customer;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new SalesOrderServiceImpl(customerRepository, inputValidation, createTimeStamp,
                salesOrderItemRepository, customerAddressRepository, salesOrderRepository, productRepository, salesResourceService);
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
                new Date(),
                true
        );
        SalesOrder salesOrder = new SalesOrder();
        List<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);

        when(productRepository.findBySku(anyString())).thenReturn(product);

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
        SalesOrder salesOrder = new SalesOrder();

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
        SalesOrder salesOrder = new SalesOrder();
        customerRepository.save(customer);
        List<Product> salesOrderItems = new ArrayList<>();
        salesOrderItems.add(product);
        productRepository.save(product);

        SalesOrder capturedSalesOrder = underTest.saveSalesOrder(salesOrderItems, salesOrder, customer);
        assertThat(capturedSalesOrder.getGrandTotal()).isEqualTo(product.getProductPrice());
    }

    @Test
    public void shouldCreateCompleteSalesOrder() {
        Product product = new Product(
                1L,
                "Sku_111111",
                "Test product",
                "This is a test",
                11.99,
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

        when(customerRepository.findByEmailAddress(anyString())).thenReturn(customer);
        when(productRepository.findBySku(anyString())).thenReturn(product);

        SalesOrder capturedSalesOrder = underTest.createOrder(salesOrderItemDto, bindingResult);

        assertThat(capturedSalesOrder.getOrderItemList().get(0).getOrderId().getGrandTotal()).isEqualTo(product.getProductPrice());
    }
}
