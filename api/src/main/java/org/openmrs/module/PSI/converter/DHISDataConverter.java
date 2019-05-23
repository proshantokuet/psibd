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
		trackentityInstances.put("trackedEntityType", DHISMapper.registrationMapper.get("trackedEntityType"));
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
			} else {
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
			
		}
		if (patient.has("identifiers")) {
			JSONArray identifiers = patient.getJSONArray("identifiers");
			if (identifiers.length() != 0) {
				JSONObject identifier = identifiers.getJSONObject(0);
				String identifierValue = identifier.getString("identifier");
				JSONObject patientId = new JSONObject();
				patientId.put("attribute", DHISMapper.registrationMapper.get("patientId"));
				patientId.put("value", identifierValue);
				attributes.put(patientId);
				
			}
			
		}
		
		JSONObject preferredName = person.getJSONObject("preferredName");
		JSONObject givenName = new JSONObject();
		
		if (preferredName.has("givenName")) {
			if (!preferredName.isNull("givenName")) {
				if (DHISMapper.registrationMapper.containsKey("givenName")) {
					givenName.put("attribute", DHISMapper.registrationMapper.get("givenName"));
					givenName.put("value", preferredName.getString("givenName"));
					attributes.put(givenName);
				}
			}
		}
		
		if (preferredName.has("familyName")) {
			if (!preferredName.isNull("familyName")) {
				if (DHISMapper.registrationMapper.containsKey("familyName")) {
					JSONObject familyName = new JSONObject();
					familyName.putOpt("attribute", DHISMapper.registrationMapper.get("familyName"));
					familyName.putOpt("value", preferredName.getString("familyName"));
					attributes.put(familyName);
				}
			}
		}
		if (person.has("birthdate")) {
			if (!person.isNull("birthdate")) {
				if (DHISMapper.registrationMapper.containsKey("birthdate")) {
					JSONObject birthdate = new JSONObject();
					birthdate.put("attribute", DHISMapper.registrationMapper.get("birthdate"));
					birthdate.put("value", person.getString("birthdate").substring(0, 10));
					attributes.put(birthdate);
				}
			}
		}
		if (person.has("age")) {
			if (!person.isNull("age")) {
				if (DHISMapper.registrationMapper.containsKey("age")) {
					JSONObject age = new JSONObject();
					age.put("attribute", DHISMapper.registrationMapper.get("age"));
					age.put("value", Integer.parseInt(person.getString("age")));
					attributes.put(age);
				}
			}
		}
		if (person.has("gender")) {
			if (!person.isNull("gender")) {
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
		}
		
		if (person.has("uuid")) {
			if (!person.isNull("uuid")) {
				if (DHISMapper.registrationMapper.containsKey("uuid")) {
					JSONObject uuid = new JSONObject();
					uuid.put("attribute", DHISMapper.registrationMapper.get("uuid"));
					uuid.put("value", person.getString("uuid"));
					attributes.put(uuid);
				}
			}
		}
		if (person.has("preferredAddress")) {
			if (!person.isNull("preferredAddress")) {
				
				JSONObject preferredAddress = person.getJSONObject("preferredAddress");
				if (!preferredAddress.isNull("stateProvince")) {
					if (preferredAddress.has("stateProvince")) {
						JSONObject division = new JSONObject();
						division.put("attribute", DHISMapper.registrationMapper.get("stateProvince"));
						division.put("value", preferredAddress.getString("stateProvince"));
						attributes.put(division);
					}
				}
				if (!preferredAddress.isNull("countyDistrict")) {
					if (preferredAddress.has("countyDistrict")) {
						JSONObject countyDistrict = new JSONObject();
						countyDistrict.put("attribute", DHISMapper.registrationMapper.get("countyDistrict"));
						countyDistrict.put("value", preferredAddress.getString("countyDistrict"));
						attributes.put(countyDistrict);
					}
				}
				if (!preferredAddress.isNull("cityVillage")) {
					if (preferredAddress.has("cityVillage")) {
						JSONObject unionMunicipality = new JSONObject();
						unionMunicipality.put("attribute", DHISMapper.registrationMapper.get("cityVillage"));
						unionMunicipality.put("value", preferredAddress.getString("cityVillage"));
						attributes.put(unionMunicipality);
					}
				}
				if (!preferredAddress.isNull("address2")) {
					if (preferredAddress.has("address2")) {
						JSONObject ward = new JSONObject();
						ward.put("attribute", DHISMapper.registrationMapper.get("address2"));
						ward.put("value", preferredAddress.getString("address2"));
						attributes.put(ward);
					}
				}
				if (!preferredAddress.isNull("address1")) {
					if (preferredAddress.has("address1")) {
						JSONObject vrm = new JSONObject();
						vrm.put("attribute", DHISMapper.registrationMapper.get("address1"));
						vrm.put("value", preferredAddress.getString("address1"));
						attributes.put(vrm);
					}
				}
				if (!preferredAddress.isNull("address3")) {
					if (preferredAddress.has("address3")) {
						JSONObject upazilaCityCorporation = new JSONObject();
						upazilaCityCorporation.put("attribute", DHISMapper.registrationMapper.get("address3"));
						upazilaCityCorporation.put("value", preferredAddress.getString("address3"));
						attributes.put(upazilaCityCorporation);
					}
				}
			}
		}
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		trackentityInstances.put("attributes", attributes);
		JSONArray enrollments = new JSONArray();
		JSONObject enrollment = new JSONObject();
		enrollment.put("orgUnit", orgUnit);
		enrollment.put("program", DHISMapper.registrationMapper.get("program"));
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
		event.put("program", DHISMapper.registrationMapper.get("program"));
		event.put("programStage", DHISMapper.ServiceProvision.get("programStage"));
		event.put("status", "COMPLETED");
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		event.put("eventDate", today);
		JSONArray dataValues = new JSONArray();
		
		JSONObject clinicName = new JSONObject();
		clinicName.put("dataElement", DHISMapper.ServiceProvision.get("clinicName"));
		clinicName.put("value", psiServiceProvision.getPsiMoneyReceiptId().getClinicName());
		dataValues.put(clinicName);
		
		JSONObject clinicId = new JSONObject();
		clinicId.put("dataElement", DHISMapper.ServiceProvision.get("clinicId"));
		clinicId.put("value", psiServiceProvision.getPsiMoneyReceiptId().getClinicCode());
		dataValues.put(clinicId);
		
		JSONObject servicePoint = new JSONObject();
		servicePoint.put("dataElement", DHISMapper.ServiceProvision.get("servicePoint"));
		servicePoint.put("value", psiServiceProvision.getPsiMoneyReceiptId().getServicePoint());
		dataValues.put(servicePoint);
		
		JSONObject saletilteClinicId = new JSONObject();
		saletilteClinicId.put("dataElement", DHISMapper.ServiceProvision.get("saletilteClinicId"));
		saletilteClinicId.put("value", psiServiceProvision.getPsiMoneyReceiptId().getSateliteClinicId());
		dataValues.put(saletilteClinicId);
		
		JSONObject serviceDate = new JSONObject();
		serviceDate.put("dataElement", DHISMapper.ServiceProvision.get("serviceDate"));
		serviceDate.put("value", dateFormat.format(psiServiceProvision.getPsiMoneyReceiptId().getMoneyReceiptDate()));
		dataValues.put(serviceDate);
		
		JSONObject teamNo = new JSONObject();
		teamNo.put("dataElement", DHISMapper.ServiceProvision.get("teamNo"));
		teamNo.put("value", psiServiceProvision.getPsiMoneyReceiptId().getTeamNo());
		dataValues.put(teamNo);
		
		JSONObject slipNo = new JSONObject();
		slipNo.put("dataElement", DHISMapper.ServiceProvision.get("slipNo"));
		slipNo.put("value", psiServiceProvision.getPsiMoneyReceiptId().getSlipNo());
		dataValues.put(slipNo);
		
		JSONObject reference = new JSONObject();
		reference.put("dataElement", DHISMapper.ServiceProvision.get("reference"));
		reference.put("value", psiServiceProvision.getPsiMoneyReceiptId().getReference());
		dataValues.put(reference);
		
		JSONObject referenceId = new JSONObject();
		referenceId.put("dataElement", DHISMapper.ServiceProvision.get("referenceId"));
		referenceId.put("value", psiServiceProvision.getPsiMoneyReceiptId().getReferenceId());
		dataValues.put(referenceId);
		
		/*JSONObject session = new JSONObject();
		session.put("dataElement", "DyHphdoGGXQ");
		session.put("value", psiServiceProvision.getPsiMoneyReceiptId().getSession());
		dataValues.put(session);
		JSONObject other = new JSONObject();
		other.put("dataElement", "DyHphdoGGXQ");
		other.put("value", psiServiceProvision.getPsiMoneyReceiptId().getOthers());
		dataValues.put(other);*/
		
		JSONObject item = new JSONObject();
		item.put("dataElement", DHISMapper.ServiceProvision.get("item"));
		item.put("value", psiServiceProvision.getItem());
		dataValues.put(item);
		
		JSONObject description = new JSONObject();
		description.put("dataElement", DHISMapper.ServiceProvision.get("description"));
		description.put("value", psiServiceProvision.getDescription());
		dataValues.put(description);
		
		JSONObject unitCost = new JSONObject();
		unitCost.put("dataElement", DHISMapper.ServiceProvision.get("unitCost"));
		unitCost.put("value", psiServiceProvision.getUnitCost());
		dataValues.put(unitCost);
		
		JSONObject quantity = new JSONObject();
		quantity.put("dataElement", DHISMapper.ServiceProvision.get("quantity"));
		quantity.put("value", psiServiceProvision.getQuantity());
		dataValues.put(quantity);
		
		JSONObject totalAmount = new JSONObject();
		totalAmount.put("dataElement", DHISMapper.ServiceProvision.get("totalAmount"));
		totalAmount.put("value", psiServiceProvision.getTotalAmount());
		dataValues.put(totalAmount);
		
		JSONObject discount = new JSONObject();
		discount.put("dataElement", DHISMapper.ServiceProvision.get("discount"));
		discount.put("value", psiServiceProvision.getDiscount());
		dataValues.put(discount);
		
		JSONObject code = new JSONObject();
		code.put("dataElement", DHISMapper.ServiceProvision.get("code"));
		code.put("value", psiServiceProvision.getCode());
		dataValues.put(code);
		
		JSONObject category = new JSONObject();
		category.put("dataElement", DHISMapper.ServiceProvision.get("category"));
		category.put("value", psiServiceProvision.getCategory());
		dataValues.put(category);
		
		/*JSONObject provider = new JSONObject();
		provider.put("dataElement", "sNpMqQaWrUV");
		provider.put("value", psiServiceProvision.getProvider());
		dataValues.put(provider);*/
		
		JSONObject netPayableAmount = new JSONObject();
		netPayableAmount.put("dataElement", DHISMapper.ServiceProvision.get("netPayableAmount"));
		netPayableAmount.put("value", psiServiceProvision.getNetPayable());
		dataValues.put(netPayableAmount);
		event.put("dataValues", dataValues);
		
		return event;
		
	}
}
