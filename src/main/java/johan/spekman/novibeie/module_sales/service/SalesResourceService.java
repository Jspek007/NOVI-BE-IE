package johan.spekman.novibeie.module_sales.service;

import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_customer_address.repository.CustomerAddressRepository;
import johan.spekman.novibeie.module_product.product.model.Product;
import johan.spekman.novibeie.module_sales.SalesResource;
import johan.spekman.novibeie.module_sales.SalesResourceItem;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public record SalesResourceService(CustomerAddressRepository customerAddressRepository, CreateTimeStamp createTimeStamp) {

    public void prepareCustomerData(SalesResource salesResource, Customer customer) throws ParseException {
        salesResource.setCustomerId(customer.getCustomerId());
        salesResource.setCustomerFirstName(customer.getFirstName());
        salesResource.setCustomerInsertion(customer.getInsertion());
        salesResource.setCustomerLastName(customer.getLastName());
        salesResource.setCustomerEmail(customer.getEmailAddress());
        salesResource.setCustomerPhoneNumber(customer.getPhoneNumber());
        salesResource.setCreatedAtDate(createTimeStamp.createTimeStamp());
    }

    public void prepareCustomerShippingAddress(SalesResource salesResource, Customer customer) {
        CustomerAddress customerAddress = customerAddressRepository.getCustomerAddressByCustomerAndType(
                customer.getId(), "shipping"
        );
        salesResource.setShippingAddressStreet(customerAddress.getStreetName());
        salesResource.setShippingAddressHouseNumber(customerAddress.getHouseNumber());
        salesResource.setShippingAddressAddition(customerAddress.getAddition());
        salesResource.setShippingAddressPostalCode(customerAddress.getPostalCode());
        salesResource.setShippingAddressCity(customerAddress.getCity());
    }

    public void prepareCustomerBillingAddress(SalesResource salesResource, Customer customer) {
        CustomerAddress customerAddress = customerAddressRepository.getCustomerAddressByCustomerAndType(
                customer.getId(), "billing"
        );
        salesResource.setBillingAddressStreet(customerAddress.getStreetName());
        salesResource.setBillingAddressHouseNumber(customerAddress.getHouseNumber());
        salesResource.setBillingAddressAddition(customerAddress.getAddition());
        salesResource.setBillingAddressPostalCode(customerAddress.getPostalCode());
        salesResource.setBillingAddressCity(customerAddress.getCity());
    }

    public void prepareSalesResourceItemInformation(SalesResourceItem salesResourceItem, Product product) {
        salesResourceItem.setSku(product.getSku());
        salesResourceItem.setProductTitle(product.getProductTitle());
        salesResourceItem.setProductPrice(product.getProductPrice());
    }
}
