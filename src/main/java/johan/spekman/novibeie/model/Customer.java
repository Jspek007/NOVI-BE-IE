package johan.spekman.novibeie.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "customer_first_name")
    @NotBlank(message = "firstName may not be blank")
    String firstName;

    @Column(name = "customer_insertion")
    String insertion;

    @Column(name = "customer_last_name")
    @NotBlank(message = "lastName may not be blank")
    String lastName;

    @Column(name = "customer_phone_number")
    @Pattern(regexp = "^\\(?([+]31|0031|0)-?6(\\s?|-)([0-9]\\s{0,3}){8}$", message = "Following pattern required: " +
            "+31612345678")
    String phoneNumber;

    @Column(name = "customer_email_address")
    @Email(message = "Provide correct email-address")
    String emailAddress;

    @Column(name = "customer_password")
    String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInsertion() {
        return insertion;
    }

    public void setInsertion(String insertion) {
        this.insertion = insertion;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
