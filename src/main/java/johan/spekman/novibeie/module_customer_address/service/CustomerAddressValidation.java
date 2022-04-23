package johan.spekman.novibeie.module_customer_address.service;

import org.jetbrains.annotations.NotNull;


public class CustomerAddressValidation {
    public static boolean checkPostalCode(@NotNull String postcode) {
        return postcode.matches("[1-9]\\d{3}[a-zA-Z]{2}");
    }
}
