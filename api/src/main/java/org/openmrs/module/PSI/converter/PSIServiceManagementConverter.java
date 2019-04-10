package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIServiceManagement;

public class PSIServiceManagementConverter {
	
	public JSONObject toConvert(PSIServiceManagement psiServiceManagement) throws JSONException {
		JSONObject service = new JSONObject();
		service.putOpt("sid", psiServiceManagement.getSid());
		service.putOpt("name", psiServiceManagement.getName());
		service.putOpt("code", psiServiceManagement.getCode());
		service.putOpt("category", psiServiceManagement.getCategory());
		service.putOpt("provider", psiServiceManagement.getProvider());
		service.putOpt("unitCost", psiServiceManagement.getUnitCost());
		return service;
		
	}
	
	public JSONArray toConvert(List<PSIServiceManagement> psiServiceManagements) throws JSONException {
		JSONArray serviceManagements = new JSONArray();
		for (PSIServiceManagement psiServiceManagement : psiServiceManagements) {
			serviceManagements.put(toConvert(psiServiceManagement));
		}
		return serviceManagements;
		
	}
}
