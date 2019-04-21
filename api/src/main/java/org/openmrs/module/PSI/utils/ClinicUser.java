package org.openmrs.module.PSI.utils;

import java.util.Set;

import org.openmrs.module.PSI.PSIClinicUser;

public class ClinicUser {
	
	public static boolean isExists(Set<PSIClinicUser> psiClinicUsers, String userName) {
		for (PSIClinicUser psiClinicUser : psiClinicUsers) {
			if (userName.equalsIgnoreCase(psiClinicUser.getUserName())) {
				return true;
			}
		}
		return false;
		
	}
	
	public static boolean isExists(Set<PSIClinicUser> psiClinicUsers, String userName, String editedUset) {
		for (PSIClinicUser psiClinicUser : psiClinicUsers) {
			if (userName.equalsIgnoreCase(psiClinicUser.getUserName())
			        && !psiClinicUser.getUserName().equalsIgnoreCase(editedUset)) {
				return true;
			}
		}
		return false;
		
	}
}
