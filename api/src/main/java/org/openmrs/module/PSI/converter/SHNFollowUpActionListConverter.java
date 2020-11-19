package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.SHNFollowUpAction;

public class SHNFollowUpActionListConverter {
	public JSONObject toConvert(SHNFollowUpAction shnFollowUpAction) throws JSONException {
		JSONObject followUp = new JSONObject();
		followUp.putOpt("encounterUuid", shnFollowUpAction.getEncounterUuid());
		followUp.putOpt("visitUuid", shnFollowUpAction.getVisitUuid());
		followUp.putOpt("valueCoded", shnFollowUpAction.getValueCoded());
		followUp.putOpt("codedConceptName",shnFollowUpAction.getCodedConceptName());
		followUp.putOpt("contactDateWithTimeStamp", shnFollowUpAction.getContactDate());
		followUp.putOpt("isResponded", shnFollowUpAction.getIsResponded());
		followUp.putOpt("respondResult", shnFollowUpAction.getRespondResult());
		followUp.putOpt("uuid", shnFollowUpAction.getUuid());
		followUp.putOpt("dateChangedWithTimestamp", shnFollowUpAction.getLastTimeStamp());
		followUp.putOpt("followUpCounts", shnFollowUpAction.getFollowUpCounts());
		followUp.putOpt("valueChanged", shnFollowUpAction.getValueChanged());
		
		return followUp;
		
	}
	
	public JSONArray toConvert(List<SHNFollowUpAction> shnFollowUpActions) throws JSONException {
		JSONArray foloUpActionJsonArray = new JSONArray();
		for (SHNFollowUpAction shnUpAction : shnFollowUpActions) {
			foloUpActionJsonArray.put(toConvert(shnUpAction));
		}
		return foloUpActionJsonArray;
		
	}
}
