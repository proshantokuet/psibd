package org.openmrs.module.PSI.converter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.module.PSI.PSIClinicOutwardReferral;

public class PSIClinicOutwardReferralConverter {
	
	public JSONObject toConvert(PSIClinicOutwardReferral psiClinicOutwardReferral) throws JSONException {
		JSONObject psiOutwardReferralObject = new JSONObject();
		psiOutwardReferralObject.putOpt("outwardReferralId", psiClinicOutwardReferral.getOutwardReferralId());
		psiOutwardReferralObject.putOpt("referredTo", psiClinicOutwardReferral.getReferredTo());
		psiOutwardReferralObject.putOpt("provisonalDiagnosis", psiClinicOutwardReferral.getProvisonalDiagnosis());
		psiOutwardReferralObject.putOpt("cheiefComplaints", psiClinicOutwardReferral.getChiefComplaints());
		psiOutwardReferralObject.putOpt("referralReason", psiClinicOutwardReferral.getReferralReason());
		psiOutwardReferralObject.putOpt("patientUuid", psiClinicOutwardReferral.getPatientUuid());
		psiOutwardReferralObject.putOpt("allergy", psiClinicOutwardReferral.getAllergy());
		psiOutwardReferralObject.putOpt("airway", psiClinicOutwardReferral.getAirway());
		psiOutwardReferralObject.putOpt("medication", psiClinicOutwardReferral.getMedication());
		psiOutwardReferralObject.putOpt("breathing", psiClinicOutwardReferral.getBreathing());
		psiOutwardReferralObject.putOpt("pastMedicalHistory", psiClinicOutwardReferral.getPastMedicalHistory());
		psiOutwardReferralObject.putOpt("circulation", psiClinicOutwardReferral.getCirculation());
		psiOutwardReferralObject.putOpt("lastMeal", psiClinicOutwardReferral.getLastMeal());
		psiOutwardReferralObject.putOpt("conscious", psiClinicOutwardReferral.getConscious());
		psiOutwardReferralObject.putOpt("events", psiClinicOutwardReferral.getEvents());
		psiOutwardReferralObject.putOpt("exposure", psiClinicOutwardReferral.getExposure());
		return psiOutwardReferralObject;
	}
	
	public JSONArray toConvert(List<PSIClinicOutwardReferral> psiClinicOutwardReferrals) throws JSONException {

		JSONArray outwardReferralsList = new JSONArray();
		for (PSIClinicOutwardReferral psiClinicOutwardReferral : psiClinicOutwardReferrals) {
			outwardReferralsList.put(toConvert(psiClinicOutwardReferral));
		}
		return outwardReferralsList;
	}

}
