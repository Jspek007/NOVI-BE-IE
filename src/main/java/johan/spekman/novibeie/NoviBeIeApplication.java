package johan.spekman.novibeie;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.authority.model.Authority;
import johan.spekman.novibeie.module_authentication.appuser.service.AppUserService;
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
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner runner(AppUserService appUserService) {
        return args -> {
            appUserService.saveAuthority(new Authority(null, "USER"));
            appUserService.saveAuthority(new Authority(null, "MANAGER"));
            appUserService.saveAuthority(new Authority(null, "ADMIN"));

            appUserService.saveUser(new AppUser(null, "Johan Spekman", "Jspek", "Admin123",
                    new ArrayList<Authority>()));

            appUserService.addRoleToAppUser("Jspek", "ADMIN");
        };
    }
}
