package org.openmrs.module.PSI.web.controller.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.api.SHNPackageService;
import org.openmrs.module.PSI.dto.SHNPackageDTO;
import org.openmrs.module.PSI.dto.SHNPackageDetailsDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/v1/package")
@RestController
public class SHNPackageRestController {
	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/save-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> savePackage(@RequestBody SHNPackageDTO dto) throws Exception {
		
		JSONObject response = new JSONObject();
		log.error("DTO" + dto);
		try {
			
			SHNPackage checkShnPackage = Context.getService(SHNPackageService.class).findbyPackageCode(dto.getPackageCode(), dto.getClinicCode(), dto.getPackageId());
			if(checkShnPackage != null) {
				response.put("message", "Package Code Already Exist");
				response.put("isSuccess", false);
			}
			
			else {
				Set<SHNPackageDetailsDTO> packageDetailsDTOs = dto.getShnPackageDetails();
				SHNPackage shnPackage = Context.getService(SHNPackageService.class).findById(dto.getPackageId());
				if (shnPackage == null) {
					
					shnPackage = new SHNPackage();
					shnPackage.setUuid(UUID.randomUUID().toString());
					shnPackage.setDateCreated(new Date());
				}
				else {
					shnPackage.setChangedBy(Context.getAuthenticatedUser());
					shnPackage.setDateChanged(new Date());
	
				}
				
				shnPackage.setVoided(dto.getVoided());
				shnPackage.setClinicName(dto.getClinicName());
				shnPackage.setClinicCode(dto.getClinicCode());
				shnPackage.setPackageName(dto.getPackageName());
				shnPackage.setPackageCode(dto.getPackageCode());
				shnPackage.setAccumulatedPrice(dto.getAccumulatedPrice());
				shnPackage.setPackagePrice(dto.getPackagePrice());
				shnPackage.setCreator(Context.getAuthenticatedUser());
					
				log.error("Package Object Creating Seuccess " + dto.getPackageCode());
				
				Set<SHNPackageDetails> shnPackageDetailsList = new HashSet<SHNPackageDetails>();
				for (SHNPackageDetailsDTO shnPackageDetailsDTO : packageDetailsDTOs) {
					SHNPackageDetails shnPackageDetails = Context.getService(SHNPackageService.class).finPackageDetailsById(shnPackageDetailsDTO.getPackageDetailsId());
	
					if(shnPackageDetails ==null) {
						shnPackageDetails = new SHNPackageDetails();
						shnPackageDetails.setDateCreated(new Date());
					}
					else {
						shnPackageDetails.setChangedBy(Context.getAuthenticatedUser());
						shnPackageDetails.setDateChanged(new Date());
						
					}
					shnPackageDetails.setPackageItemName(shnPackageDetailsDTO.getPackageItemName());
					shnPackageDetails.setPackageItemCode(shnPackageDetailsDTO.getPackageItemCode());
					shnPackageDetails.setShnPackage(shnPackage);
					shnPackageDetails.setPackageItemUnitPrice(shnPackageDetailsDTO.getPackageItemUnitPrice());
					shnPackageDetails.setQuantity(shnPackageDetailsDTO.getQuantity());
					shnPackageDetails.setPackageItemPriceInPackage(shnPackageDetailsDTO.getPackageItemPriceInPackage());
					shnPackageDetails.setCreator(Context.getAuthenticatedUser());
					shnPackageDetails.setDateCreated(new Date());
					shnPackageDetailsList.add(shnPackageDetails);
				}
	
				shnPackage.setShnPackageDetails(shnPackageDetailsList);
				
				SHNPackage responsePackage =  Context.getService(SHNPackageService.class).saveOrUpdate(shnPackage);
				response.put("message", "Package Successfully Saved");
				response.put("isSuccess", true);
				response.put("packageId", responsePackage.getPackageId());
				
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
