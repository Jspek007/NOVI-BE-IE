package johan.spekman.novibeie;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.appuser.service.AppUserService;
import johan.spekman.novibeie.module_authentication.role.model.Role;
import johan.spekman.novibeie.module_customer.dto.CustomerDto;
import johan.spekman.novibeie.module_customer.model.Customer;
import johan.spekman.novibeie.module_customer.service.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;

import javax.naming.Binding;
import java.util.ArrayList;

@SpringBootApplication
@EnableEncryptableProperties
public class NoviBeIeApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoviBeIeApplication.class, args);
    }

    @Bean
    BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(AppUserService appUserService, CustomerService customerService) {
        return args -> {
//            appUserService.saveUser(new AppUser(null, "adminUser", "adminUser", "admin123!", new ArrayList<>()));
//            appUserService.saveUser(new AppUser(null, "customerService", "customerService", "customerService",
//                    new ArrayList<>()));
//            appUserService.saveUser(new AppUser(null, "productManager", "productManager", "productManager",
//                    new ArrayList<>()));
//            appUserService.saveRole(new Role(null, "CUSTOMERSERVICE"));
//            appUserService.saveRole(new Role(null, "PRODUCTMANAGER"));
//            appUserService.saveRole(new Role(null, "ADMIN"));
//
//            appUserService.addRoleToAppUser("adminUser", "ADMIN");
//            appUserService.addRoleToAppUser("customerService", "CUSTOMERSERVICE");
//            appUserService.addRoleToAppUser("productManager", "PRODUCTMANAGER");
        };
    }
}
