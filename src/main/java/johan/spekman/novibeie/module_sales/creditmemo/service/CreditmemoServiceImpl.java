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
import johan.spekman.novibeie.module_sales.service.SalesResourceService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class CreditmemoServiceImpl implements CreditmemoService {
    private final CreditmemoRepository creditmemoRepository;
    private final ProductRepository productRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final CreditmemoItemRepository creditmemoItemRepository;
    private final CreditmemoHelper creditmemoHelper;
    private final SalesResourceService salesResourceService;

    public CreditmemoServiceImpl(CreditmemoRepository creditmemoRepository,
                                 ProductRepository productRepository,
                                 SalesOrderRepository salesOrderRepository,
                                 CreditmemoItemRepository creditmemoItemRepository,
                                 CreditmemoHelper creditmemoHelper, SalesResourceService salesResourceService) {
        this.creditmemoRepository = creditmemoRepository;
        this.productRepository = productRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.creditmemoItemRepository = creditmemoItemRepository;
        this.creditmemoHelper = creditmemoHelper;
        this.salesResourceService = salesResourceService;
    }

    @Override
    public Creditmemo processCreditmemoRequest(@PathVariable("orderId") Long orderId,
                                               @RequestBody String[] skus) throws ParseException {
        SalesOrder salesOrder = salesOrderRepository.getById(orderId);
        List<SalesOrderItem> salesOrderItems = salesOrder.getOrderItemList();
        Customer customer = salesOrder.getCustomer();
        Creditmemo creditmemo = new Creditmemo();

        Arrays.stream(skus).forEach(sku -> {
            if (!creditmemoHelper.checkIfOrderContainsRequestedItems(salesOrder, skus)) {
                throw new ApiRequestException("Product with sku: " + sku + " can not be found on the original order, " +
                        "and could not be added to the creditmemo");
            } else {
                Product product = productRepository.findBySku(sku);
                CreditmemoItem creditmemoItem = new CreditmemoItem();
                creditmemoItem.setSalesOrder(salesOrder);
                creditmemoItem.setItemPrice(product.getProductPrice());
                creditmemoItem.setSku(product.getSku());
                creditmemoItemRepository.save(creditmemoItem);
            }
        });
        double sum = 0;
        for (SalesOrderItem salesOrderItem : salesOrderItems) {
            sum += salesOrderItem.getProductPrice();
        }
        salesResourceService.prepareCustomerData(creditmemo, customer);
        salesResourceService.prepareCustomerShippingAddress(creditmemo, customer);
        salesResourceService.prepareCustomerBillingAddress(creditmemo, customer);
        creditmemo.setSalesOrder(salesOrder);
        creditmemo.setAmountRefunded(sum);
        salesOrder.setAmountRefunded(sum);
        creditmemoRepository.save(creditmemo);
        salesOrderRepository.save(salesOrder);
        return creditmemo;
    }

    @Override
    public void createCreditmemo() {

    }
}
