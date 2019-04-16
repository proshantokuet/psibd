package org.openmrs.module.PSI.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.utils.HttpResponse;
import org.openmrs.module.PSI.utils.HttpUtil;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> getResearvedHealthId() throws Exception {
		
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		List<String> datas = new ArrayList<String>();
		datas = Context.getService(PSIDHISMarkerService.class).rawQuery(0);
		String s = "";
		
		for (String url : datas) {
			s += url;
		}
		JSONObject patient = psiapiServiceFactory.getAPiObject("openmrs").get("", "",
		    "/openmrs/ws/rest/v1/patient/6d4fdfef-84ab-4112-b76e-4cc7687ac96b?v=full");
		return new ResponseEntity<String>(s.toString(), HttpStatus.OK);
		
	}
}
