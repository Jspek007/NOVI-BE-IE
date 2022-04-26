package johan.spekman.novibeie.module_authentication.appuser.service;

import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.role.model.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    Role saveRole(Role role);
    AppUser addRoleToAppUser(String username, String roleName);
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();
}
