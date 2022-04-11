package johan.spekman.novibeie.module_customer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class CustomerDto {
    @NotBlank
    private final String firstName;

    private final String insertion;

    @NotBlank
    private final String lastName;

    @NotBlank
    private final String phoneNumber;

    @NotEmpty
    @Email
    private final String emailAddress;

    @NotBlank
    @Size(min = 8, max = 40)
    private final String password;

    public CustomerDto(String firstName, String insertion, String lastName, String phoneNumber, String emailAddress, String password) {
        this.firstName = firstName;
        this.insertion = insertion;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
    }

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
