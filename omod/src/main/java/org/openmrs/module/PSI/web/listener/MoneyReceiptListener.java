package org.openmrs.module.PSI.web.listener;

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
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
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
public class MoneyReceiptListener {

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
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	private static final ReentrantLock lock = new ReentrantLock();
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendMoneyReceiptData() throws Exception {
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
				sendMoneyReceipt();
			}
			catch (Exception e) {
				
			}
			finally {
				lock.unlock();
				log.error("complete listener MoneyReceipt at:" +new Date());
			}
		}

	}
		
	private synchronized void sendMoneyReceipt() {
		log.error("Entered in send money receipt listener " + new Date());

		//log.error("Entered in the function sendMoneyReceipt " + System.currentTimeMillis());
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
	    log.error("goint to fetch all money receipt for that timestamp " + timestamp);
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllByTimestamp(timestamp);

		if (psiServiceProvisions.size() != 0) {
			log.error("psiServiceProvisions size" + psiServiceProvisions.size());
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				if(psiServiceProvision.getSpid() % 3 == 0 && psiServiceProvision.getSendToDhisFromGlobal() == 1) {
				//log.error("Entered in the loop for sendMoneyReceipt " + System.currentTimeMillis());
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				JSONObject moneyReceiptJson = new JSONObject();
				String patientUuid = psiServiceProvision.getPatientUuid();
				DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				String eventURL = "";
				int statusCode = 0;
				psiServiceProvision.getUuid();

					try {
						UserDTO userDTO = Context.getService(PSIClinicUserService.class).findOrgUnitFromOpenMRS(patientUuid);
						userDTO.getOrgUnit();
						PSIDHISException findRefereceIdPatient = Context.getService(PSIDHISExceptionService.class).findReferenceIdOfPatient(patientUuid, 1);
						if (findRefereceIdPatient != null && !StringUtils.isBlank(findRefereceIdPatient.getReferenceId())) {
							//JSONObject trackedEntityInstance = trackedEntityInstances.getJSONObject(0);
							String trackedEntityInstanceId = findRefereceIdPatient.getReferenceId();
							moneyReceiptJson = DHISDataConverter.toConvertMoneyReceipt(psiServiceProvision,
							    trackedEntityInstanceId);
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
							statusCode = Integer.parseInt(eventResponse.getString("httpStatusCode"));
							String httpStatus = eventResponse.getString("httpStatus");
							if (statusCode == 200) {
								JSONObject successResponse = eventResponse.getJSONObject("response");
								if(successResponse.has("reference")) {
									String importStatus = successResponse.getString("status");
									if (importStatus.equalsIgnoreCase("SUCCESS")) {
										String referenceId = successResponse.getString("reference");
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
												eventResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
									}
									else {
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "",psiServiceProvision.getDhisId(), eventResponse + "",
										    statusCode, "Dhis2 returns importStatus failed while editing", PSIConstants.CONNECTIONTIMEOUTSTATUS);
									}
								}
								else {
									
								JSONArray importSummaries = successResponse.getJSONArray("importSummaries");
								if (importSummaries.length() != 0) {
									JSONObject importSummary = importSummaries.getJSONObject(0);
									String referenceId = importSummary.getString("reference");
									updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
											eventResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
									//log.error("saving complete response from dhis2 in table " + System.currentTimeMillis());
								} else {
									
									updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
									    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.CONNECTIONTIMEOUTSTATUS);
								}
								
								}
							 }
							else {
								String errorDetails = errorMessageCreation(eventResponse);
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
								    statusCode, errorDetails, PSIConstants.CONNECTIONTIMEOUTSTATUS);
							}
							
						} else {
							
							updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
							    statusCode, "No Track Entity Instances found in DHIS2 Containing this URL " + URL, PSIConstants.CONNECTIONTIMEOUTSTATUS);
						}
					}
					catch (Exception e) {
						updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "", statusCode,
						    e.toString(), PSIConstants.CONNECTIONTIMEOUTSTATUS);
					}
					
					finally {
						Context.openSession();
						getlastTimeStamp.setTimestamp(psiServiceProvision.getTimestamp());
						getlastTimeStamp.setVoidReason("");
						Context.getService(PSIDHISMarkerService.class).saveOrUpdate(getlastTimeStamp);
						Context.clearSession();
						log.error("updated at MoneyReceipt at"+new Date());
						PSIDHISMarker getTimeStamp = Context.getService(PSIDHISMarkerService.class).findByType("MoneyReceipt");
						log.error("updated at MoneyReceipt at"+getTimeStamp+" at "+new Date());
					}
					
				}
			}
			
		  }
		}
	
	private void updateServiceProvision(PSIServiceProvision psiServiceProvision, String moneyReceiptJson,
	                                    String referenceId, String getResponse, int statusCode, String URL, int status) {
		Context.openSession();
		PSIServiceProvision psiServiceProvisionUpdate = new PSIServiceProvision();
		psiServiceProvisionUpdate.setField2(moneyReceiptJson);
		psiServiceProvisionUpdate.setDhisId(referenceId);
		psiServiceProvisionUpdate.setField1(getResponse + "");
		psiServiceProvisionUpdate.setField2(moneyReceiptJson + "");
		psiServiceProvisionUpdate.setField3(statusCode);
		psiServiceProvisionUpdate.setError("" + URL);
		psiServiceProvisionUpdate.setIsSendToDHIS(status);
		psiServiceProvisionUpdate.setSpid(psiServiceProvision.getSpid());
		log.error("updating moneyreceipt listener" + psiServiceProvisionUpdate.getDhisId());
		Context.getService(PSIServiceProvisionService.class).updateCoulumnInServiceProvision(psiServiceProvisionUpdate);
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

