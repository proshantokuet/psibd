package org.openmrs.module.PSI.web.listener;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
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
public class MoneyReceiptFailedListener1 {

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
	public void sendFailedMoneyReceiptData() throws Exception {
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
				sendFailedMoneyReceipt();
				Thread.sleep(1000);
			}
			catch (Exception e) {
				
			}
			finally {
				lock.unlock();
				log.error("complete listener failed MoneyReceipt1 at:" +new Date());
			}
		}

	}

	
	private synchronized void sendFailedMoneyReceipt() {
		log.error("Entered in failed money receipt listener " + new Date());
		List<PSIServiceProvision> psiServiceProvisions = Context.getService(PSIServiceProvisionService.class)
		        .findAllResend();
		if (psiServiceProvisions.size() != 0) {
			for (PSIServiceProvision psiServiceProvision : psiServiceProvisions) {
				if(psiServiceProvision.getSpid() % 3 == 1 && psiServiceProvision.getSendToDhisFromGlobal() == 1) {
				//log.error("completion time moneyreceipt " + new Date());
				JSONObject eventResponse = new JSONObject();
				JSONObject getResponse = new JSONObject();
				JSONObject moneyReceiptJson = new JSONObject();
				String patientUuid = psiServiceProvision.getPatientUuid();
				DHISMapper.registrationMapper.get("uuid");
				String URL = "";
				int statusCode = 0;
				String eventURL = "";
				
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
										
										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", referenceId,
												eventResponse + "", statusCode, eventURL, PSIConstants.SUCCESSSTATUS);
									} else {

										updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), eventResponse + "",
										    statusCode, "Dhis2 returns empty import summaries without reference id", PSIConstants.FAILEDSTATUS);
									}
								}
							} else {
								
								String errorDetails = errorMessageCreation(eventResponse);
								updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
								    statusCode, errorDetails, PSIConstants.FAILEDSTATUS);
							}
							
						} else {

							updateServiceProvision(psiServiceProvision, moneyReceiptJson + "", psiServiceProvision.getDhisId(), getResponse + "",
							    statusCode, "No Track Entity Instances found in DHIS2 Containing this URL " + URL, PSIConstants.FAILEDSTATUS);
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
		PSIServiceProvision psiServiceProvisionUpdate = new PSIServiceProvision();
		psiServiceProvisionUpdate.setField2(moneyReceiptJson);
		psiServiceProvisionUpdate.setDhisId(referenceId);
		psiServiceProvisionUpdate.setField1(getResponse + "");
		psiServiceProvisionUpdate.setField2(moneyReceiptJson + "");
		psiServiceProvisionUpdate.setField3(statusCode);
		psiServiceProvisionUpdate.setError("" + URL);
		psiServiceProvisionUpdate.setIsSendToDHIS(status);
		psiServiceProvisionUpdate.setSpid(psiServiceProvision.getSpid());
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

