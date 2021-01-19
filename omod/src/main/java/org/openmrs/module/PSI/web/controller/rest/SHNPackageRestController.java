package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.api.SHNPackageService;
import org.openmrs.module.PSI.converter.SHNPackageConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.SHNPackageDTO;
import org.openmrs.module.PSI.dto.SHNPackageDetailsDTO;
import org.openmrs.module.PSI.dto.SHNPackageReportDTO;
import org.openmrs.module.PSI.utils.DateTimeTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RequestMapping("/rest/v1/package")
@RestController
public class SHNPackageRestController {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	private final String CLINIC_ENDPOINT = "/rest/v1/clinic/byClinicCode";
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/save-update", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseEntity<String> savePackage(@RequestBody SHNPackageDTO dto) throws Exception {
		
		JSONObject response = new JSONObject();
		log.error("DTO" + dto);
		try {
			
			SHNPackage checkShnPackage = Context.getService(SHNPackageService.class).findbyPackageCode(dto.getPackageCode(), dto.getClinicId(), dto.getPackageId());
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
				shnPackage.setClinicId(dto.getClinicId());
				shnPackage.setClinicCode(dto.getClinicCode());
				shnPackage.setPackageName(dto.getPackageName());
				shnPackage.setPackageCode(dto.getPackageCode());
				shnPackage.setPackagePrice(dto.getPackagePrice());
				shnPackage.setCreator(Context.getAuthenticatedUser());
					
				log.error("Package Object Creating Success " + dto.getPackageCode());
				
				Set<SHNPackageDetails> shnPackageDetailsList = new HashSet<SHNPackageDetails>();
				for (SHNPackageDetailsDTO shnPackageDetailsDTO : packageDetailsDTOs) {
					SHNPackageDetails shnPackageDetails = Context.getService(SHNPackageService.class).findPackageDetailsById(shnPackageDetailsDTO.getPackageDetailsId());
	
					if(shnPackageDetails == null) {
						shnPackageDetails = new SHNPackageDetails();
						shnPackageDetails.setDateCreated(new Date());
						shnPackageDetails.setUuid(UUID.randomUUID().toString());
					}
					else {
						shnPackageDetails.setChangedBy(Context.getAuthenticatedUser());
						shnPackageDetails.setDateChanged(new Date());
						
					}
					shnPackageDetails.setServiceProductId(shnPackageDetailsDTO.getServiceProductId());
					shnPackageDetails.setShnPackage(shnPackage);
					shnPackageDetails.setQuantity(shnPackageDetailsDTO.getQuantity());
					shnPackageDetails.setPackageItemPriceInPackage(shnPackageDetailsDTO.getPackageItemPriceInPackage());
					shnPackageDetails.setUnitPriceInPackage(shnPackageDetailsDTO.getUnitPriceInPackage());
					shnPackageDetails.setCreator(Context.getAuthenticatedUser());
					shnPackageDetails.setDateCreated(new Date());
					shnPackageDetailsList.add(shnPackageDetails);
				}
	
				shnPackage.setShnPackageDetails(shnPackageDetailsList);
				
				SHNPackage responsePackage =  Context.getService(SHNPackageService.class).saveOrUpdate(shnPackage);
				if(dto.getPackageId() != 0) {
					Context.getService(SHNPackageService.class).deletePackageHavingNullPackageId();
				}
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
	
	@RequestMapping(value = "/getStockStatusFromPackage/{clinicId}/{quantity}/{packageId}", method = RequestMethod.GET)
	public ResponseEntity<String> findUserForSync(@PathVariable int clinicId,@PathVariable int quantity,@PathVariable int packageId) throws Exception {
		JSONObject packageJsonObject = new  JSONObject();
		String stockOutProducts = "";
		boolean flag = false;
		try {
			List<SHNPackageReportDTO> shnPackageDto = Context.getService(SHNPackageService.class).getstockStatusFromPackage(clinicId, quantity, packageId);
			if(shnPackageDto.size() > 0) {
				for (SHNPackageReportDTO shnPackageReportDTO : shnPackageDto) {
					if(shnPackageReportDTO.getIsStockExceed() == 0) {
						stockOutProducts = stockOutProducts + shnPackageReportDTO.getItemName() +"("+shnPackageReportDTO.getItemCode() + ")"+",";
						flag = true;
					}
				}
				if (stockOutProducts.endsWith(",")) {
					stockOutProducts = stockOutProducts.substring(0, stockOutProducts.length() - 1);
					} 
				packageJsonObject.put("exceedStock", flag);
				packageJsonObject.put("stockOutProducts", stockOutProducts);
			}
		}
		catch (Exception e) {
			packageJsonObject.put("exceedStock", true);
			packageJsonObject.put("message", e.getMessage());
			return new ResponseEntity<String>(packageJsonObject.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(packageJsonObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getItemsFromPackage/{packageId}", method = RequestMethod.GET)
	public ResponseEntity<String> getItemsForPackage(@PathVariable int packageId) throws Exception {
		JSONArray shnPackageArray = new JSONArray();
		try {
			List<SHNPackageReportDTO> shnPackageDto = Context.getService(SHNPackageService.class).getPackageByPackageIdForEdit(packageId);
			if(shnPackageDto.size() > 0) {
				for (SHNPackageReportDTO shnPackageReportDTO : shnPackageDto) {
					JSONObject packageJsonObject = new JSONObject();
					packageJsonObject.put("itemCode", shnPackageReportDTO.getItemCode());
					packageJsonObject.put("itemName", shnPackageReportDTO.getItemName());
					packageJsonObject.put("itemId", shnPackageReportDTO.getItemId());
					shnPackageArray.put(packageJsonObject);
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(shnPackageArray.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(shnPackageArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getPackageForSync/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findUserForSync(@PathVariable int id) throws Exception {
		JSONArray shnPackageArray = new JSONArray();
		try {
			List<SHNPackage> shnPackages = Context.getService(SHNPackageService.class).getAllPackageByClinicIdWithVoided(id);
			if(shnPackages != null) {
				shnPackageArray = new SHNPackageConverter().toConvert(shnPackages);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>("", HttpStatus.OK);
		}
		return new ResponseEntity<String>(shnPackageArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/package-sync/{clinicId}/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> syncPackage(@PathVariable int clinicId,@PathVariable String code) throws Exception {
		JSONObject clinicJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRS("", "",
			    CLINIC_ENDPOINT + "/" + code);
		if(clinicJson.length() < 1) {
			return new ResponseEntity<String>("No Clinic Found For This Clinic ID in Global Server", HttpStatus.OK);
		}
		else {
			JSONArray packages = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "","/rest/v1/package/getPackageForSync" + "/" + clinicId);
			
			List<SHNPackageDTO> shnPackageDTOs = gson.fromJson(packages.toString(),
			    new TypeToken<ArrayList<SHNPackageDTO>>() {}.getType());
			
			try {
				
				for (SHNPackageDTO shnPackageDTO : shnPackageDTOs) {

						Set<SHNPackageDetailsDTO> packageDetailsDTOs = shnPackageDTO.getShnPackageDetails();
						SHNPackage shnPackage = Context.getService(SHNPackageService.class).findPackageByUuid(shnPackageDTO.getUuid());
						if (shnPackage == null) {
							
							shnPackage = new SHNPackage();
							shnPackage.setUuid(shnPackageDTO.getUuid());
							shnPackage.setDateCreated(new Date());
						}
						else {
							shnPackage.setChangedBy(Context.getAuthenticatedUser());
							shnPackage.setDateChanged(new Date());
			
						}
						
						shnPackage.setVoided(shnPackageDTO.getVoided());
						shnPackage.setClinicName(shnPackageDTO.getClinicName());
						shnPackage.setClinicId(shnPackageDTO.getClinicId());
						shnPackage.setClinicCode(shnPackageDTO.getClinicCode());
						shnPackage.setPackageName(shnPackageDTO.getPackageName());
						shnPackage.setPackageCode(shnPackageDTO.getPackageCode());
						shnPackage.setPackagePrice(shnPackageDTO.getPackagePrice());
						shnPackage.setCreator(Context.getAuthenticatedUser());
							
						log.error("Package Object Creating Seuccess " + shnPackageDTO.getUuid());
						
						Set<SHNPackageDetails> shnPackageDetailsList = new HashSet<SHNPackageDetails>();
						for (SHNPackageDetailsDTO shnPackageDetailsDTO : packageDetailsDTOs) {
							SHNPackageDetails shnPackageDetails = Context.getService(SHNPackageService.class).findPackageDetailsByUuid(shnPackageDetailsDTO.getUuid());
			
							if(shnPackageDetails ==null) {
								shnPackageDetails = new SHNPackageDetails();
								shnPackageDetails.setDateCreated(new Date());
								shnPackageDetails.setUuid(shnPackageDetailsDTO.getUuid());
							}
							else {
								shnPackageDetails.setChangedBy(Context.getAuthenticatedUser());
								shnPackageDetails.setDateChanged(new Date());
								
							}
							shnPackageDetails.setServiceProductId(shnPackageDetailsDTO.getServiceProductId());
							shnPackageDetails.setShnPackage(shnPackage);
							shnPackageDetails.setQuantity(shnPackageDetailsDTO.getQuantity());
							shnPackageDetails.setPackageItemPriceInPackage(shnPackageDetailsDTO.getPackageItemPriceInPackage());
							shnPackageDetails.setUnitPriceInPackage(shnPackageDetailsDTO.getUnitPriceInPackage());
							shnPackageDetails.setCreator(Context.getAuthenticatedUser());
							shnPackageDetails.setDateCreated(new Date());
							shnPackageDetailsList.add(shnPackageDetails);
						}
			
						shnPackage.setShnPackageDetails(shnPackageDetailsList);
						
						Context.getService(SHNPackageService.class).saveOrUpdate(shnPackage);
						Context.getService(SHNPackageService.class).deletePackageHavingNullPackageId();
				}
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
			}
			return new ResponseEntity<String>("Sync Success", HttpStatus.OK);
		}
	}
	
}
