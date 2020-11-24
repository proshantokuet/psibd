package org.openmrs.module.PSI.dhis.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface PSIAPIService {
	
	public JSONObject add(String payload, JSONObject jsonObject, String URL) throws JSONException;
	
	public JSONObject update(String payload, JSONObject jsonObject, String uuid, String URL) throws JSONException;
	
	public JSONObject get(String payload, String uuid, String URL) throws JSONException;
	
	public JSONObject getByQuery(String payload, String URL) throws JSONException;
	
	public JSONObject delete(String payload, String uuid, String URL) throws JSONException;
	
	public JSONObject getFromRemoteOpenMRS(String payload, String uuid, String URL) throws JSONException;
	
	public JSONArray getFromRemoteOpenMRSAsArray(String payload, String uuid, String URL) throws JSONException;
	
	public JSONArray getJsonArray(String payload, String uuid, String URL) throws JSONException;

	
	public JSONObject postInRemoteOpenMRS(String payload, JSONObject jsonObject, String URL) throws JSONException;

	
}
