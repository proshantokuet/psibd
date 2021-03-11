package org.openmrs.module.PSI.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.dto.SHNHistoricalDataDTO;
import org.openmrs.module.PSI.dto.ShnIndicatorDetailsDTO;
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
		String registeredDate = "";
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
						registeredDate = patientAttribute.getString("value").substring(0, 10);
						attribute.put("value", registeredDate);
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
		/*		if (person.has("age")) {
					if (!person.isNull("age")) {
						if (DHISMapper.registrationMapper.containsKey("age")) {
							JSONObject age = new JSONObject();
							age.put("attribute", DHISMapper.registrationMapper.get("age"));
							age.put("value", Integer.parseInt(person.getString("age")));
							attributes.put(age);
						}
					}
				}*/
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
						genderName = "Other";
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
		
		if (person.has("AlternateMobileNumber")) {
			if (!person.isNull("AlternateMobileNumber")) {
				if (DHISMapper.registrationMapper.containsKey("AlternateMobileNumber")) {
					JSONObject alternateMobileNUmber = new JSONObject();
					alternateMobileNUmber.put("attribute", DHISMapper.registrationMapper.get("AlternateMobileNumber"));
					alternateMobileNUmber.put("value", person.getString("AlternateMobileNumber"));
					attributes.put(alternateMobileNUmber);
				}
			}
		}
		
		if (person.has("Spouse/FathersName")) {
			if (!person.isNull("Spouse/FathersName")) {
				if (DHISMapper.registrationMapper.containsKey("Spouse/FathersName")) {
					JSONObject spouseorFatherName = new JSONObject();
					spouseorFatherName.put("attribute", DHISMapper.registrationMapper.get("Spouse/FathersName"));
					spouseorFatherName.put("value", person.getString("Spouse/FathersName"));
					attributes.put(spouseorFatherName);
				}
			}
		}
		
		//		if (person.has("Gov_Card_Type")) {
		//			if (!person.isNull("Gov_Card_Type")) {
		//				if (DHISMapper.registrationMapper.containsKey("Gov_Card_Type")) {
		//					JSONObject govCardType = new JSONObject();
		//					govCardType.put("attribute", DHISMapper.registrationMapper.get("Gov_Card_Type"));
		//					govCardType.put("value", person.getString("Gov_Card_Type"));
		//					attributes.put(govCardType);
		//				}
		//			}
		//		}
		
		//		if (person.has("PreferredCallingTime")) {
		//			if (!person.isNull("PreferredCallingTime")) {
		//				if (DHISMapper.registrationMapper.containsKey("PreferredCallingTime")) {
		//					JSONObject preferredCallingTime = new JSONObject();
		//					preferredCallingTime.put("attribute", DHISMapper.registrationMapper.get("PreferredCallingTime"));
		//					preferredCallingTime.put("value", person.getString("PreferredCallingTime"));
		//					attributes.put(preferredCallingTime);
		//				}
		//			}
		//		}
		
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
		enrollment.put("enrollmentDate", registeredDate);
		enrollment.put("incidentDate", registeredDate);
		//enrollment.put("status", "COMPLETED");
		enrollments.put(enrollment);
		trackentityInstances.put("enrollments", enrollments);
		return trackentityInstances;
		
	}
	
	public static JSONObject toConvertMoneyReceipt(PSIServiceProvision psiServiceProvision, String trackedEntityInstanceId)
	    throws JSONException {
		JSONObject event = new JSONObject();
		PSIMoneyReceipt psiMoneyReceipt = psiServiceProvision.getPsiMoneyReceiptId();
		String orgUit = psiMoneyReceipt.getOrgUnit();
		String getClinicName = psiMoneyReceipt.getClinicName();
		String getClinicCOde = psiMoneyReceipt.getClinicCode();
		String getServicePoint = psiMoneyReceipt.getServicePoint();
		String getSateliteClinicId = psiMoneyReceipt.getSateliteClinicId();
		Date getServiceDate = psiMoneyReceipt.getMoneyReceiptDate();
		Date birthDate = psiMoneyReceipt.getDob();
		String getTeamNo = psiMoneyReceipt.getTeamNo();
		String getSlipNo = psiMoneyReceipt.getSlipNo();
		String getreferenceId = psiMoneyReceipt.getReferenceId();
		String getreference = psiMoneyReceipt.getReference();
		String getSession = psiMoneyReceipt.getSession();
		String getOthers = psiMoneyReceipt.getOthers();
		String getCspId = psiMoneyReceipt.getCspId();
		String getDataCollector = psiMoneyReceipt.getDataCollector();
		String getEslipNo = psiMoneyReceipt.getEslipNo();
		
		event.put("trackedEntityInstance", trackedEntityInstanceId);
		event.put("orgUnit", orgUit);
		event.put("program", DHISMapper.registrationMapper.get("program"));
		event.put("programStage", DHISMapper.ServiceProvision.get("programStage"));
		event.put("status", "COMPLETED");
		// Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		// String today = dateFormat.format(date);
		String moneyReceiptDate = dateFormat.format(getServiceDate);
		event.put("eventDate", moneyReceiptDate);
		JSONArray dataValues = new JSONArray();
		
		JSONObject clinicName = new JSONObject();
		clinicName.put("dataElement", DHISMapper.ServiceProvision.get("clinicName"));
		clinicName.put("value", getClinicName);
		dataValues.put(clinicName);
		
		JSONObject clinicId = new JSONObject();
		clinicId.put("dataElement", DHISMapper.ServiceProvision.get("clinicId"));
		clinicId.put("value", getClinicCOde);
		dataValues.put(clinicId);
		
		JSONObject servicePoint = new JSONObject();
		servicePoint.put("dataElement", DHISMapper.ServiceProvision.get("servicePoint"));
		servicePoint.put("value", getServicePoint);
		dataValues.put(servicePoint);
		
		JSONObject saletilteClinicId = new JSONObject();
		saletilteClinicId.put("dataElement", DHISMapper.ServiceProvision.get("saletilteClinicId"));
		saletilteClinicId.put("value", getSateliteClinicId);
		dataValues.put(saletilteClinicId);
		
		JSONObject serviceDate = new JSONObject();
		serviceDate.put("dataElement", DHISMapper.ServiceProvision.get("serviceDate"));
		serviceDate.put("value", dateFormat.format(getServiceDate));
		dataValues.put(serviceDate);
		
		JSONObject teamNo = new JSONObject();
		teamNo.put("dataElement", DHISMapper.ServiceProvision.get("teamNo"));
		teamNo.put("value", getTeamNo);
		dataValues.put(teamNo);
		
		JSONObject slipNo = new JSONObject();
		slipNo.put("dataElement", DHISMapper.ServiceProvision.get("slipNo"));
		slipNo.put("value", getSlipNo);
		dataValues.put(slipNo);
		
		JSONObject reference = new JSONObject();
		reference.put("dataElement", DHISMapper.ServiceProvision.get("reference"));
		reference.put("value", getreference);
		dataValues.put(reference);
		
		JSONObject referenceId = new JSONObject();
		referenceId.put("dataElement", DHISMapper.ServiceProvision.get("referenceId"));
		referenceId.put("value", getreferenceId);
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
		discount.put("value", (psiServiceProvision.getDiscount() + psiServiceProvision.getFinancialDiscount()));
		dataValues.put(discount);
		
		JSONObject code = new JSONObject();
		code.put("dataElement", DHISMapper.ServiceProvision.get("code"));
		code.put("value", psiServiceProvision.getCode());
		dataValues.put(code);
		
		JSONObject category = new JSONObject();
		category.put("dataElement", DHISMapper.ServiceProvision.get("category"));
		category.put("value", psiServiceProvision.getCategory());
		dataValues.put(category);
		
		JSONObject session = new JSONObject();
		session.put("dataElement", DHISMapper.ServiceProvision.get("session"));
		session.put("value", getSession);
		dataValues.put(session);
		
		JSONObject sessionName = new JSONObject();
		sessionName.put("dataElement", DHISMapper.ServiceProvision.get("others"));
		sessionName.put("value", getOthers);
		dataValues.put(sessionName);
		
		JSONObject cspId = new JSONObject();
		cspId.put("dataElement", DHISMapper.ServiceProvision.get("cspId"));
		cspId.put("value", getCspId);
		dataValues.put(cspId);
		
		JSONObject dataCollector = new JSONObject();
		dataCollector.put("dataElement", DHISMapper.ServiceProvision.get("dataCollector"));
		dataCollector.put("value", getDataCollector);
		dataValues.put(dataCollector);
		
		String serviceUuid = psiServiceProvision.getUuid();
		JSONObject serviceUuId = new JSONObject();
		serviceUuId.put("dataElement", DHISMapper.ServiceProvision.get("serviceUuid"));
		serviceUuId.put("value", serviceUuid);
		dataValues.put(serviceUuId);
		
		JSONObject eslipNo = new JSONObject();
		eslipNo.put("dataElement", DHISMapper.ServiceProvision.get("eslipNo"));
		eslipNo.put("value", getEslipNo);
		dataValues.put(eslipNo);
		
		/*JSONObject provider = new JSONObject();
		provider.put("dataElement", "sNpMqQaWrUV");
		provider.put("value", psiServiceProvision.getProvider());
		dataValues.put(provider);*/
		
		JSONObject netPayableAmount = new JSONObject();
		netPayableAmount.put("dataElement", DHISMapper.ServiceProvision.get("netPayableAmount"));
		netPayableAmount.put("value", psiServiceProvision.getNetPayable());
		dataValues.put(netPayableAmount);
		event.put("dataValues", dataValues);
		

		
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(birthDate);
		 int year = cal.get(Calendar.YEAR);
		 int month = cal.get(Calendar.MONTH);
		 int day = cal.get(Calendar.DAY_OF_MONTH);
		 
		 LocalDate birthday = LocalDate.of(year,month+1, day);  //Birth date
		
		 Calendar calMoneyReceipt = Calendar.getInstance();
		 calMoneyReceipt.setTime(getServiceDate);
		 int yearMoney = calMoneyReceipt.get(Calendar.YEAR);
		 int monthMoney = calMoneyReceipt.get(Calendar.MONTH);
		 int dayMoney = calMoneyReceipt.get(Calendar.DAY_OF_MONTH);
		 
		LocalDate moneyReceiptLocalDate = LocalDate.of(yearMoney,monthMoney+1, dayMoney);  //Birth date
		Period period = Period.between(birthday, moneyReceiptLocalDate);
		//String ageStringFormat = Integer.toString(period.getYears()) + " Y " + Integer.toString(period.getMonths()) + " M " +  Integer.toString(period.getDays()) + " D";
		//ageMoneyReceipt.put("value", Integer.toString(period.getYears()));
		
		JSONObject ageInYearsMoneyReceipt = new JSONObject();
		ageInYearsMoneyReceipt.put("dataElement", DHISMapper.ServiceProvision.get("ageInYear"));
		ageInYearsMoneyReceipt.put("value", period.getYears());
		dataValues.put(ageInYearsMoneyReceipt);
		
		JSONObject ageInMonthMoneyReceipt = new JSONObject();
		ageInMonthMoneyReceipt.put("dataElement", DHISMapper.ServiceProvision.get("ageInMonth"));
		ageInMonthMoneyReceipt.put("value", period.getMonths());
		dataValues.put(ageInMonthMoneyReceipt);
		
		JSONObject ageInYearMoneyReceipt = new JSONObject();
		ageInYearMoneyReceipt.put("dataElement", DHISMapper.ServiceProvision.get("ageInDay"));
		ageInYearMoneyReceipt.put("value", period.getDays());
		dataValues.put(ageInYearMoneyReceipt);
		
		JSONObject netPayableAfterDiscount = new JSONObject();
		netPayableAfterDiscount.put("dataElement", DHISMapper.ServiceProvision.get("netPayableAfterDiscount"));
		netPayableAfterDiscount.put("value",psiServiceProvision.getPsiMoneyReceiptId().getTotalAmount());
		dataValues.put(netPayableAfterDiscount);
		
		JSONObject overAllDiscount = new JSONObject();
		overAllDiscount.put("dataElement", DHISMapper.ServiceProvision.get("overallDiscount"));
		overAllDiscount.put("value", psiServiceProvision.getPsiMoneyReceiptId().getOverallDiscount());
		dataValues.put(overAllDiscount);
		
		return event;
		
	}
	
	public static JSONObject toConvertDhisHistoricalData(SHNHistoricalDataDTO shnHistoricalDataDTO, String orgUnit)
		    throws JSONException {
			JSONObject event = new JSONObject();
			//event.put("orgUnit", "oIzIrO0I5qu");
			event.put("orgUnit", orgUnit);
			event.put("program", DHISMapper.historicalDataMapper.get("program"));
			event.put("status", "COMPLETED");
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(date);
			event.put("eventDate", today);
			
			JSONArray dataValues = new JSONArray();
			
			JSONObject adoloscent = new JSONObject();
			adoloscent.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Adolescent"));
			adoloscent.put("value", shnHistoricalDataDTO.getHdAdolescent());
			dataValues.put(adoloscent);
			
			JSONObject hdAgeDay = new JSONObject();
			hdAgeDay.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Age_day"));
			hdAgeDay.put("value", shnHistoricalDataDTO.getHdAge_day());
			dataValues.put(hdAgeDay);
			
			JSONObject hdAgeMonth = new JSONObject();
			hdAgeMonth.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Age_month"));
			hdAgeMonth.put("value", shnHistoricalDataDTO.getHdAge_month());
			dataValues.put(hdAgeMonth);
			
			JSONObject gethdageYear = new JSONObject();
			gethdageYear.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Age_year"));
			gethdageYear.put("value", shnHistoricalDataDTO.getHdAge_year());
			dataValues.put(gethdageYear);
			
			JSONObject hdBkNo = new JSONObject();
			hdBkNo.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Bkno"));
			hdBkNo.put("value", shnHistoricalDataDTO.getHdBkno());
			dataValues.put(hdBkNo);
			
			JSONObject hdCard = new JSONObject();
			hdCard.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Card"));
			hdCard.put("value", shnHistoricalDataDTO.getHdCard());
			dataValues.put(hdCard);
			
			JSONObject hdClinicId = new JSONObject();
			hdClinicId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-ClinicId"));
			hdClinicId.put("value", shnHistoricalDataDTO.getHdClinicId());
			dataValues.put(hdClinicId);
			
			JSONObject hdCompId = new JSONObject();
			hdCompId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-compId"));
			hdCompId.put("value", shnHistoricalDataDTO.getHdCompId());
			dataValues.put(hdCompId);
			
			JSONObject hdcomp_id = new JSONObject();
			hdcomp_id.put("dataElement", DHISMapper.historicalDataMapper.get("HD-comp_id"));
			hdcomp_id.put("value", shnHistoricalDataDTO.getHdComp_id());
			dataValues.put(hdcomp_id);
			
			
			JSONObject cspId = new JSONObject();
			cspId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-CSPID"));
			cspId.put("value", shnHistoricalDataDTO.getHdCspId());
			dataValues.put(cspId);
			
			JSONObject hdCustomerId = new JSONObject();
			hdCustomerId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Customer_id"));
			hdCustomerId.put("value", shnHistoricalDataDTO.getHdCustomer_id());
			dataValues.put(hdCustomerId);
			
			JSONObject customerName = new JSONObject();
			customerName.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Customer_name"));
			customerName.put("value", shnHistoricalDataDTO.getHdCustomer_name());
			dataValues.put(customerName);
			
			JSONObject hdDay = new JSONObject();
			hdDay.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Day"));
			hdDay.put("value", shnHistoricalDataDTO.getHdDay());
			dataValues.put(hdDay);
			
			JSONObject hdDtk = new JSONObject();
			hdDtk.put("dataElement", DHISMapper.historicalDataMapper.get("HD-DTK"));
			hdDtk.put("value", shnHistoricalDataDTO.getHdDtk());
			dataValues.put(hdDtk);
			
			JSONObject hdIdNo = new JSONObject();
			hdIdNo.put("dataElement", DHISMapper.historicalDataMapper.get("HD-idno"));
			hdIdNo.put("value", shnHistoricalDataDTO.getHdidno());
			dataValues.put(hdIdNo);
			
			JSONObject kkNo = new JSONObject();
			kkNo.put("dataElement", DHISMapper.historicalDataMapper.get("HD-kkno"));
			kkNo.put("value", shnHistoricalDataDTO.getHdkkno());
			dataValues.put(kkNo);
			
			JSONObject hdMobileNo = new JSONObject();
			hdMobileNo.put("dataElement", DHISMapper.historicalDataMapper.get("HD-MoblieNo"));
			hdMobileNo.put("value", shnHistoricalDataDTO.getHdMoblieno());
			dataValues.put(hdMobileNo);
			
			JSONObject hdMonth = new JSONObject();
			hdMonth.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Month"));
			hdMonth.put("value", shnHistoricalDataDTO.getHdMonth());
			dataValues.put(hdMonth);
			
			JSONObject hdNgoId = new JSONObject();
			hdNgoId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-NGOId"));
			hdNgoId.put("value", shnHistoricalDataDTO.getHdNgoid());
			dataValues.put(hdNgoId);
			
			JSONObject hdPtype = new JSONObject();
			hdPtype.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Ptype"));
			hdPtype.put("value", shnHistoricalDataDTO.getHdPtype());
			dataValues.put(hdPtype);
			
			JSONObject hdQuantity = new JSONObject();
			hdQuantity.put("dataElement", DHISMapper.historicalDataMapper.get("HD-QTY"));
			hdQuantity.put("value", shnHistoricalDataDTO.getHdQty());
			dataValues.put(hdQuantity);
			
			JSONObject hdSateliteId = new JSONObject();
			hdSateliteId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-SatelliteId"));
			hdSateliteId.put("value", shnHistoricalDataDTO.getHdSatelliteId());
			dataValues.put(hdSateliteId);
			
			JSONObject HdServiceCode = new JSONObject();
			HdServiceCode.put("dataElement", DHISMapper.historicalDataMapper.get("HD-ServiceCode"));
			HdServiceCode.put("value", shnHistoricalDataDTO.getHdServiceCode());
			dataValues.put(HdServiceCode);
			
			JSONObject sessionHd = new JSONObject();
			sessionHd.put("dataElement", DHISMapper.historicalDataMapper.get("HD-session"));
			sessionHd.put("value", shnHistoricalDataDTO.getHdsession());
			dataValues.put(sessionHd);
			
			JSONObject sex = new JSONObject();
			sex.put("dataElement", DHISMapper.historicalDataMapper.get("HD-sex"));
			sex.put("value", shnHistoricalDataDTO.getHdsex());
			dataValues.put(sex);
			
			JSONObject hdTaka = new JSONObject();
			hdTaka.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Taka"));
			hdTaka.put("value", shnHistoricalDataDTO.getHdTaka());
			dataValues.put(hdTaka);
			
			JSONObject teamId = new JSONObject();
			teamId.put("dataElement", DHISMapper.historicalDataMapper.get("HD-teamId"));
			teamId.put("value", shnHistoricalDataDTO.getHdteamId());
			dataValues.put(teamId);
			
			JSONObject voucherNo = new JSONObject();
			voucherNo.put("dataElement", DHISMapper.historicalDataMapper.get("HD-voucherno"));
			voucherNo.put("value", shnHistoricalDataDTO.getHdvoucherno());
			dataValues.put(voucherNo);
			
			JSONObject wtq = new JSONObject();
			wtq.put("dataElement", DHISMapper.historicalDataMapper.get("HD-WtQ"));
			wtq.put("value", shnHistoricalDataDTO.getHdWtq());
			dataValues.put(wtq);
			
			JSONObject hdYear = new JSONObject();
			hdYear.put("dataElement", DHISMapper.historicalDataMapper.get("HD-Year"));
			hdYear.put("value", shnHistoricalDataDTO.getHdYear());
			dataValues.put(hdYear);
			
			
			
			event.put("dataValues", dataValues);
			
			return event;
			
		}
	
	
	public static JSONObject toConvertDhisIndicatorData(ShnIndicatorDetailsDTO shnIndicatorDetailsDTO)
		    throws JSONException {
			JSONObject event = new JSONObject();
			//event.put("trackedEntityInstance", "NpKZqkyYZFk");
			event.put("orgUnit", "cyBOiz4GPdX");
			event.put("dataSet", "zPaSPZ5vk4n");
			//event.put("program", DHISMapper.indicatorDataMapper.get("program"));
			//event.put("programStage", "kqTAymIelcm");
			//event.put("status", "COMPLETED");
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(date);
			event.put("completeDate", today);
			DateFormat dateFormatForPeriod = new SimpleDateFormat("yyyyMMdd");
			String period = dateFormatForPeriod.format(date);
			event.put("period", period);
			JSONArray dataValues = new JSONArray();
			
//			JSONObject fpContraceptiveMethod = new JSONObject();
//			fpContraceptiveMethod.put("dataElement", DHISMapper.indicatorDataMapper.get("fpContraceptiveMethod"));
//			fpContraceptiveMethod.put("value", shnIndicatorDetailsDTO.getFpContraceptiveMethod());
//			dataValues.put(fpContraceptiveMethod);
//			
//			JSONObject fpHypertensionAndDiabetic = new JSONObject();
//			fpHypertensionAndDiabetic.put("dataElement", DHISMapper.indicatorDataMapper.get("fpHypertensionAndDiabetic"));
//			fpHypertensionAndDiabetic.put("value", shnIndicatorDetailsDTO.getFpHypertensionAndDiabetic());
//			dataValues.put(fpHypertensionAndDiabetic);
//			
//			JSONObject fpPermanentMethod = new JSONObject();
//			fpPermanentMethod.put("dataElement", DHISMapper.indicatorDataMapper.get("fpPermanentMethod"));
//			fpPermanentMethod.put("value", shnIndicatorDetailsDTO.getFpPermanentMethod());
//			dataValues.put(fpPermanentMethod);
			
			JSONObject calculateAncAllTakenFullCount = new JSONObject();
			calculateAncAllTakenFullCount.put("dataElement", "CnSwzusr2an");
			calculateAncAllTakenFullCount.put("value", shnIndicatorDetailsDTO.getCalculateAncAllTakenFullCount());
			dataValues.put(calculateAncAllTakenFullCount);
			
//			JSONObject fpAncTakenAtleastOne = new JSONObject();
//			fpAncTakenAtleastOne.put("dataElement", DHISMapper.indicatorDataMapper.get("fpAncTakenAtleastOne"));
//			fpAncTakenAtleastOne.put("value", shnIndicatorDetailsDTO.getFpAncTakenAtleastOne());
//			dataValues.put(fpAncTakenAtleastOne);
//			
//			JSONObject calculatePercentageOfFp = new JSONObject();
//			calculatePercentageOfFp.put("dataElement", DHISMapper.indicatorDataMapper.get("calculatePercentageOfFp"));
//			calculatePercentageOfFp.put("value", shnIndicatorDetailsDTO.getCalculatePercentageOfFp());
//			dataValues.put(calculatePercentageOfFp);
			
			event.put("dataValues", dataValues);
			
			return event;
			
		}
}
