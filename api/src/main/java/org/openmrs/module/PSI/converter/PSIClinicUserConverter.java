package org.openmrs.module.PSI.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIClinicUser;

public class PSIClinicUserConverter {
	
	public JSONObject toConvertClinic(PSIClinicUser psiClinicUser) throws JSONException {
		JSONObject clinic = new JSONObject();
		clinic.putOpt("clinicName", psiClinicUser.getPsiClinicManagementId().getName());
		clinic.putOpt("clinicCode", psiClinicUser.getPsiClinicManagementId().getClinicId());
		clinic.putOpt("clinicId", psiClinicUser.getPsiClinicManagementId().getCid());
		return clinic;
		
	}
}
