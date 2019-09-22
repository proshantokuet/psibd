package org.openmrs.module.PSI.dhis.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.dhis.service.PSIAPIService;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.springframework.stereotype.Service;

@Service
public class PSIOpenmrsServiceImpl implements PSIAPIService {
	
	private final String OPENMRS_BASE_URL = "https://localhost";
	
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
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + URL, payload, "sohel", "Sohel@123");
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
	
}
