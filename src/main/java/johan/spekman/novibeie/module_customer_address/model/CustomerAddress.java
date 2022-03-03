package johan.spekman.novibeie.module_customer_address.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import johan.spekman.novibeie.module_customer.model.Customer;

import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "addresses")
@Transactional
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class CustomerAddress {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "parent_id")
    private Customer customer;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private String streetName;
    private int houseNumber;
    private String addition;
    private String postalCode;
    private String city;
    @Enumerated(EnumType.STRING)
    private CustomerAddressType customerAddressType;

    public CustomerAddress() {
    }

    public CustomerAddress(Customer customer, Long id, Long customerId, String streetName, int houseNumber, String addition, String postalCode, String city, CustomerAddressType customerAddressType) {
        this.customer = customer;
        this.id = id;
        this.customerId = customerId;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.addition = addition;
        this.postalCode = postalCode;
        this.city = city;
        this.customerAddressType = customerAddressType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CustomerAddressType getCustomerAddressType() {
        return customerAddressType;
    }

    public void setCustomerAddressType(CustomerAddressType customerAddressType) {
        this.customerAddressType = customerAddressType;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
