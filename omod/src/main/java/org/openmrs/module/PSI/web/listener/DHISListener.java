package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNDhisEncounterException;
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
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.DHISMapper;
import org.openmrs.module.PSI.utils.PSIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import com.jayway.jsonpath.JsonPath;

@Service
@EnableScheduling
@Configuration
@EnableAsync
@Controller
public class DHISListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	private final String DHIS2BASEURL = "http://192.168.19.149";
	
	//private final String DHIS2BASEURL = "http://192.168.19.162:8080";
	
	//private final String DHIS2BASEURL = "http://10.100.11.3:5271";
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	private final String GETEVENTURL = DHIS2BASEURL + "/api/events.json";
	
	private Map<String, String> ObserVationDHISMapping = new HashMap<String, String>();

	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		
//		JSONObject getResponse = null;
//		boolean status = true;
//		try {
//			getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
//			
//		}
//		catch (Exception e) {
//			
//			status = false;
//		}
//		if (status) {
//			
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
//		}

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
						    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", errorDetails);
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
		if(!StringUtils.isEmpty(eventReceordDTO.getUrl())) {
			String eventUrl = eventReceordDTO.getUrl();
			getPsidhisException.setPatientUuid(eventUrl.substring(28,64));
		}
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
//		Context.openSession();
//		PSIDHISException psidhisException1 = new PSIDHISException();
//		psidhisException1.setResponse("");
//		psidhisException1.setError(psiServiceProvisions.size() + "");
//		psidhisException1.setType("fff");
//		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException1);
//		
//		Context.clearSession();
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
//					Context.openSession();
//					PSIDHISException psidhisException = new PSIDHISException();
//					psidhisException.setResponse(getEventResponse + "");
//					psidhisException.setError(getEevnts + "");
//					psidhisException.setType(getEevnts.length() + "");
//					psidhisException.setJson(eventURL);
//					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
//					
//					Context.clearSession();
					
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
									    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.CONNECTIONTIMEOUTSTATUS);
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
								String errorDetails = errorMessageCreation(eventResponse);
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
								    statusCode, errorDetails, PSIConstants.CONNECTIONTIMEOUTSTATUS);
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
							    statusCode, "No Track Entity Instances found in DHIS2 Containing this URL " + URL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
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
						    e.toString(), PSIConstants.CONNECTIONTIMEOUTSTATUS);
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
//		PSIDHISException psidhisException = new PSIDHISException();
//		psidhisException.setResponse("");
//		psidhisException.setError("");
//		psidhisException.setType(psiServiceProvisions.size() + "");
//		psidhisException.setJson("");
//		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
//		
//		Context.clearSession();
		
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
									    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.FAILEDSTATUS);
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
								String errorDetails = errorMessageCreation(eventResponse);
								Context.clearSession();
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "",
								    statusCode, errorDetails, PSIConstants.FAILEDSTATUS);
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
							    statusCode, "No Track Entity Instances found in DHIS2 Containing this URL " + URL, PSIConstants.FAILEDSTATUS);
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
						    e.toString(), status);
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
				errorMessage = "has importsummaries";
					JSONObject importsObject = importSummaries.getJSONObject(0);
					if (importsObject.has("conflicts")) {
						JSONArray conflictsArray = importsObject.getJSONArray("conflicts");
						JSONObject conflictsObject = conflictsArray.getJSONObject(0);
						String httpStatusCode = responsefull.getString("httpStatusCode");
						errorMessage = "Http Status Code : " + httpStatusCode + " Message: " + conflictsObject.getString("value");
				}
			} catch (Exception e) {
				errorMessage = e.getMessage();
			}

		}
		return errorMessage;
	}
	
	public void sendEncounter() {
		int lastReadEncounter = 0;
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Encounter");
		if (getlastReadEntry == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("Encounter");
			psidhisMarker.setTimestamp(0l);
			psidhisMarker.setLastPatientId(0);
			psidhisMarker.setDateCreated(new Date());
			psidhisMarker.setUuid(UUID.randomUUID().toString());
			psidhisMarker.setVoided(false);
			Context.openSession();
			Context.getService(PSIDHISMarkerService.class).saveOrUpdate(psidhisMarker);
			Context.clearSession();
		} else {
			lastReadEncounter = getlastReadEntry.getLastPatientId();
		}
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		eventReceordDTOs = Context.getService(PSIDHISMarkerService.class).getEventRecordsOfEncounter(lastReadEncounter);
		if (eventReceordDTOs.size() != 0 && eventReceordDTOs != null) {
			mapDhisDataElementsId(); //mapping dhis element into hashmap from database
			for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
				SHNDhisEncounterException geDhisEncounterException = Context.getService(SHNDhisEncounterExceptionService.class)
						.findAllById(eventReceordDTO.getId());
				String encounterUUid = eventReceordDTO.getUrl().split("/|\\?")[7];
				try {  
					JSONObject EncounterObj = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
					JSONObject  servicesToPost = new JSONObject();
					String patientUuid = (String)EncounterObj.get("patientUuid");
					// getting track entity instance and org unit
					JSONObject patientEventInformation = getDhisEventInformation(patientUuid);
					boolean  patientEventStatus = patientEventInformation.getBoolean("patientEventStatus");
					String orgUnit = patientEventInformation.getString("orgUnit");
					String tackedEntityInstance = patientEventInformation.getString("tackedEntityInstance");
					String trackEntityInstanceUrl = patientEventInformation.getString("trackEntityInstanceUrl");
					if (patientEventStatus) {
					org.json.simple.JSONArray obs = (org.json.simple.JSONArray) EncounterObj.get("observations");
					// Converting Obs data into dhis post format
					String IntialJsonDHISArray = DhisObsJsonDataConverter.getObservations(obs);
					Object document = DhisObsJsonDataConverter.parseDocument(IntialJsonDHISArray);
					List<String> servicesInObservation = JsonPath.read(document, "$..service"); //extract service name
					//removing duplicity from list
					Set<String> uniqueSetOfServices = new HashSet<>(servicesInObservation);
					uniqueSetOfServices.forEach(uniqueSetOfService ->{
						//extract service wise JSON
						List<String> extractServiceJSON = JsonPath.read(document, "$.[?(@.service == '"+uniqueSetOfService+ "' && @.voidReason == null)]"); 
							try {
								JSONArray extractServiceArray = new JSONArray(extractServiceJSON);
								// Event Metadata for posting into dhis
								JSONObject event = (JSONObject) DhisObsEventDataConverter.getEventMetaDataForDhis(tackedEntityInstance, orgUnit).get(uniqueSetOfService);
								JSONArray dataValues = new JSONArray();
								for (int i = 0; i < extractServiceArray.length(); i++) {
									JSONObject serviceObject = (JSONObject) extractServiceArray.get(i);
									String field = (String) serviceObject.get("question");
									Object value =  serviceObject.get("answer");
									
									String elementId = ObserVationDHISMapping.get(field);
									if (!StringUtils.isEmpty(elementId)){
									JSONObject dataValue = new JSONObject();
									dataValue.put("dataElement", elementId);
									dataValue.put("value", value);
									dataValues.put(dataValue);			
									}
								}
								event.put("dataValues", dataValues);
								servicesToPost.put(uniqueSetOfService, event);
							} catch (Exception e) {
								e.printStackTrace(); //need to check the error
							}
					});
	                 
					// need to check the error
					JSONArray keys = servicesToPost.names();
					
					//posting in dhis for each forms
					for (int i = 0; i < keys.length(); i++) {
						
						String formsName = keys.getString(i); // Here's your key
						String value = servicesToPost.getString(formsName);
						JSONObject postEncounter = new JSONObject(value);
						SHNDhisEncounterException getDhisEncounterExceptionforEachForms = Context.getService(SHNDhisEncounterExceptionService.class).findAllBymarkerIdAndFormName(eventReceordDTO.getId(), formsName);
						SHNDhisEncounterException checkEncounterExistsOrNot = Context.getService(SHNDhisEncounterExceptionService.class).findEncByFormAndEncId(encounterUUid, formsName);
						if(checkEncounterExistsOrNot != null) {
							if(!StringUtils.isEmpty(checkEncounterExistsOrNot.getReferenceId()))
							{   String referenceUrl = EVENTURL + "/" + checkEncounterExistsOrNot.getReferenceId();
								JSONObject referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
								String status = referenceExist.getString("status");
								if (!status.equalsIgnoreCase("ERROR")) {
									JSONObject deleteEventObject = psiapiServiceFactory.getAPIType("dhis2").delete("", "", referenceUrl);
								}
							}
						}
						JSONObject eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", postEncounter, EVENTURL);
						int statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
						if (statusCode == 200) {
							JSONObject successResponse = eventResponse.getJSONObject("response");
							JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
							if (importSummaries.length() != 0) {
								JSONObject importSummariesObject = importSummaries.getJSONObject(0);
								String referenceId = importSummariesObject.getString("reference");
								if (getDhisEncounterExceptionforEachForms == null) {
									SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
									getDhisEncounterExceptionforEachForms = newDhisEncounterException;
								}
								updateEncounterException(getDhisEncounterExceptionforEachForms, postEncounter + "", eventReceordDTO, PSIConstants.SUCCESSSTATUS,
										eventResponse + "", "",referenceId,encounterUUid,patientUuid,formsName);
							} else {								
								if (getDhisEncounterExceptionforEachForms == null) {
									SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
									getDhisEncounterExceptionforEachForms = newDhisEncounterException;
								}
								updateEncounterException(getDhisEncounterExceptionforEachForms, postEncounter + "", eventReceordDTO, PSIConstants.CONNECTIONTIMEOUTSTATUS,
										eventResponse + "", "Dhis2 returns empty import summaries without reference id","",encounterUUid,patientUuid,formsName);
							}
						}
						else 
						{
							if (getDhisEncounterExceptionforEachForms == null) {
								SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
								getDhisEncounterExceptionforEachForms = newDhisEncounterException;
							}
							String errorDetails = errorMessageCreation(eventResponse);
							updateEncounterException(getDhisEncounterExceptionforEachForms, postEncounter+ "", eventReceordDTO,
							    PSIConstants.CONNECTIONTIMEOUTSTATUS, eventResponse + "", errorDetails,"",encounterUUid,patientUuid,formsName);
						}
					} //loop end
					Context.openSession();
					//increasing dhis marker by the id we find
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					Context.clearSession();
					}
					else {
						getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
						Context.openSession();
						if (geDhisEncounterException == null) {
							SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
							geDhisEncounterException = newDhisEncounterException;
						}
						//increasing dhis marker by the id we find
						Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
						Context.clearSession();
						updateEncounterException(geDhisEncounterException, trackEntityInstanceUrl, eventReceordDTO,
						    PSIConstants.CONNECTIONTIMEOUTSTATUS, "Check the trackinstance url", "No Track Entity Instances found in DHIS2 Containing the patient id provided","",encounterUUid,patientUuid,"");
					}
				} 
				catch (Exception e) {
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.openSession();
					if (geDhisEncounterException == null) {
						SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
						geDhisEncounterException = newDhisEncounterException;
					}
					//increasing dhis marker by the id we find
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					Context.clearSession();
					updateEncounterException(geDhisEncounterException, "Internal Error Occured" + "", eventReceordDTO,
					    PSIConstants.CONNECTIONTIMEOUTSTATUS, "Please check Error for details" + "", e.toString(),"",encounterUUid,"","");
					}
				}
		 }
	
	 }
	
	private void sendEncounterFailed() {
		List<SHNDhisEncounterException> shnDhisEncounterExceptions = Context.getService(SHNDhisEncounterExceptionService.class).findAllFailedEncounterByStatus(
			    PSIConstants.CONNECTIONTIMEOUTSTATUS);
			if (shnDhisEncounterExceptions.size() != 0 && shnDhisEncounterExceptions != null) {
				mapDhisDataElementsId();
				for (SHNDhisEncounterException shnDhisEncounterException : shnDhisEncounterExceptions) {
					try {  
						JSONObject EncounterObj = psiapiServiceFactory.getAPIType("openmrs").get("", "", shnDhisEncounterException.getUrl());
					
						JSONObject  servicesToPost = new JSONObject();
						String patientUuid = (String)EncounterObj.get("patientUuid");
						JSONObject patientEventInformation = getDhisEventInformation(patientUuid);
						boolean  patientEventStatus = patientEventInformation.getBoolean("patientEventStatus");
						String orgUnit = patientEventInformation.getString("orgUnit");
						String tackedEntityInstance = patientEventInformation.getString("tackedEntityInstance");
						String trackEntityInstanceUrl = patientEventInformation.getString("trackEntityInstanceUrl");
						if (patientEventStatus) {
							org.json.simple.JSONArray obs = (org.json.simple.JSONArray) EncounterObj.get("observations");
							String IntialJsonDHISArray = DhisObsJsonDataConverter.getObservations(obs);
							Object document = DhisObsJsonDataConverter.parseDocument(IntialJsonDHISArray);
							String serviceForm = shnDhisEncounterException.getFormsName();
							Set<String> uniqueSetOfServices = new HashSet<>();
							if (StringUtils.isEmpty(serviceForm)) {
								List<String> servicesInObservation = JsonPath.read(document, "$..service");
								uniqueSetOfServices.addAll(servicesInObservation);
							}
							else {
								uniqueSetOfServices.add(serviceForm);
							}
							uniqueSetOfServices.forEach(uniqueSetOfService ->{
								List<String> extractServiceJSON = JsonPath.read(document, "$.[?(@.service == '"+uniqueSetOfService+ "' && @.voidReason == null)]");
									try {
										JSONArray extractServiceArray = new JSONArray(extractServiceJSON);
										JSONObject event = (JSONObject) DhisObsEventDataConverter.getEventMetaDataForDhis(tackedEntityInstance, orgUnit).get(uniqueSetOfService);
										JSONArray dataValues = new JSONArray();
										for (int i = 0; i < extractServiceArray.length(); i++) {
											JSONObject serviceObject = (JSONObject) extractServiceArray.get(i);
											String field = (String) serviceObject.get("question");
											Object value =  serviceObject.get("answer");
											
											String elementId = ObserVationDHISMapping.get(field);
											if (!StringUtils.isEmpty(elementId)){
											JSONObject dataValue = new JSONObject();
											dataValue.put("dataElement", elementId);
											dataValue.put("value", value);
											dataValues.put(dataValue);			
											}
										}
										event.put("dataValues", dataValues);
										servicesToPost.put(uniqueSetOfService, event);
									} catch (Exception e) {
										e.printStackTrace();
									}
							});
		                     
							JSONArray keys = servicesToPost.names();
							
							for (int i = 0; i < keys.length(); i++) {
								
								String formsName = keys.getString(i); // Here's your key
								String value = servicesToPost.getString(formsName);
								SHNDhisEncounterException checkEncounterExistsOrNot = Context.getService(SHNDhisEncounterExceptionService.class).findEncByFormAndEncId(shnDhisEncounterException.getEncounterId(), formsName);
								if(checkEncounterExistsOrNot != null) {
									if(!StringUtils.isEmpty(checkEncounterExistsOrNot.getReferenceId()))
									{   String referenceUrl = EVENTURL + "/" + checkEncounterExistsOrNot.getReferenceId();
										JSONObject referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
										String status = referenceExist.getString("status");
										if (!status.equalsIgnoreCase("ERROR")) {
											JSONObject deleteEventObject = psiapiServiceFactory.getAPIType("dhis2").delete("", "", referenceUrl);
										}
									}
								}
								JSONObject postEncounter = new JSONObject(value);
								JSONObject eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", postEncounter, EVENTURL);
								int statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
								if (statusCode == 200) {
									JSONObject successResponse = eventResponse.getJSONObject("response");
									JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
									if (importSummaries.length() != 0) {
										JSONObject importSummariesObject = importSummaries.getJSONObject(0);
										String referenceId = importSummariesObject.getString("reference");
										updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.SUCCESSSTATUS,eventResponse + "","",referenceId,patientUuid,formsName);
									} else {								
										updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "","Dhis2 returns empty import summaries without reference id","",patientUuid,formsName);
									}
								}
								else 
								{
									String errorDetails = errorMessageCreation(eventResponse);
									updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "",errorDetails,"",patientUuid,formsName);
								}
							} //loop end
						}
						else {
							updateExceptionForEncounterFailed(shnDhisEncounterException,trackEntityInstanceUrl, PSIConstants.FAILEDSTATUS, "Check the trackinstance url","No Track Entity Instances found in DHIS2 Containing the patient id provided","",patientUuid,"");
						}
					 } 
					catch (Exception e) {
						updateExceptionForEncounterFailed(shnDhisEncounterException,"Internal Error Occured", PSIConstants.FAILEDSTATUS, "Please check Error for details", e.toString(),"","","");
					}
				}
				
			}
	}
	
	private  JSONObject getDhisEventInformation(String patientUuid) throws JSONException {
		boolean patientEventStatus = true;
		JSONObject eventInformationObject = new JSONObject();
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
			JSONObject trackentityIsntances = psiapiServiceFactory.getAPIType("dhis2").get("", "", trackEntityInstanceUrl);
		
		
				JSONArray trackedEntityInstances = new JSONArray();
				if (trackentityIsntances.has("trackedEntityInstances")) {
					trackedEntityInstances = (JSONArray) trackentityIsntances.get("trackedEntityInstances");
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
		}
		else {
			patientEventStatus = true;
			eventInformationObject.put("orgUnit", orgUnit);
			eventInformationObject.put("tackedEntityInstance", tackedEntityInstance);
			eventInformationObject.put("patientEventStatus", patientEventStatus);
			eventInformationObject.put("trackEntityInstanceUrl", trackEntityInstanceUrl);
		}
		return eventInformationObject;
	}
	
	private void mapDhisDataElementsId() {
		List<SHNDhisObsElement> dhisObsElement = Context.getService(SHNDhisObsElementService.class).getAllDhisElement();
		for (SHNDhisObsElement item : dhisObsElement) {
			ObserVationDHISMapping.put(item.getElementName(),item.getElementDhisId());
		}
	}
	
	private void updateEncounterException(SHNDhisEncounterException shnDhisEncounterException,
			String encounterJson, EventReceordDTO eventReceordDTO, int status,
			String response, String error,String referenceId, String encounterUuid, String patientUuid, String formsName) {
		Context.openSession();

		shnDhisEncounterException.setError(error);
		shnDhisEncounterException.setPostJson(encounterJson.toString());
		shnDhisEncounterException.setMarkId(eventReceordDTO.getId());
		shnDhisEncounterException.setUrl(eventReceordDTO.getUrl());
		shnDhisEncounterException.setStatus(status);
		shnDhisEncounterException.setResponse(response.toString());
		shnDhisEncounterException.setDateCreated(new Date());
		shnDhisEncounterException.setReferenceId(referenceId);
		shnDhisEncounterException.setEncounterId(encounterUuid);
		shnDhisEncounterException.setPatientUuid(patientUuid);
		shnDhisEncounterException.setFormsName(formsName);
		Context.getService(SHNDhisEncounterExceptionService.class).saveOrUpdate(
				shnDhisEncounterException);
		Context.clearSession();
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
}
	
