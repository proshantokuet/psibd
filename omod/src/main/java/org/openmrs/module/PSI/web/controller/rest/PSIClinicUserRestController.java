package org.openmrs.module.PSI.web.controller.rest;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.converter.PSIClinicUserConverter;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/clinic-user/")
@RestController
public class PSIClinicUserRestController extends MainResourceController {
	
	@RequestMapping(value = "/get-by-username/{username}", method = RequestMethod.GET)
	public ResponseEntity<String> findById(@PathVariable String username) throws Exception {
		PSIClinicUser psiClinicUser = new PSIClinicUser();
		JSONObject psiClinic = new JSONObject();
		try {
			psiClinicUser = Context.getService(PSIClinicUserService.class).findByUserName(username);
			psiClinic = new PSIClinicUserConverter().toConvertClinic(psiClinicUser);
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiClinic.toString(), HttpStatus.OK);
	}
	
}
