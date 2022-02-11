package johan.spekman.novibeie.service;

import johan.spekman.novibeie.model.AppUser;
import johan.spekman.novibeie.model.Authority;
import johan.spekman.novibeie.repository.AuthorityRepository;
import johan.spekman.novibeie.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service @Transactional @Slf4j
public class AppUserServiceImplementation implements AppUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public AppUserServiceImplementation(UserRepository userRepository, AuthorityRepository authorityRepository,
    PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.error("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getAuthorities().forEach(authority ->
                authorities.add(new SimpleGrantedAuthority(authority.getName()))
        );
        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                authorities
        );
    }

    @Override
    public AppUser saveUser(AppUser appUser) {
        log.info("Saving a new user to the dattabase");
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return userRepository.save(appUser);
    }

    @Override
    public Authority saveAuthority(Authority authority) {
        log.info("Saving a new role {} to the dattabase", authority.getName());
        return authorityRepository.save(authority);
    }

    @Override
    public void addRoleToAppUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser appUser = userRepository.findByUsername(username);
        Authority authority = authorityRepository.findByName(roleName);
        appUser.getAuthorities().add(authority);
    }

    @Override
    public AppUser getAppUser(String username) {
        log.info("Fetching user {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAppUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}
