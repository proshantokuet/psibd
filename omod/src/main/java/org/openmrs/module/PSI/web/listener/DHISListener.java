package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.DHISMapper;
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
public class DHISListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	private final String DHIS2BASEURL = "http://192.168.19.149:1971";
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		JSONObject getResponse = null;
		boolean status = true;
		try {
			getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			/*PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setVoidReason(" " + getResponse);
			psidhisMarker.setDateCreated(new Date());
			Context.openSession();
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			Context.clearSession();*/
		}
		catch (Exception e) {
			/*PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setVoidReason(e.toString() + " " + getResponse);
			Context.openSession();
			psidhisMarker.setDateCreated(new Date());
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			Context.clearSession();*/
			status = false;
		}
		
		if (status) {
			try {
				sendPatientAgain();
			}
			catch (Exception e) {
				
			}
			try {
				sendPatient();
			}
			catch (Exception e) {
				
			}
			try {
				sendMoneyReceipt();
			}
			catch (Exception e) {
				
			}
			
			try {
				sendNotSendingMoneyReceipt();
			}
			catch (Exception e) {
				
			}
		}
	}
	
	public void sendPatientAgain() {
		
		List<PSIDHISException> psidhisExceptions = Context.getService(PSIDHISExceptionService.class).findAllByStatus(
		    DHISMapper.DEFAULTERRORSTATUS);
		
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
					JSONArray trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					if (trackedEntityInstances.length() != 0) {
						patientJson.remove("enrollments");
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String UpdateUrl = trackerUrl + "/" + trackedEntityInstance.getString("trackedEntityInstance");
						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
					} else {
						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
					}
					
					String status = response.getString("status");
					
					if (!status.equalsIgnoreCase("ERROR")) {
						Context.openSession();
						psidhisException.setStatus(DHISMapper.SUCCESSSTATUS);
						psidhisException.setTimestamp(1l);
						Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
						Context.clearSession();
					}
					
				}
				catch (Exception e) {
					Context.openSession();
					if ("java.lang.RuntimeException: java.net.ConnectException: Connection refused (Connection refused)"
					        .equalsIgnoreCase(e.toString())
					        || "org.hibernate.LazyInitializationException: could not initialize proxy - no Session"
					                .equalsIgnoreCase(e.toString())) {
						psidhisException.setStatus(DHISMapper.CONNECTIONTIMEOUTSTATUS);
					} else {
						psidhisException.setStatus(DHISMapper.FAILEDSTATUS);
					}
					
					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
					Context.clearSession();
				}
			}
			
		}
	}
	
	public void sendPatient() {
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
		JSONObject patientJson = new JSONObject();
		if (eventReceordDTOs.size() != 0 && eventReceordDTOs != null) {
			for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
				PSIDHISException getPsidhisException = Context.getService(PSIDHISExceptionService.class).findAllById(
				    eventReceordDTO.getId());
				try {
					JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
					patientJson = DHISDataConverter.toConvertPatient(patient);
					JSONObject person = patient.getJSONObject("person");
					String orgUit = patientJson.getString("orgUnit");
					String uuid = DHISMapper.registrationMapper.get("uuid");
					String personUuid = person.getString("uuid");
					String URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + personUuid + "&ou=" + orgUit;
					JSONObject getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
					JSONArray trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					if (trackedEntityInstances.length() != 0) {
						patientJson.remove("enrollments");
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String UpdateUrl = trackerUrl + "/" + trackedEntityInstance.getString("trackedEntityInstance");
						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
					} else {
						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
					}
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.openSession();
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					String status = response.getString("status");
					if (!status.equalsIgnoreCase("ERROR")) {
						if (getPsidhisException == null) {
							PSIDHISException newPsidhisException = new PSIDHISException();
							getPsidhisException = newPsidhisException;
						}
						getPsidhisException.setError("");
						getPsidhisException.setJson(patientJson.toString());
						getPsidhisException.setMarkId(eventReceordDTO.getId());
						getPsidhisException.setUrl(eventReceordDTO.getUrl());
						getPsidhisException.setStatus(DHISMapper.SUCCESSSTATUS);
						getPsidhisException.setResponse(response.toString());
						getPsidhisException.setDateCreated(new Date());
						Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
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
					getPsidhisException.setError(e.toString());
					getPsidhisException.setJson(patientJson.toString());
					getPsidhisException.setMarkId(eventReceordDTO.getId());
					getPsidhisException.setUrl(eventReceordDTO.getUrl());
					getPsidhisException.setStatus(DHISMapper.DEFAULTERRORSTATUS);
					getPsidhisException.setResponse(response.toString());
					getPsidhisException.setDateCreated(new Date());
					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
					
					Context.clearSession();
				}
			}
			
		}
	}
	
	private void sendMoneyReceipt() {
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
		
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllByTimestamp(timestamp);
		if (psiServiceProvisions.size() != 0 && psiServiceProvisions != null) {
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				JSONObject moneyReceiptJson = new JSONObject();
				String patientUuid = psiServiceProvision.getPatientUuid();
				String uuid = DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				int statusCode = 0;
				String orgUnit = "";
				
				try {
					UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
					orgUnit = userDTO.getOrgUnit();
					URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + patientUuid + "&ou=" + orgUnit;
					getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
					JSONArray trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					eventResponse = new JSONObject();
					log.info("ADD:URL:" + URL + "getResponse:" + getResponse);
					if (trackedEntityInstances.length() != 0) {
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String trackedEntityInstanceId = trackedEntityInstance.getString("trackedEntityInstance");
						moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
						    trackedEntityInstanceId);
						
						eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
						statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
						log.info("ADD:statusCode:" + statusCode + "" + eventResponse);
						if (statusCode == 200) {
							JSONObject successResponse = eventResponse.getJSONObject("response");
							JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
							if (importSummaries.length() != 0) {
								JSONObject importSummary = importSummaries.getJSONObject(0);
								String referenceId = importSummary.getString("reference");
								Context.openSession();
								psiServiceProvision.setDhisId(referenceId);
								psiServiceProvision.setIsSendToDHIS(1);
								psiServiceProvision.setField1(getResponse + "");
								psiServiceProvision.setField2(moneyReceiptJson + "");
								psiServiceProvision.setField3(statusCode);
								psiServiceProvision.setError(URL);
								Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
								getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
								Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
								Context.clearSession();
							}
						}
						
					} else {
						
						Context.openSession();
						getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
						getlastTimeStamp.setVoidReason("else cindtion");
						//psiServiceProvision.setField1("not found");
						psiServiceProvision.setField1(getResponse + "");
						psiServiceProvision.setField2(moneyReceiptJson + "");
						psiServiceProvision.setField3(statusCode);
						psiServiceProvision.setError(":" + URL);
						psiServiceProvision.setIsSendToDHIS(DHISMapper.DEFAULTERRORSTATUS);
						Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
						Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
						Context.clearSession();
					}
				}
				catch (Exception e) {
					e.printStackTrace();
					Context.openSession();
					getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
					psiServiceProvision.setField1(getResponse + "");
					psiServiceProvision.setField2(moneyReceiptJson + "");
					psiServiceProvision.setField3(statusCode);
					psiServiceProvision.setError(e + ":" + URL);
					psiServiceProvision.setIsSendToDHIS(DHISMapper.DEFAULTERRORSTATUS);
					Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
					getlastTimeStamp.setVoidReason(e.toString());
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
					Context.clearSession();
				}
			}
			
		}
	}
	
	private void sendNotSendingMoneyReceipt() {
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllResend();
		if (psiServiceProvisions.size() != 0 && psiServiceProvisions != null) {
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				JSONObject moneyReceiptJson = new JSONObject();
				String patientUuid = psiServiceProvision.getPatientUuid();
				String uuid = DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				int statusCode = 0;
				String orgUnit = "";
				try {
					UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
					orgUnit = userDTO.getOrgUnit();
					URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + patientUuid + "&ou=" + orgUnit;
					getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
					JSONArray trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					log.info("URL:" + URL + "getResponse:" + getResponse);
					if (trackedEntityInstances.length() != 0) {
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String trackedEntityInstanceId = trackedEntityInstance.getString("trackedEntityInstance");
						moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
						    trackedEntityInstanceId);
						
						eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
						statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
						log.info("statusCode:" + statusCode + "" + eventResponse);
						if (statusCode == 200) {
							JSONObject successResponse = eventResponse.getJSONObject("response");
							JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
							if (importSummaries.length() != 0) {
								JSONObject importSummary = importSummaries.getJSONObject(0);
								String referenceId = importSummary.getString("reference");
								Context.openSession();
								psiServiceProvision.setField2(moneyReceiptJson + "");
								psiServiceProvision.setDhisId(referenceId);
								psiServiceProvision.setField1(getResponse + "");
								psiServiceProvision.setField2(moneyReceiptJson + "");
								psiServiceProvision.setField3(statusCode);
								psiServiceProvision.setError(":" + URL);
								psiServiceProvision.setIsSendToDHIS(DHISMapper.SUCCESSSTATUS);
								Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
								Context.clearSession();
							}
						}
						
					} else {
						Context.openSession();
						psiServiceProvision.setField1("not found");
						psiServiceProvision.setField1(getResponse + "");
						psiServiceProvision.setField2(moneyReceiptJson + "");
						psiServiceProvision.setField3(statusCode);
						psiServiceProvision.setError(":" + URL);
						psiServiceProvision.setIsSendToDHIS(DHISMapper.FAILEDSTATUS);
						Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
						Context.clearSession();
					}
				}
				catch (Exception e) {
					Context.openSession();
					e.printStackTrace();
					psiServiceProvision.setField1(getResponse + "");
					psiServiceProvision.setField2(moneyReceiptJson + "");
					psiServiceProvision.setField3(statusCode);
					psiServiceProvision.setError(e + ":" + URL);
					if ("java.lang.RuntimeException: java.net.ConnectException: Connection refused (Connection refused)"
					        .equalsIgnoreCase(e.toString())
					        || "org.hibernate.LazyInitializationException: could not initialize proxy - no Session"
					                .equalsIgnoreCase(e.toString())) {
						psiServiceProvision.setIsSendToDHIS(DHISMapper.CONNECTIONTIMEOUTSTATUS);
					} else {
						psiServiceProvision.setIsSendToDHIS(DHISMapper.FAILEDSTATUS);
					}
					Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
					Context.clearSession();
				}
			}
			
		}
	}
}
