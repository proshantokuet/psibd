package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.AUHCClinicTypeService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.UserDTO;
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
public class DHISListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
//	private final String DHIS2BASEURL = "http://192.168.19.149:1971";
	
	//private final String DHIS2BASEURL = "http://192.168.19.162:8080";
	
	private final String DHIS2BASEURL = "http://10.100.11.3:5271";
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	private final String GETEVENTURL = DHIS2BASEURL + "/api/events.json";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		
		//log testing
//		log.info("Info Message");
//		log.debug("Debug Message");
//		log.error("Error Message Testing");
		//log testing
		
		JSONObject getResponse = null;
		boolean status = true;
		try {
			getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			
		}
		catch (Exception e) {
			
			status = false;
		}
		if (status) {
			
//			Context.openSession();
//			String errorStatus = "";
//			PSIDHISException psidhisException = new PSIDHISException();
//			psidhisException.setResponse("testee");
//			psidhisException.setError("adds");
//			psidhisException.setType("test");
//			psidhisException.setJson("dsd");
//			Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
//			
//			Context.clearSession();
//			try {
//				sendFailedPatient();
//			}
//			catch (Exception e) {
//				
//			}
//			try {
//				sendPatient();
//			}
//			catch (Exception e) {
//				
//			}
//			try {
//				sendMoneyReceipt();
//			}
//			catch (Exception e) {
//				
//			}
//			
//			try {
//				sendFailedMoneyReceipt();
//			}
//			catch (Exception e) {
//				
//			}
		}
