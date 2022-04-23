package johan.spekman.novibeie.module_sales.creditmemo.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.module_sales.creditmemo.model.Creditmemo;
import johan.spekman.novibeie.module_sales.creditmemo.model.CreditmemoItem;
import johan.spekman.novibeie.module_sales.creditmemo.repository.CreditmemoRepository;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;
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
    private final CreditmemoHelper creditmemoHelper;
    private final SalesResourceService salesResourceService;
    private final CustomerRepository customerRepository;

    public CreditmemoServiceImpl(CreditmemoRepository creditmemoRepository,
                                 ProductRepository productRepository,
                                 SalesOrderRepository salesOrderRepository,
                                 CreditmemoHelper creditmemoHelper,
                                 SalesResourceService salesResourceService,
                                 CustomerRepository customerRepository) {
        this.creditmemoRepository = creditmemoRepository;
        this.productRepository = productRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.creditmemoHelper = creditmemoHelper;
        this.salesResourceService = salesResourceService;
        this.customerRepository = customerRepository;
    }

    @Override
    public Creditmemo processCreditmemoRequest(@PathVariable("orderId") Long orderId,
                                               @RequestBody String[] skus) throws ParseException {
        SalesOrder salesOrder = salesOrderRepository.getById(orderId);
        List<CreditmemoItem> creditmemoItemList = new ArrayList<>();
        Customer customer = customerRepository.findByEmailAddress(salesOrder.getCustomerEmail());
        Creditmemo creditmemo = new Creditmemo();

        if (salesOrder.getAmountRefunded() == salesOrder.getAmountPaid()) {
            throw new ApiRequestException("This order has already been refunded.");
        }

        Arrays.stream(skus).forEach(sku -> {
            if (!creditmemoHelper.checkIfOrderContainsRequestedItems(salesOrder, skus)) {
                throw new ApiRequestException("Product with sku: " + sku + " can not be found on the original order, " +
                        "and could not be added to the creditmemo");
            } else {
                Product product = productRepository.findBySku(sku);
                CreditmemoItem creditmemoItem = new CreditmemoItem();
                salesResourceService.prepareSalesResourceItemInformation(creditmemoItem, product);
                creditmemoItemList.add(creditmemoItem);
            }
        });
        double sum = 0;
        for (CreditmemoItem creditmemoItem : creditmemoItemList) {
            sum += creditmemoItem .getProductPrice();
        }
        salesResourceService.prepareCustomerData(creditmemo, customer);
        salesResourceService.prepareCustomerShippingAddress(creditmemo, customer);
        salesResourceService.prepareCustomerBillingAddress(creditmemo, customer);
        creditmemo.setSalesOrder(salesOrder);
        creditmemo.setCreditmemoItemList(creditmemoItemList);
        creditmemo.setAmountRefunded((sum + salesOrder.getAmountRefunded()));
        salesOrder.setAmountRefunded(sum);
        creditmemoRepository.save(creditmemo);
        salesOrderRepository.save(salesOrder);
        return creditmemo;
    }
}
