package org.openmrs.module.PSI.dhis.service.impl;

import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.dhis.service.PSIAPIService;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.springframework.stereotype.Service;

@Service
public class PSIOpenmrsServiceImpl implements PSIAPIService {
	
	private final String OPENMRS_BASE_URL = "https://localhost";
	//private final String CENTRAL_OPENMRS_BASE_URL = "https://localhost/openmrs/ws";
	//private final String CENTRAL_OPENMRS_BASE_URL = "https://182.160.99.132/openmrs/ws";
	private static ResourceBundle resourceFOrOpenmrs = ResourceBundle.getBundle("deploymentConfig");
	private final static String CENTRAL_OPENMRS_BASE_URL = resourceFOrOpenmrs.getString("centralOpenmrsBaseUrl");
	//private final String CENTRAL_OPENMRS_BASE_URL = "https://192.168.19.158/openmrs/ws";
	
	@Override
	public JSONObject add(String payload, JSONObject jsonObject, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JSONObject update(String payload, JSONObject jsonObject, String uuid, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JSONObject get(String payload, String uuid, String URL) throws JSONException {
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + URL, payload, "admin", "test");
		return new JSONObject(op.body());
	}
	
	@Override
	public JSONObject getByQuery(String payload, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JSONObject delete(String payload, String uuid, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JSONObject getFromRemoteOpenMRS(String payload, String uuid, String URL) throws JSONException {
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(CENTRAL_OPENMRS_BASE_URL) + URL, payload, "admin",
		    "test");
		return new JSONObject(op.body());
	}
	
	@Override
	public JSONArray getFromRemoteOpenMRSAsArray(String payload, String uuid, String URL) throws JSONException {
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(CENTRAL_OPENMRS_BASE_URL) + URL, payload, "admin",
		    "test");
		return new JSONArray(op.body());
	}

	@Override
	public JSONObject postInRemoteOpenMRS(String payload,
			JSONObject jsonObject, String URL) throws JSONException {
		// TODO Auto-generated method stub
		HttpResponse op = HttpUtil.post(HttpUtil.removeEndingSlash(CENTRAL_OPENMRS_BASE_URL) + URL, payload, jsonObject.toString(), "admin", "test");
		return new JSONObject(op.body());
	}

	@Override
	public JSONArray getJsonArray(String payload, String uuid, String URL)
			throws JSONException {
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + URL, payload, "admin","test");
		return new JSONArray(op.body());
	}
	
}
