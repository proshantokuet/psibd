package org.openmrs.module.PSI.converter;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DHISDataConverter {
	
	public JSONObject toConvertPatient(JSONObject patient) throws JSONException {
		JSONObject trackentityInstances = new JSONObject();
		trackentityInstances.put("trackedEntity", "");
		trackentityInstances.put("orgUnit", "");
		JSONArray attributes = new JSONArray();
		
		JSONArray patientAttributes = patient.getJSONArray("attributes");
		for (int i = 0; i < patientAttributes.length(); i++) {
			JSONObject patientAttribute = patientAttributes.getJSONObject(i);
			JSONObject attribute = new JSONObject();
			attribute.put("attribute", "");
			attribute.put("value", patientAttribute.getString("value"));
			attributes.put(attribute);
		}
		trackentityInstances.put("attributes", attributes);
		
		JSONObject preferredName = patient.getJSONObject("preferredName");
		JSONObject givenName = new JSONObject();
		givenName.put("attribute", "givenName");
		givenName.put("value", preferredName.getString("givenName"));
		attributes.put(givenName);
		
		JSONObject middleName = new JSONObject();
		middleName.put("attribute", "middleName");
		middleName.put("value", preferredName.getString("middleName"));
		attributes.put(middleName);
		
		JSONObject familyName = new JSONObject();
		familyName.put("attribute", "familyName");
		familyName.put("value", preferredName.getString("familyName"));
		attributes.put(familyName);
		
		JSONObject preferredAddress = patient.getJSONObject("preferredAddress");
		JSONObject country = new JSONObject();
		country.put("attribute", "country");
		country.put("value", preferredAddress.getString("country"));
		attributes.put(country);
		
		JSONObject stateProvince = new JSONObject();
		stateProvince.put("attribute", "stateProvince");
		stateProvince.put("value", preferredAddress.getString("stateProvince"));
		attributes.put(stateProvince);
		
		JSONObject countyDistrict = new JSONObject();
		countyDistrict.put("attribute", "countyDistrict");
		countyDistrict.put("value", preferredAddress.getString("countyDistrict"));
		attributes.put(countyDistrict);
		
		JSONObject cityVillage = new JSONObject();
		cityVillage.put("attribute", "cityVillage");
		cityVillage.put("value", preferredAddress.getString("cityVillage"));
		attributes.put(cityVillage);
		
		JSONObject address1 = new JSONObject();
		address1.put("attribute", "address1");
		address1.put("value", preferredAddress.getString("address1"));
		attributes.put(address1);
		
		JSONObject address2 = new JSONObject();
		address2.put("attribute", "address2");
		address2.put("value", preferredAddress.getString("address2"));
		attributes.put(address2);
		
		JSONObject address3 = new JSONObject();
		address3.put("attribute", "address2");
		address3.put("value", preferredAddress.getString("address3"));
		attributes.put(address3);
		
		JSONObject address4 = new JSONObject();
		address4.put("attribute", "address2");
		address4.put("value", preferredAddress.getString("address4"));
		attributes.put(address4);
		
		JSONArray enrollments = new JSONArray();
		JSONObject enrollment = new JSONObject();
		enrollment.put("orgUnit", "orgUnit");
		enrollment.put("program", "program");
		enrollment.put("enrollmentDate", new Date());
		enrollment.put("incidentDate", new Date());
		enrollments.put(enrollment);
		trackentityInstances.put("enrollments", enrollments);
		return patient;
		
	}
	
	public JSONObject toConvertMoneyReceipt() {
		return null;
		
	}
	
}
