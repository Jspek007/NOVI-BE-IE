package johan.spekman.novibeie.module_customer.service;

public class CustomerValidation {
    public static boolean checkCustomerPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("^\\(?([+]31|0031|0)-?6(\\s?|-)(\\d\\s{0,3}){8}$");
    }
}
