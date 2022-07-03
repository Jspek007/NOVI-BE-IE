package johan.spekman.novibeie.module_authentication.appuser.service;

import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.role.model.Role;
import johan.spekman.novibeie.module_authentication.role.repository.RoleRepository;
import johan.spekman.novibeie.module_authentication.appuser.repository.UserRepository;
import johan.spekman.novibeie.utililies.CreateTimeStamp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final CreateTimeStamp createTimeStamp;

    public AppUserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            CreateTimeStamp createTimeStamp) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.createTimeStamp = createTimeStamp;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found in the database");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        appUser.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(
                appUser.getUsername(),
                appUser.getPassword(),
                authorities
        );
    }

    @Override
    public AppUser saveUser(AppUser appUser) throws ParseException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        appUser.setCreatedAtDate(createTimeStamp.createTimeStamp());
        appUser.setUpdatedAtDate(createTimeStamp.createTimeStamp());
        return userRepository.save(appUser);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public AppUser addRoleToAppUser(String username, String roleName) throws ParseException {
        AppUser appUser = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        appUser.getRoles().add(role);
        appUser.setUpdatedAtDate(createTimeStamp.createTimeStamp());
        return appUser;
    }

    @Override
    public AppUser getAppUser(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getAppUsers() {
        return userRepository.findAll();
    }
}
