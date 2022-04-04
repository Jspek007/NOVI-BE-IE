package johan.spekman.novibeie.module_orders.service;

import johan.spekman.novibeie.exceptions.ApiRequestException;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.repository.CustomerRepository;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_customer_address.service.CustomerAddressService;
import johan.spekman.novibeie.module_orders.dto.SalesOrderDto;
import johan.spekman.novibeie.module_orders.model.SalesOrder;
import johan.spekman.novibeie.module_orders.repository.SalesOrderRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_product.product.repository.ProductRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import johan.spekman.novibeie.utililies.InputValidation;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.ArrayList;

@Service
public class SalesOrderServiceImpl implements SalesOrderService {

    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final InputValidation inputValidation;
    private final CreateTimeStamp createTimeStamp;
    private final SalesOrderRepository salesOrderRepository;
    private final CustomerAddressRepository customerAddressRepository;

    public SalesOrderServiceImpl(CustomerRepository customerRepository,
                                 ProductRepository productRepository,
                                 InputValidation inputValidation,
                                 CreateTimeStamp createTimeStamp,
                                 SalesOrderRepository salesOrderRepository,
                                 CustomerAddressRepository customerAddressRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.inputValidation = inputValidation;
        this.createTimeStamp = createTimeStamp;
        this.salesOrderRepository = salesOrderRepository;
        this.customerAddressRepository = customerAddressRepository;
    }

    @Override
    public void createOrder(@Valid @RequestBody SalesOrderDto salesOrderDto,
                            BindingResult bindingResult) throws ParseException {
        if (inputValidation.validate(bindingResult) != null) {
            throw new ApiRequestException("Invalid input" + bindingResult.getFieldErrors());
        }
        Customer existingCustomer = customerRepository.findByEmailAddress(salesOrderDto.getCustomer().getEmailAddress());
        SalesOrder salesOrder = new SalesOrder();
        ArrayList<Product> products = salesOrderDto.getProducts();

        for (Product product : products) {
            String orderItemSku = product.getSku();
            Product orderItem = productRepository.findBySku(orderItemSku);
            salesOrder.setOrderItem(orderItem);
            salesOrder.setProductPrice(orderItem.getProductPrice());
        }
        double sum = 0;
        for (Product product : products) {
            sum += product.getProductPrice();
        }
        salesOrder.setGrandTotal(sum);

        salesOrder.setCustomer(existingCustomer);
        salesOrder.setCreatedAtDate(createTimeStamp.CreateTimeStamp());
        salesOrder.setShippingAddress(customerAddressRepository.getCustomerAddressByCustomerAndType(existingCustomer.getId(),
                "shipping"));
        salesOrder.setBillingAddress(customerAddressRepository.getCustomerAddressByCustomerAndType(existingCustomer.getId(),
                "billing"));
        salesOrderRepository.save(salesOrder);

    }
}
