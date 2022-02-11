package johan.spekman.novibeie;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import johan.spekman.novibeie.model.AppUser;
import johan.spekman.novibeie.model.Authority;
import johan.spekman.novibeie.service.AppUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

            appUserService.saveUser(new AppUser(null, "Johan Spekman", "Jspek", "1234", new ArrayList<Authority>()));

            appUserService.addRoleToAppUser("Jspek", "ADMIN");
        };
    }
}
