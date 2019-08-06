package org.openmrs.module.PSI.utils;

import java.util.Collection;
import java.util.Set;

import org.openmrs.Privilege;
import org.openmrs.Role;

public class Utils {
	
	public static boolean hasPrivilige(Collection<Privilege> priviliges, String priveligeName) {
		for (Privilege privilege : priviliges) {
			if (privilege.getName().equalsIgnoreCase(priveligeName)) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean hasRole(Set<Role> roles, String roleName) {
		for (Role role : roles) {
			if (role.getName().equalsIgnoreCase(roleName)) {
				return true;
			}
		}
		return false;
	}
}
