package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNDhisEncounterException;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.SHNDhisEncounterExceptionService;
import org.openmrs.module.PSI.api.SHNDhisObsElementService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.converter.DhisObsEventDataConverter;
import org.openmrs.module.PSI.converter.DhisObsJsonDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.SHNDataSyncStatusDTO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.DHISMapper;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;

@Service
@EnableScheduling
@Configuration
@EnableAsync
@Controller
public class Patient3Listener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	//private final String DHIS2BASEURL = "http://192.168.19.149";
	
	//test server psi
	//private final String DHIS2BASEURL = "http://10.100.11.2:5271";
	
	//live dhis url
	private static ResourceBundle resource = ResourceBundle.getBundle("deploymentConfig");
	
	private final static String isDeployInLightEmr = resource.getString("isDeployInLightEmr");
	
	private final static String isDeployInGlobal = resource.getString("isDeployInGlobal");
	
	private final static String DHIS2BASEURL = resource.getString("dhis2BaseUrl");
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	private final String GETEVENTURL = DHIS2BASEURL + "/api/events.json";
	
	private Map<String, String> ObserVationDHISMapping = new HashMap<String, String>();
	
	private Map<String, String> multipleObsDHISMapping = new HashMap<String, String>();


	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		
		JSONObject getResponse = null;
		boolean status = true;
		try {
			getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			
		}
		catch (Exception e) {
			
			status = false;
		}
		if (status) {
			
			try {
				//sendFailedPatient();
			}
			catch (Exception e) {
				
			}
			try {
				sendPatient();
			}
			catch (Exception e) {
				
			}
			try {
				//sendMoneyReceipt();
			}
			catch (Exception e) {
				
			}
			
			try {
				//sendFailedMoneyReceipt();
			}
			catch (Exception e) {
				
			}
			try {
				//sendEncounter();
			}
			catch (Exception e) {
				
			}
			try {
				//sendEncounterFailed();
			}
			catch (Exception e) {
				
			}
		}

	}
	
	public void sendFailedPatient() {
		
		List<PSIDHISException> psidhisExceptions = Context.getService(PSIDHISExceptionService.class).findAllByStatus(
		    PSIConstants.CONNECTIONTIMEOUTSTATUS);
		
		JSONObject response = new JSONObject();
		JSONObject patientJson = new JSONObject();
		if (psidhisExceptions.size() != 0 && psidhisExceptions != null) {
			for (PSIDHISException psidhisException : psidhisExceptions) {
				try {
					JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", psidhisException.getUrl());
					patientJson = DHISDataConverter.toConvertPatient(patient);
					JSONObject person = patient.getJSONObject("person");
					
					String orgUit = patientJson.getString("orgUnit");
					String uuid = DHISMapper.registrationMapper.get("uuid");
					String personUuid = person.getString("uuid");
					String URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + personUuid + "&ou=" + orgUit;
					JSONObject getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
					JSONArray trackedEntityInstances = new JSONArray();
					if (getResponse.has("trackedEntityInstances")) {
						trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					}
					if (trackedEntityInstances.length() != 0) {
						patientJson.remove("enrollments");
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String UpdateUrl = trackerUrl + "/" + trackedEntityInstance.getString("trackedEntityInstance");
						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
					} else {
						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
					}
					/*JSONObject responseofResponse = new JSONObject();
					responseofResponse = response.getJSONObject("response");*/
					
					String status = response.getString("status");
					if (!status.equalsIgnoreCase("ERROR")) {
						/*Context.openSession();
						psidhisException.setStatus(PSIConstants.SUCCESSSTATUS);
						psidhisException.setTimestamp(1l);
						psidhisException.setJson(patientJson.toString());
						//psidhisException.setError(responseofResponse.toString());
						psidhisException.setResponse(response.toString());
						Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
						Context.clearSession();*/
						updateExceptionForFailed(psidhisException, patientJson + "", PSIConstants.SUCCESSSTATUS, response
						        + "", "");
					} else {
						/*Context.openSession();
						psidhisException.setJson(patientJson.toString());
						psidhisException.setResponse(response.toString());
						//psidhisException.setError(responseofResponse.toString());
						psidhisException.setTimestamp(2l);
						psidhisException.setStatus(PSIConstants.FAILEDSTATUS);
						Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
						Context.clearSession();*/
						String errorDetails = errorMessageCreation(response);
						updateExceptionForFailed(psidhisException, patientJson + "", PSIConstants.FAILEDSTATUS, response
						        + "", errorDetails);
					}
					
				}
				catch (Exception e) {
					//Context.openSession();
					int status = 0;
					if ("java.lang.RuntimeException: java.net.ConnectException: Connection refused (Connection refused)"
					        .equalsIgnoreCase(e.toString())
					        || "org.hibernate.LazyInitializationException: could not initialize proxy - no Session"
					                .equalsIgnoreCase(e.toString())) {
						psidhisException.setStatus(PSIConstants.CONNECTIONTIMEOUTSTATUS);
						status = PSIConstants.CONNECTIONTIMEOUTSTATUS;
					} else {
						psidhisException.setStatus(PSIConstants.FAILEDSTATUS);
						status = PSIConstants.FAILEDSTATUS;
					}
					
					/*Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
					Context.clearSession();*/
					
					updateExceptionForFailed(psidhisException, patientJson + "", status, response + "", e.toString());
				}
			}
			
		}
	}
	
	private void updateExceptionForFailed(PSIDHISException getPsidhisException, String patientJson, int status,
	                                      String response, String error) {
		Context.openSession();
		
		getPsidhisException.setError(error);
		getPsidhisException.setJson(patientJson.toString());
		getPsidhisException.setStatus(status);
		getPsidhisException.setResponse(response.toString());
		getPsidhisException.setDateChanged(new Date());
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
		
		Context.clearSession();
	}
	
	public void sendPatient() {
		log.error("Entered in send Patient listener3 " + new Date());

		int lastReadPatient = 0;
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Patient3");
		if (getlastReadEntry == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("Patient3");
			psidhisMarker.setTimestamp(0l);
			psidhisMarker.setLastPatientId(544312);
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
		JSONObject patientJson = new JSONObject();
		if (eventReceordDTOs.size() != 0 && eventReceordDTOs != null) {
			for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
				if(eventReceordDTO.getId() % 4 == 0) {
				PSIDHISException getPsidhisException = Context.getService(PSIDHISExceptionService.class).findAllById(
				    eventReceordDTO.getId());
				String patientUUidToCheck = eventReceordDTO.getUrl().substring(28,64);
				SHNDataSyncStatusDTO syncStatus = Context.getService(PSIDHISExceptionService.class).findStatusToSendDataDhis("patient_uuid", patientUUidToCheck);
				boolean willSent = false;
				
				if(syncStatus == null && isDeployInLightEmr.equalsIgnoreCase("1")) {
					willSent = true;
				}
				else if(syncStatus != null  && isDeployInLightEmr.equalsIgnoreCase("1")) {
					willSent = false;
				}
				else if(syncStatus == null && isDeployInGlobal.equalsIgnoreCase("1")) {
					willSent = false;
				}
				else if(syncStatus != null && isDeployInGlobal.equalsIgnoreCase("1") && syncStatus.getSendToDhisFromGlobal() == 1) {
					willSent = true;
				}
				if(willSent) {
				try {
					JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
					patientJson = DHISDataConverter.toConvertPatient(patient);
					JSONObject person = patient.getJSONObject("person");
					String orgUit = patientJson.getString("orgUnit");
					String uuid = DHISMapper.registrationMapper.get("uuid");
					String personUuid = person.getString("uuid");
//					log.error("Converting patient data done for dhis2 " + System.currentTimeMillis());
//					String URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + personUuid + "&ou=" + orgUit;
//					log.error("Event Response time of patient before sending to dhis2 " + System.currentTimeMillis());
//					JSONObject getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
//					log.error("Event Response time of patient after sending to dhis2 " + System.currentTimeMillis());
//					JSONArray trackedEntityInstances = new JSONArray();
//					if (getResponse.has("trackedEntityInstances")) {
//						trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
//					}
					PSIDHISException findRefereceIdPatient = Context.getService(PSIDHISExceptionService.class).findReferenceIdOfPatient(personUuid, 1);
					if (findRefereceIdPatient != null && !StringUtils.isBlank(findRefereceIdPatient.getReferenceId())) {
						patientJson.remove("enrollments");
						//JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String UpdateUrl = trackerUrl + "/" + findRefereceIdPatient.getReferenceId();
						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
					} else {
						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
					}
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.openSession();
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					/*JSONObject responseofResponse = new JSONObject();
					responseofResponse = response.getJSONObject("response");*/
					String status = response.getString("status");
					JSONObject responseObject = response.getJSONObject("response");
					if(responseObject.has("importSummaries")) {
						JSONArray importSummaries = responseObject.getJSONArray("importSummaries");
						if (importSummaries.length() != 0) {
							JSONObject importSummary = importSummaries.getJSONObject(0);
							JSONObject enrollmentObject = importSummary.getJSONObject("enrollments");
							status = enrollmentObject.getString("status");
						}
					}
					else {
						if(responseObject.has("enrollments")) {
							JSONObject enrollmentObject = responseObject.getJSONObject("enrollments");
							status = enrollmentObject.getString("status");
						}
					}
					if (!status.equalsIgnoreCase("ERROR")) {
						if (getPsidhisException == null) {
							PSIDHISException newPsidhisException = new PSIDHISException();
							getPsidhisException = newPsidhisException;
						}
						/*getPsidhisException.setError("");
						getPsidhisException.setJson(patientJson.toString());
						getPsidhisException.setMarkId(eventReceordDTO.getId());
						getPsidhisException.setUrl(eventReceordDTO.getUrl());
						getPsidhisException.setStatus(PSIConstants.SUCCESSSTATUS);
						getPsidhisException.setResponse(response.toString());
						getPsidhisException.setDateCreated(new Date());
						Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
						*/
						String referenceId = "";
						JSONObject successResponse = response.getJSONObject("response");
						if(successResponse.has("importSummaries")) {
							JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
							if (importSummaries.length() != 0) {
								JSONObject importSummary = importSummaries.getJSONObject(0);
								referenceId = importSummary.getString("reference");
							}
						}
						else {
							referenceId = successResponse.getString("reference");
						}
						updateException(getPsidhisException, patientJson + "", eventReceordDTO, PSIConstants.SUCCESSSTATUS,
						    response + "", "",referenceId);
					} else {
						if (getPsidhisException == null) {
							PSIDHISException newPsidhisException = new PSIDHISException();
							getPsidhisException = newPsidhisException;
						}
						String errorDetails = errorMessageCreation(response);
						/*getPsidhisException.setError("");
						getPsidhisException.setJson(patientJson.toString());
						getPsidhisException.setMarkId(eventReceordDTO.getId());
						getPsidhisException.setUrl(eventReceordDTO.getUrl());
						getPsidhisException.setStatus(PSIConstants.CONNECTIONTIMEOUTSTATUS);
						getPsidhisException.setResponse(response.toString());
						getPsidhisException.setDateCreated(new Date());
						Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
						*/
						updateException(getPsidhisException, patientJson + "", eventReceordDTO,
						    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", errorDetails, "");
					}
					
					Context.clearSession();
					
				}
				catch (Exception e) {
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.openSession();
					//Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					if (getPsidhisException == null) {
						PSIDHISException newPsidhisException = new PSIDHISException();
						getPsidhisException = newPsidhisException;
					}
					/*getPsidhisException.setError(e.toString());
					getPsidhisException.setJson(patientJson.toString());
					getPsidhisException.setMarkId(eventReceordDTO.getId());
					getPsidhisException.setUrl(eventReceordDTO.getUrl());
					getPsidhisException.setStatus(PSIConstants.CONNECTIONTIMEOUTSTATUS);
					getPsidhisException.setResponse(response.toString());
					getPsidhisException.setDateCreated(new Date());
					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
					*/
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					Context.clearSession();
					updateException(getPsidhisException, patientJson + "", eventReceordDTO,
					    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", e.toString(),"");
				}
				
				}
			}
			
		 }
		}
	}
	
	private void updateException(PSIDHISException getPsidhisException, String patientJson, EventReceordDTO eventReceordDTO,
	                             int status, String response, String error, String referenceId) {
		Context.openSession();
		
		getPsidhisException.setError(error);
		getPsidhisException.setJson(patientJson.toString());
		getPsidhisException.setMarkId(eventReceordDTO.getId());
		getPsidhisException.setUrl(eventReceordDTO.getUrl());
		getPsidhisException.setStatus(status);
		getPsidhisException.setResponse(response.toString());
		getPsidhisException.setDateCreated(new Date());
		getPsidhisException.setReferenceId(referenceId);
		if(!StringUtils.isEmpty(eventReceordDTO.getUrl())) {
			String eventUrl = eventReceordDTO.getUrl();
			getPsidhisException.setPatientUuid(eventUrl.substring(28,64));
		}
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
		Context.clearSession();
	}
	
	private void updateServiceProvision(PSIServiceProvision psiServiceProvision, String moneyReceiptJson,
	                                    String referenceId, String getResponse, int statusCode, String URL, int status) {
		psiServiceProvision.setField2(moneyReceiptJson);
		psiServiceProvision.setDhisId(referenceId);
		psiServiceProvision.setField1(getResponse + "");
		psiServiceProvision.setField2(moneyReceiptJson + "");
		psiServiceProvision.setField3(statusCode);
		psiServiceProvision.setError("" + URL);
		psiServiceProvision.setIsSendToDHIS(status);
		Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
		Context.clearSession();
		
	}
	
	private String errorMessageCreation(JSONObject responsefull) {
		JSONObject response = new JSONObject();
		String errorMessage = "";
		try {
			 response = responsefull.getJSONObject("response");
		} catch (Exception e) {
			errorMessage = e.toString();
		}
		
		if (response.has("importSummaries")) {
			try {
				JSONArray importSummaries = response.getJSONArray("importSummaries");
					JSONObject importsObject = importSummaries.getJSONObject(0);
					if (importsObject.has("conflicts")) {
						JSONArray conflictsArray = importsObject.getJSONArray("conflicts");
						JSONObject conflictsObject = conflictsArray.getJSONObject(0);
						String httpStatusCode = responsefull.getString("httpStatusCode");
						errorMessage = "Http Status Code : " + httpStatusCode + " Message: " + conflictsObject.getString("value");
					}
					else if(importsObject.has("enrollments")) {
						JSONObject enrollmentObject = importsObject.getJSONObject("enrollments");
						if(enrollmentObject.has("importSummaries")) {
							JSONArray enrollmentImportSummary = enrollmentObject.getJSONArray("importSummaries");
							if(enrollmentImportSummary.length() > 0) {
								JSONObject enrollmentImportSummariesObject = enrollmentImportSummary.getJSONObject(0);
								if (enrollmentImportSummariesObject.has("conflicts")) {
									JSONArray conflictsArray = enrollmentImportSummariesObject.getJSONArray("conflicts");
									JSONObject conflictsObject = conflictsArray.getJSONObject(0);
									errorMessage = "Message: " + conflictsObject.getString("value");
								}
							}
							
						}
					}
					else {
						if(importsObject.has("description")) {
							errorMessage = importsObject.getString("description");
						}
					}
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}

		}
		return errorMessage;
	}
	
	
	
	private  JSONObject getDhisEventInformation(String patientUuid) throws JSONException {
		JSONObject eventInformationObject = new JSONObject();
		JSONObject trackentityIsntanceResponse = new JSONObject();
		boolean patientEventStatus = true;
		String orgUnit = "";
		String tackedEntityInstance = "";
		String trackEntityInstanceUrl = "";
		try {
			String patientUrl =  "/openmrs/ws/rest/v1/patient/"+patientUuid+"?v=full";
			JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", patientUrl);
			JSONObject person = (JSONObject) patient.get("person");
			JSONArray patientAttributes = (JSONArray) person.get("attributes");
			for (int i = 0; i < patientAttributes.length(); i++) {
				JSONObject patientAttribute = patientAttributes.getJSONObject(i);
				JSONObject attributeType = patientAttribute.getJSONObject("attributeType");
				String attributeTypeName = attributeType.getString("display");
				if ("orgUnit".equalsIgnoreCase(attributeTypeName)) {
					orgUnit = (String) patientAttribute.get("value");
				}
				
			}
			String uuid = DHISMapper.registrationMapper.get("uuid");
			trackEntityInstanceUrl = trackInstanceUrl + "filter=" + uuid + ":EQ:" + patientUuid + "&ou=" + orgUnit;
			trackentityIsntanceResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", trackEntityInstanceUrl);
		
		
				JSONArray trackedEntityInstances = new JSONArray();
				if (trackentityIsntanceResponse.has("trackedEntityInstances")) {
					trackedEntityInstances = (JSONArray) trackentityIsntanceResponse.get("trackedEntityInstances");
				}
		
				if (trackedEntityInstances.length() != 0) {
					JSONObject trackedEntityInstance = (JSONObject) trackedEntityInstances.get(0);
					tackedEntityInstance = (String) trackedEntityInstance.get("trackedEntityInstance");
					}
				}
		catch (Exception e) {
			e.printStackTrace();
		}
		if (StringUtils.isEmpty(orgUnit) || StringUtils.isEmpty(tackedEntityInstance)) {
			patientEventStatus = false;
			eventInformationObject.put("orgUnit", orgUnit);
			eventInformationObject.put("tackedEntityInstance", tackedEntityInstance);
			eventInformationObject.put("patientEventStatus", patientEventStatus);
			eventInformationObject.put("trackEntityInstanceUrl", trackEntityInstanceUrl);
			eventInformationObject.put("trackentityIsntanceResponse", trackentityIsntanceResponse);
		}
		else {
			patientEventStatus = true;
			eventInformationObject.put("orgUnit", orgUnit);
			eventInformationObject.put("tackedEntityInstance", tackedEntityInstance);
			eventInformationObject.put("patientEventStatus", patientEventStatus);
			eventInformationObject.put("trackEntityInstanceUrl", trackEntityInstanceUrl);
			eventInformationObject.put("trackentityIsntanceResponse", trackentityIsntanceResponse);
		}
		return eventInformationObject;
	}
	
	private void mapDhisDataElementsId(String formName) {
		ObserVationDHISMapping.clear();
		List<SHNDhisObsElement> dhisObsElement = Context.getService(SHNDhisObsElementService.class).getAllDhisElement(formName);
		for (SHNDhisObsElement item : dhisObsElement) {
			ObserVationDHISMapping.put(item.getElementName().trim(),item.getElementDhisId());
		}
	}
	
	private void mapDhisMultipleDataElementsId(String formName) {
		multipleObsDHISMapping.clear();
		List<SHNDhisMultipleChoiceObsElement> dhisMUltipleObsElement = Context.getService(SHNDhisObsElementService.class).getAllMultipleChoiceDhisElement(formName);
		for (SHNDhisMultipleChoiceObsElement item : dhisMUltipleObsElement) {
			multipleObsDHISMapping.put(item.getElementName().trim(),item.getElementDhisId());
		}
	}
	
	private void updateExceptionForEncounterFailed(SHNDhisEncounterException shnDhisEncounterException,
			String encounterJson, int status, String response, String error, String referenceId, String patientUuid,String formsName) {
		Context.openSession();
		shnDhisEncounterException.setError(error);
		shnDhisEncounterException.setPostJson(encounterJson.toString());
		shnDhisEncounterException.setStatus(status);
		shnDhisEncounterException.setResponse(response.toString());
		shnDhisEncounterException.setReferenceId(referenceId);
		shnDhisEncounterException.setPatientUuid(patientUuid);
		shnDhisEncounterException.setFormsName(formsName);
		shnDhisEncounterException.setDateChanged(new Date());
		Context.getService(SHNDhisEncounterExceptionService.class).saveOrUpdate(
				shnDhisEncounterException);
		Context.clearSession();
	}
	
	public static boolean isNumeric(String str) {
		return str.matches("[0-9.]*");
	}
}
	
