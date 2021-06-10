package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.SHNDataSyncStatusDTO;
import org.openmrs.module.PSI.utils.DHISMapper;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

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
	
	private static final ReentrantLock lock = new ReentrantLock();
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		if (!lock.tryLock()) {
			log.error("It is already in progress.");
	        return;
		}
		boolean status = true;
		try {
			psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			
		}
		catch (Exception e) {
			
			status = false;
		}
		if (status) {
			
			try {
				sendPatient();
				Thread.sleep(1000);
			}
			catch (Exception e) {
				
			}
			finally {
				lock.unlock();
				log.error("complete listener patient3 at:" +new Date());
			}
		}

	}
	
	public synchronized void sendPatient() {
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
				log.error("Entered in loop" + eventReceordDTO.getId());

				if(eventReceordDTO.getId() % 4 == 0 ) {
					log.error("printing dhis response" + eventReceordDTO.getDhisResponse());	
				if(!StringUtils.isBlank(eventReceordDTO.getDhisResponse())) {
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
//					JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
//					patientJson = DHISDataConverter.toConvertPatient(patient);
//					JSONObject person = patient.getJSONObject("person");
//					patientJson.getString("orgUnit");
//					DHISMapper.registrationMapper.get("uuid");
//					String personUuid = person.getString("uuid");
//					PSIDHISException findRefereceIdPatient = Context.getService(PSIDHISExceptionService.class).findReferenceIdOfPatient(personUuid, 1);
//					if (findRefereceIdPatient != null && !StringUtils.isBlank(findRefereceIdPatient.getReferenceId())) {
//						patientJson.remove("enrollments");
//						//JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
//						String UpdateUrl = trackerUrl + "/" + findRefereceIdPatient.getReferenceId();
//						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
//					} else {
//						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
//					}
					
					
					response = new JSONObject(eventReceordDTO.getDhisResponse()); 
					String status = response.getString("status");
					JSONObject responseObject = response.getJSONObject("response");
					if(responseObject.has("importSummaries")) {
						JSONArray importSummaries = responseObject.getJSONArray("importSummaries");
						if (importSummaries.length() != 0) {
							JSONObject importSummary = importSummaries.getJSONObject(0);
							if(importSummary.has("enrollments")){
								JSONObject enrollmentObject = importSummary.getJSONObject("enrollments");
								status = enrollmentObject.getString("status");
							}
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

						updateException(getPsidhisException, patientJson + "", eventReceordDTO,
						    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", errorDetails, "");
					}
					
				}
				catch (Exception e) {
					if (getPsidhisException == null) {
						PSIDHISException newPsidhisException = new PSIDHISException();
						getPsidhisException = newPsidhisException;
					}
					updateException(getPsidhisException, patientJson + "", eventReceordDTO,
					    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", e.toString(),"");
				}
				finally {
					Context.openSession();
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					Context.clearSession();
				}
				
				}
				else {
					Context.openSession();
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					Context.clearSession();
				}
				}
				else {
					break;
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
	
	public static boolean isNumeric(String str) {
		return str.matches("[0-9.]*");
	}
}
	
