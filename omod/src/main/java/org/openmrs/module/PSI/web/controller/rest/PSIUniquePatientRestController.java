package org.openmrs.module.PSI.web.controller.rest;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.SHnPrescriptionMetaData;
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/rest/v1/check/uniquePatient")
@RestController
public class PSIUniquePatientRestController {
	
	@RequestMapping(value = "/{uic}/{mobileNo}", method = RequestMethod.GET)
	public ResponseEntity<String> saveClinic(@PathVariable String uic, @PathVariable String mobileNo) throws Exception {
		
		Boolean patientAvailabilityStatus = Context.getService(PSIUniquePatientService.class)
		        .findPatientByUicandMobileNo(uic, mobileNo);
		return new ResponseEntity<>(patientAvailabilityStatus.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prescriptionMetaData", method = RequestMethod.GET)
	public ResponseEntity<String> getMetaData() throws Exception {
		
		List<SHnPrescriptionMetaData> sHnPrescriptionMetaDatas = Context.getService(PSIUniquePatientService.class)
		        .getAllPrescriptionMetaData();
		JSONArray metadataJsonArray = new JSONArray();
		for (SHnPrescriptionMetaData sHnPrescriptionMetaData : sHnPrescriptionMetaDatas) {
			metadataJsonArray.put(sHnPrescriptionMetaData.getFieldName());
		}
		return new ResponseEntity<>(metadataJsonArray.toString(), HttpStatus.OK);
	}
}
