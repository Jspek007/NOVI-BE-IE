package johan.spekman.novibeie.module_orders.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_orders.dto.SalesOrderItemDto;
import johan.spekman.novibeie.module_orders.model.SalesOrder;
import johan.spekman.novibeie.module_orders.model.SalesOrderItem;
import johan.spekman.novibeie.module_orders.repository.SalesOrderItemRepository;
import johan.spekman.novibeie.module_orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {

    private final CustomerRepository customerRepository;
    private final InputValidation inputValidation;
    private final CreateTimeStamp createTimeStamp;
    private final SalesOrderItemRepository salesOrderItemRepository;
    private final CustomerAddressRepository customerAddressRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final ProductRepository productRepository;

    public SalesOrderServiceImpl(CustomerRepository customerRepository,
            InputValidation inputValidation,
            CreateTimeStamp createTimeStamp,
            SalesOrderItemRepository salesOrderItemRepository,
            CustomerAddressRepository customerAddressRepository,
            SalesOrderRepository salesOrderRepository,
            ProductRepository productRepository) {
        this.customerRepository = customerRepository;
        this.inputValidation = inputValidation;
        this.createTimeStamp = createTimeStamp;
        this.salesOrderItemRepository = salesOrderItemRepository;
        this.customerAddressRepository = customerAddressRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.productRepository = productRepository;
    }

    public List<SalesOrderItem> saveOrderItems(List<Product> products, SalesOrder salesOrder,
            Customer existingCustomer) {
        List<SalesOrderItem> salesOrderItems = new ArrayList<>();
        for (Product product : products) {
            SalesOrderItem salesOrderItem = new SalesOrderItem();
            Product orderItem = productRepository.findBySku(product.getSku());
            if (orderItem == null) {
                throw new ApiRequestException("No product found with SKU: " + product.getSku());
            }
            salesOrderItem.setOrderItem(productRepository.findBySku(orderItem.getSku()));
            salesOrderItem.setProductPrice(orderItem.getProductPrice());
            salesOrderItem.setCustomer(existingCustomer);
            salesOrderItem.setOrderId(salesOrder);
            salesOrderItemRepository.save(salesOrderItem);
            salesOrderItems.add(salesOrderItem);
        }
        return salesOrderItems;
    }

    public void saveSalesOrder(List<Product> products, SalesOrder salesOrder, Customer existingCustomer) {
        String shipping = "shipping";
        String billing = "billing";
        try {
            double sum = 0;
            for (Product product : products) {
                sum += product.getProductPrice();
            }
            salesOrder.setGrandTotal(sum);
            salesOrder.setTotalItems(products.size());
            salesOrder.setShippingAddress(
                    customerAddressRepository.getCustomerAddressByCustomerAndType(existingCustomer.getId(),
                            shipping));
            salesOrder.setBillingAddress(
                    customerAddressRepository.getCustomerAddressByCustomerAndType(existingCustomer.getId(),
                            billing));
            salesOrder.setCreatedAtDate(createTimeStamp.createTimeStamp());
            salesOrder.setCustomer(existingCustomer);
            salesOrderRepository.save(salesOrder);
        } catch (Exception exception) {
            throw new ApiRequestException("Sales order could not be saved: " + exception.getMessage());
        }
    }

    @Override
    public void createOrder(@Valid @RequestBody SalesOrderItemDto salesOrderItemDto,
            BindingResult bindingResult) {
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException("Invalid input" + bindingResult.getFieldErrors());
        }
        if (customerRepository.findByEmailAddress(salesOrderItemDto.getCustomer().getEmailAddress()) == null) {
            throw new ApiRequestException(
                    "No customer found with this e-mail: " + salesOrderItemDto.getCustomer().getEmailAddress());
        }
        SalesOrder salesOrder = new SalesOrder();
        salesOrderRepository.save(salesOrder);

        Customer existingCustomer = customerRepository
                .findByEmailAddress(salesOrderItemDto.getCustomer().getEmailAddress());
        List<Product> products = salesOrderItemDto.getProducts();

        try {
            saveOrderItems(products, salesOrder, existingCustomer);
        } catch (Exception exception) {
            throw new ApiRequestException("Could not save order items: " + exception.getMessage());
        }

        try {
            saveSalesOrder(products, salesOrder, existingCustomer);
        } catch (Exception exception) {
            throw new ApiRequestException("Could not save sales order: " + exception.getMessage());
        }
    }

    @Override
    public List<SalesOrder> getOrdersByCustomerEmail(String email) {
        try {
            Long customerId = customerRepository.findByEmailAddress(email).getId();
            return salesOrderRepository.findByCustomerId(customerId);
        } catch (Exception exception) {
            throw new ApiRequestException("No existing customer found with: " + email);
        }
    }
}
