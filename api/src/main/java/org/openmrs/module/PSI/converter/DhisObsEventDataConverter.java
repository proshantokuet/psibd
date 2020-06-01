package org.openmrs.module.PSI.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class DhisObsEventDataConverter {
	
	public static  JSONObject getEventMetaDataForDhis(String trackeEntityInstance, String orgUnit){
		JSONObject serviceEvents = new JSONObject();
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(date);
			
			JSONObject clientHistory = new JSONObject();
			clientHistory.put("trackedEntityInstance", trackeEntityInstance);
			clientHistory.put("orgUnit", orgUnit);
			clientHistory.put("program", "q2uZRqRc0UD");
			clientHistory.put("programStage", "gMhLM145zph");
			clientHistory.put("status", "COMPLETED");
			clientHistory.put("eventDate", today);
			serviceEvents.put("Client History", clientHistory);
			
			JSONObject generalExamination = new JSONObject();
			generalExamination.put("trackedEntityInstance", trackeEntityInstance);
			generalExamination.put("orgUnit", orgUnit);
			generalExamination.put("program", "q2uZRqRc0UD");
			generalExamination.put("programStage", "MsQuyOp8mRh");
			generalExamination.put("status", "COMPLETED");
			generalExamination.put("eventDate", today);
			serviceEvents.put("General Examination", generalExamination);
			
			JSONObject delivery = new JSONObject();
			delivery.put("trackedEntityInstance", trackeEntityInstance);
			delivery.put("orgUnit", orgUnit);
			delivery.put("program", "q2uZRqRc0UD");
			delivery.put("programStage", "IuoFEwM0dP4");
			delivery.put("status", "COMPLETED");
			delivery.put("eventDate", today);
			serviceEvents.put("Delivery", delivery);
			
			JSONObject lcc = new JSONObject();
			lcc.put("trackedEntityInstance", trackeEntityInstance);
			lcc.put("orgUnit", orgUnit);
			lcc.put("program", "q2uZRqRc0UD");
			lcc.put("programStage", "sETH3JvTMR4");
			lcc.put("status", "COMPLETED");
			lcc.put("eventDate", today);
			serviceEvents.put("Limited Curative Care", lcc);
			
			JSONObject womenVaccination = new JSONObject();
			womenVaccination.put("trackedEntityInstance", trackeEntityInstance);
			womenVaccination.put("orgUnit", orgUnit);
			womenVaccination.put("program", "q2uZRqRc0UD");
			womenVaccination.put("programStage", "OAVc5c3vIiV");
			womenVaccination.put("status", "COMPLETED");
			womenVaccination.put("eventDate", today);
			serviceEvents.put("Women Vaccination 15 to 49 Years old", womenVaccination);
			
			JSONObject stiAndRti = new JSONObject();
			stiAndRti.put("trackedEntityInstance", trackeEntityInstance);
			stiAndRti.put("orgUnit", orgUnit);
			stiAndRti.put("program", "q2uZRqRc0UD");
			stiAndRti.put("programStage", "bwq2DoZANeK");
			stiAndRti.put("status", "COMPLETED");
			stiAndRti.put("eventDate", today);
			serviceEvents.put("STI and RTI", stiAndRti);
			
			JSONObject cervicalCancer = new JSONObject();
			stiAndRti.put("trackedEntityInstance", trackeEntityInstance);
			stiAndRti.put("orgUnit", orgUnit);
			stiAndRti.put("program", "q2uZRqRc0UD");
			stiAndRti.put("programStage", "AhNyAtFbZ32");
			stiAndRti.put("status", "COMPLETED");
			stiAndRti.put("eventDate", today);
			serviceEvents.put("Cervical Cancer", cervicalCancer);
			
			JSONObject familyPlanning = new JSONObject();
			familyPlanning.put("trackedEntityInstance", trackeEntityInstance);
			familyPlanning.put("orgUnit", orgUnit);
			familyPlanning.put("program", "q2uZRqRc0UD");
			familyPlanning.put("programStage", "vLyly6fRKFX");
			familyPlanning.put("status", "COMPLETED");
			familyPlanning.put("eventDate", today);
			serviceEvents.put("Family Planning", familyPlanning);
			
			JSONObject pacs = new JSONObject();
			pacs.put("trackedEntityInstance", trackeEntityInstance);
			pacs.put("orgUnit", orgUnit);
			pacs.put("program", "q2uZRqRc0UD");
			pacs.put("programStage", "SCOvq6tbHep");
			pacs.put("status", "COMPLETED");
			pacs.put("eventDate", today);
			serviceEvents.put("Post Abortion Care", pacs);
			
			JSONObject eyeCare = new JSONObject();
			eyeCare.put("trackedEntityInstance", trackeEntityInstance);
			eyeCare.put("orgUnit", orgUnit);
			eyeCare.put("program", "q2uZRqRc0UD");
			eyeCare.put("programStage", "e3rWjcoeYYt");
			eyeCare.put("status", "COMPLETED");
			eyeCare.put("eventDate", today);
			serviceEvents.put("Eye Care", eyeCare);
			
			JSONObject discharge = new JSONObject();
			discharge.put("trackedEntityInstance", trackeEntityInstance);
			discharge.put("orgUnit", orgUnit);
			discharge.put("program", "q2uZRqRc0UD");
			discharge.put("programStage", "WmTXHPPpxU3");
			discharge.put("status", "COMPLETED");
			discharge.put("eventDate", today);
			serviceEvents.put("Discharge Certificate", discharge);
			
			JSONObject antenatalCare = new JSONObject();
			antenatalCare.put("trackedEntityInstance", trackeEntityInstance);
			antenatalCare.put("orgUnit", orgUnit);
			antenatalCare.put("program", "q2uZRqRc0UD");
			antenatalCare.put("programStage", "yljgHb76CZm");
			antenatalCare.put("status", "COMPLETED");
			antenatalCare.put("eventDate", today);
			serviceEvents.put("Antenatal Care", antenatalCare);
			
			JSONObject pnc = new JSONObject();
			pnc.put("trackedEntityInstance", trackeEntityInstance);
			pnc.put("orgUnit", orgUnit);
			pnc.put("program", "q2uZRqRc0UD");
			pnc.put("programStage", "rEqTh49ChIx");
			pnc.put("status", "COMPLETED");
			pnc.put("eventDate", today);
			serviceEvents.put("Postnatal Care", pnc);
			
			JSONObject vaccinationChild = new JSONObject();
			vaccinationChild.put("trackedEntityInstance", trackeEntityInstance);
			vaccinationChild.put("orgUnit", orgUnit);
			vaccinationChild.put("program", "q2uZRqRc0UD");
			vaccinationChild.put("programStage", "GNQSZ6ZMnys");
			vaccinationChild.put("status", "COMPLETED");
			vaccinationChild.put("eventDate", today);
			serviceEvents.put("Vaccination for Child", vaccinationChild);
			
			JSONObject adolescent = new JSONObject();
			adolescent.put("trackedEntityInstance", trackeEntityInstance);
			adolescent.put("orgUnit", orgUnit);
			adolescent.put("program", "q2uZRqRc0UD");
			adolescent.put("programStage", "DDrycKd33cw");
			adolescent.put("status", "COMPLETED");
			adolescent.put("eventDate", today);
			serviceEvents.put("Adolescent Health", adolescent);
			
			JSONObject firstAid = new JSONObject();
			firstAid.put("trackedEntityInstance", trackeEntityInstance);
			firstAid.put("orgUnit", orgUnit);
			firstAid.put("program", "q2uZRqRc0UD");
			firstAid.put("programStage", "i8djL6jzrxP");
			firstAid.put("status", "COMPLETED");
			firstAid.put("eventDate", today);
			serviceEvents.put("First Aid", firstAid);
			
			JSONObject imciChild = new JSONObject();
			imciChild.put("trackedEntityInstance", trackeEntityInstance);
			imciChild.put("orgUnit", orgUnit);
			imciChild.put("program", "q2uZRqRc0UD");
			imciChild.put("programStage", "OZ61jJpZOEn");
			imciChild.put("status", "COMPLETED");
			imciChild.put("eventDate", today);
			serviceEvents.put("IMCI (age below 2 months)", imciChild);
			
			JSONObject imciAdult = new JSONObject();
			imciAdult.put("trackedEntityInstance", trackeEntityInstance);
			imciAdult.put("orgUnit", orgUnit);
			imciAdult.put("program", "q2uZRqRc0UD");
			imciAdult.put("programStage", "wMSY39GW3sl");
			imciAdult.put("status", "COMPLETED");
			imciAdult.put("eventDate", today);
			serviceEvents.put("IMCI (age 2 months to 5 years)", imciAdult);
			
			JSONObject obstetric = new JSONObject();
			obstetric.put("trackedEntityInstance", trackeEntityInstance);
			obstetric.put("orgUnit", orgUnit);
			obstetric.put("program", "q2uZRqRc0UD");
			obstetric.put("programStage", "rJGtR1OgqNV");
			obstetric.put("status", "COMPLETED");
			obstetric.put("eventDate", today);
			serviceEvents.put("Obstetric History", obstetric);
			
			JSONObject newBorn = new JSONObject();
			newBorn.put("trackedEntityInstance", trackeEntityInstance);
			newBorn.put("orgUnit", orgUnit);
			newBorn.put("program", "q2uZRqRc0UD");
			newBorn.put("programStage", "m0rBW5OOwQO");
			newBorn.put("status", "COMPLETED");
			newBorn.put("eventDate", today);
			serviceEvents.put("New Born Baby Care", newBorn);
			
			JSONObject generalVaccination = new JSONObject();
			generalVaccination.put("trackedEntityInstance", trackeEntityInstance);
			generalVaccination.put("orgUnit", orgUnit);
			generalVaccination.put("program", "q2uZRqRc0UD");
			generalVaccination.put("programStage", "d2dVaBF6cqY");
			generalVaccination.put("status", "COMPLETED");
			generalVaccination.put("eventDate", today);
			serviceEvents.put("General Vaccination", generalVaccination);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		    return serviceEvents;
	}
}
