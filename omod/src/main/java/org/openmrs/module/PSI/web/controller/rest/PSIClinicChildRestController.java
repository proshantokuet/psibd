package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicChild;
import org.openmrs.module.PSI.api.PSIClinicChildService;
import org.openmrs.module.PSI.converter.PSIClinicChildConverter;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/child-info")
@RestController
public class PSIClinicChildRestController extends MainResourceController {
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");

	@RequestMapping(value = "/add-or-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addChildInformation(
			@RequestBody String jsonStr) throws Exception {
		JSONObject childInfo = new JSONObject(jsonStr);
		PSIClinicChild psiClinicChild = new PSIClinicChild();

		try {
			
			if (childInfo.has("outcomeNo")) {
				psiClinicChild.setOutComeNo(childInfo.getInt("outcomeNo"));
			}
			
			if (childInfo.has("result")) {
				psiClinicChild.setResult(childInfo.getString("result"));
			}
			
			if (childInfo.has("outcomeDate")) {
				psiClinicChild.setOutComeDate(yyyyMMdd.parse(childInfo.getString("outcomeDate")));
			}
			
			if (childInfo.has("complications")) {
				psiClinicChild.setComplications(childInfo.getString("complications"));
			}
			
			if (childInfo.has("typeOfDelivery")) {
				psiClinicChild.setTypeOfDelivery(childInfo.getString("typeOfDelivery"));
			}
			
			if (childInfo.has("sex")) {
				psiClinicChild.setSex(childInfo.getString("sex"));
			}
			
			if (childInfo.has("birthWeight")) {
				psiClinicChild.setBirthWeight(Float.parseFloat(childInfo.getString("birthWeight")));
			}
			
			if (childInfo.has("vaccine")) {
				psiClinicChild.setVaccine(childInfo.getString("vaccine"));
			}
			
			if (childInfo.has("healthStatus")) {
				psiClinicChild.setLastHealthStatus(childInfo.getString("healthStatus"));
			}
			
			if (childInfo.has("healthStatus")) {
				psiClinicChild.setLastHealthStatus(childInfo.getString("healthStatus"));
			}
			
			if (childInfo.has("motherUuid")) {
				psiClinicChild.setMotherUuid(childInfo.getString("motherUuid"));
			}
			psiClinicChild.setDateCreated(new Date());
			psiClinicChild.setCreator(Context.getAuthenticatedUser());
			psiClinicChild.setUuid(UUID.randomUUID().toString());
			
			psiClinicChild = Context.getService(PSIClinicChildService.class).saveOrUpdate(psiClinicChild);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<String>(psiClinicChild.getChildId() + "",HttpStatus.OK);

	}
	
	@RequestMapping(value = "/get-all-child-by-patient-uuid/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllChildByPatient(@PathVariable String id) throws Exception {
		List<PSIClinicChild> psiClinicChild = new ArrayList<PSIClinicChild>();
		JSONArray psiClinicChildObject = new JSONArray();
		try {
			psiClinicChild = Context.getService(PSIClinicChildService.class).findByMotherUuid(id);
			if (psiClinicChild != null) {
				psiClinicChildObject = new PSIClinicChildConverter().toConvert(psiClinicChild);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiClinicChildObject.toString(), HttpStatus.OK);
	}
}
