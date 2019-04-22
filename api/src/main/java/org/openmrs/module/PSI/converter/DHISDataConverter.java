package org.openmrs.module.PSI.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.utils.DHISMapper;
import org.springframework.stereotype.Component;

@Component
public class DHISDataConverter {
	
	public static JSONObject toConvertPatient(JSONObject patient) throws JSONException {
		
		JSONObject trackentityInstances = new JSONObject();
		trackentityInstances.put("trackedEntityType", "c9H923L9Lbz");
		//trackentityInstances.put("orgUnit", "DcDZR6okGhw");
		JSONArray attributes = new JSONArray();
		JSONObject person = patient.getJSONObject("person");
		JSONArray patientAttributes = person.getJSONArray("attributes");
		for (int i = 0; i < patientAttributes.length(); i++) {
			JSONObject patientAttribute = patientAttributes.getJSONObject(i);
			JSONObject attributeType = patientAttribute.getJSONObject("attributeType");
			String attributeTypeName = attributeType.getString("display");
			
			if ("orgUnit".equalsIgnoreCase(attributeTypeName)) {
				trackentityInstances.put("orgUnit", patientAttribute.getString("value"));
			}
			if (DHISMapper.registrationMapper.containsKey(attributeTypeName)) {
				JSONObject attribute = new JSONObject();
				attribute.put("attribute", DHISMapper.registrationMapper.get(attributeTypeName));
				
				if (DHISMapper.selectOptionMapper.containsKey(attributeTypeName)) {
					attribute.put("value", patientAttribute.getString("display"));
				} else if (DHISMapper.dateMapper.containsKey(attributeTypeName)) {
					attribute.put("value", patientAttribute.getString("value").substring(0, 10));
				} else {
					attribute.put("value", patientAttribute.getString("value"));
				}
				attributes.put(attribute);
				
			}
			
		}
		
		JSONObject preferredName = person.getJSONObject("preferredName");
		JSONObject givenName = new JSONObject();
		if (DHISMapper.registrationMapper.containsKey("givenName")) {
			givenName.put("attribute", DHISMapper.registrationMapper.get("givenName"));
			givenName.put("value", preferredName.getString("givenName"));
			attributes.put(givenName);
		}
		
		if (DHISMapper.registrationMapper.containsKey("middleName")) {
			JSONObject middleName = new JSONObject();
			middleName.put("attribute", DHISMapper.registrationMapper.get("middleName"));
			middleName.put("value", preferredName.getString("middleName"));
			attributes.put(middleName);
		}
		
		if (DHISMapper.registrationMapper.containsKey("familyName")) {
			JSONObject familyName = new JSONObject();
			familyName.put("attribute", DHISMapper.registrationMapper.get("familyName"));
			familyName.put("value", preferredName.getString("familyName"));
			attributes.put(familyName);
		}
		
		if (DHISMapper.registrationMapper.containsKey("birthdate")) {
			JSONObject birthdate = new JSONObject();
			birthdate.put("attribute", DHISMapper.registrationMapper.get("birthdate"));
			birthdate.put("value", person.getString("birthdate").substring(0, 10));
			attributes.put(birthdate);
		}
		
		if (DHISMapper.registrationMapper.containsKey("age")) {
			JSONObject age = new JSONObject();
			age.put("attribute", DHISMapper.registrationMapper.get("age"));
			age.put("value", Integer.parseInt(person.getString("age")));
			attributes.put(age);
		}
		
		if (DHISMapper.registrationMapper.containsKey("gender")) {
			String genderOptiion = person.getString("gender");
			String genderName = "";
			if (genderOptiion.equalsIgnoreCase("M")) {
				genderName = "Male";
			} else if (genderOptiion.equalsIgnoreCase("F")) {
				genderName = "Female";
			} else {
				genderName = "Third Gender";
			}
			JSONObject gender = new JSONObject();
			gender.put("attribute", DHISMapper.registrationMapper.get("gender"));
			gender.put("value", genderName);
			attributes.put(gender);
		}
		
		if (DHISMapper.registrationMapper.containsKey("uuid")) {
			JSONObject uuid = new JSONObject();
			uuid.put("attribute", DHISMapper.registrationMapper.get("uuid"));
			uuid.put("value", person.getString("uuid"));
			attributes.put(uuid);
		}
		/*JSONObject preferredAddress = person.getJSONObject("preferredAddress");
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
		attributes.put(address4);*/
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		trackentityInstances.put("attributes", attributes);
		JSONArray enrollments = new JSONArray();
		JSONObject enrollment = new JSONObject();
		enrollment.put("orgUnit", "DcDZR6okGhw");
		enrollment.put("program", "o6zWmo6on9K");
		enrollment.put("enrollmentDate", today);
		enrollment.put("incidentDate", today);
		//enrollment.put("status", "COMPLETED");
		enrollments.put(enrollment);
		trackentityInstances.put("enrollments", enrollments);
		return trackentityInstances;
		
	}
	
	public static JSONObject toConvertMoneyReceipt(PSIServiceProvision psiServiceProvision, String trackedEntityInstanceId)
	    throws JSONException {
		JSONObject event = new JSONObject();
		
		event.put("trackedEntityInstance", trackedEntityInstanceId);
		event.put("orgUnit", psiServiceProvision.getPsiMoneyReceiptId().getOrgUnit());
		event.put("program", "o6zWmo6on9K");
		event.put("programStage", "UKTFJZZ5I66");
		event.put("status", "COMPLETED");
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		event.put("eventDate", today);
		JSONArray dataValues = new JSONArray();
		
		JSONObject item = new JSONObject();
		item.put("dataElement", "gFQGgrmfYX9");
		item.put("value", psiServiceProvision.getItem());
		dataValues.put(item);
		
		JSONObject description = new JSONObject();
		description.put("dataElement", "AGuYIDbmiIY");
		description.put("value", psiServiceProvision.getDescription());
		dataValues.put(description);
		
		JSONObject unitCost = new JSONObject();
		unitCost.put("dataElement", "sydU3SHbPoR");
		unitCost.put("value", psiServiceProvision.getUnitCost());
		dataValues.put(unitCost);
		
		JSONObject quantity = new JSONObject();
		quantity.put("dataElement", "bNrHdhSMqxA");
		quantity.put("value", psiServiceProvision.getQuantity());
		dataValues.put(quantity);
		
		JSONObject totalAmount = new JSONObject();
		totalAmount.put("dataElement", "zKtNgQuvN1O");
		totalAmount.put("value", psiServiceProvision.getTotalAmount());
		dataValues.put(totalAmount);
		
		JSONObject discount = new JSONObject();
		discount.put("dataElement", "qyhmRo71iYR");
		discount.put("value", psiServiceProvision.getDiscount());
		dataValues.put(discount);
		
		JSONObject netPayableAmount = new JSONObject();
		netPayableAmount.put("dataElement", "u4GXgQ54Fpn");
		netPayableAmount.put("value", psiServiceProvision.getNetPayable());
		dataValues.put(netPayableAmount);
		event.put("dataValues", dataValues);
		
		return event;
		
	}
}
