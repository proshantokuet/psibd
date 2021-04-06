package org.openmrs.module.PSI.web.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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
public class MoneyReceiptFailedListener {

	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	//private final String DHIS2BASEURL = "http://dhis.mpower-social.com:1971";
	
	//private final String DHIS2BASEURL = "http://192.168.19.149";
	
	//test server psi
	//private final String DHIS2BASEURL = "http://10.100.11.2:5271";
	
	//live dhis url
	private static ResourceBundle resource = ResourceBundle.getBundle("deploymentConfig");
	
	private final static String DHIS2BASEURL = resource.getString("dhis2BaseUrl");
	
	private final String VERSIONAPI = DHIS2BASEURL + "/api/metadata/version";
	
	private final String trackerUrl = DHIS2BASEURL + "/api/trackedEntityInstances";
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendFailedMoneyReceiptData() throws Exception {
		
		boolean status = true;
		try {
			psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
			
		}
		catch (Exception e) {
			
			status = false;
		}
		if (status) {
			try {
				sendFailedMoneyReceipt();
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
		log.error("Entered in the function sendPatient " + System.currentTimeMillis());
		int lastReadPatient = 0;
		PSIDHISMarker getlastReadEntry = Context.getService(PSIDHISMarkerService.class).findByType("Patient");
		log.error("Getting last Entry for marker " + System.currentTimeMillis());
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
		log.error("Goint to fetch last marker patient " + System.currentTimeMillis());
		eventReceordDTOs = Context.getService(PSIDHISMarkerService.class).rawQuery(lastReadPatient);
		log.error("fetch complete last marker patient " + System.currentTimeMillis());
		JSONObject response = new JSONObject();
		JSONObject patientJson = new JSONObject();
		if (eventReceordDTOs.size() != 0 && eventReceordDTOs != null) {
			for (EventReceordDTO eventReceordDTO : eventReceordDTOs) {
				log.error("Entered in the EventReceordDTO loop " + System.currentTimeMillis());
				log.error("going to find if this exist in exception table " + System.currentTimeMillis());
				PSIDHISException getPsidhisException = Context.getService(PSIDHISExceptionService.class).findAllById(
				    eventReceordDTO.getId());
				log.error("Find complete if this exist in exception table " + System.currentTimeMillis());
				try {
					log.error("going to fetch patient from openmrs " + System.currentTimeMillis());
					JSONObject patient = psiapiServiceFactory.getAPIType("openmrs").get("", "", eventReceordDTO.getUrl());
					log.error("fetch patient complete from openmrs " + System.currentTimeMillis());
					log.error("Converting patient data for dhis2 " + System.currentTimeMillis());
					patientJson = DHISDataConverter.toConvertPatient(patient);
					JSONObject person = patient.getJSONObject("person");
					String orgUit = patientJson.getString("orgUnit");
					String uuid = DHISMapper.registrationMapper.get("uuid");
					String personUuid = person.getString("uuid");
					log.error("Converting patient data done for dhis2 " + System.currentTimeMillis());
					String URL = trackInstanceUrl + "filter=" + uuid + ":EQ:" + personUuid + "&ou=" + orgUit;
					log.error("Event Response time of patient before sending to dhis2 " + System.currentTimeMillis());
					JSONObject getResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", URL);
					log.error("Event Response time of patient after sending to dhis2 " + System.currentTimeMillis());
					JSONArray trackedEntityInstances = new JSONArray();
					if (getResponse.has("trackedEntityInstances")) {
						trackedEntityInstances = getResponse.getJSONArray("trackedEntityInstances");
					}
					
					if (trackedEntityInstances.length() != 0) {
						patientJson.remove("enrollments");
						JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
						String UpdateUrl = trackerUrl + "/" + trackedEntityInstance.getString("trackedEntityInstance");
						log.error("Time taken of patient update before sending to dhis2 " + System.currentTimeMillis());
						response = psiapiServiceFactory.getAPIType("dhis2").update("", patientJson, "", UpdateUrl);
						log.error("Time taken of patient update after sending to dhis2 " + System.currentTimeMillis());
					} else {
						log.error("Time taken of patient add before sending to dhis2 " + System.currentTimeMillis());
						response = psiapiServiceFactory.getAPIType("dhis2").add("", patientJson, trackerUrl);
						log.error("Time taken of patient add after sending to dhis2 " + System.currentTimeMillis());
					}
					log.error("Going to save marker status in table " + System.currentTimeMillis());
					getlastReadEntry.setLastPatientId(eventReceordDTO.getId());
					Context.openSession();
					Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastReadEntry);
					log.error("save complete of putting marker status in table " + System.currentTimeMillis());
					/*JSONObject responseofResponse = new JSONObject();
					responseofResponse = response.getJSONObject("response");*/
					log.error("save complete of putting marker status in table " + System.currentTimeMillis());
					log.error("Going to save response data in exception table " + System.currentTimeMillis());
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
						log.error("save complete of response data in exception table " + System.currentTimeMillis());
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
	
	private void sendFailedMoneyReceipt() {
		log.error("Entered in failed money receipt listener " + new Date());
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllResend();
		log.error("Chunk Count" + psiServiceProvisions.size());
		if (psiServiceProvisions.size() != 0) {
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				if(psiServiceProvision.getSpid() % 3 == 0 && psiServiceProvision.getSendToDhisFromGlobal() == 1) {
				long timestampLog = System.currentTimeMillis();	
				log.error("(MONEY RECEIPT) start money receipt to send data  " + timestampLog);
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				JSONObject moneyReceiptJson = new JSONObject();
				new JSONObject();
				new JSONArray();
				String patientUuid = psiServiceProvision.getPatientUuid();
				DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				int statusCode = 0;
				String eventURL = "";
				
				psiServiceProvision.getUuid();
				
//				eventURL = GETEVENTURL + "?program=" + DHISMapper.registrationMapper.get("program") + "&filter="
//				        + DHISMapper.ServiceProvision.get("serviceUuid") + ":eq:" + serviceUunid;
//				
//				try {
//					//log.error("Event Response time moneyreceipt before sending to dhis2 " + new Date());
//					getEventResponse = psiapiServiceFactory.getAPIType("dhis2").get("", "", eventURL);
//					//log.error("Event Response time moneyreceipt after sending to dhis2 " + new Date());
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
				
					try {
						UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
						userDTO.getOrgUnit();
						
						//log.info("URL:" + URL + "getResponse:" + getResponse);
						PSIDHISException findRefereceIdPatient = Context.getService(PSIDHISExceptionService.class).findReferenceIdOfPatient(patientUuid, 1);
						if (findRefereceIdPatient != null && !StringUtils.isBlank(findRefereceIdPatient.getReferenceId())) {
							//JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
							String trackedEntityInstanceId = findRefereceIdPatient.getReferenceId();
							moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
							    trackedEntityInstanceId);
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
									log.error("Coudnot find reference in response" + successResponse.toString());
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
												eventResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
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
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), eventResponse + "",
										    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.FAILEDSTATUS);
									}
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
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
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
							updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
							    statusCode, "No Track Entity Instances found in DHIS2 Containing this URL " + URL, PSIConstants.FAILEDSTATUS);
						}
					}
					catch (Exception e) {
						//Context.openSession();
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
						
						updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "", statusCode,
						    e.toString(), status);
					}
			}
			
			} 
		}
			else {
			
		}
		
	}
	
	private void updateServiceProvision(PSIServiceProvision psiServiceProvision, String moneyReceiptJson,
	                                    String referenceId, String getResponse, int statusCode, String URL, int status) {
		
		Context.openSession();
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

