package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIClinicSpot;

public class PSIClinicSpotConverter {
	
	public JSONObject toConvert(PSIClinicSpot psiClinicSpot) throws JSONException {
		
		JSONObject psiClinicSpotObject = new JSONObject();
		psiClinicSpotObject.putOpt("ccsid", psiClinicSpot.getCcsid());
		psiClinicSpotObject.putOpt("name", psiClinicSpot.getName());
		psiClinicSpotObject.putOpt("code", psiClinicSpot.getCode());
		psiClinicSpotObject.putOpt("address", psiClinicSpot.getAddress());
		psiClinicSpotObject.putOpt("dhisId", psiClinicSpot.getDhisId());
		psiClinicSpotObject.putOpt("psiClinicManagement", psiClinicSpot.getPsiClinicManagement());
		return psiClinicSpotObject;
	}
	
	
	public JSONArray toConvert(List<PSIClinicSpot> psiClinicSpots) throws JSONException {
		JSONArray psiclinicSpotJsonArray = new JSONArray();
		for (PSIClinicSpot psiClinicSpot : psiClinicSpots) {
			psiclinicSpotJsonArray.put(toConvert(psiClinicSpot));
		}
		return psiclinicSpotJsonArray;
		
	}

}
