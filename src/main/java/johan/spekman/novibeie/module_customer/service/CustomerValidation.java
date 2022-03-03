package johan.spekman.novibeie.module_customer.service;

import java.util.Random;

public class CustomerValidation {
    public static boolean checkCustomerPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\(?([+]31|0031|0)-?6(\\s?|-)([0-9]\\s{0,3}){8}$");
    }

    public static Long generateCustomerId() {
        Random random = new Random();
        long low = 100000L;
        long high = 999999L;
        return random.nextLong(high - low) + low;
    }
}
