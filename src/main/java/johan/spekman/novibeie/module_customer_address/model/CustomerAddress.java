package johan.spekman.novibeie.module_customer_address.model;

import javax.persistence.*;

@Entity
@Table(name = "customer_addresses")
public class CustomerAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String streetName;
    private int houseNumber;
    private String addition;
    private String postalCode;
    private String city;
    @Enumerated(EnumType.STRING)
    private CustomerAddressType customerAddressType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
