package org.openmrs.module.PSI.web.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
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
}