//		throw new RuntimeException("DHIS testing");
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
						
						updateExceptionForFailed(psidhisException, patientJson + "", PSIConstants.FAILEDSTATUS, response
						        + "", "");
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
		getPsidhisException.setDateCreated(new Date());
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
		
		Context.clearSession();
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
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.openSession();
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					
					/*JSONObject responseofResponse = new JSONObject();
					responseofResponse = response.getJSONObject("response");*/
					String status = response.getString("status");
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
						updateException(getPsidhisException, patientJson + "", eventReceordDTO, PSIConstants.SUCCESSSTATUS,
						    response + "", "");
					} else {
						if (getPsidhisException == null) {
							PSIDHISException newPsidhisException = new PSIDHISException();
							getPsidhisException = newPsidhisException;
						}
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
						    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", "");
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
					    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", e.toString());
				}
			}
			
		}
	}
	
	private void updateException(PSIDHISException getPsidhisException, String patientJson, EventReceordDTO eventReceordDTO,
	                             int status, String response, String error) {
		Context.openSession();
		
		getPsidhisException.setError(error);
		getPsidhisException.setJson(patientJson.toString());
		getPsidhisException.setMarkId(eventReceordDTO.getId());
		getPsidhisException.setUrl(eventReceordDTO.getUrl());
		getPsidhisException.setStatus(status);
		getPsidhisException.setResponse(response.toString());
		getPsidhisException.setDateCreated(new Date());
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
		
		Context.clearSession();
	}
	
	private void sendMoneyReceipt() {
		long timestamp = 0;
		
		String serviceUunid = "";
		
		PSIDHISMarker getlastTimeStamp = Context.getService(PSIDHISMarkerService.class).findByType("MoneyReceipt");
		if (getlastTimeStamp == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("MoneyReceipt");
			psidhisMarker.setTimestamp(0l);
			psidhisMarker.setLastPatientId(0);
			psidhisMarker.setDateCreated(new Date());
			psidhisMarker.setUuid(UUID.randomUUID().toString());
			psidhisMarker.setVoided(false);
			
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			
		} else {
			timestamp = getlastTimeStamp.getTimestamp();
		}
		
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllByTimestamp(timestamp);
		Context.openSession();
		PSIDHISException psidhisException1 = new PSIDHISException();
		psidhisException1.setResponse("");
		psidhisException1.setError(psiServiceProvisions.size() + "");
		psidhisException1.setType("fff");
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException1);
		
		Context.clearSession();
		if (psiServiceProvisions.size() != 0) {
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				
				JSONObject getEventResponse = new JSONObject();
				JSONArray getEevnts = new JSONArray();
				JSONObject moneyReceiptJson = new JSONObject();
				String patientUuid = psiServiceProvision.getPatientUuid();
				String uuid = DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				String eventURL = "";
				int statusCode = 0;
				String orgUnit = "";
				
				serviceUunid = "";
				serviceUunid = psiServiceProvision.getUuid();
				
				eventURL = GETEVENTURL + "?program=" + DHISMapper.registrationMapper.get("program") + "&filter="
				        + DHISMapper.ServiceProvision.get("serviceUuid") + ":eq:" + serviceUunid;
				
				try {
					getEventResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", eventURL);
					if (getEventResponse.has("events")) {
						getEevnts = getEventResponse.getJSONArray("events");
					}
					Context.openSession();
					PSIDHISException psidhisException = new PSIDHISException();
					psidhisException.setResponse(getEventResponse + "");
					psidhisException.setError(getEevnts + "");
					psidhisException.setType(getEevnts.length() + "");
					psidhisException.setJson(eventURL);
					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
					
					Context.clearSession();
					
				}
				catch (Exception e) {
					
				}
				
				if (getEevnts.length() == 0) {
					
					try {
						UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
						orgUnit = userDTO.getOrgUnit();
						URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + patientUuid + "&ou=" + orgUnit;
						getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
						JSONArray trackedEntityInstances = new JSONArray();
						if (getResponse.has("trackedEntityInstances")) {
							trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
						}
						
						eventResponse = new JSONObject();
						log.info("ADD:URL:" + URL + "getResponse:" + getResponse);
						if (trackedEntityInstances.length() != 0) {
							JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
							String trackedEntityInstanceId = trackedEntityInstance.getString("trackedEntityInstance");
							moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
							    trackedEntityInstanceId);
							
							eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
							statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
							String httpStatus = eventResponse.getString("httpStatus");
							log.info("ADD:statusCode:" + statusCode + "" + eventResponse);
							if (statusCode == 200) {
								JSONObject successResponse = eventResponse.getJSONObject("response");
								JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
								if (importSummaries.length() != 0) {
									JSONObject importSummary = importSummaries.getJSONObject(0);
									String referenceId = importSummary.getString("reference");
									Context.openSession();
									/*psiServiceProvision.setDhisId(referenceId);
									psiServiceProvision.setIsSendToDHIS(1);
									psiServiceProvision.setField1(getResponse + "");
									psiServiceProvision.setField2(moneyReceiptJson + "");
									psiServiceProvision.setField3(statusCode);
									psiServiceProvision.setError(URL);
									Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
									*/
									getlastTimeStamp.setVoidReason(httpStatus);
									getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
									Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
									Context.clearSession();
									
									updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
									    getResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
								} else {
									
									Context.openSession();
									/*psiServiceProvision.setDhisId("");
									psiServiceProvision.setIsSendToDHIS(3);
									psiServiceProvision.setField1(getResponse + "");
									psiServiceProvision.setField2(moneyReceiptJson + "");
									psiServiceProvision.setField3(statusCode);
									psiServiceProvision.setError(URL);
									getlastTimeStamp.setVoidReason(httpStatus);
									Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);*/
									getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
									Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
									Context.clearSession();
									
									updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
									    statusCode, eventURL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
								}
							} else {
								getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
								/*psiServiceProvision.setField1(getResponse + "");
								psiServiceProvision.setField2(moneyReceiptJson + "");
								psiServiceProvision.setField3(statusCode);
								psiServiceProvision.setError(URL);
								getlastTimeStamp.setVoidReason(httpStatus);
								psiServiceProvision.setIsSendToDHIS(PSIConstants.CONNECTIONTIMEOUTSTATUS);
								Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);*/
								getlastTimeStamp.setVoidReason(httpStatus);
								Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
								    statusCode, eventURL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
							}
							
						} else {
							
							Context.openSession();
							getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
							getlastTimeStamp.setVoidReason("else cindtion");
							/*psiServiceProvision.setField1("not found");
							psiServiceProvision.setField1(getResponse + "");
							psiServiceProvision.setField2(moneyReceiptJson + "");
							psiServiceProvision.setField3(statusCode);
							psiServiceProvision.setError(":" + URL);
							psiServiceProvision.setIsSendToDHIS(PSIConstants.CONNECTIONTIMEOUTSTATUS);
							Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);*/
							Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
							Context.clearSession();
							
							updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
							    statusCode, eventURL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
						}
					}
					catch (Exception e) {
						//e.printStackTrace();
						Context.openSession();
						getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
						/*psiServiceProvision.setField1(getResponse + "");
						psiServiceProvision.setField2(moneyReceiptJson + "");
						psiServiceProvision.setField3(statusCode);
						psiServiceProvision.setError(e.getMessage());
						psiServiceProvision.setIsSendToDHIS(PSIConstants.CONNECTIONTIMEOUTSTATUS);
						Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);*/
						getlastTimeStamp.setVoidReason(e.toString());
						Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
						Context.clearSession();
						updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "", statusCode,
						    eventURL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
					}
					
				} else {
					Context.openSession();
					getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
					/*psiServiceProvision.setField1(getResponse + "");
					psiServiceProvision.setField2(moneyReceiptJson + "");
					psiServiceProvision.setField3(statusCode);
					psiServiceProvision.setError("found is_send_to_dhis 1");
					psiServiceProvision.setIsSendToDHIS(PSIConstants.CONNECTIONTIMEOUTSTATUS);
					Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);*/
					//getlastTimeStamp.setVoidReason(e.toString());
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
					Context.clearSession();
					
					updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "", statusCode,
					    eventURL, PSIConstants.SUCCESSSTATUS);
				}
			}
			
		}
	}
	
	private void sendFailedMoneyReceipt() {
		String serviceUunid = "";
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllResend();
		String errorStatus = "";
		PSIDHISException psidhisException = new PSIDHISException();
		psidhisException.setResponse("");
		psidhisException.setError("");
		psidhisException.setType(psiServiceProvisions.size() + "");
		psidhisException.setJson("");
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
		
		Context.clearSession();
		
		if (psiServiceProvisions.size() != 0) {
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				JSONObject moneyReceiptJson = new JSONObject();
				JSONObject getEventResponse = new JSONObject();
				JSONArray getEevnts = new JSONArray();
				String patientUuid = psiServiceProvision.getPatientUuid();
				String uuid = DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				int statusCode = 0;
				String orgUnit = "";
				String eventURL = "";
				
				serviceUunid = "";
				serviceUunid = psiServiceProvision.getUuid();
				
				eventURL = GETEVENTURL + "?program=" + DHISMapper.registrationMapper.get("program") + "&filter="
				        + DHISMapper.ServiceProvision.get("serviceUuid") + ":eq:" + serviceUunid;
				
				try {
					getEventResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", eventURL);
					if (getEventResponse.has("events")) {
						getEevnts = getEventResponse.getJSONArray("events");
					}
					/*Context.openSession();
					PSIDHISException psidhisException = new PSIDHISException();
					psidhisException.setResponse(getEventResponse + "");
					psidhisException.setError(getEevnts + "");
					psidhisException.setType(getEevnts.length() + "");
					psidhisException.setJson(eventURL);
					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
					
					Context.clearSession();*/
					
				}
				catch (Exception e) {
					
				}
				
				if (getEevnts.length() == 0) {
					try {
						UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
						orgUnit = userDTO.getOrgUnit();
						URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + patientUuid + "&ou=" + orgUnit;
						getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
						JSONArray trackedEntityInstances = new JSONArray();
						if (getResponse.has("trackedEntityInstances")) {
							trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
						}
						
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
									/*Context.openSession();
									psiServiceProvision.setField2(moneyReceiptJson + "");
									psiServiceProvision.setDhisId(referenceId);
									psiServiceProvision.setField1(getResponse + "");
									psiServiceProvision.setField2(moneyReceiptJson + "");
									psiServiceProvision.setField3(statusCode);
									psiServiceProvision.setError(":" + URL);
									psiServiceProvision.setIsSendToDHIS(PSIConstants.SUCCESSSTATUS);
									Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
									Context.clearSession();*/
									
									updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
									    getResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
								} else {
									
									/*Context.openSession();
									psiServiceProvision.setDhisId("");
									psiServiceProvision.setIsSendToDHIS(3);
									psiServiceProvision.setField1(getResponse + "");
									psiServiceProvision.setField2(moneyReceiptJson + "");
									psiServiceProvision.setField3(statusCode);
									psiServiceProvision.setError(URL);
									
									Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
									
									Context.clearSession();*/
									updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
									    statusCode, eventURL, PSIConstants.FAILEDSTATUS);
								}
							} else {
								
								/*Context.openSession();
								psiServiceProvision.setDhisId("");
								psiServiceProvision.setIsSendToDHIS(3);
								psiServiceProvision.setField1(getResponse + "");
								psiServiceProvision.setField2(moneyReceiptJson + "");
								psiServiceProvision.setField3(statusCode);
								psiServiceProvision.setError(URL);
								
								Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
								*/
								Context.clearSession();
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
								    statusCode, eventURL, PSIConstants.FAILEDSTATUS);
							}
							
						} else {
							/*Context.openSession();
							psiServiceProvision.setField1("not found");
							psiServiceProvision.setField1(getResponse + "");
							psiServiceProvision.setField2(moneyReceiptJson + "");
							psiServiceProvision.setField3(statusCode);
							psiServiceProvision.setError(":" + URL);
							psiServiceProvision.setIsSendToDHIS(PSIConstants.FAILEDSTATUS);
							Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
							Context.clearSession();*/
							updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
							    statusCode, eventURL, PSIConstants.FAILEDSTATUS);
						}
					}
					catch (Exception e) {
						Context.openSession();
						//e.printStackTrace();
						int status = 0;
						if ("java.lang.RuntimeException: java.net.ConnectException: Connection refused (Connection refused)"
						        .equalsIgnoreCase(e.toString())
						        || "org.hibernate.LazyInitializationException: could not initialize proxy - no Session"
						                .equalsIgnoreCase(e.toString())) {
							//psiServiceProvision.setIsSendToDHIS(PSIConstants.CONNECTIONTIMEOUTSTATUS);
							status = PSIConstants.CONNECTIONTIMEOUTSTATUS;
						} else {
							psiServiceProvision.setIsSendToDHIS(PSIConstants.FAILEDSTATUS);
							status = PSIConstants.FAILEDSTATUS;
						}
						/*psiServiceProvision.setField1(getResponse + "");
						psiServiceProvision.setField2(moneyReceiptJson + "");
						psiServiceProvision.setField3(statusCode);
						psiServiceProvision.setError(e.getMessage());
						if ("java.lang.RuntimeException: java.net.ConnectException: Connection refused (Connection refused)"
						        .equalsIgnoreCase(e.toString())
						        || "org.hibernate.LazyInitializationException: could not initialize proxy - no Session"
						                .equalsIgnoreCase(e.toString())) {
							//psiServiceProvision.setIsSendToDHIS(PSIConstants.CONNECTIONTIMEOUTSTATUS);
							errorStatus = PSIConstants.CONNECTIONTIMEOUTSTATUS;
						} else {
							psiServiceProvision.setIsSendToDHIS(PSIConstants.FAILEDSTATUS);
						}
						Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
						Context.clearSession();*/
						
						updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "", statusCode,
						    eventURL, status);
					}
				} else {
					/*Context.openSession();
					psiServiceProvision.setField1("not found");
					psiServiceProvision.setField1(getResponse + "");
					psiServiceProvision.setField2(moneyReceiptJson + "");
					psiServiceProvision.setField3(psiServiceProvision.getIsSendToDHIS());
					psiServiceProvision.setError(":" + URL);
					psiServiceProvision.setIsSendToDHIS(PSIConstants.FAILEDSTATUS);
					Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
					Context.clearSession();*/
					updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "", statusCode,
					    eventURL, PSIConstants.SUCCESSSTATUS);
				}
			}
			
		} else {
			
		}
		
	}
	
	private void updateServiceProvision(PSIServiceProvision psiServiceProvision, String moneyReceiptJson,
	                                    String referenceId, String getResponse, int statusCode, String URL, int status) {
		psiServiceProvision.setField2(moneyReceiptJson);
		psiServiceProvision.setDhisId(referenceId);
		psiServiceProvision.setField1(getResponse + "");
		psiServiceProvision.setField2(moneyReceiptJson + "");
		psiServiceProvision.setField3(statusCode);
		psiServiceProvision.setError(":" + URL);
		psiServiceProvision.setIsSendToDHIS(status);
		Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
		Context.clearSession();
		
	}
	
}
