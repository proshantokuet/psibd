package org.openmrs.module.PSI.web.controller;

import org.json.JSONObject;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/userByName")
@RestController
public class OpenmrsAPIController extends MainResourceController {
	
	private static final String OPENMRS_BASE_URL = "https://192.168.33.10/openmrs";
	
	final String USER_URL = "ws/rest/v1/user";
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> getResearvedHealthId() throws Exception {
		
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		return new ResponseEntity<String>(body.toString(), HttpStatus.OK);
		
	}
	
}
