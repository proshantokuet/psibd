package org.openmrs.module.PSI.web.controller.rest;

import java.util.Date;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/exception-sync")
@RestController
public class SHNExceptionDataSyncRestController {

	
	@RequestMapping(value = "/save-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> syncExceptionData(@RequestBody PSIDHISException dto) throws Exception {
		
		JSONObject response = new JSONObject();

		try {
			
			if(dto !=null) {
				
				PSIDHISException psidhisException = new PSIDHISException();
				psidhisException.setJson(dto.getJson());
				psidhisException.setError(dto.getError());
				psidhisException.setResponse(dto.getResponse());
				psidhisException.setTimestamp(0);
				psidhisException.setMarkId(dto.getMarkId());
				psidhisException.setDateCreated(new Date());
				psidhisException.setUrl(dto.getUrl());
				psidhisException.setStatus(dto.getStatus());
				psidhisException.setPatientUuid(dto.getPatientUuid());
				psidhisException.setReferenceId(dto.getReferenceId());
				psidhisException.setIsSync(1);
				Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
				response.put("message", "Data Saved Successfully");
				response.put("isSuccess", true);				
			}
			
		} catch (Exception e) {
			response.put("message", e.getMessage().toString());
			response.put("isSuccess", false);	
		}
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
	
	}
}
