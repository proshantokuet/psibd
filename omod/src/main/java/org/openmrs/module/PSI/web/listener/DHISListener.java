package org.openmrs.module.PSI.web.listener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNDhisEncounterException;
import org.openmrs.module.PSI.SHNDhisIndicatorDetails;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.SHNDhisEncounterExceptionService;
import org.openmrs.module.PSI.api.SHNDhisObsElementService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.api.SHNVoidedMoneyReceiptLogService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.converter.DhisObsEventDataConverter;
import org.openmrs.module.PSI.converter.DhisObsJsonDataConverter;
import org.openmrs.module.PSI.converter.SHNStockAdjustDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.SHNDataSyncStatusDTO;
import org.openmrs.module.PSI.dto.ShnIndicatorDetailsDTO;
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
public class DHISListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";

	//private final String DHIS2BASEURL = "http://182.160.99.131";

	
	//private final String DHIS2BASEURL = "http://192.168.19.149";
	
	//private final String DHIS2BASEURL = "http://182.160.99.131";
	
	//test server psi
	//private final String DHIS2BASEURL = "http://10.100.11.2:5271";
	
	//live dhis url
	
	private static ResourceBundle resource = ResourceBundle.getBundle("deploymentConfig");
	
	private final static String DHIS2BASEURL = resource.getString("dhis2BaseUrl");
	
	private final static String isDeployInLightEmr = resource.getString("isDeployInLightEmr");
	
	private final static String isDeployInGlobal = resource.getString("isDeployInGlobal");
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	private final String DATASETURL = DHIS2BASEURL + "/api/dataValueSets";
	
	private final String GETEVENTURL = DHIS2BASEURL + "/api/events.json";
	
	private Map<String, String> ObserVationDHISMapping = new HashMap<String, String>();
	
	private Map<String, String> multipleObsDHISMapping = new HashMap<String, String>();

	private static final ReentrantLock lock = new ReentrantLock();

	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		if (!lock.tryLock()) {
			log.error("It is already in progress.");
	        return;
		}
		JSONObject getResponse = null;
		boolean status = true;
