package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_sales.creditmemo.model.Creditmemo;
import johan.spekman.novibeie.module_sales.creditmemo.model.CreditmemoItem;
import johan.spekman.novibeie.module_sales.creditmemo.repository.CreditmemoItemRepository;
import johan.spekman.novibeie.module_sales.creditmemo.repository.CreditmemoRepository;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrderItem;
import johan.spekman.novibeie.module_sales.orders.repository.SalesOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CreditmemoServiceImpl implements CreditmemoService {
    private final CreditmemoRepository creditmemoRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final CreditmemoItemRepository creditmemoItemRepository;
    private final CreditmemoHelper creditmemoHelper;

    public CreditmemoServiceImpl(CreditmemoRepository creditmemoRepository,
                                 ProductRepository productRepository,
                                 CustomerRepository customerRepository,
                                 SalesOrderRepository salesOrderRepository,
                                 CreditmemoItemRepository creditmemoItemRepository,
                                 CreditmemoHelper creditmemoHelper) {
        this.creditmemoRepository = creditmemoRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.creditmemoItemRepository = creditmemoItemRepository;
        this.creditmemoHelper = creditmemoHelper;
    }

    @Override
    public Creditmemo processCreditmemoRequest(@PathVariable("orderId") Long orderId,
                                               @RequestBody String[] skus) {
        SalesOrder salesOrder = salesOrderRepository.getById(orderId);
        Customer customer = salesOrder.getCustomer();
        Creditmemo creditmemo = new Creditmemo();

        Arrays.stream(skus).forEach(sku -> {
            if (!creditmemoHelper.checkIfOrderContainsRequestedItems(salesOrder, skus)) {
                throw new ApiRequestException("Product with sku: " + sku + " can not be found on the original order, " +
                        "and could not be added to the creditmemo");
            } else {
                Product product = productRepository.findBySku(sku);
                CreditmemoItem creditmemoItem = new CreditmemoItem();
                creditmemoItem.setOrderId(salesOrder);
                creditmemoItem.setProductPrice(product.getProductPrice());
                creditmemoItem.setSku(product.getSku());
                creditmemoItemRepository.save(creditmemoItem);
                double sum = 0;
                sum += product.getProductPrice();
                creditmemo.setGrandTotal(sum);
            }
        });
        creditmemo.setCustomer(customer);
        creditmemo.setSalesOrder(salesOrder);
        creditmemoRepository.save(creditmemo);
        return creditmemo;
    }

    @Override
    public void createCreditmemo() {

    }
}
