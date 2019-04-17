package org.openmrs.module.PSI.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
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
	
	private final String trackerUrl = "http://192.168.19.148:1971/api/trackedEntityInstances";
	
	final String USER_URL = "ws/rest/v1/user";
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<String> getResearvedHealthId() throws Exception {
		
		/*HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		List<EventReceordDTO> datas = new ArrayList<EventReceordDTO>();
		datas = Context.getService(PSIDHISMarkerService.class).rawQuery(0);
		String s = "";
		
		JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "",
		    "/openmrs/ws/rest/v1/patient/6d4fdfef-84ab-4112-b76e-4cc7687ac96b?v=full");
		*/
		int lastReadPatient = 0;
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Patient");
		if (getlastReadEntry == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("Patient");
			psidhisMarker.setTimestamp(0l);
			psidhisMarker.setLastPatientId(0);
			psidhisMarker.setDateCreated(new Date());
			psidhisMarker.setUuid(UUID.randomUUID().toString());
			psidhisMarker.setVoided(false);
			Context.openSession();
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			Context.clearSession();
		} else {
			lastReadPatient = getlastReadEntry.getLastPatientId();
		}
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		eventReceordDTOs = Context.getService(PSIDHISMarkerService.class).rawQuery(lastReadPatient);
		for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
			try {
				JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
				JSONObject patientJson = DHISDataConverter.toConvertPatient(patient);
				JSONObject response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
				getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
				Context.openSession();
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
				Context.clearSession();
				return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(e.toString() + e.getCause().toString() + "" + e.getMessage(),
				        HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		
		return new ResponseEntity<String>("not ok ", HttpStatus.OK);
		
	}
}
