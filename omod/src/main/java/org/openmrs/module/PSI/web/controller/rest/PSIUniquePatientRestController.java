package org.openmrs.module.PSI.web.controller.rest;
import org.openmrs.api.context.Context;
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
}
