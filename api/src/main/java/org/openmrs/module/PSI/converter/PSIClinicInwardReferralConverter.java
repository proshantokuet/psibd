package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIClinicInwardReferral;

public class PSIClinicInwardReferralConverter {
	
	public JSONObject toConvert(PSIClinicInwardReferral psiClinicInwardReferral) throws JSONException {
		JSONObject psiInwardReferralObject = new JSONObject();
		psiInwardReferralObject.putOpt("inwardReferralId", psiClinicInwardReferral.getInwardReferralId());
		psiInwardReferralObject.putOpt("referralNo", psiClinicInwardReferral.getReferralNo());
		psiInwardReferralObject.putOpt("referralDate", psiClinicInwardReferral.getReferralDate());
		psiInwardReferralObject.putOpt("referredBy", psiClinicInwardReferral.getReferredBy());
		psiInwardReferralObject.putOpt("cspId", psiClinicInwardReferral.getCspId());
		psiInwardReferralObject.putOpt("notes", psiClinicInwardReferral.getNotes());
		psiInwardReferralObject.putOpt("patientUuid", psiClinicInwardReferral.getPatientUuid());
		return psiInwardReferralObject;
	}
	
	public JSONArray toConvert(List<PSIClinicInwardReferral> psiClinicInwardReferrals) throws JSONException {

		JSONArray inwardReferralsList = new JSONArray();
		for (PSIClinicInwardReferral psiClinicInwardReferral : psiClinicInwardReferrals) {
			inwardReferralsList.put(toConvert(psiClinicInwardReferral));
		}
		return inwardReferralsList;
	}
}
