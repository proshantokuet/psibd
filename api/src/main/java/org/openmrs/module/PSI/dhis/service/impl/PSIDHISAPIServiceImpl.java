/**
 * 
 */
package org.openmrs.module.PSI.dhis.service.impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.dhis.service.PSIAPIService;
import org.springframework.stereotype.Service;

/**
 * @author proshanto
 */
@Service
public class PSIDHISAPIServiceImpl implements PSIAPIService {
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#add(java.lang.String, org.json.JSONObject, java.lang.String)
	 */
	@Override
	public JSONObject add(String payload, JSONObject jsonObject, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#update(java.lang.String, org.json.JSONObject, java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject update(String payload, JSONObject jsonObject, String uuid, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.PSI.dhis.service.PSIAPIService#get(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public JSONObject get(String payload, String uuid, String URL) throws JSONException {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}
	
}
