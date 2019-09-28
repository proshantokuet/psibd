package org.openmrs.module.PSI.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openmrs.Role;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;

public class PSIMapper {
	
	public static final Map<String, String> rolesMap = new HashMap<String, String>();
	static {
		rolesMap.put("Admin-Assistant", "Admin-Assistant");
		rolesMap.put("Bahmni-App", "Bahmni-App");
		rolesMap.put("Clinic-Manager", "Clinic-Manager");
		rolesMap.put("Counselor", "Counselor");
		rolesMap.put("CSP", "CSP");
		rolesMap.put("Data-Entry-Operator", "Data-Entry-Operator");
		rolesMap.put("Doctor", "Doctor");
		rolesMap.put("Lab Technician", "Lab Technician");
		rolesMap.put("Paramedic", "Paramedic");
		rolesMap.put("Pharmacist", "Pharmacist");
		rolesMap.put("SuperAdmin", "SuperAdmin");
		rolesMap.put("Admin", "Admin");
		rolesMap.put("Clinic Aid", "Clinic Aid");
		
	}
	
	public static List<Role> getRoles() {
		List<Role> seelctedRoles = new ArrayList<Role>();
		List<Role> roles = Context.getService(UserService.class).getAllRoles();
		for (Role role : roles) {
			if (rolesMap.containsKey(role.getName())) {
				seelctedRoles.add(role);
			}
			
		}
		return seelctedRoles;
		
	}
	
}
