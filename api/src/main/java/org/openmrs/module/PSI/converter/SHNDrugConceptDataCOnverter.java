package org.openmrs.module.PSI.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.Drug;

public class SHNDrugConceptDataCOnverter {
	public JSONObject toConvert(Drug drugObject,int eventId) throws JSONException {
		JSONObject shnDrugDataObject = new JSONObject();
		shnDrugDataObject.putOpt("drugId", drugObject.getDrugId());
		shnDrugDataObject.putOpt("name", drugObject.getName());
		shnDrugDataObject.putOpt("uuid", drugObject.getUuid());
		shnDrugDataObject.putOpt("description", drugObject.getDescription());
		shnDrugDataObject.putOpt("dosageFrom", drugObject.getDosageForm().getUuid());
		shnDrugDataObject.putOpt("maximumDailyDose", drugObject.getMaximumDailyDose());
		shnDrugDataObject.putOpt("minimumDailyDose", drugObject.getMinimumDailyDose());
		shnDrugDataObject.putOpt("concept", drugObject.getConcept().getUuid());
		shnDrugDataObject.putOpt("combination", drugObject.getCombination());
		shnDrugDataObject.putOpt("strength", drugObject.getStrength());
		shnDrugDataObject.putOpt("eventId", eventId);
		shnDrugDataObject.putOpt("retired", drugObject.getRetired());
		shnDrugDataObject.putOpt("retireReason", drugObject.getRetireReason());
		return shnDrugDataObject;
	}
}
