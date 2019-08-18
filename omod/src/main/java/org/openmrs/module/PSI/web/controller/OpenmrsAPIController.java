package org.openmrs.module.PSI.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.management.relation.RoleStatus;

import org.hibernate.service.internal.ProvidedService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.api.LocationService;
import org.openmrs.api.PersonService;

import org.openmrs.api.ProviderService;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.utils.DHISMapper;
import org.openmrs.module.PSI.web.listener.DHISListener;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.openmrs.validator.RoleValidatorTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/dhis")
@RestController
public class OpenmrsAPIController extends MainResourceController {
	
	private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	//private final String DHIS2BASEURL = "http://192.168.19.149:1971";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	final String USER_URL = "ws/rest/v1/user";
	
	@Autowired
	private DHISListener dhisListener;
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	@RequestMapping(value = "/fakecall", method = RequestMethod.GET)
	public ResponseEntity<String> emptyCall() throws Exception {
		Context.getAuthenticatedUser().getRoles().contains("View Report");
		
		//dhisListener.sendPatient();
		Location l = new Location();
		LocationTag lt = new LocationTag();
		Context.getService(LocationService.class).saveLocation(l);
		Context.getService(UserService.class).changePassword(Context.getService(UserService.class).getUser(4), "Admin1234",
		    "Admin12345");
		Context.getService(UserService.class).changePassword(Context.getService(UserService.class).getUser(4), "Admin12345",
		    "Admin123456");
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Patient");
		JSONObject res = new JSONObject();
		res.putOpt("OK", "OKK");
		
		/*Context.getService(ProviderService.class)
		Context.getService(PersonService.class).savePerson(person);
		Context.getService(UserService.class).saveUser(user);*/
		return new ResponseEntity<String>(res.toString(), HttpStatus.OK);
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/not", method = RequestMethod.GET)
	public ResponseEntity<String> sfsdf() throws Exception {
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllByTimestampNotSending(0l);
		return new ResponseEntity<String>(psiServiceProvisions.size() + ": " + psiServiceProvisions.toString(),
		        HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/patient", method = RequestMethod.GET)
	public ResponseEntity<String> getResearvedHealthId() throws Exception {
		/*
		HttpResponse op = HttpUtil.get(HttpUtil.removeEndingSlash(OPENMRS_BASE_URL) + "/" + USER_URL + "/", "v=default",
		    "superman", "Admin123");
		JSONObject body = new JSONObject(op.body());
		List<EventReceordDTO> datas = new ArrayList<EventReceordDTO>();
		datas = Context.getService(PSIDHISMarkerService.class).rawQuery(0);
		String s = "";
		
		JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "",
		    "/openmrs/ws/rest/v1/patient/6d4fdfef-84ab-4112-b76e-4cc7687ac96b?v=full");*/
		
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
		JSONObject response = new JSONObject();
		String UpdateUrl = "";
		JSONObject patient = new JSONObject();
		for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
			try {
				patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
				JSONObject patientJson = DHISDataConverter.toConvertPatient(patient);
				JSONObject person = patient.getJSONObject("person");
				String URL = trackInstanceUrl + "filter=" + DHISMapper.registrationMapper.get("uuid") + ":EQ:"
				        + person.getString("uuid") + "&ou=" + patientJson.getString("orgUnit");
				JSONObject getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
				JSONArray trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
				if (trackedEntityInstances.length() != 0) {
					JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
					UpdateUrl = trackerUrl + "/" + trackedEntityInstance.getString("trackedEntityInstance");
					response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
				} else {
					response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
				}
				getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
				Context.openSession();
				Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
				Context.clearSession();
				return new ResponseEntity<String>(patient.toString() + "$$$$$$$$$$$Up:" + patientJson.toString() + "::"
				        + response, HttpStatus.OK);
			}
			catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>(patient + e.toString() + e.getCause().toString() + "" + e.getMessage(),
				        HttpStatus.OK);
			}
		}
		JSONObject res = new JSONObject();
		res.putOpt("OK", "OKK");
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
		
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/service", method = RequestMethod.GET)
	public ResponseEntity<String> service() throws Exception {
		
		long timestamp = 0;
		
		PSIDHISMarker getlastTimeStamp = Context.getService(PSIDHISMarkerService.class).findByType("MoneyReceipt");
		if (getlastTimeStamp == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("MoneyReceipt");
			psidhisMarker.setTimestamp(0l);
			psidhisMarker.setLastPatientId(0);
			psidhisMarker.setDateCreated(new Date());
			psidhisMarker.setUuid(UUID.randomUUID().toString());
			psidhisMarker.setVoided(false);
			Context.openSession();
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			Context.clearSession();
		} else {
			timestamp = getlastTimeStamp.getTimestamp();
		}
		
		try {
			List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
			        .findAllByTimestamp(timestamp);
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				try {
					String URL = trackInstanceUrl + "filter=" + DHISMapper.registrationMapper.get("uuid") + ":EQ:"
					        + psiServiceProvision.getPatientUuid() + "&ou="
					        + psiServiceProvision.getPsiMoneyReceiptId().getOrgUnit();
					JSONObject getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
					JSONArray trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					JSONObject eventResponse = new JSONObject();
					if (trackedEntityInstances.length() != 0) {
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String trackedEntityInstanceId = trackedEntityInstance.getString("trackedEntityInstance");
						JSONObject moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
						    trackedEntityInstanceId);
						if (psiServiceProvision.getDhisId() != null) {
							
							eventResponse = psiapiServiceFactory.getAPIType("dhis2").update("", moneyReceiptJson, "",
							    EVENTURL + "/" + psiServiceProvision.getDhisId());
							getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
							Context.openSession();
							Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
							Context.clearSession();
							
						} else {
							
							eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
							int statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
							if (statusCode == 200) {
								JSONObject successResponse = eventResponse.getJSONObject("response");
								JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
								if (importSummaries.length() != 0) {
									JSONObject importSummary = importSummaries.getJSONObject(0);
									String referenceId = importSummary.getString("reference");
									
									Context.openSession();
									psiServiceProvision.setDhisId(referenceId);
									Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
									
									getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
									Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
									Context.clearSession();
									
								}
							}
						}
						return new ResponseEntity<String>(psiServiceProvision.getDhisId() + " ---- "
						        + moneyReceiptJson.toString() + "$$$$$$$$$$$:" + eventResponse, HttpStatus.OK);
					} else {
						
						Context.openSession();
						getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
						Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
						Context.clearSession();
					}
				}
				catch (Exception e) {
					return new ResponseEntity<String>(psiServiceProvision.getDhisId() + "---" + e.toString(), HttpStatus.OK);
				}
				
			}
			
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("not ok ", HttpStatus.OK);
		
	}
}
