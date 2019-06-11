package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.converter.PSIServiceProvisionConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/service-provision/")
@RestController
public class PSIServiceProvisionRestController {
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> delete(@PathVariable int id) throws Exception {
		try {
			Context.getService(PSIServiceProvisionService.class).delete(id);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>("ok", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-byid/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getId(@PathVariable int id) throws Exception {
		
		JSONObject psiServicesJsonObject = new JSONObject();
		try {
			PSIServiceProvision psiService = Context.getService(PSIServiceProvisionService.class).findById(id);
			if (psiService != null) {
				psiServicesJsonObject = new PSIServiceProvisionConverter().toConvert(psiService);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiServicesJsonObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-all-by-patient-uuid/{patientUuid}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllByPatient(@PathVariable String patientUuid) throws Exception {
		List<PSIServiceProvision> psiServices = new ArrayList<PSIServiceProvision>();
		JSONArray psiServicesJsonArray = new JSONArray();
		try {
			psiServices = Context.getService(PSIServiceProvisionService.class).getAllByPatient(patientUuid);
			if (psiServices != null) {
				psiServicesJsonArray = new PSIServiceProvisionConverter().toConvert(psiServices);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiServicesJsonArray.toString(), HttpStatus.OK);
	}
}
