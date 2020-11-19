package org.openmrs.module.PSI.web.controller.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.api.SHNFollowUpActionService;
import org.openmrs.module.PSI.converter.PSIMoneyReceiptConverter;
import org.openmrs.module.PSI.converter.SHNFollowUpActionListConverter;
import org.openmrs.module.PSI.dto.SHNFollowUpActionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
			shnFollowUpAction.setLastTimeStamp(new Date());
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
	
	
	@RequestMapping(value = "/get/followUpList", method = RequestMethod.GET)
	public ResponseEntity<String> getAllFollowUp() throws Exception {
		JSONArray shnfollowUpJsonArray = new JSONArray();
		try {
			List<SHNFollowUpAction> shnFolowUpList = Context.getService(SHNFollowUpActionService.class).getAllFollowUPAction();
			if(shnFolowUpList.size() > 0) {
				shnfollowUpJsonArray = new SHNFollowUpActionListConverter().toConvert(shnFolowUpList);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(shnfollowUpJsonArray.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/save-update-in-global", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> followUpSavingInGlobal(@RequestBody SHNFollowUpActionDTO dto) throws Exception {
		DateFormat dateFormatTwentyFourHour = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		JSONObject response = new JSONObject();
		try {

			SHNFollowUpAction shnFollowUpAction  = Context.getService(SHNFollowUpActionService.class).findByUuidAndEncounter(dto.getUuid(), dto.getEncounterUuid());
			if(shnFollowUpAction == null) {
				
				shnFollowUpAction = new SHNFollowUpAction();
				shnFollowUpAction.setDateCreated(new Date());
				shnFollowUpAction.setUuid(dto.getUuid());
				shnFollowUpAction.setEncounterUuid(dto.getEncounterUuid());
				shnFollowUpAction.setVisitUuid(dto.getVisitUuid());
				shnFollowUpAction.setValueCoded(dto.getValueCoded());
				shnFollowUpAction.setCodedConceptName(dto.getCodedConceptName());
				shnFollowUpAction.setContactDate(dateFormatTwentyFourHour.parse(dto.getContactDateWithTimeStamp()));
				shnFollowUpAction.setIsResponded(dto.getIsResponded());
				shnFollowUpAction.setRespondResult(dto.getRespondResult());
				shnFollowUpAction.setCreator(Context.getAuthenticatedUser());
				shnFollowUpAction.setValueChanged(dto.getValueChanged());
				shnFollowUpAction.setLastTimeStamp(dateFormatTwentyFourHour.parse(dto.getDateChangedWithTimestamp()));
				shnFollowUpAction.setFollowUpCounts(dto.getFollowUpCounts());
	
				SHNFollowUpAction shnFollowUpActionResult =  Context.getService(SHNFollowUpActionService.class).saveOrUpdate(shnFollowUpAction);
				response.put("message", "Follow-Up Action Successfully Saved");
				response.put("isSuccess", true);
				response.put("followupUuid", shnFollowUpActionResult.getUuid());
			}
			
			else if(shnFollowUpAction !=null && shnFollowUpAction.getFollowUpCounts() == dto.getFollowUpCounts()) {
				response.put("message", "Follow Up Already UpDated");
				response.put("isSuccess", true);
				response.put("followupUuid", shnFollowUpAction.getUuid());
			}
			
			else if(shnFollowUpAction !=null && shnFollowUpAction.getFollowUpCounts() != dto.getFollowUpCounts()) {
				shnFollowUpAction.setEncounterUuid(dto.getEncounterUuid());
				shnFollowUpAction.setVisitUuid(dto.getVisitUuid());
				shnFollowUpAction.setValueCoded(dto.getValueCoded());
				shnFollowUpAction.setCodedConceptName(dto.getCodedConceptName());
				shnFollowUpAction.setContactDate(dateFormatTwentyFourHour.parse(dto.getContactDateWithTimeStamp()));
				shnFollowUpAction.setIsResponded(dto.getIsResponded());
				shnFollowUpAction.setRespondResult(dto.getRespondResult());
				shnFollowUpAction.setCreator(Context.getAuthenticatedUser());
				shnFollowUpAction.setValueChanged(dto.getValueChanged());
				shnFollowUpAction.setLastTimeStamp(dateFormatTwentyFourHour.parse(dto.getDateChangedWithTimestamp()));
				shnFollowUpAction.setFollowUpCounts(dto.getFollowUpCounts());

				SHNFollowUpAction shnFollowUpActionResult =  Context.getService(SHNFollowUpActionService.class).saveOrUpdate(shnFollowUpAction);
				response.put("message", "Follow-Up Action Successfully Saved");
				response.put("isSuccess", true);
				response.put("followupUuid", shnFollowUpActionResult.getUuid());
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
			response.put("isSuccess", false);
			response.put("message", e.getMessage());
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(response.toString(), HttpStatus.OK);
		
	}
}
