package org.openmrs.module.PSI.web.controller.rest;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.api.SHNFollowUpActionService;
import org.openmrs.module.PSI.dto.SHNFollowUpActionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/followup")
@RestController
public class SHNFollowUpActionRestController {
	
	@RequestMapping(value = "/save-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> followUpSaving(@RequestBody SHNFollowUpActionDTO dto) throws Exception {
		
		JSONObject response = new JSONObject();
		try {

			SHNFollowUpAction shnFollowUpAction  = Context.getService(SHNFollowUpActionService.class).findByCodedConceptAndEncounter(dto.getValueCoded(), dto.getEncounterUuid());
			if (shnFollowUpAction == null) {
				shnFollowUpAction = new SHNFollowUpAction();
				shnFollowUpAction.setUuid(UUID.randomUUID().toString());
				shnFollowUpAction.setDateCreated(new Date());
				shnFollowUpAction.setFollowUpCounts(1);
				shnFollowUpAction.setValueChanged(false);
			}
			else {
				shnFollowUpAction.setValueChanged(true);
				shnFollowUpAction.setFollowUpCounts(shnFollowUpAction.getFollowUpCounts() + 1);
			}
			shnFollowUpAction.setEncounterUuid(dto.getEncounterUuid());
			shnFollowUpAction.setVisitUuid(dto.getVisitUuid());
			shnFollowUpAction.setValueCoded(dto.getValueCoded());
			shnFollowUpAction.setCodedConceptName(dto.getCodedConceptName());
			shnFollowUpAction.setContactDate(dto.getContactDate());
			shnFollowUpAction.setIsResponded(dto.getIsResponded());
			shnFollowUpAction.setRespondResult(dto.getRespondResult());
			shnFollowUpAction.setCreator(Context.getAuthenticatedUser());
			shnFollowUpAction.setDateChanged(new Date());
			SHNFollowUpAction shnFollowUpActionResult =  Context.getService(SHNFollowUpActionService.class).saveOrUpdate(shnFollowUpAction);
			response.put("message", "Follow-Up Action Successfully Saved");
			response.put("isSuccess", true);
			response.put("followupId", shnFollowUpActionResult.getFollowUpActionId());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			response.put("isSuccess", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/save-update-in-global", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> followUpSavingInGlobal(@RequestBody SHNFollowUpActionDTO dto) throws Exception {
		
		JSONObject response = new JSONObject();
		try {

			SHNFollowUpAction shnFollowUpAction  = Context.getService(SHNFollowUpActionService.class).findByUuid(dto.getUuid());
			if (shnFollowUpAction == null) {
				shnFollowUpAction = new SHNFollowUpAction();
				shnFollowUpAction.setDateCreated(new Date());
				shnFollowUpAction.setUuid(dto.getUuid());
			}
			shnFollowUpAction.setEncounterUuid(dto.getEncounterUuid());
			shnFollowUpAction.setVisitUuid(dto.getVisitUuid());
			shnFollowUpAction.setValueCoded(dto.getValueCoded());
			shnFollowUpAction.setCodedConceptName(dto.getCodedConceptName());
			shnFollowUpAction.setContactDate(dto.getContactDate());
			shnFollowUpAction.setIsResponded(dto.getIsResponded());
			shnFollowUpAction.setRespondResult(dto.getRespondResult());
			shnFollowUpAction.setCreator(Context.getAuthenticatedUser());
			shnFollowUpAction.setValueChanged(dto.getValueChanged());
			shnFollowUpAction.setDateChanged(dto.getDateChanged());
			shnFollowUpAction.setFollowUpCounts(dto.getFollowUpCounts());

			SHNFollowUpAction shnFollowUpActionResult =  Context.getService(SHNFollowUpActionService.class).saveOrUpdate(shnFollowUpAction);
			response.put("message", "Follow-Up Action Successfully Saved");
			response.put("followupId", shnFollowUpActionResult.getFollowUpActionId());
			
		}
		catch (Exception e) {
			e.printStackTrace();
			
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		
	}
}
