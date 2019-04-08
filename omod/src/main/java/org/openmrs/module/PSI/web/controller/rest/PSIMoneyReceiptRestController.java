package org.openmrs.module.PSI.web.controller.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/money-receipt/")
@RestController
public class PSIMoneyReceiptRestController extends MainResourceController {
	
	@RequestMapping(value = "/add", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> addMoneyReceipt(@RequestBody String jsonStr) throws Exception {
		
		JSONObject requestBody = new JSONObject(jsonStr);
		JSONObject moneyReceipt = requestBody.getJSONObject("moneyReceipt");
		
		PSIMoneyReceipt psiMoneyReceipt = new PSIMoneyReceipt();
		psiMoneyReceipt.setDateCreated(new Date());
		psiMoneyReceipt.setAddress(requestBody.toString());
		psiMoneyReceipt.setCreator(Context.getAuthenticatedUser());
		psiMoneyReceipt.setUuid(UUID.randomUUID().toString());
		psiMoneyReceipt.setDob(new Date());
		psiMoneyReceipt.setPatientName(moneyReceipt.getString("patientName"));
		PSIServiceProvision psiServiceProvision = new PSIServiceProvision();
		psiServiceProvision.setDateCreated(new Date());
		psiServiceProvision.setCreator(Context.getAuthenticatedUser());
		psiServiceProvision.setUuid(UUID.randomUUID().toString());
		Set<PSIServiceProvision> services = new HashSet<PSIServiceProvision>();
		services.add(psiServiceProvision);
		
		psiMoneyReceipt.setServices(services);
		Context.getService(PSIMoneyReceiptService.class).saveOrUpdate(psiMoneyReceipt);
		return new ResponseEntity<String>("OKK", HttpStatus.OK);
		
	}
	
}
