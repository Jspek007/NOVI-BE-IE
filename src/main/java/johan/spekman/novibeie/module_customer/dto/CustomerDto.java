package johan.spekman.novibeie.module_customer.dto;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.TableGenerator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerDto {
    @NotBlank
    private String firstName;

    private String insertion;

    @NotBlank
    private String lastName;

    @NotBlank
    private String phoneNumber;

    @NotEmpty
    @Email
    private String emailAddress;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public String getInsertion() {
        return insertion;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

}
