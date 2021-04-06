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
public class EncounterListner {
	
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
	
	private final String trackInstanceUrl = DHIS2BASEURL + "/api/trackedEntityInstances.json?";
	
	private final String EVENTURL = DHIS2BASEURL + "/api/events";
	
	private Map<String, String> ObserVationDHISMapping = new HashMap<String, String>();
	
	private Map<String, String> multipleObsDHISMapping = new HashMap<String, String>();


	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		boolean status = true;
			try {
				psiapiServiceFactory.getAPIType("dhis2").get("", "", VERSIONAPI);
				
			}
			catch (Exception e) {
				
				status = false;
			}
		if (status) {
			
				try {
					sendEncounter();
				}
				catch (Exception e) {
					
				}
				try {
					sendEncounterFailed();
				}
				catch (Exception e) {
					
				}

			}
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
				SHNDataSyncStatusDTO syncStatus = Context.getService(PSIDHISExceptionService.class).findStatusToSendDataDhis("encounter_uuid", encounterUUid);
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
										if (!StringUtils.isBlank(elementId)){
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
										if (!StringUtils.isBlank(elementIdMultiple)){
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
						if(checkEncounterExistsOrNot != null && !StringUtils.isBlank(checkEncounterExistsOrNot.getReferenceId())) {
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
											eventResponse + "", "Dhis2 returns empty import summaries without reference id",getDhisEncounterExceptionforEachForms.getReferenceId(),encounterUUid,patientUuid,formsName);
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
							    PSIConstants.CONNECTIONTIMEOUTSTATUS, eventResponse + "", errorDetails,getDhisEncounterExceptionforEachForms.getReferenceId(),encounterUUid,patientUuid,formsName);
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
						    PSIConstants.CONNECTIONTIMEOUTSTATUS,trackEntityResponse + "", "No Track Entity Instances found in DHIS2 Containing the patient id provided",geDhisEncounterException.getReferenceId(),encounterUUid,patientUuid,geDhisEncounterException.getFormsName());
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
					    PSIConstants.CONNECTIONTIMEOUTSTATUS, "Please check Error for details" + "", e.toString(),geDhisEncounterException.getReferenceId(),encounterUUid,geDhisEncounterException.getPatientUuid(),geDhisEncounterException.getFormsName());
					}
				  }
				}
		 }
	
	 }
	
	private void sendEncounterFailed() {
		List<SHNDhisEncounterException> shnDhisEncounterExceptions = Context.getService(SHNDhisEncounterExceptionService.class).findAllFailedEncounterByStatus(
			    PSIConstants.CONNECTIONTIMEOUTSTATUS);
			if (shnDhisEncounterExceptions.size() != 0 && shnDhisEncounterExceptions != null) {
				for (SHNDhisEncounterException shnDhisEncounterException : shnDhisEncounterExceptions) {
					SHNDataSyncStatusDTO syncStatus = Context.getService(PSIDHISExceptionService.class).findStatusToSendDataDhis("encounter_uuid", shnDhisEncounterException.getEncounterId());
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
							if (StringUtils.isBlank(serviceForm)) {
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
												if (!StringUtils.isBlank(elementId)){
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
												if (!StringUtils.isBlank(elementIdMultiple)){
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
								if(checkEncounterExistsOrNot != null && !StringUtils.isBlank(checkEncounterExistsOrNot.getReferenceId())) {
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
											if(checkEncounterExistsOrNot == null) {
												SHNDhisEncounterException shnNewencounter = new SHNDhisEncounterException();
												updateExceptionForEncounterFailed(shnNewencounter,postEncounter + "", PSIConstants.SUCCESSSTATUS,eventResponse + "","",referenceId,patientUuid,formsName);
											}
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
											if(checkEncounterExistsOrNot == null) {
												SHNDhisEncounterException shnNewencounter = new SHNDhisEncounterException();
												updateExceptionForEncounterFailed(shnNewencounter,postEncounter + "", PSIConstants.SUCCESSSTATUS,eventResponse + "","",referenceId,patientUuid,formsName);
											}
											updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.SUCCESSSTATUS,eventResponse + "","",referenceId,patientUuid,formsName);
										} else {								
											updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "","Dhis2 returns empty import summaries without reference id",shnDhisEncounterException.getReferenceId(),patientUuid,formsName);
										}
											
									}
								}
								else 
								{
									String errorDetails = errorMessageCreation(eventResponse);
									updateExceptionForEncounterFailed(shnDhisEncounterException,postEncounter + "", PSIConstants.FAILEDSTATUS,eventResponse + "",errorDetails,shnDhisEncounterException.getReferenceId(),patientUuid,formsName);
								}
							 }
							
							}//loop end
						}
						else {
							updateExceptionForEncounterFailed(shnDhisEncounterException,trackEntityInstanceUrl, PSIConstants.CONNECTIONTIMEOUTSTATUS, trackEntityResponse + "" ,"No Track Entity Instances found in DHIS2 Containing the patient id provided",shnDhisEncounterException.getReferenceId(),patientUuid,"");
						}
					 } 
					catch (Exception e) {
						updateExceptionForEncounterFailed(shnDhisEncounterException,"Internal Error Occured", PSIConstants.FAILEDSTATUS, "Please check Error for details", e.toString(),shnDhisEncounterException.getReferenceId(),shnDhisEncounterException.getPatientUuid(),shnDhisEncounterException.getFormsName());
					}
					
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
				PSIDHISException findRefereceIdPatient = Context.getService(PSIDHISExceptionService.class).findReferenceIdOfPatient(patientUuid, 1);
				if (findRefereceIdPatient != null && !StringUtils.isBlank(findRefereceIdPatient.getReferenceId())) {
					
					if (StringUtils.isBlank(orgUnit)) {
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
						eventInformationObject.put("tackedEntityInstance", findRefereceIdPatient.getReferenceId());
						eventInformationObject.put("patientEventStatus", patientEventStatus);
						eventInformationObject.put("trackEntityInstanceUrl", trackEntityInstanceUrl);
						eventInformationObject.put("trackentityIsntanceResponse", trackentityIsntanceResponse);
					}
				}
				else {
					patientEventStatus = false;
					eventInformationObject.put("orgUnit", orgUnit);
					eventInformationObject.put("tackedEntityInstance", tackedEntityInstance);
					eventInformationObject.put("patientEventStatus", patientEventStatus);
					eventInformationObject.put("trackEntityInstanceUrl", trackEntityInstanceUrl);
					eventInformationObject.put("trackentityIsntanceResponse", trackentityIsntanceResponse);
				}

			}
		catch (Exception e) {
			e.printStackTrace();
			patientEventStatus = false;
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

}
	
