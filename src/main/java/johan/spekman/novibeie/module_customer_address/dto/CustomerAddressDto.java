package johan.spekman.novibeie.module_customer_address.dto;

import johan.spekman.novibeie.module_customer_address.model.CustomerAddressType;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CustomerAddressDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long parentId;

    @NotBlank
    private String customerEmail;

    @NotBlank
    private String streetName;

    private int houseNumber;

    @NotBlank
    @Size(min = 1, max = 6)
    private String addition;

    private String postalCode;

    @NotBlank
    private String city;

    private boolean defaultAddress;

    public CustomerAddressDto(Long parentId, String customerEmail, String streetName, int houseNumber, String addition, String postalCode, String city, boolean defaultAddress, CustomerAddressType customerAddressType) {
        this.parentId = parentId;
        this.customerEmail = customerEmail;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.addition = addition;
        this.postalCode = postalCode;
        this.city = city;
        this.defaultAddress = defaultAddress;
        this.customerAddressType = customerAddressType;
    }

    private CustomerAddressType customerAddressType;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
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

    public boolean isDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(boolean defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}
