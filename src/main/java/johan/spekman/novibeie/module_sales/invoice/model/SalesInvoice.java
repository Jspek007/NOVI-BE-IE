package johan.spekman.novibeie.module_sales.invoice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer_address.model.CustomerAddress;
import johan.spekman.novibeie.module_sales.orders.model.SalesOrder;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "sales_invoices")
public class SalesInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long entityId;
    @ManyToOne
    @JoinColumn(name = "customer_entity_id")
    private Customer customer;
    private String customerFirstName;
    private String customerInsertion;
    private String customerLastName;
    private String customerEmail;
    private String customerPhoneNumber;
    @ManyToOne
    @JoinColumn(name = "sales_order_entity_id")
    private SalesOrder salesOrder;
    private Date createdAtDate;
    private double grandTotal;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shipping_address_id")
    private CustomerAddress shippingAddress;
    private String shippingAddressStreet;
    private String shippingAddressAddition;
    private String shippingAddressCity;
    private String shippingAddressPostalCode;
    private int shippingAddressHouseNumber;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "billing_address_id")
    private CustomerAddress billingAddress;
    private String billingAddressStreet;
    private String billingAddressAddition;
    private String billingAddressCity;
    private String billingAddressPostalCode;
    private int billingAddressHouseNumber;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCustomerFirstName() {
        return customerFirstName;
    }

    public void setCustomerFirstName(String customerFirstName) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerInsertion() {
        return customerInsertion;
    }

    public void setCustomerInsertion(String customerInsertion) {
        this.customerInsertion = customerInsertion;
    }

    public String getCustomerLastName() {
        return customerLastName;
    }

    public void setCustomerLastName(String customerLastName) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public SalesOrder getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(SalesOrder salesOrder) {
        this.salesOrder = salesOrder;
    }

    public Date getCreatedAtDate() {
        return createdAtDate;
    }

    public void setCreatedAtDate(Date createdAtDate) {
        this.createdAtDate = createdAtDate;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(double grandTotal) {
        this.grandTotal = grandTotal;
    }

    public CustomerAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(CustomerAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getShippingAddressStreet() {
        return shippingAddressStreet;
    }

    public void setShippingAddressStreet(String shippingAddressStreet) {
        this.shippingAddressStreet = shippingAddressStreet;
    }

    public String getShippingAddressAddition() {
        return shippingAddressAddition;
    }

    public void setShippingAddressAddition(String shippingAddressAddition) {
        this.shippingAddressAddition = shippingAddressAddition;
    }

    public String getShippingAddressCity() {
        return shippingAddressCity;
    }

    public void setShippingAddressCity(String shippingAddressCity) {
        this.shippingAddressCity = shippingAddressCity;
    }

    public String getShippingAddressPostalCode() {
        return shippingAddressPostalCode;
    }

    public void setShippingAddressPostalCode(String shippingAddressPostalCode) {
        this.shippingAddressPostalCode = shippingAddressPostalCode;
    }

    public int getShippingAddressHouseNumber() {
        return shippingAddressHouseNumber;
    }

    public void setShippingAddressHouseNumber(int shippingAddressHouseNumber) {
        this.shippingAddressHouseNumber = shippingAddressHouseNumber;
    }

    public CustomerAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(CustomerAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public String getBillingAddressStreet() {
        return billingAddressStreet;
    }

    public void setBillingAddressStreet(String billingAddressStreet) {
        this.billingAddressStreet = billingAddressStreet;
    }

    public String getBillingAddressAddition() {
        return billingAddressAddition;
    }

    public void setBillingAddressAddition(String billingAddressAddition) {
        this.billingAddressAddition = billingAddressAddition;
    }

    public String getBillingAddressCity() {
        return billingAddressCity;
    }

    public void setBillingAddressCity(String billingAddressCity) {
        this.billingAddressCity = billingAddressCity;
    }

    public String getBillingAddressPostalCode() {
        return billingAddressPostalCode;
    }

    public void setBillingAddressPostalCode(String billingAddressPostalCode) {
        this.billingAddressPostalCode = billingAddressPostalCode;
    }

    public int getBillingAddressHouseNumber() {
        return billingAddressHouseNumber;
    }

    public void setBillingAddressHouseNumber(int billingAddressHouseNumber) {
        this.billingAddressHouseNumber = billingAddressHouseNumber;
    }
}
