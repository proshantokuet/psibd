package org.openmrs.module.PSI.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

public class DhisObsEventDataConverter {
	
	public static  JSONObject getEventMetaDataForDhis(String trackeEntityInstance, String orgUnit){
		JSONObject serviceEvents = new JSONObject();
		JSONObject clientHistory = new JSONObject();
		try {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String today = dateFormat.format(date);
			
			clientHistory.put("trackedEntityInstance", trackeEntityInstance);
			clientHistory.put("orgUnit", orgUnit);
			clientHistory.put("program", "q2uZRqRc0UD");
			clientHistory.put("programStage", "qfKF04HR0lU");
			clientHistory.put("status", "COMPLETED");
			clientHistory.put("eventDate", today);
			serviceEvents.put("Client History", clientHistory);
			
			JSONObject inwardReferral = new JSONObject();
			inwardReferral.put("trackedEntityInstance", "inwardReferral");
			inwardReferral.put("orgUnit", "orgUnitinwardReferral");
			inwardReferral.put("program", "programinwardReferral");
			inwardReferral.put("programStage", "programStage inwardReferral");
			inwardReferral.put("status", "COMPLETED");
			inwardReferral.put("eventDate", today);
			serviceEvents.put("Inward Referral", inwardReferral);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		    return serviceEvents;
	}
}
