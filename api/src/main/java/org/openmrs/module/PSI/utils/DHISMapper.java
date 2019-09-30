package org.openmrs.module.PSI.utils;

import java.util.HashMap;
import java.util.Map;

public class DHISMapper {
	
	public static final Map<String, String> registrationMapper = new HashMap<String, String>();
	static {
		registrationMapper.put("IDno", "ncDUQ63WGMq");
		registrationMapper.put("SelectanIDcard", "CKjTmuYrlJI");
		registrationMapper.put("RegistrationPoint", "oga1dnA50Yn");
		registrationMapper.put("FinancialStatus", "FoMLHU7uLT7");
		registrationMapper.put("RegistrationDate", "m4Ugf7IyAKT");
		registrationMapper.put("MobileNoOwner", "aeg04huFGjq");
		registrationMapper.put("MobileNo", "uerw9vcwNQW");
		registrationMapper.put("UIC", "EhGAasyq73e");
		
		registrationMapper.put("givenName", "pwfKehQdh97");
		registrationMapper.put("familyName", "yWEqgzbdhWt");
		registrationMapper.put("birthMothersFirstName", "EGXASsHOq4a");
		registrationMapper.put("birthRank", "qVfDyAIDrNV");
		registrationMapper.put("birthDistrict", "aA51MHDmkBU");
		registrationMapper.put("birthUpazilla", "zGDqX95hHp4");
		registrationMapper.put("gender", "etYMZTKI74a");
		registrationMapper.put("birthdate", "r1JB3f1OAnA");
		registrationMapper.put("age", "YeScM5bHQOI");
		registrationMapper.put("PatientUUID", "PlytPA42Nbe");
		
		registrationMapper.put("uuid", "oW51s5NUIqo");
		
		registrationMapper.put("stateProvince", "vwkkJFdFhTf");
		registrationMapper.put("countyDistrict", "Cy82HbkRQSR");
		registrationMapper.put("cityVillage", "E70xA9pLSJe");
		registrationMapper.put("address2", "q1m3gMByl6v");
		registrationMapper.put("address1", "vbiiaD1vlEb");
		registrationMapper.put("address3", "BHh4UtACHPc");
		registrationMapper.put("trackedEntityType", "NpKZqkyYZFk");
		registrationMapper.put("program", "q2uZRqRc0UD");
		registrationMapper.put("AlternateMobileNumber", "qATeZGtk5sL"); //newly added
		
		registrationMapper.put("patientId", "IiW2IfHRxfG");
		registrationMapper.put("Spouse/FathersName", "Ng4MW4SP7wM"); //newly added
		registrationMapper.put("Gov_Card_Type", "Ph40wkLaX1T"); // newly added
		registrationMapper.put("PreferredCallingTime", "AY39jOq51CP"); //newly added
		
	}
	
	public static final Map<String, String> ServiceProvision = new HashMap<String, String>();
	static {
		ServiceProvision.put("programStage", "UXgONLVlRlA");
		ServiceProvision.put("clinicName", "NOOR11jHbxK");
		ServiceProvision.put("clinicId", "d49IcANqJh9");
		ServiceProvision.put("servicePoint", "OH8Bn6DK4sU");
		ServiceProvision.put("saletilteClinicId", "gqY36WgTq73");
		ServiceProvision.put("serviceDate", "mfFgHWjJI8E");
		ServiceProvision.put("teamNo", "FszGIJF3Ypl");
		ServiceProvision.put("cspId", "miEwNVVW8ar");// newly added
		ServiceProvision.put("dataCollector", "DLzjJdIteUN"); // newly added
		ServiceProvision.put("slipNo", "LeqJ8Eymj9W");
		ServiceProvision.put("reference", "deL99WbV9Ra");
		ServiceProvision.put("referenceId", "RQqYt5aQbQ1");
		ServiceProvision.put("item", "c0tuO285CZB");
		ServiceProvision.put("description", "oLdDkadPIF5");
		ServiceProvision.put("unitCost", "RoOpVdj0wi7");
		ServiceProvision.put("quantity", "AQws6wNieQ3");
		ServiceProvision.put("totalAmount", "HTBprdCWSDx");
		ServiceProvision.put("discount", "vvojvJk7o8E");
		ServiceProvision.put("code", "Gep3YabXrJi");
		ServiceProvision.put("category", "oW1rh8s2SQ4");
		ServiceProvision.put("netPayableAmount", "Ey8wpnMTGl3");
		ServiceProvision.put("session", "FzFCFM4Zr43"); // newly added
		ServiceProvision.put("others", "otXoI3cMuJU"); // newly added
		ServiceProvision.put("serviceUuid", "sy8PbgNLffQ");
	}
	
	public static final Map<String, String> selectOptionMapper = new HashMap<String, String>();
	static {
		selectOptionMapper.put("RegistrationPoint", "");
		selectOptionMapper.put("SelectanIDcard", "");
		selectOptionMapper.put("FinancialStatus", "");
		selectOptionMapper.put("MaritalStatus", "");
		selectOptionMapper.put("MobileNoOwner", "");
		selectOptionMapper.put("Gov_Card_Type", "");
		selectOptionMapper.put("PreferredCallingTime", "");
	}
	
	public static final Map<String, String> dateMapper = new HashMap<String, String>();
	static {
		dateMapper.put("RegistrationDate", "");
	}
	
}
