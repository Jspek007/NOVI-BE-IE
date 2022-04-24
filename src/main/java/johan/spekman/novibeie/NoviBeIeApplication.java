package johan.spekman.novibeie;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.appuser.service.AppUserService;
import johan.spekman.novibeie.module_authentication.role.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
//
//     @Bean
//     CommandLineRunner runner(AppUserService appUserService) {
//     return args -> {
//         appUserService.saveUser(new AppUser(null, "Johan Spekman", "adminUser", "admin123!", new ArrayList<>()));
//     appUserService.saveRole(new Role(null, "CUSTOMERSERVICE"));
//     appUserService.saveRole(new Role(null, "PRODUCTMANAGER"));
//     appUserService.saveRole(new Role(null, "ADMIN"));
//
//     appUserService.addRoleToAppUser("adminUser", "ADMIN");
//     };
//     }
}
