package johan.spekman.novibeie.module_authentication.appuser.service;

import johan.spekman.novibeie.module_authentication.appuser.model.AppUser;
import johan.spekman.novibeie.module_authentication.role.model.Role;

import java.text.ParseException;
import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser) throws ParseException;
    Role saveRole(Role role);
    AppUser addRoleToAppUser(String username, String roleName) throws ParseException;
    AppUser getAppUser(String username);
    List<AppUser> getAppUsers();
}