//			try {
//				getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
//				
//			}
//			catch (Exception e) {
//				
//				status = false;
//			}
		if (status) {
			
				try {
					sendPatient();
					Thread.sleep(1000);
				}
				catch (Exception e) {
					
				}
				finally {
					lock.unlock();
					log.error("complete listener patient at:" +new Date());
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
	
	public  void sendPatient() {
		
		log.error("Entered in send Patient listener " + new Date());
		//String logResult = "";
		int lastReadPatient = 0;
		Context.openSession();
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Patient0");
		if (getlastReadEntry == null) {
			PSIDHISMarker psidhisMarker = new PSIDHISMarker();
			psidhisMarker.setType("Patient0");
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
		Context.clearSession();
		log.error("last read entry" + lastReadPatient);
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		eventReceordDTOs = Context.getService(PSIDHISMarkerService.class).rawQuery(lastReadPatient);
		JSONObject response = new JSONObject();
		JSONObject patientJson = new JSONObject();
		log.error("object size" + eventReceordDTOs.size());
		if (eventReceordDTOs.size() != 0 && eventReceordDTOs != null) {
			for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
				String logResult = "";
				long timestampLog = System.currentTimeMillis();	
				log.error("Entered in loop" + eventReceordDTO.getId());
				PSIDHISException getPsidhisException = Context.getService(PSIDHISExceptionService.class).findAllById(
				    eventReceordDTO.getId());
				String patientUUidToCheck = eventReceordDTO.getUrl().substring(28,64);
				SHNDataSyncStatusDTO syncStatus = Context.getService(PSIDHISExceptionService.class).findStatusToSendDataDhis("patient_uuid", patientUUidToCheck);
				log.error("isDeployInGlobal" + isDeployInGlobal);
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
				log.error("willSent" + willSent);
				if(willSent) {
					log.error("checking response of dhis2" + eventReceordDTO.getDhisResponse());
					if(!StringUtils.isBlank(eventReceordDTO.getDhisResponse()))	{

				try {

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
						
						logResult = logResult + " (Patient) comeplete time taken patient to send data in dhis2: " + (System.currentTimeMillis() - timestampLog);
						updateException(getPsidhisException, patientJson + "", eventReceordDTO, PSIConstants.SUCCESSSTATUS,
						    response + "", "",referenceId,logResult);
						long currentime = System.currentTimeMillis();
						log.error("(Patient) comeplete time taken patient to send data in dhis2  " + (currentime - timestampLog));
						
					} else {
						if (getPsidhisException == null) {
							PSIDHISException newPsidhisException = new PSIDHISException();
							getPsidhisException = newPsidhisException;
						}
						String errorDetails = errorMessageCreation(response);
						updateException(getPsidhisException, patientJson + "", eventReceordDTO,
						    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", errorDetails,"",logResult);
					}					
				}
				catch (Exception e) {
					if (getPsidhisException == null) {
						PSIDHISException newPsidhisException = new PSIDHISException();
						getPsidhisException = newPsidhisException;
					}
					updateException(getPsidhisException, patientJson + "", eventReceordDTO,
					    PSIConstants.CONNECTIONTIMEOUTSTATUS, response + "", e.toString(),"", logResult);
				}
				finally {
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
				else {
					Context.openSession();
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					Context.clearSession();
				}

		}
	 }
		
	}
	
	private void updateException(PSIDHISException getPsidhisException, String patientJson, EventReceordDTO eventReceordDTO,
	                             int status, String response, String error, String referenceId, String logresult) {
		Context.openSession();
		
		getPsidhisException.setError(error);
		getPsidhisException.setJson(patientJson.toString());
		getPsidhisException.setMarkId(eventReceordDTO.getId());
		getPsidhisException.setUrl(eventReceordDTO.getUrl());
		getPsidhisException.setStatus(status);
		getPsidhisException.setResponse(response.toString());
		getPsidhisException.setDateCreated(new Date());
		getPsidhisException.setReferenceId(referenceId);
		getPsidhisException.setType(logresult);
		if(!StringUtils.isEmpty(eventReceordDTO.getUrl())) {
			String eventUrl = eventReceordDTO.getUrl();
			getPsidhisException.setPatientUuid(eventUrl.substring(28,64));
		}
		Context.getService(PSIDHISExceptionService.class).saveOrUpdate(getPsidhisException);
		Context.clearSession();
	}
	
	private void sendMoneyReceipt() {
		log.error("Entered in the function sendMoneyReceipt " + System.currentTimeMillis());
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
		log.error("goint to fetch all money receipt for that timestamp " + System.currentTimeMillis());
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllByTimestamp(timestamp);
		log.error("fetch complete all money receipt for that timestamp " + System.currentTimeMillis());
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
				log.error("Entered in the loop for sendMoneyReceipt " + System.currentTimeMillis());
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
					log.error("Event Response time moneyreceipt before sending to dhis2 " + System.currentTimeMillis());
					getEventResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", eventURL);
					log.error("Event Response time moneyreceipt after sending to dhis2 " + System.currentTimeMillis());
					if (getEventResponse.has("events")) {
						getEevnts = getEventResponse.getJSONArray("events");
					}
					
				}
				catch (Exception e) {
					
				}
				
				//if (getEevnts.length() == 0) {
					
					try {
						UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
						orgUnit = userDTO.getOrgUnit();
						URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + patientUuid + "&ou=" + orgUnit;
						log.error("going to check patient exist for this money receipt in dhis2 " + System.currentTimeMillis());
						getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
						log.error("check complete patient exist for this money receipt in dhis2 " + System.currentTimeMillis());
						log.error("convertingsendMoneyReceipt money receipt data for sending in  dhis2 " + System.currentTimeMillis());
						JSONArray trackedEntityInstances = new JSONArray();
						if (getResponse.has("trackedEntityInstances")) {
							trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
						}
						
						eventResponse = new JSONObject();
						//log.info("ADD:URL:" + URL + "getResponse:" + getResponse);
						if (trackedEntityInstances.length() != 0) {
							JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
							String trackedEntityInstanceId = trackedEntityInstance.getString("trackedEntityInstance");
							moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,trackedEntityInstanceId);
							log.error("dhisId" + psiServiceProvision.getDhisId());
							if(!StringUtils.isBlank(psiServiceProvision.getDhisId())) {
								log.error("Entering for update" + psiServiceProvision.getDhisId());
								String referenceUrl = EVENTURL + "/" + psiServiceProvision.getDhisId();
								JSONObject referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
								String status = referenceExist.getString("status");
								if (!status.equalsIgnoreCase("ERROR")) {
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").update("", moneyReceiptJson, "", referenceUrl);
								}
								else {
									log.error("didnot found" + psiServiceProvision.getDhisId());
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
								}
							}
							else {
								log.error("dont have previous money receipt addidng new" + psiServiceProvision.getDhisId());
								eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
							}
							moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
							    trackedEntityInstanceId);
							log.error("converting done money receipt data for sending in  dhis2 " + System.currentTimeMillis());
							log.error("completion time moneyreceipt before sending to dhis2 " + System.currentTimeMillis());
							eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
							log.error("completion time moneyreceipt after sending to dhis2 " + System.currentTimeMillis());
							log.error("saving response form dhis2 in table " + System.currentTimeMillis());
							statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
							String httpStatus = eventResponse.getString("httpStatus");
							//log.info("ADD:statusCode:" + statusCode + "" + eventResponse);
							if (statusCode == 200) {
								JSONObject successResponse = eventResponse.getJSONObject("response");
								if(successResponse.has("reference")) {
									String importStatus = successResponse.getString("status");
									if (importStatus.equalsIgnoreCase("SUCCESS")) {
										String referenceId = successResponse.getString("reference");
										Context.openSession();
										getlastTimeStamp.setVoidReason(httpStatus);
										getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
										Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
										Context.clearSession();
										
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
												eventResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
									}
									else {
										Context.openSession();
										getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
										Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
										Context.clearSession();
										
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "",psiServiceProvision.getDhisId(), eventResponse + "",
										    statusCode, "Dhis2 returns importStatus failed while editing", PSIConstants.CONNECTIONTIMEOUTSTATUS);
									}
								}
								else {
										
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
										
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", eventResponse + "",
										    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.CONNECTIONTIMEOUTSTATUS);
									}
							}
							else {
							
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
			}
			
		}
	}
	
	private void sendFailedMoneyReceipt() {
		String serviceUunid = "";
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllResend();
		log.error("Failed MOney receipt called for log error " + psiServiceProvisions.size());
		
		log.error("Chunk Count" + psiServiceProvisions.size());
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
				log.error("completion time moneyreceipt " + new Date());
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
				
//				try {
//					getEventResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", eventURL);
//					if (getEventResponse.has("events")) {
//						getEevnts = getEventResponse.getJSONArray("events");
//					}
//					/*Context.openSession();
//					PSIDHISException psidhisException = new PSIDHISException();
//					psidhisException.setResponse(getEventResponse + "");
//					psidhisException.setError(getEevnts + "");
//					psidhisException.setType(getEevnts.length() + "");
//					psidhisException.setJson(eventURL);
//					Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
//					
//					Context.clearSession();*/
//					
//				}
//				catch (Exception e) {
//					
//				}
				log.error("Event LEngth " + getEevnts.length());

//				if (getEevnts.length() == 0) {
				try {
					log.error("Event Response time moneyreceipt before sending to dhis2 " + new Date());
					getEventResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", eventURL);
					log.error("Event Response time moneyreceipt after sending to dhis2 " + new Date());
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
						
						//log.info("URL:" + URL + "getResponse:" + getResponse);
						if (trackedEntityInstances.length() != 0) {
							JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
							String trackedEntityInstanceId = trackedEntityInstance.getString("trackedEntityInstance");
							moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
							    trackedEntityInstanceId);
							log.error("dhisId" + psiServiceProvision.getDhisId());
							if(!StringUtils.isBlank(psiServiceProvision.getDhisId())) {
								String referenceUrl = EVENTURL + "/" + psiServiceProvision.getDhisId();
								JSONObject referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
								String status = referenceExist.getString("status");
								if (!status.equalsIgnoreCase("ERROR")) {
									log.error("Entering to edit" + status);
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").update("", moneyReceiptJson, "", referenceUrl);
								}
								else {
									log.error("Entering to ADD" + status);
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
								}
							}
							else{
								eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
							}
							log.error("completion time moneyreceipt before sending to dhis2 " + new Date());
							eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", moneyReceiptJson, EVENTURL);
							log.error("completion time moneyreceipt after getting response from dhis2 " + new Date());
							statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
							//log.info("statusCode:" + statusCode + "" + eventResponse);
							if (statusCode == 200) {
								JSONObject successResponse = eventResponse.getJSONObject("response");
								log.error("successResponse" + successResponse.toString());
								if(successResponse.has("reference")) {
									log.error("successResponse has reference" + successResponse.toString());
									String importStatus = successResponse.getString("status");
									if (importStatus.equalsIgnoreCase("SUCCESS")) {
										log.error("response has SUCCESS" + importStatus);
										String referenceId = successResponse.getString("reference");
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
												eventResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
									}
									else {
										log.error("response has not SUCCESS" + importStatus);
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), eventResponse + "",
											    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.FAILEDSTATUS);
									}
								}
							    else {
										
										/*Context.openSession();
										psiServiceProvision.setDhisId("");
										psiServiceProvision.setIsSendToDHIS(3);
										psiServiceProvision.setField1(getResponse + "");
										psiServiceProvision.setField2(moneyReceiptJson + "");
										psiServiceProvision.setField3(statusCode);
										psiServiceProvision.setError(URL);
										
										Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
										
										Context.clearSession();*/
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", eventResponse + "",
										    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.FAILEDSTATUS);
									}
							} 
							else {
								
								/*Context.openSession();
								psiServiceProvision.setDhisId("");
								psiServiceProvision.setIsSendToDHIS(3);
								psiServiceProvision.setField1(getResponse + "");
								psiServiceProvision.setField2(moneyReceiptJson + "");
								psiServiceProvision.setField3(statusCode);
								psiServiceProvision.setError(URL);
								
								Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
								Context.clearSession();
								*/
								String errorDetails = errorMessageCreation(eventResponse);
								
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", eventResponse + "",
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
							    statusCode, "No Track Entity Instances found in DHIS2 Containing this URL " + URL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
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
//				} else {
//					/*Context.openSession();
//					psiServiceProvision.setField1("not found");
//					psiServiceProvision.setField1(getResponse + "");
//					psiServiceProvision.setField2(moneyReceiptJson + "");
//					psiServiceProvision.setField3(psiServiceProvision.getIsSendToDHIS());
//					psiServiceProvision.setError(":" + URL);
//					psiServiceProvision.setIsSendToDHIS(PSIConstants.FAILEDSTATUS);
//					Context.getService(PSIServiceProvisionService.class).saveOrUpdate(psiServiceProvision);
//					Context.clearSession();*/
//					updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", "", getResponse + "", statusCode,
//					    eventURL, PSIConstants.SUCCESSSTATUS);
//				}
			}
			
		}
			
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
					String tackedEntityInstanceId = patientEventInformation.getString("tackedEntityInstance");
					String trackEntityInstanceUrl = patientEventInformation.getString("trackEntityInstanceUrl");
					JSONObject trackEntityResponse = patientEventInformation.getJSONObject("trackentityIsntanceResponse");
					if (patientEventStatus) {
						JSONParser parser = new JSONParser();
						org.json.simple.JSONObject jsonObsEncounter = (org.json.simple.JSONObject) parser.parse(EncounterObj.toString());
						org.json.simple.JSONArray obs = (org.json.simple.JSONArray) jsonObsEncounter.get("observations");
					// Converting Obs data into dhis post format
					String IntialJsonDHISArray = DhisObsJsonDataConverter.getObservations(obs);
					Object document = DhisObsJsonDataConverter.parseDocument(IntialJsonDHISArray);
					List<String> servicesInObservation = JsonPath.read(document, "$..service"); //extract service name

					//removing duplicity from list
					Set<String> uniqueSetOfServices = new HashSet<>(servicesInObservation);
					uniqueSetOfServices.forEach(uniqueSetOfService ->{
						//extract service wise JSON
						List<String> extractServiceJSON = JsonPath.read(document, "$.[?(@.service == '"+uniqueSetOfService+ "' && @.isVoided == false)]"); 
						mapDhisDataElementsId(uniqueSetOfService); //mapping dhis element into hashmap from database
						mapDhisMultipleDataElementsId(uniqueSetOfService); //mapping dhis multiple choice element into hashmap from database
							try {
								String convertedJson = new Gson().toJson(extractServiceJSON);
								JSONArray extractServiceArray = new JSONArray(convertedJson);
								// Event Metadata for posting into dhis
								JSONObject event = (JSONObject) DhisObsEventDataConverter.getEventMetaDataForDhis(tackedEntityInstanceId, orgUnit, uniqueSetOfService).get(uniqueSetOfService);
								if(event != null) {
									JSONArray dataValues = new JSONArray();
									for (int i = 0; i < extractServiceArray.length(); i++) {
										JSONObject serviceObject = extractServiceArray.getJSONObject(i);
										String field = serviceObject.getString("question");
										String value = serviceObject.getString("answer");
										String elementId = ObserVationDHISMapping.get(field.trim());
										if (!StringUtils.isEmpty(elementId)){
											JSONObject dataValue = new JSONObject();
											dataValue.put("dataElement", elementId);
											if(isNumeric(value)) {
												dataValue.put("value", Double.parseDouble(value));
											}
											else if(isDateValid(value)) {
												DateFormat dateFormatFirst = new SimpleDateFormat("yyyy-MM-dd hh:mm");
												DateFormat dateFormatSecond = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
												Date stringToDate = dateFormatFirst.parse(value);
												String DhisDateFormat = dateFormatSecond.format(stringToDate);
												dataValue.put("value", DhisDateFormat);
											}
											else {
												dataValue.put("value", value);
											}
											dataValues.put(dataValue);			
										}
										String elementIdMultiple = multipleObsDHISMapping.get(value.trim());
										if (!StringUtils.isEmpty(elementIdMultiple)){
											JSONObject dataValue = new JSONObject();
											dataValue.put("dataElement", elementIdMultiple);
											dataValue.put("value", "Yes");
											dataValues.put(dataValue);	
										}
									}
									event.put("dataValues", dataValues);
									servicesToPost.put(uniqueSetOfService, event);
								}
							} catch (Exception e) {
								e.printStackTrace(); //need to check the error
								SHNDhisEncounterException getDhisEncounterExceptionforEachFormsinTryCatch = Context.getService(SHNDhisEncounterExceptionService.class).findAllBymarkerIdAndFormName(eventReceordDTO.getId(), uniqueSetOfService,encounterUUid);
								if (getDhisEncounterExceptionforEachFormsinTryCatch == null) {
									SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
									getDhisEncounterExceptionforEachFormsinTryCatch = newDhisEncounterException;
								}
								updateEncounterException(getDhisEncounterExceptionforEachFormsinTryCatch,"", eventReceordDTO, PSIConstants.CONNECTIONTIMEOUTSTATUS,
										"", "Dhis2 Data Element json Parse error","",encounterUUid,patientUuid,uniqueSetOfService);
							}
					});
	                 
					// need to check the error
					JSONArray keys = servicesToPost.names();
					if (keys != null) {
					//posting in dhis for each forms
					for (int i = 0; i < keys.length(); i++) {
						
						String formsName = keys.getString(i); // Here's your key
						String value = servicesToPost.getString(formsName);
						JSONObject postEncounter = new JSONObject(value);
						SHNDhisEncounterException getDhisEncounterExceptionforEachForms = Context.getService(SHNDhisEncounterExceptionService.class).findAllBymarkerIdAndFormName(eventReceordDTO.getId(), formsName,encounterUUid);
						SHNDhisEncounterException checkEncounterExistsOrNot = Context.getService(SHNDhisEncounterExceptionService.class).findEncByFormAndEncId(encounterUUid, formsName);
						JSONObject eventResponse = new JSONObject();
						if(checkEncounterExistsOrNot != null && !StringUtils.isEmpty(checkEncounterExistsOrNot.getReferenceId())) {
								String referenceUrl = EVENTURL + "/" + checkEncounterExistsOrNot.getReferenceId();
								JSONObject referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
								String status = referenceExist.getString("status");
								if (!status.equalsIgnoreCase("ERROR")) {
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").update("", postEncounter, "", referenceUrl);
									//JSONObject deleteEventObject = psiapiServiceFactory.getAPIType("dhis2").delete("", "", referenceUrl);
								}
								else {
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", postEncounter, EVENTURL);
								}
						}
						else{
							eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", postEncounter, EVENTURL);
						}
						int statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
						if (statusCode == 200) {
							JSONObject successResponse = eventResponse.getJSONObject("response");
							if(successResponse.has("reference")) {
								String importStatus = successResponse.getString("status");
								if (importStatus.equalsIgnoreCase("SUCCESS")) {
									String referenceId = successResponse.getString("reference");
									if (getDhisEncounterExceptionforEachForms == null) {
										SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
										getDhisEncounterExceptionforEachForms = newDhisEncounterException;
									}
									updateEncounterException(getDhisEncounterExceptionforEachForms, postEncounter + "", eventReceordDTO, PSIConstants.SUCCESSSTATUS,
											eventResponse + "", "",referenceId,encounterUUid,patientUuid,formsName);
								}
								else {
									if (getDhisEncounterExceptionforEachForms == null) {
										SHNDhisEncounterException newDhisEncounterException = new SHNDhisEncounterException();
										getDhisEncounterExceptionforEachForms = newDhisEncounterException;
									}
									updateEncounterException(getDhisEncounterExceptionforEachForms, postEncounter + "", eventReceordDTO, PSIConstants.CONNECTIONTIMEOUTSTATUS,
											eventResponse + "", "Dhis2 returns importStatus failed while editing",getDhisEncounterExceptionforEachForms.getReferenceId(),encounterUUid,patientUuid,formsName);
								}
							}
							else {
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
						    PSIConstants.CONNECTIONTIMEOUTSTATUS,trackEntityResponse + "", "No Track Entity Instances found in DHIS2 Containing the patient id provided","",encounterUUid,patientUuid,"");
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
				for (SHNDhisEncounterException shnDhisEncounterException : shnDhisEncounterExceptions) {
					try {  
						JSONObject EncounterObj = psiapiServiceFactory.getAPIType("openmrs").get("", "", shnDhisEncounterException.getUrl());
					
						JSONObject  servicesToPost = new JSONObject();
						String patientUuid = (String)EncounterObj.get("patientUuid");
						JSONObject patientEventInformation = getDhisEventInformation(patientUuid);
						boolean  patientEventStatus = patientEventInformation.getBoolean("patientEventStatus");
						String orgUnit = patientEventInformation.getString("orgUnit");
						String tackedEntityInstanceId = patientEventInformation.getString("tackedEntityInstance");
						String trackEntityInstanceUrl = patientEventInformation.getString("trackEntityInstanceUrl");
						JSONObject trackEntityResponse = patientEventInformation.getJSONObject("trackentityIsntanceResponse");
						if (patientEventStatus) {
							JSONParser parser = new JSONParser();
							org.json.simple.JSONObject jsonObsEncounter = (org.json.simple.JSONObject) parser.parse(EncounterObj.toString());
							org.json.simple.JSONArray obs = (org.json.simple.JSONArray) jsonObsEncounter.get("observations");
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
								List<String> extractServiceJSON = JsonPath.read(document, "$.[?(@.service == '"+uniqueSetOfService+ "' && @.isVoided == false)]");
								mapDhisDataElementsId(uniqueSetOfService); //mapping dhis element into hashmap from database
								mapDhisMultipleDataElementsId(uniqueSetOfService); //mapping dhis multiple choice element into hashmap from database
									try {
										String convertedJson = new Gson().toJson(extractServiceJSON);
										JSONArray extractServiceArray = new JSONArray(convertedJson);
										// Event Metadata for posting into dhis
										JSONObject event = (JSONObject) DhisObsEventDataConverter.getEventMetaDataForDhis(tackedEntityInstanceId, orgUnit, uniqueSetOfService).get(uniqueSetOfService);
										if(event != null) {
											JSONArray dataValues = new JSONArray();
											for (int i = 0; i < extractServiceArray.length(); i++) {
												JSONObject serviceObject = extractServiceArray.getJSONObject(i);
												String field = serviceObject.getString("question");
												String value = serviceObject.getString("answer");
												String elementId = ObserVationDHISMapping.get(field.trim());
												if (!StringUtils.isEmpty(elementId)){
													JSONObject dataValue = new JSONObject();
													dataValue.put("dataElement", elementId);
													if(isNumeric(value)) {
														dataValue.put("value", Double.parseDouble(value));
													}
													else if(isDateValid(value)) {
														DateFormat dateFormatFirst = new SimpleDateFormat("yyyy-MM-dd hh:mm");
														DateFormat dateFormatSecond = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm");
														Date stringToDate = dateFormatFirst.parse(value);
														String DhisDateFormat = dateFormatSecond.format(stringToDate);
														dataValue.put("value", DhisDateFormat);
													}
													else {
														dataValue.put("value", value);
													}
													dataValues.put(dataValue);			
												}
												String elementIdMultiple = multipleObsDHISMapping.get(value.trim());
												if (!StringUtils.isEmpty(elementIdMultiple)){
													JSONObject dataValue = new JSONObject();
													dataValue.put("dataElement", elementIdMultiple);
													dataValue.put("value", "Yes");
													dataValues.put(dataValue);	
												}
											}
											event.put("dataValues", dataValues);
											servicesToPost.put(uniqueSetOfService, event);
									   }
									} catch (Exception e) {
										e.printStackTrace();
										updateExceptionForEncounterFailed(shnDhisEncounterException, "", PSIConstants.FAILEDSTATUS,"","Dhis2 Data Element json Parse error while editing","",patientUuid,uniqueSetOfService);
									}
							});
		                     
							JSONArray keys = servicesToPost.names();
							if (keys != null) {
							for (int i = 0; i < keys.length(); i++) {
								
								String formsName = keys.getString(i); // Here's your key
								String value = servicesToPost.getString(formsName);
								JSONObject postEncounter = new JSONObject(value);
								SHNDhisEncounterException checkEncounterExistsOrNot = Context.getService(SHNDhisEncounterExceptionService.class).findEncByFormAndEncIdForFailedEvent(shnDhisEncounterException.getEncounterId(), formsName);
								JSONObject eventResponse = new JSONObject();
								if(checkEncounterExistsOrNot != null && !StringUtils.isEmpty(checkEncounterExistsOrNot.getReferenceId())) {
									String referenceUrl = EVENTURL + "/" + checkEncounterExistsOrNot.getReferenceId();
									JSONObject referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
									String status = referenceExist.getString("status");
									if (!status.equalsIgnoreCase("ERROR")) {
										eventResponse = psiapiServiceFactory.getAPIType("dhis2").update("", postEncounter, "", referenceUrl);
										//JSONObject deleteEventObject = psiapiServiceFactory.getAPIType("dhis2").delete("", "", referenceUrl);
									}
									else {
										eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", postEncounter, EVENTURL);
									}
								}
								else{
									eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", postEncounter, EVENTURL);
								}
								int statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
								if (statusCode == 200) {
									JSONObject successResponse = eventResponse.getJSONObject("response");
									if(successResponse.has("reference")) {
										String importStatus = successResponse.getString("status");
										if (importStatus.equalsIgnoreCase("SUCCESS")) { 
											String referenceId = successResponse.getString("reference");
											updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.SUCCESSSTATUS,eventResponse + "","",referenceId,patientUuid,formsName);
										}
										else {
											updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "","Dhis2 returns importStatus failed while editing",shnDhisEncounterException.getReferenceId(),patientUuid,formsName);
										}
									}
									else {
										JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
										if (importSummaries.length() != 0) {
											JSONObject importSummariesObject = importSummaries.getJSONObject(0);
											String referenceId = importSummariesObject.getString("reference");
											updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.SUCCESSSTATUS,eventResponse + "","",referenceId,patientUuid,formsName);
										} else {								
											updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "","Dhis2 returns empty import summaries without reference id","",patientUuid,formsName);
										}
											
									}
								}
								else 
								{
									String errorDetails = errorMessageCreation(eventResponse);
									updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "",errorDetails,"",patientUuid,formsName);
								}
							 }
							
							}//loop end
						}
						else {
							updateExceptionForEncounterFailed(shnDhisEncounterException,trackEntityInstanceUrl, PSIConstants.CONNECTIONTIMEOUTSTATUS, trackEntityResponse + "" ,"No Track Entity Instances found in DHIS2 Containing the patient id provided","",patientUuid,"");
						}
					 } 
					catch (Exception e) {
						updateExceptionForEncounterFailed(shnDhisEncounterException,"Internal Error Occured", PSIConstants.FAILEDSTATUS, "Please check Error for details", e.toString(),"","","");
					}
				}
				
			}
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
	
	public static boolean isNumeric(String str) {
		return str.matches("[0-9.]*");
	}
	
	public static boolean isDateValid(String date) 
	{
		String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
        try {
            DateFormat df = new SimpleDateFormat(DATE_FORMAT);
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
	}
	
	public void sendStockDataInGLobalServer() {
		List<PSIClinicManagement> clinic = Context.getService(PSIClinicManagementService.class).getAllClinic();
		if(clinic.size() > 0) {
			PSIClinicManagement singleClinic = clinic.get(0);
			log.error("Stock Clinic primary key "+ singleClinic.getCid());
			List<SHNStock> getStockList = Context.getService(SHNStockService.class).getAllStockByClinicIdForSync(singleClinic.getCid());
			log.error("getStockList Size "+ getStockList.size());
			try {
				log.error("ENtering in try catch "+ getStockList.size());
				String stockUrl = "/openmrs/ws/rest/v1/stock/getstock/get-converted-data/" + singleClinic.getCid();
				JSONArray stockJsonArray = psiapiServiceFactory.getAPIType("openmrs").getJsonArray("", "", stockUrl);
				log.error("stockJsonArray Size "+ stockJsonArray.length());
				for (int i = 0; i < stockJsonArray.length(); i++) {
					log.error("ENtering in loop "+ stockJsonArray.length());
					JSONObject stockJsonObeject = stockJsonArray.getJSONObject(i);
					log.error("stockJsonObeject "+ stockJsonObeject.toString());
					String url = "/rest/v1/stock/save-update-inGlobal";
					JSONObject Stock = psiapiServiceFactory.getAPIType("openmrs").postInRemoteOpenMRS("", stockJsonObeject, url);
					log.error("response"+ Stock.toString());
				}
			} catch (JSONException e) {
				log.error("Exception Happened "+ getStockList.size());
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("Error in stock "+ e.getMessage().toString());
			}

			log.error("Leaving try catch "+ getStockList.size());
		}
		
	}
	
	public void sendAdjustStockDataInGLobalServer() {
		List<PSIClinicManagement> clinic = Context.getService(PSIClinicManagementService.class).getAllClinic();
		if(clinic.size() > 0) {
			PSIClinicManagement singleClinic = clinic.get(0);
			log.error("Clinic ID Adjust "+ singleClinic.getClinicId());
			List<SHNStockAdjust> getStockList = Context.getService(SHNStockService.class).getAllAdjustHistoryForSync(singleClinic.getCid());
			log.error("getAdjustStockList Size "+ getStockList.size());
			try {
				JSONArray stockJsonArray = new SHNStockAdjustDataConverter().toConvert(getStockList);
				for (int i = 0; i < stockJsonArray.length(); i++) {
					JSONObject stockJsonObeject = stockJsonArray.getJSONObject(i);
					log.error("Adjust Json Object"+ stockJsonObeject.toString());
					String url = "/rest/v1/stock/adjust-save-update-in-global";
					JSONObject Stock = psiapiServiceFactory.getAPIType("openmrs").postInRemoteOpenMRS("", stockJsonObeject, url);
					log.error("response"+ Stock.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		 
	}
	
	public void sendIndicatorDataToDhis() {
		
		SHNDhisIndicatorDetails shnDhisIndicatorDetails = null;
		
		try {
//			int calculateCountOfFpConraceptiveMethod = Context.getService(SHNDhisObsElementService.class).calculateCountOfFpConraceptiveMethod();
//			log.error("calculateCountOfFpConraceptiveMethod" + calculateCountOfFpConraceptiveMethod);
//	
//			int calculateCountOfFphypertensionAndDiabetic = Context.getService(SHNDhisObsElementService.class).calculateCountOfFphypertensionAndDiabetic();
//			log.error("calculateCountOfFphypertensionAndDiabetic" + calculateCountOfFphypertensionAndDiabetic);
//			
//			int calculateCountOfFpPermanentMethod = Context.getService(SHNDhisObsElementService.class).calculateCountOfFpPermanentMethod();
//			log.error("calculateCountOfFpPermanentMethod" + calculateCountOfFpPermanentMethod);
//			
//			int calculateCountOfFpAncTakenAtleastOne = Context.getService(SHNDhisObsElementService.class).calculateCountOfFpAncTakenAtleastOne();
//			log.error("calculateCountOfFpAncTakenAtleastOne" + calculateCountOfFpAncTakenAtleastOne);
//			
//			int calculatePercentageOfFp = Context.getService(SHNDhisObsElementService.class).calculatePercentageOfFp();
//			log.error("calculatePercentageOfFp" + calculatePercentageOfFp);
			
			int getCompletedAncFullCountFromMoneyReceipt = Context.getService(SHNDhisObsElementService.class).getCompletedAncFullCountFromMoneyReceipt();
			log.error("getCompletedAncFullCountFromMoneyReceipt" + getCompletedAncFullCountFromMoneyReceipt);
			
			ShnIndicatorDetailsDTO shnIndicatorDetailsDTO = new ShnIndicatorDetailsDTO();
			
//			shnIndicatorDetailsDTO.setFpContraceptiveMethod(calculateCountOfFpConraceptiveMethod);
//			shnIndicatorDetailsDTO.setFpHypertensionAndDiabetic(calculateCountOfFphypertensionAndDiabetic);
//			shnIndicatorDetailsDTO.setFpPermanentMethod(calculateCountOfFpPermanentMethod);
//			shnIndicatorDetailsDTO.setFpAncTakenAtleastOne(calculateCountOfFpAncTakenAtleastOne);
//			shnIndicatorDetailsDTO.setCalculatePercentageOfFp(calculatePercentageOfFp);
			shnIndicatorDetailsDTO.setCalculateAncAllTakenFullCount(getCompletedAncFullCountFromMoneyReceipt);
			
			JSONObject indicatorData = DHISDataConverter.toConvertDhisIndicatorData(shnIndicatorDetailsDTO);
			log.error("indicatorData" + indicatorData.toString());
			JSONObject eventResponse = new JSONObject();
			shnDhisIndicatorDetails = Context.getService(SHNDhisObsElementService.class).getDhisIndicatorByType("INDICATOR");
				eventResponse = psiapiServiceFactory.getAPIType("dhis2").add("", indicatorData, DATASETURL);

					String importStatus = eventResponse.getString("status");
					if (importStatus.equalsIgnoreCase("SUCCESS")) {
						log.error("response has SUCCESS" + importStatus);
						if(shnDhisIndicatorDetails == null) {
							shnDhisIndicatorDetails = new SHNDhisIndicatorDetails();
						}
						updateIndicatorDetailsStatus(shnDhisIndicatorDetails,indicatorData.toString(),DATASETURL,PSIConstants.SUCCESSSTATUS,eventResponse.toString(),"","zPaSPZ5vk4n");
					}
					else {
						log.error("response has not SUCCESS" + importStatus);
						if(shnDhisIndicatorDetails == null) {
							shnDhisIndicatorDetails = new SHNDhisIndicatorDetails();
						}
						updateIndicatorDetailsStatus(shnDhisIndicatorDetails,indicatorData.toString(),DATASETURL,PSIConstants.FAILEDSTATUS,eventResponse.toString(),"Check response for details error","zPaSPZ5vk4n");
					}
			}
	
		catch (Exception ex) {
			if(shnDhisIndicatorDetails == null) {
				shnDhisIndicatorDetails = new SHNDhisIndicatorDetails();
			}
			updateIndicatorDetailsStatus(shnDhisIndicatorDetails,"Internal Error Occured",DATASETURL,PSIConstants.FAILEDSTATUS,"Check Error Message for Details",ex.getMessage(),"zPaSPZ5vk4n");
		}
	}
	
	
	private void updateIndicatorDetailsStatus(SHNDhisIndicatorDetails shnDhisIndicatorDetails,
			String postJson,String url, int status,
			String response, String error,String referenceId) {
		Context.openSession();

		shnDhisIndicatorDetails.setError(error);
		shnDhisIndicatorDetails.setPostJson(postJson.toString());
		shnDhisIndicatorDetails.setUrl(url);
		shnDhisIndicatorDetails.setStatus(status);
		shnDhisIndicatorDetails.setResponse(response.toString());
		shnDhisIndicatorDetails.setDateCreated(new Date());
		shnDhisIndicatorDetails.setIndicatorType("INDICATOR");
		shnDhisIndicatorDetails.setReferenceId(referenceId);

		Context.getService(SHNDhisObsElementService.class).saveOrupdate(shnDhisIndicatorDetails);
		Context.clearSession();
	}
	
	
	private void deleteMoneyReceiptFromDhis2() {
		
		List<SHNVoidedMoneyReceiptLog> shnVoidedMoneyReceiptLog = Context.getService(SHNVoidedMoneyReceiptLogService.class).getAllVoidedMoneyReceipt();
		for (SHNVoidedMoneyReceiptLog shnVoidedMoneyReceiptLogObject : shnVoidedMoneyReceiptLog) {
			if(shnVoidedMoneyReceiptLogObject != null && !StringUtils.isEmpty(shnVoidedMoneyReceiptLogObject.getDhisId())) {
				
				ArrayList<String> elephantList = new ArrayList<>(Arrays.asList(shnVoidedMoneyReceiptLogObject.getDhisId().split(",")));
				try {
					for (String dhisId : elephantList) {
						if(!StringUtils.isEmpty(dhisId)) {
						log.error("splitted single dhisid" + dhisId);
						JSONObject eventResponse = new JSONObject();
						String referenceUrl = EVENTURL + "/" + dhisId.trim();
						JSONObject referenceExist;
	
							referenceExist = psiapiServiceFactory.getAPIType("dhis2").get("", "", referenceUrl);
						
							String status = referenceExist.getString("status");
							if (!status.equalsIgnoreCase("ERROR")) {
								eventResponse = psiapiServiceFactory.getAPIType("dhis2").delete("", "", referenceUrl);
								int statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
								//log.info("statusCode:" + statusCode + "" + eventResponse);
								if (statusCode == 200) {
									JSONObject successResponse = eventResponse.getJSONObject("response");
									log.error("successResponse" + successResponse.toString());
									if(successResponse.has("reference")) {
										log.error("successResponse has reference" + successResponse.toString());
										String importStatus = successResponse.getString("status");
										if (importStatus.equalsIgnoreCase("SUCCESS")) {
											log.error("response has SUCCESS" + importStatus);
											shnVoidedMoneyReceiptLogObject.setVoided(true);
											Context.getService(SHNVoidedMoneyReceiptLogService.class).saveOrUpdate(shnVoidedMoneyReceiptLogObject);
	
										}
									}
								}
							}
						}
					  }

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
	
