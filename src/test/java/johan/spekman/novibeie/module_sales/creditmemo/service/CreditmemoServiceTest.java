package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_sales.creditmemo.model.Creditmemo;
import johan.spekman.novibeie.module_sales.creditmemo.repository.CreditmemoRepository;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrderItem;
import johan.spekman.novibeie.module_sales.orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_sales.service.SalesResourceService;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
public class CreditmemoServiceTest {

    @MockBean
    private CreditmemoServiceImpl underTest;
    private AutoCloseable autoCloseable;

    @Autowired
    private CreditmemoHelper creditmemoHelper;
    @MockBean
    private SalesResourceService resourceService;

    @Mock
    private CreditmemoRepository creditmemoRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private SalesOrderRepository salesOrderRepository;
    @Mock
    private SalesOrder salesOrder;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CreditmemoServiceImpl(creditmemoRepository, productRepository, salesOrderRepository,
                creditmemoHelper, resourceService);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void shouldCreateCreditmemo_successfully() throws ParseException {
        Customer customer = new Customer(
                1L,
                123456L,
                "Henk",
                "de",
                "Tester",
                "+31612345678",
                "Test@test.nl",
                "Test123");
        Product product = new Product();
        SalesOrder testOrder = new SalesOrder();
        List<SalesOrderItem> productList = new ArrayList<>();
        SalesOrderItem salesOrderItem = new SalesOrderItem();
        salesOrderItem.setSku("sku_123456");
        salesOrderItem.setProductPrice(11);
        productList.add(salesOrderItem);
        testOrder.setOrderItemList(productList);
        testOrder.setCustomer(customer);
        testOrder.setAmountPaid(11);
        String[] skus = {"sku_123456"};


        when(salesOrderRepository.getById(anyLong())).thenReturn(testOrder);
        when(salesOrder.getOrderItemList()).thenReturn(productList);
        when(productRepository.findBySku(anyString())).thenReturn(product);
        Creditmemo capturedCreditmemo = underTest.processCreditmemoRequest(1L, skus);

        assertThat(capturedCreditmemo.getAmountRefunded()).isEqualTo(testOrder.getAmountPaid());
    }
}
