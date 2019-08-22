package org.openmrs.module.PSI.utils;

public class HtmlUtils {
	
	public static boolean roleChecked(String stringTxt, String compareValue) {
		String[] roles = stringTxt.split(",");
		for (String role : roles) {
			if (role.trim().equalsIgnoreCase(compareValue.trim())) {
				return true;
			}
		}
		return false;
	}
	
	public static String genderChecked(String str, String value) {
		if (str.equalsIgnoreCase(value)) {
			return "checked";
		} else {
			return "";
		}
	}
}
