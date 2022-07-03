package johan.spekman.novibeie.module_customer.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public record CustomerDto(@NotBlank String firstName, String insertion, @NotBlank String lastName,
                          @NotBlank String phoneNumber, @NotEmpty @Email String emailAddress,
                          @NotBlank @Size(min = 8, max = 40) String password) {
    public CustomerDto(String firstName, String insertion, String lastName, String phoneNumber, String emailAddress, String password) {
        this.firstName = firstName;
        this.insertion = insertion;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    @Override
    public String firstName() {
        return firstName;
    }

    @Override
    public String lastName() {
        return lastName;
    }

    @Override
    public String phoneNumber() {
        return phoneNumber;
    }

    @Override
    public String emailAddress() {
        return emailAddress;
    }

    @Override
    public String password() {
        return password;
    }

}
