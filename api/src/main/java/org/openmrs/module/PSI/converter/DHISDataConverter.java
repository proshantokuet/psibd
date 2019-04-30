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
		String orgUnit = "";
		JSONObject person = patient.getJSONObject("person");
		JSONArray patientAttributes = person.getJSONArray("attributes");
		for (int i = 0; i < patientAttributes.length(); i++) {
			JSONObject patientAttribute = patientAttributes.getJSONObject(i);
			JSONObject attributeType = patientAttribute.getJSONObject("attributeType");
			String attributeTypeName = attributeType.getString("display");
			
			if ("orgUnit".equalsIgnoreCase(attributeTypeName)) {
				trackentityInstances.put("orgUnit", patientAttribute.getString("value"));
				orgUnit = patientAttribute.getString("value");
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
		if (preferredName.has("givenName")) {
			if (DHISMapper.registrationMapper.containsKey("givenName")) {
				givenName.put("attribute", DHISMapper.registrationMapper.get("givenName"));
				givenName.put("value", preferredName.getString("givenName"));
				attributes.put(givenName);
			}
		}
		if (preferredName.has("middleName")) {
			if (DHISMapper.registrationMapper.containsKey("middleName")) {
				JSONObject middleName = new JSONObject();
				middleName.put("attribute", DHISMapper.registrationMapper.get("middleName"));
				middleName.put("value", preferredName.getString("middleName"));
				attributes.put(middleName);
			}
		}
		if (preferredName.has("familyName")) {
			if (DHISMapper.registrationMapper.containsKey("familyName")) {
				JSONObject familyName = new JSONObject();
				familyName.put("attribute", DHISMapper.registrationMapper.get("familyName"));
				familyName.put("value", preferredName.getString("familyName"));
				attributes.put(familyName);
			}
		}
		if (person.has("birthdate")) {
			if (DHISMapper.registrationMapper.containsKey("birthdate")) {
				JSONObject birthdate = new JSONObject();
				birthdate.put("attribute", DHISMapper.registrationMapper.get("birthdate"));
				birthdate.put("value", person.getString("birthdate").substring(0, 10));
				attributes.put(birthdate);
			}
		}
		if (person.has("age")) {
			if (DHISMapper.registrationMapper.containsKey("age")) {
				JSONObject age = new JSONObject();
				age.put("attribute", DHISMapper.registrationMapper.get("age"));
				age.put("value", Integer.parseInt(person.getString("age")));
				attributes.put(age);
			}
		}
		if (person.has("gender")) {
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
		}
		
		if (person.has("uuid")) {
			if (DHISMapper.registrationMapper.containsKey("uuid")) {
				JSONObject uuid = new JSONObject();
				uuid.put("attribute", DHISMapper.registrationMapper.get("uuid"));
				uuid.put("value", person.getString("uuid"));
				attributes.put(uuid);
			}
		}
		
		JSONObject preferredAddress = person.getJSONObject("preferredAddress");
		
		if (preferredAddress.has("stateProvince")) {
			JSONObject division = new JSONObject();
			division.put("attribute", "yrbd4rRgRcR");
			division.put("value", preferredAddress.getString("stateProvince"));
			attributes.put(division);
		}
		if (preferredAddress.has("countyDistrict")) {
			JSONObject countyDistrict = new JSONObject();
			countyDistrict.put("attribute", "DPSa3yVkyJg");
			countyDistrict.put("value", preferredAddress.getString("countyDistrict"));
			attributes.put(countyDistrict);
		}
		if (preferredAddress.has("cityVillage")) {
			JSONObject unionMunicipality = new JSONObject();
			unionMunicipality.put("attribute", "dLt4JC3UB4d");
			unionMunicipality.put("value", preferredAddress.getString("cityVillage"));
			attributes.put(unionMunicipality);
		}
		
		if (preferredAddress.has("address2")) {
			JSONObject ward = new JSONObject();
			ward.put("attribute", "gpy80dko26B");
			ward.put("value", preferredAddress.getString("address2"));
			attributes.put(ward);
		}
		if (preferredAddress.has("address3")) {
			JSONObject upazilaCityCorporation = new JSONObject();
			upazilaCityCorporation.put("attribute", "PHBH7RJozpn");
			upazilaCityCorporation.put("value", preferredAddress.getString("address3"));
			attributes.put(upazilaCityCorporation);
		}
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		trackentityInstances.put("attributes", attributes);
		JSONArray enrollments = new JSONArray();
		JSONObject enrollment = new JSONObject();
		enrollment.put("orgUnit", orgUnit);
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
		
		JSONObject clinicName = new JSONObject();
		clinicName.put("dataElement", "iiZK5h6CDnG");
		clinicName.put("value", psiServiceProvision.getPsiMoneyReceiptId().getClinicName());
		dataValues.put(clinicName);
		
		JSONObject clinicId = new JSONObject();
		clinicId.put("dataElement", "oNgz5FFG5Az");
		clinicId.put("value", psiServiceProvision.getPsiMoneyReceiptId().getClinicCode());
		dataValues.put(clinicId);
		
		JSONObject servicePoint = new JSONObject();
		servicePoint.put("dataElement", "WgfyHCvhqYK");
		servicePoint.put("value", psiServiceProvision.getPsiMoneyReceiptId().getServicePoint());
		dataValues.put(servicePoint);
		
		JSONObject saletilteClinicId = new JSONObject();
		saletilteClinicId.put("dataElement", "CMNIQFwqSUl");
		saletilteClinicId.put("value", psiServiceProvision.getPsiMoneyReceiptId().getSateliteClinicId());
		dataValues.put(saletilteClinicId);
		
		JSONObject serviceDate = new JSONObject();
		serviceDate.put("dataElement", "bKEmultRiUQ");
		serviceDate.put("value", dateFormat.format(psiServiceProvision.getPsiMoneyReceiptId().getMoneyReceiptDate()));
		dataValues.put(serviceDate);
		
		JSONObject teamNo = new JSONObject();
		teamNo.put("dataElement", "MJ7nruCm0ub");
		teamNo.put("value", psiServiceProvision.getPsiMoneyReceiptId().getTeamNo());
		dataValues.put(teamNo);
		
		JSONObject slipNo = new JSONObject();
		slipNo.put("dataElement", "LAGnqBgA1pG");
		slipNo.put("value", psiServiceProvision.getPsiMoneyReceiptId().getSlipNo());
		dataValues.put(slipNo);
		
		JSONObject reference = new JSONObject();
		reference.put("dataElement", "dXwORvJ4ODG");
		reference.put("value", psiServiceProvision.getPsiMoneyReceiptId().getReference());
		dataValues.put(reference);
		
		JSONObject referenceId = new JSONObject();
		referenceId.put("dataElement", "DyHphdoGGXQ");
		referenceId.put("value", psiServiceProvision.getPsiMoneyReceiptId().getReferenceId());
		dataValues.put(referenceId);
		
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
