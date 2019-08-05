package org.openmrs.module.PSI.utils;

import java.util.Collection;

import org.openmrs.Privilege;

public class Utils {
	
	public static boolean getPrivilige(Collection<Privilege> priviliges, String priveligeName) {
		for (Privilege privilege : priviliges) {
			if (privilege.getName().equalsIgnoreCase(priveligeName)) {
				return true;
			}
		}
		return false;
	}
	
}
