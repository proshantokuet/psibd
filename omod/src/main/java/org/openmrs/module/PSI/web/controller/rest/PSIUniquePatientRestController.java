package org.openmrs.module.PSI.web.controller.rest;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.SHNFormPdfDetails;
import org.openmrs.module.PSI.SHnPrescriptionMetaData;
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.springframework.http.HttpHeaders;
import org.openmrs.module.PSI.web.listener.HnqisToShnListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/rest/v1/check/uniquePatient")
@RestController
public class PSIUniquePatientRestController {
	protected final Log log = LogFactory.getLog(getClass());
	@RequestMapping(value = "/{uic}/{mobileNo}", method = RequestMethod.GET)
	public ResponseEntity<String> saveClinic(@PathVariable String uic, @PathVariable String mobileNo) throws Exception {
		
		Boolean patientAvailabilityStatus = Context.getService(PSIUniquePatientService.class)
		        .findPatientByUicandMobileNo(uic, mobileNo);
		return new ResponseEntity<>(patientAvailabilityStatus.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{uic}/{mobileNo}/{patientUuid}", method = RequestMethod.GET)
	public ResponseEntity<String> findPatientByUuidMobileandUic(@PathVariable String uic, @PathVariable String mobileNo,@PathVariable String patientUuid) throws Exception {
		
		Boolean patientAvailabilityStatus = Context.getService(PSIUniquePatientService.class)
		        .findPatientByUicandMobileNoWhileEdit(uic, mobileNo,patientUuid);
		return new ResponseEntity<>(patientAvailabilityStatus.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/prescriptionMetaData", method = RequestMethod.GET,produces = "application/json; charset=UTF-8")
	public ResponseEntity<String> getMetaData(HttpServletResponse response) throws Exception {
		List<SHnPrescriptionMetaData> sHnPrescriptionMetaDatas = Context.getService(PSIUniquePatientService.class)
		        .getAllPrescriptionMetaData();
		JSONArray metadataJsonArray = new JSONArray();
		for (SHnPrescriptionMetaData sHnPrescriptionMetaData : sHnPrescriptionMetaDatas) {
			//log.error("string" + sHnPrescriptionMetaData.getFieldName());
			metadataJsonArray.put(sHnPrescriptionMetaData.getFieldName());
		}
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");
		//log.error("response" + metadataJsonArray.toString());
		
		 return ResponseEntity.ok()
		            .headers(headers)
		            .body(metadataJsonArray.toString());
	}
	
	@RequestMapping(value = "/dischargeData/{patientUuid}/{visitUuid}", method = RequestMethod.GET)
	public ResponseEntity<String> getDischargeData(@PathVariable String patientUuid, @PathVariable String visitUuid) throws Exception {
		
		List<SHNFormPdfDetails> shnDischargeSummaries = Context.getService(PSIUniquePatientService.class)
		        .getDischargeInformationByVisit(patientUuid, visitUuid);
		JSONObject dischargeJsonObject = new JSONObject();
		for (SHNFormPdfDetails shnDischargeSummary : shnDischargeSummaries) {
			dischargeJsonObject.put(shnDischargeSummary.getQuestion(), shnDischargeSummary.getAnswer());
		}
		return new ResponseEntity<>(dischargeJsonObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/birthData/{patientUuid}/{visitUuid}", method = RequestMethod.GET)
	public ResponseEntity<String> getBirthData(@PathVariable String patientUuid, @PathVariable String visitUuid) throws Exception {
		
		List<SHNFormPdfDetails> shnbirthDetails = Context.getService(PSIUniquePatientService.class)
		        .getbirthInformationByVisit(patientUuid, visitUuid);
		JSONArray birthArray = new JSONArray();
		JSONObject birthJsonObject = new JSONObject();
		int cnt=0;
		for(int i = 0; i<shnbirthDetails.size();i++) {
			cnt++;
			if(cnt%4 == 0) {
				birthJsonObject.put(shnbirthDetails.get(i).getQuestion(), shnbirthDetails.get(i).getAnswer());
				birthArray.put(birthJsonObject);
				birthJsonObject = new JSONObject();
				
			}
			else {
				birthJsonObject.put(shnbirthDetails.get(i).getQuestion(), shnbirthDetails.get(i).getAnswer());
			}
		}
//		for (SHNFormPdfDetails shnbirthDetail : shnbirthDetails) {
//			birthJsonObject.put(shnbirthDetail.getQuestion(), shnbirthDetail.getAnswer());
//		}
//		birthArray.put(birthJsonObject);
		return new ResponseEntity<>(birthArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/lastProviderData/{visitUuid}", method = RequestMethod.GET)
	public ResponseEntity<String> getLastProviderData(@PathVariable String visitUuid) throws Exception {
		
		String lastProviderName = Context.getService(PSIUniquePatientService.class)
		        .getLastProviderName(visitUuid);
		JSONObject providerJsonObject = new JSONObject();
		providerJsonObject.put("isSuccess",true);
		providerJsonObject.put("lastVisitedProvider",lastProviderName);
		return new ResponseEntity<>(providerJsonObject.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/syncpreviousMonthDataToGovtDhis2", method = RequestMethod.GET)
	public ResponseEntity<String> syncPreviousYearData() throws Exception {
		String successMonths = "";
		for (int i = 1; i < 13; i++) {
			boolean status = new HnqisToShnListener().sendPreviousDataTOGovtDhis2(i, 2020);
			if(status) {
				successMonths = successMonths + i + ",";
			}
		}
		
		return new ResponseEntity<>(successMonths.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/syncpreviousHnqisToSHNDhis2", method = RequestMethod.GET)
	public ResponseEntity<String> syncPreviousYearDatatoShn() throws Exception {
		String successMonths = "";
		for (int i = 1; i < 13; i++) {
			boolean status = new HnqisToShnListener().sendPreviousHnqisToDhis2(i, 2020);
			if(status) {
				successMonths = successMonths + i + ",";
			}
		}
		
		return new ResponseEntity<>(successMonths.toString(), HttpStatus.OK);
	}
}
