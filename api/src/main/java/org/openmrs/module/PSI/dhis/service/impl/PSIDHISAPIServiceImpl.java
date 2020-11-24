/**
 * 
 */
package org.openmrs.module.PSI.dhis.service.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.dhis.service.PSIAPIService;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.springframework.stereotype.Service;

/**
 * @author proshanto
 */
@Service
public class PSIDHISAPIServiceImpl implements PSIAPIService {
	
	private final String userName = "apiadmin";
	
	private final String password = "Apiadmin@123";
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#add(java.lang.String, org.json.JSONObject, java.lang.String)
	 */
	@Override
	public JSONObject add(String payload, JSONObject jsonObject, String URL) throws JSONException {
		// TODO Auto-generated method stub
		HttpResponse op = HttpUtil.post(URL, payload, jsonObject.toString(), userName, password);
		return new JSONObject(op.body());
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#update(java.lang.String, org.json.JSONObject, java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject update(String payload, JSONObject jsonObject, String uuid, String URL) throws JSONException {
		
		HttpResponse op = HttpUtil.put(URL, payload, jsonObject.toString(), userName, password);
		return new JSONObject(op.body());
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#get(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject get(String payload, String uuid, String URL) throws JSONException {
		HttpResponse op = HttpUtil.get(URL, payload, userName, password);
		return new JSONObject(op.body());
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#getByQuery(java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject getByQuery(String payload, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#delete(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject delete(String payload, String uuid, String URL) throws JSONException {
		HttpResponse op = HttpUtil.delete(URL, payload, userName, password);
		return new JSONObject(op.body());
	}
	
	@Override
	public JSONObject getFromRemoteOpenMRS(String payload, String uuid, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public JSONArray getFromRemoteOpenMRSAsArray(String payload, String uuid, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject postInRemoteOpenMRS(String payload,
			JSONObject jsonObject, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONArray getJsonArray(String payload, String uuid, String URL)
			throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
