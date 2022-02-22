package johan.spekman.novibeie.module_authentication.appuser.service;

import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.authority.model.Authority;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    Authority saveAuthority(Authority authority);
    void addRoleToAppUser(String username, String roleName);
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();
}
