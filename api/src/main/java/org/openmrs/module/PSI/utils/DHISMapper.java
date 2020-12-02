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
		
		//forliver server
		//registrationMapper.put("MaritalStatus", "EWAnQfRVHf2"); //newly added
		//registrationMapper.put("occupation", "NQGLe2tymjJ"); // newly added
		//registrationMapper.put("Email", "vTckcsYYYMZ"); //newly added
		
		//for test server
		registrationMapper.put("MaritalStatus", "BYXudrz9IES"); //newly added
		registrationMapper.put("occupation", "dtBgIm6W3z2"); // newly added
		registrationMapper.put("Email", "XU8WVJTcnZu"); //newly added

		
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
		ServiceProvision.put("serviceUuid", "DGayB2UWV42");
		ServiceProvision.put("eslipNo", "IchA74zTEDE");
		ServiceProvision.put("age", "Po8XMVt2qlH");
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
		selectOptionMapper.put("occupation", "");
		selectOptionMapper.put("MaritalStatus", "");
	}
	
	public static final Map<String, String> dateMapper = new HashMap<String, String>();
	static {
		dateMapper.put("RegistrationDate", "");
	}
	
	public static final Map<String, String> historicalDataMapper = new HashMap<String, String>();
	static {
		historicalDataMapper.put("program", "xCSBjHIx2PH");
		historicalDataMapper.put("HD-Adolescent", "RSOg2EReloc");
		historicalDataMapper.put("HD-Age_day", "mhL8AhExWBM");
		historicalDataMapper.put("HD-Age_month", "x57unwS748X");
		historicalDataMapper.put("HD-Age_year", "jnlQ04ITipO");
		historicalDataMapper.put("HD-Bkno", "bNbJJnlQ1y4");
		historicalDataMapper.put("HD-Card", "v42sy4tiLrX");
		historicalDataMapper.put("HD-ClinicId", "LbLoXkGgYTi");// newly added
		historicalDataMapper.put("HD-compId", "xMwt55z6WDv"); // newly added
		historicalDataMapper.put("HD-comp_id", "Gy4CqhaqVmH");
		historicalDataMapper.put("HD-CSPID", "esZJi6o7al3");
		historicalDataMapper.put("HD-Customer_id", "BLbpLZ6iPA1");
		historicalDataMapper.put("HD-Customer_name", "rPuFgTml2jI");
		historicalDataMapper.put("HD-Day", "N9gaEdO2Dee");
		historicalDataMapper.put("HD-DTK", "kNdZKuFCil1");
		historicalDataMapper.put("HD-idno", "JkoTUEsGV06");
		historicalDataMapper.put("HD-kkno", "OeuGspPakcb");
		historicalDataMapper.put("HD-MoblieNo", "c6X0MyzchrR");
		historicalDataMapper.put("HD-Month", "OK0zTD3MxUa");
		historicalDataMapper.put("HD-NGOId", "THBKbeFFeAO");
		historicalDataMapper.put("HD-Ptype", "san642Fj24b");
		historicalDataMapper.put("HD-QTY", "d1xRTZhOZkR"); // newly added
		historicalDataMapper.put("HD-SatelliteId", "QP0aDk0peRH"); // newly added
		historicalDataMapper.put("HD-ServiceCode", "VlsYK1ehjix");
		historicalDataMapper.put("HD-session", "kNUb0Cdnk2J");
		historicalDataMapper.put("HD-sex", "bhPWMR8FHNq");
		historicalDataMapper.put("HD-Taka", "tkG5R1MlRY4");
		historicalDataMapper.put("HD-teamId", "hUOP0StDyef");
		historicalDataMapper.put("HD-voucherno", "bEWdNURznGS");
		historicalDataMapper.put("HD-WtQ", "wOP0JnWPaJl");
		historicalDataMapper.put("HD-Year", "ACKxZ8Dmq8k");
	}
	
}
