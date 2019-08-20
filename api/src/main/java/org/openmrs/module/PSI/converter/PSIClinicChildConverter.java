package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIClinicChild;

public class PSIClinicChildConverter {
	
	public JSONObject toConvert(PSIClinicChild psiClinicChild) throws JSONException {
		JSONObject psiClinicChildObject = new JSONObject();
		psiClinicChildObject.putOpt("outcomeNo", psiClinicChild.getOutComeNo());
		psiClinicChildObject.putOpt("result", psiClinicChild.getResult());
		psiClinicChildObject.putOpt("outcomeDate", psiClinicChild.getOutComeDate());
		psiClinicChildObject.putOpt("complications", psiClinicChild.getComplications());
		psiClinicChildObject.putOpt("typeOfDelivery", psiClinicChild.getTypeOfDelivery());
		psiClinicChildObject.putOpt("sex", psiClinicChild.getSex());
		psiClinicChildObject.putOpt("birthWeight", psiClinicChild.getBirthWeight());
		psiClinicChildObject.putOpt("vaccine", psiClinicChild.getVaccine());
		psiClinicChildObject.putOpt("healthStatus", psiClinicChild.getLastHealthStatus());
		psiClinicChildObject.putOpt("motherUuid", psiClinicChild.getMotherUuid());
		return psiClinicChildObject;
	}
	
	
	public JSONArray toConvert(List<PSIClinicChild> psiClinicChilds) throws JSONException {
		JSONArray psiChildJsonArray = new JSONArray();
		for (PSIClinicChild psiClinicChild : psiClinicChilds) {
			psiChildJsonArray.put(toConvert(psiClinicChild));
		}
		return psiChildJsonArray;
		
	}

}
