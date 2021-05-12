package org.openmrs.module.PSI.web.listener;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
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
public class PatientFailedListener {

	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	//private final String DHIS2BASEURL = "http://192.168.19.149";
	
	//test server psi
	//private final String DHIS2BASEURL = "http://10.100.11.2:5271";
	
	//live dhis url
	private static ResourceBundle resource = ResourceBundle.getBundle("deploymentConfig");
	
	private final static String DHIS2BASEURL = resource.getString("dhis2BaseUrl");
	
	private final static String isDeployInLightEmr = resource.getString("isDeployInLightEmr");
	
	private final static String isDeployInGlobal = resource.getString("isDeployInGlobal");
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendFailedPatientData() throws Exception {
		log.error("Called patient failed listener " + new Date());
		boolean status = true;
		try {
			psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			
		}
		catch (Exception e) {
			
			status = false;
		}
		if (status) {
			
			try {
				sendFailedPatient();
				Thread.sleep(1000);
			}
			catch (Exception e) {
				
			}
		}

	}
	
	public synchronized void sendFailedPatient() {
		log.error("Entered in failed patient listener " + new Date());
		List<PSIDHISException> psidhisExceptions = Context.getService(PSIDHISExceptionService.class).findAllByStatus(
		    PSIConstants.CONNECTIONTIMEOUTSTATUS);
		
		JSONObject response = new JSONObject();
		JSONObject patientJson = new JSONObject();
		if (psidhisExceptions.size() != 0 && psidhisExceptions != null) {
			for (PSIDHISException psidhisException : psidhisExceptions) {
				SHNDataSyncStatusDTO syncStatus = Context.getService(PSIDHISExceptionService.class).findStatusToSendDataDhis("patient_uuid", psidhisException.getPatientUuid());
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
					JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", psidhisException.getUrl());
					patientJson = DHISDataConverter.toConvertPatient(patient);
					JSONObject person = patient.getJSONObject("person");
					
					patientJson.getString("orgUnit");
					DHISMapper.registrationMapper.get("uuid");
					String personUuid = person.getString("uuid");

					PSIDHISException findRefereceIdPatient = Context.getService(PSIDHISExceptionService.class).findReferenceIdOfPatient(personUuid, 1);
					if (findRefereceIdPatient != null && !StringUtils.isBlank(findRefereceIdPatient.getReferenceId())) {
						patientJson.remove("enrollments");
						String UpdateUrl = trackerUrl + "/" + findRefereceIdPatient.getReferenceId();
						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
					} else {
						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
					}

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
						updateExceptionForFailed(psidhisException, patientJson + "", PSIConstants.SUCCESSSTATUS, response
						        + "", "",referenceId);
					} else {

						String errorDetails = errorMessageCreation(response);
						updateExceptionForFailed(psidhisException, patientJson + "", PSIConstants.FAILEDSTATUS, response
						        + "", errorDetails,"");
					}
					
				}
				catch (Exception e) {
					int status = 0;
					if ("java.lang.RuntimeException: java.net.ConnectException: Connection refused (Connection refused)"
					        .equalsIgnoreCase(e.toString())
					        || "org.hibernate.LazyInitializationException: could not initialize proxy - no Session"
					                .equalsIgnoreCase(e.toString())) {
						status = PSIConstants.CONNECTIONTIMEOUTSTATUS;
					} else {
						status = PSIConstants.FAILEDSTATUS;
					}					
					updateExceptionForFailed(psidhisException, patientJson + "", status, response + "", e.toString(),"");
				}
				
				}
			}
			
		}
	}
	
	private void updateExceptionForFailed(PSIDHISException getPsidhisException, String patientJson, int status,
	                                      String response, String error, String referenceId) {
		Context.openSession();
		
		getPsidhisException.setError(error);
		getPsidhisException.setJson(patientJson.toString());
		getPsidhisException.setStatus(status);
		getPsidhisException.setResponse(response.toString());
		getPsidhisException.setDateChanged(new Date());
		getPsidhisException.setReferenceId(referenceId);
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
