package org.openmrs.module.PSI.converter;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIClinicUser;

public class PSIClinicUserConverter {
	
	public JSONObject toConvertClinic(PSIClinicUser psiClinicUser) throws JSONException {
		JSONObject clinic = new JSONObject();
		clinic.putOpt("clinicName", psiClinicUser.getPsiClinicManagementId().getName());
		clinic.putOpt("clinicId", psiClinicUser.getPsiClinicManagementId().getClinicId());
		clinic.putOpt("id", psiClinicUser.getPsiClinicManagementId().getCid());
		clinic.putOpt("uuid", psiClinicUser.getPsiClinicManagementId().getUuid());
		clinic.putOpt("orgUnit", psiClinicUser.getPsiClinicManagementId().getDhisId());
		return clinic;
		
	}
}
