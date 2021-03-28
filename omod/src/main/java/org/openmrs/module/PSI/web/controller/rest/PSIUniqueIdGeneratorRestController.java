package org.openmrs.module.PSI.web.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.SHNEslipNoGenerate;
import org.openmrs.module.PSI.api.PSIUniqueIdGeneratorService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/generate/uniqueid")
@RestController
public class PSIUniqueIdGeneratorRestController extends MainResourceController {
	
	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> saveClinic(@PathVariable String code) throws Exception {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		
		PSIUniqueIdGenerator psiUGenerator = new PSIUniqueIdGenerator();
		synchronized(this) {
			PSIUniqueIdGenerator pisPsiUniqueIdGenerator = Context.getService(PSIUniqueIdGeneratorService.class)
			        .findByClinicCodeAndDate(today, code);
			psiUGenerator.setClinicCode(code);
			psiUGenerator.setGenerateId(0);
			
			psiUGenerator.setDateCreated(new Date());
			if (pisPsiUniqueIdGenerator.getGenerateId() == 0) {
				psiUGenerator.setGenerateId(0 + 1);
			} else {
				psiUGenerator.setGenerateId(pisPsiUniqueIdGenerator.getGenerateId() + 1);
			}
			Context.openSession();
			Context.getService(PSIUniqueIdGeneratorService.class).saveOrUpdate(psiUGenerator);
			Context.clearSession();
		}
		String serquenceNumber = "";
		String serquenceNumberToString = psiUGenerator.getGenerateId() + "";
		if (serquenceNumberToString.length() == 1) {
			serquenceNumber += "000" + serquenceNumberToString;
		} else if (serquenceNumberToString.length() == 2) {
			serquenceNumber += "00" + serquenceNumberToString;
		} else if (serquenceNumberToString.length() == 3) {
			serquenceNumber += "0" + serquenceNumberToString;
		} else {
			serquenceNumber = serquenceNumberToString;
		}
		JSONObject sequence = new JSONObject();
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		String dayS = day >= 10 ? "" + day : "0" + day;
		String monthS = month >= 10 ? "" + month : "0" + month;
		String healtId = "" + year + monthS + dayS + code.substring(0, 3) + serquenceNumber;
		sequence.put("sequenceId", healtId);
		return new ResponseEntity<>(sequence.toString(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/eslip/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> generateEslip(@PathVariable String code) throws Exception {
		
		Date date = Calendar.getInstance().getTime();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = dateFormat.format(date);
		
		SHNEslipNoGenerate shnEslipNoGenerate = new SHNEslipNoGenerate();
		SHNEslipNoGenerate afterSaveSlip = null;
		synchronized(this) {
			SHNEslipNoGenerate getLastSlipNoByclinic = Context.getService(PSIUniqueIdGeneratorService.class)
			        .findEslipByClinicCodeAndDate(today, code);
			
			shnEslipNoGenerate.setClinicCode(code);
			shnEslipNoGenerate.setGenerateId(0);
			
			shnEslipNoGenerate.setDateCreated(new Date());
			if (getLastSlipNoByclinic.getGenerateId() == 0) {
				shnEslipNoGenerate.setGenerateId(0 + 1);
			} else {
				shnEslipNoGenerate.setGenerateId(getLastSlipNoByclinic.getGenerateId() + 1);
			}
			Context.openSession();
			afterSaveSlip = Context.getService(PSIUniqueIdGeneratorService.class).saveOrUpdate(shnEslipNoGenerate);
			Context.clearSession();
		}
		String serquenceNumber = "";
		String serquenceNumberToString = afterSaveSlip.getGenerateId() + "";
		if (serquenceNumberToString.length() == 1) {
			serquenceNumber += "000" + serquenceNumberToString;
		} else if (serquenceNumberToString.length() == 2) {
			serquenceNumber += "00" + serquenceNumberToString;
		} else if (serquenceNumberToString.length() == 3) {
			serquenceNumber += "0" + serquenceNumberToString;
		} else {
			serquenceNumber = serquenceNumberToString;
		}
		JSONObject sequence = new JSONObject();
		
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DATE);
		int month = cal.get(Calendar.MONTH) + 1;
		String dayS = day >= 10 ? "" + day : "0" + day;
		String monthS = month >= 10 ? "" + month : "0" + month;
		DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
		String year = df.format(Calendar.getInstance().getTime());
		String eslipId = "" + year + monthS + dayS + code.substring(0, 3)+ "-" + serquenceNumber;
		sequence.put("eslipNo", eslipId);
		return new ResponseEntity<>(sequence.toString(), HttpStatus.OK);
		
	}
}
