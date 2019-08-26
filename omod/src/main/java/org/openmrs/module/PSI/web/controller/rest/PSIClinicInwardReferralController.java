package org.openmrs.module.PSI.web.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicInwardReferral;
import org.openmrs.module.PSI.api.PSIClinicInwardReferralService;
import org.openmrs.module.PSI.converter.PSIClinicInwardReferralConverter;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/rest/v1/inward-referral")
@RestController
public class PSIClinicInwardReferralController extends MainResourceController {
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping(value = "/add-or-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> saveInwardReferral(@RequestBody String jString) throws Exception {
		JSONObject inwardReferralJsonObject = new JSONObject(jString);
		PSIClinicInwardReferral psiClinicInwardReferral = new PSIClinicInwardReferral();
		int inwardReferralId = 0;
		
		try {
			
			if (inwardReferralJsonObject.has("inwardReferralId")) {
				if (!inwardReferralJsonObject.getString("inwardReferralId").equalsIgnoreCase("")) {
					inwardReferralId = Integer.parseInt(inwardReferralJsonObject.getString("inwardReferralId"));
					psiClinicInwardReferral.setInwardReferralId(inwardReferralId);
				}
			}
			
			if (inwardReferralJsonObject.has("referralNo")) {
				psiClinicInwardReferral.setReferralNo(inwardReferralJsonObject.getString("referralNo"));
			}
			
			if (inwardReferralJsonObject.has("referralDate")) {
				psiClinicInwardReferral.setReferralDate(yyyyMMdd.parse(inwardReferralJsonObject.getString("referralDate")));
			}
			
			if (inwardReferralJsonObject.has("referredBy")) {
				psiClinicInwardReferral.setReferredBy(inwardReferralJsonObject.getString("referredBy"));
			}
			
			if (inwardReferralJsonObject.has("cspId")) {
				psiClinicInwardReferral.setCspId(inwardReferralJsonObject.getString("cspId"));
			}
			
			if (inwardReferralJsonObject.has("notes")) {
				psiClinicInwardReferral.setNotes(inwardReferralJsonObject.getString("notes"));
			}
			
			if (inwardReferralJsonObject.has("patientUuid")) {
				psiClinicInwardReferral.setPatientUuid(inwardReferralJsonObject.getString("patientUuid"));
			}
			
			psiClinicInwardReferral.setDateCreated(new Date());
			psiClinicInwardReferral.setCreator(Context.getAuthenticatedUser());
			psiClinicInwardReferral.setUuid(UUID.randomUUID().toString());

			psiClinicInwardReferral = Context.getService(PSIClinicInwardReferralService.class).saveOrUpdate(psiClinicInwardReferral);
			
		} 
		catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		
			return new ResponseEntity<String>(psiClinicInwardReferral.getInwardReferralId() + "", HttpStatus.OK);
		}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
		try {
			Context.getService(PSIClinicInwardReferralService.class).delete(id);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-by-referral-no/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findByReferralNo(@PathVariable String id) throws Exception {
		PSIClinicInwardReferral psiInwardReferral = new PSIClinicInwardReferral();
		JSONObject psiInwardReferralObject = new JSONObject();
		try {
			psiInwardReferral = Context.getService(PSIClinicInwardReferralService.class).findByReferralNo(id);
			if (psiInwardReferral != null) {
				psiInwardReferralObject = new PSIClinicInwardReferralConverter().toConvert(psiInwardReferral);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiInwardReferralObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-by-patient-uuid/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findByPatientUuid(@PathVariable String id) throws Exception {
		List<PSIClinicInwardReferral> psiInwardReferrals = new ArrayList<PSIClinicInwardReferral>();
		JSONArray psiInwardReferralArray = new JSONArray();
		try {
			psiInwardReferrals = Context.getService(PSIClinicInwardReferralService.class).findByPatientUuid(id);
			if (psiInwardReferrals != null) {
				psiInwardReferralArray = new PSIClinicInwardReferralConverter().toConvert(psiInwardReferrals);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiInwardReferralArray.toString(), HttpStatus.OK);
	}

}
