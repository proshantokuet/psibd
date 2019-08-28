package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicOutwardReferral;
import org.openmrs.module.PSI.api.PSIClinicOutwardReferralService;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.converter.PSIClinicOutwardReferralConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/outward-referral")
@RestController
public class PSIClinicOutwardReferralController {
	
	@RequestMapping(value = "/add-or-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String>saveOutwardReferral(@RequestBody String jString) throws Exception {
		
		JSONObject psiOutwardJsonObject = new JSONObject(jString);
		PSIClinicOutwardReferral psiClinicOutwardReferral = new PSIClinicOutwardReferral();
		int outwardReferralId = 0;
		
		try {
			
			if (psiOutwardJsonObject.has("outwardReferralId")) {
				if (!psiOutwardJsonObject.getString("outwardReferralId").equalsIgnoreCase("")) {
					outwardReferralId = Integer.parseInt(psiOutwardJsonObject.getString("outwardReferralId"));
					psiClinicOutwardReferral.setOutwardReferralId(outwardReferralId);
				}
			}
			
			if (psiOutwardJsonObject.has("referredTo")) {
				psiClinicOutwardReferral.setReferredTo(psiOutwardJsonObject.getString("referredTo"));
			}
			
			if (psiOutwardJsonObject.has("provisonalDiagnosis")) {
				psiClinicOutwardReferral.setProvisonalDiagnosis(psiOutwardJsonObject.getString("provisonalDiagnosis"));
			}
			
			if (psiOutwardJsonObject.has("referralReason")) {
				psiClinicOutwardReferral.setReferralReason(psiOutwardJsonObject.getString("referralReason"));
			}
			
			if (psiOutwardJsonObject.has("chiefComplaints")) {
				psiClinicOutwardReferral.setChiefComplaints(psiOutwardJsonObject.getString("chiefComplaints"));
			}
			
			if (psiOutwardJsonObject.has("patientUuid")) {
				psiClinicOutwardReferral.setPatientUuid(psiOutwardJsonObject.getString("patientUuid"));
			}
			
			if (psiOutwardJsonObject.has("clientInformation")) {
				JSONObject clientJsonObject = new JSONObject(psiOutwardJsonObject.getString("clientInformation"));
				psiClinicOutwardReferral.setAllergy(clientJsonObject.getString("allergy"));
				psiClinicOutwardReferral.setAirway(clientJsonObject.getString("airway"));
				psiClinicOutwardReferral.setMedication(clientJsonObject.getString("medication"));
				psiClinicOutwardReferral.setBreathing(clientJsonObject.getString("breathing"));
				psiClinicOutwardReferral.setPastMedicalHistory(clientJsonObject.getString("pastMedicalHistory"));
				psiClinicOutwardReferral.setCirculation(clientJsonObject.getString("circulation"));
				psiClinicOutwardReferral.setLastMeal(clientJsonObject.getString("lastMeal"));
				psiClinicOutwardReferral.setConscious(clientJsonObject.getString("conscious"));
				psiClinicOutwardReferral.setEvents(clientJsonObject.getString("events"));
				psiClinicOutwardReferral.setExposure(clientJsonObject.getString("exposure"));
			}
			psiClinicOutwardReferral.setDateCreated(new Date());
			psiClinicOutwardReferral.setCreator(Context.getAuthenticatedUser());
			psiClinicOutwardReferral.setUuid(UUID.randomUUID().toString());
			psiClinicOutwardReferral = Context.getService(PSIClinicOutwardReferralService.class).saveOrUpdate(psiClinicOutwardReferral);
			
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<String>(psiClinicOutwardReferral.getOutwardReferralId() + "", HttpStatus.OK);
		 
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
		try {
			Context.getService(PSIClinicOutwardReferralService.class).delete(id);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-by-patient-uuid/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findByPatientUuid(@PathVariable String id) throws Exception {
		List<PSIClinicOutwardReferral> psiClinicOutwardReferrals = new ArrayList<PSIClinicOutwardReferral>();
		JSONArray psiOutwardReferralJsonArray = new JSONArray();
		try {
			psiClinicOutwardReferrals = Context.getService(PSIClinicOutwardReferralService.class).findByPatientUuid(id);
			if (psiClinicOutwardReferrals != null) {
				psiOutwardReferralJsonArray = new PSIClinicOutwardReferralConverter().toConvert(psiClinicOutwardReferrals);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiOutwardReferralJsonArray.toString(), HttpStatus.OK);
	}
	
	
}
