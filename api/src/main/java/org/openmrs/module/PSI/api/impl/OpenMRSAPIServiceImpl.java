package org.openmrs.module.PSI.api.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;

public class OpenMRSAPIServiceImpl {
	
	private static final String OPENMRS_BASE_URL = "https://192.168.33.10/openmrs";
	
	public JSONObject get(String payload, String uuid, String URL) throws JSONException {
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + URL + "/" + uuid, payload,
		    "admins", "Sohel@1234");
		return new JSONObject(op.body());
	}
}
