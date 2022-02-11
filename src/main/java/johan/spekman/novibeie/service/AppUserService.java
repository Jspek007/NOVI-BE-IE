package johan.spekman.novibeie.service;

import johan.spekman.novibeie.model.AppUser;
import johan.spekman.novibeie.model.Authority;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    Authority saveAuthority(Authority authority);
    void addRoleToAppUser(String username, String roleName);
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();
}
