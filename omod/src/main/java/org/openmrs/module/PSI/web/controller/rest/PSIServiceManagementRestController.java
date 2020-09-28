package org.openmrs.module.PSI.web.controller.rest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.converter.ClinicServiceConverter;
import org.openmrs.module.PSI.converter.PSIServiceManagementConverter;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.dto.PSILocationTag;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@RequestMapping("/rest/v1/service-management")
@RestController
public class PSIServiceManagementRestController extends MainResourceController {
	
	@RequestMapping(value = "/get-by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> findById(@PathVariable int id) throws Exception {
		PSIServiceManagement psiServiceManagement = new PSIServiceManagement();
		
		JSONObject psiServiceManagementJsonOject = new JSONObject();
		try {
			psiServiceManagement = Context.getService(PSIServiceManagementService.class).findById(id);
			if (psiServiceManagement != null) {
				psiServiceManagementJsonOject = new PSIServiceManagementConverter().toConvert(psiServiceManagement);
			}
			
		}
		catch (Exception e) {
			return new ResponseEntity<String>(psiServiceManagementJsonOject.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiServiceManagementJsonOject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-all/{clinicId}/{age}/{gender}", method = RequestMethod.GET)
	public ResponseEntity<String> getAll(@PathVariable int clinicId, @PathVariable int age, @PathVariable String gender)
	    throws Exception {
		List<PSIServiceManagement> psiServiceManagement = new ArrayList<PSIServiceManagement>();
		
		JSONArray psiServiceManagementArrayOject = new JSONArray();
		try {
			psiServiceManagement = Context.getService(PSIServiceManagementService.class).getAllByClinicIdAgeGender(clinicId,
			    age, gender);
			if (psiServiceManagement != null) {
				psiServiceManagementArrayOject = new PSIServiceManagementConverter().toConvert(psiServiceManagement);
			}
		}
		catch (Exception e) {
			
			return new ResponseEntity<String>(psiServiceManagementArrayOject.toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiServiceManagementArrayOject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveClinicService(@RequestBody ClinicServiceDTO clinicServiceDTO, ModelMap model)
	    throws Exception {
		PSIServiceManagement psiServiceManagement = ClinicServiceConverter.toConvert(clinicServiceDTO);
		
		psiServiceManagement.setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(
		    clinicServiceDTO.getPsiClinicManagement()));
		String msg = "";
		PSIServiceManagement getByCode = Context.getService(PSIServiceManagementService.class).findByIdNotByClinicId(
		    clinicServiceDTO.getSid(), clinicServiceDTO.getCode(), clinicServiceDTO.getPsiClinicManagement());
		if (getByCode != null) {
			msg = "This code is already taken";
		} else {
			try {
				Context.openSession();
				Context.getService(PSIServiceManagementService.class).saveOrUpdate(psiServiceManagement);
				Context.clearSession();
				msg = "";
			}
			catch (Exception e) {
				return new ResponseEntity<>(new Gson().toJson(e.toString()), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadClinicService(@RequestParam MultipartFile file, HttpServletRequest request,
	                                                  ModelMap model, @RequestParam(required = false) int id)
	    throws Exception {
		String msg = "";
		String failedMessage = "";
		if (file.isEmpty()) {
			msg = "failed to upload file because its empty";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
			
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			msg = "failed to process file because : " + e.getMessage();
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int index = 0;
		int notUploded = 0;
		try {
			PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String[] service = line.split(cvsSplitBy);
				if (index != 0) {
					PSIServiceManagement psiServiceManagement = Context.getService(PSIServiceManagementService.class)
					        .findByCodeAndClinicId(service[1], id);
					if (psiServiceManagement == null) {
						psiServiceManagement = new PSIServiceManagement();
					}
					String name = "";
					if (!StringUtils.isBlank(service[0])) {
						name = service[0];
					}
					psiServiceManagement.setName(name);
					String code = "";
					if (!StringUtils.isBlank(service[1])) {
						code = service[1];
					}
					psiServiceManagement.setEligible("");
					psiServiceManagement.setCode(code);
					String category = "";
					if (!StringUtils.isBlank(service[2])) {
						category = service[2];
					}
					psiServiceManagement.setCategory(category);
					String provider = "";
					if (!StringUtils.isBlank(service[3])) {
						provider = service[3];
					}
					psiServiceManagement.setProvider(provider);
					Float unitCost = 0f;
					if (service[4] != null || !service[11].isEmpty()) {
						unitCost = Float.parseFloat(service[11]);
					}
					psiServiceManagement.setUnitCost(unitCost);
					psiServiceManagement.setPsiClinicManagement(psiClinicManagement);
					String gender = "";
					if (!StringUtils.isBlank(service[4])) {
						gender = service[4];
					}
					
					int yearTo = 0;
					if (!StringUtils.isBlank(service[5])) {
						yearTo = Integer.parseInt(service[5]);
					}
					int monthTo = 0;
					if (!StringUtils.isBlank(service[6])) {
						monthTo = Integer.parseInt(service[6]);
					}
					int dayTo = 0;
					if (!StringUtils.isBlank(service[7])) {
						dayTo = Integer.parseInt(service[7]);
					}
					int yearFrom = 0;
					if (!StringUtils.isBlank(service[8])) {
						yearFrom = Integer.parseInt(service[8]);
					}
					int monthFrom = 0;
					if (!StringUtils.isBlank(service[9])) {
						monthFrom = Integer.parseInt(service[9]);
					}
					int dayFrom = 0;
					if (!StringUtils.isBlank(service[10])) {
						dayFrom = Integer.parseInt(service[10]);
					}
					
					int ageStart = ClinicServiceConverter.getDaysFromYMD(yearTo, monthTo, dayTo);
					int ageEnd = ClinicServiceConverter.getDaysFromYMD(yearFrom, monthFrom, dayFrom);
					if (ageStart != 0 && ageEnd == 0) {
						ageEnd = 43800;
					}
					psiServiceManagement.setGender(gender);
					
					psiServiceManagement.setYearTo(yearTo);
					psiServiceManagement.setYearFrom(yearFrom);
					
					psiServiceManagement.setMonthFrom(monthFrom);
					psiServiceManagement.setMonthTo(monthTo);
					
					psiServiceManagement.setDaysFrom(dayFrom);
					psiServiceManagement.setDaysTo(dayTo);
					psiServiceManagement.setAgeStart(ageStart);
					psiServiceManagement.setAgeEnd(ageEnd);
					
					psiServiceManagement.setDateCreated(new Date());
					psiServiceManagement.setCreator(Context.getAuthenticatedUser());
					psiServiceManagement.setTimestamp(System.currentTimeMillis());
					psiServiceManagement.setUuid(UUID.randomUUID().toString());
					Context.getService(PSIServiceManagementService.class).saveOrUpdate(psiServiceManagement);
					
				}
				index++;
			}
			msg = "Total successfully service uploaded: " + (index - 1);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg + ", and  got error at column : " + (index + 1) + " due to "
			        + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/location", method = RequestMethod.POST)
	public ResponseEntity<String> uploadClinicLocation(@RequestParam MultipartFile file, HttpServletRequest request)
	    throws Exception {
		String msg = "";
		String failedMessage = "";
		if (file.isEmpty()) {
			msg = "failed to upload file because its empty";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
			
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			msg = "failed to process file because : " + e.getMessage();
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		
		BufferedReader br = null;
		
		String line = "";
		String cvsSplitBy = ",";
		
		int position = 0;
		String[] tags = null;
		try {
			
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String locationTag = "";
				String locationCode = "";
				String locationName = "";
				String parentLocationName = "";
				String parentLocationCode = "";
				String parentLocationtag = "";
				String[] locations = line.split(cvsSplitBy);
				if (position == 0) {
					tags = locations;
				} else {
					for (int i = 0; i < locations.length; i = i + 2) {
						locationCode = locations[i].trim();
						locationName = (locations[i + 1].trim());
						
						if (i != 0) {
							parentLocationName = (locations[i - 1]);
							parentLocationCode = locations[i - 2];
							parentLocationtag = tags[i - 1].trim();
						}
						locationTag = tags[i + 1].trim();
						
						PSILocationTag psiParentLocationTag = Context.getService(PSIClinicManagementService.class)
						        .findLocationTagByName(parentLocationtag);
						int parentLocationTagId = 0;
						if (psiParentLocationTag != null) {
							parentLocationTagId = psiParentLocationTag.getId();
						}
						PSILocation psiParentLocation = Context.getService(PSIClinicManagementService.class)
						        .findLocationByNameCodeLocationTag(parentLocationName, parentLocationCode,
						            parentLocationTagId);
						
						PSILocationTag psiLocationTag = Context.getService(PSIClinicManagementService.class)
						        .findLocationTagByName(locationTag);
						int psiLocationTagId = 0;
						if (psiLocationTag != null) {
							psiLocationTagId = psiLocationTag.getId();
						}
						PSILocation psiLocation = new PSILocation();
						if (i == 6) {
							psiLocation = Context.getService(PSIClinicManagementService.class)
							        .findLocationByNameCodeLocationTagParent(locationName, locationCode, psiLocationTagId,
							            psiParentLocation.getId());
							
						} else {
							psiLocation = Context.getService(PSIClinicManagementService.class)
							        .findLocationByNameCodeLocationTag(locationName, locationCode, psiLocationTagId);
						}
						if (psiLocation == null) {
							
							LocationTag mainTag = new LocationTag();
							mainTag.setLocationTagId(psiLocationTag.getId());
							mainTag.setName(psiLocationTag.getName());
							mainTag.setUuid(psiLocationTag.getUuid());
							Set<LocationTag> tagList = new HashSet<LocationTag>();
							tagList.add(mainTag);
							
							PSILocationTag psiTagLoginLocation = Context.getService(PSIClinicManagementService.class)
							        .findLocationTagByName("Login Location");
							LocationTag tagLoginLocation = new LocationTag();
							tagLoginLocation.setLocationTagId(psiTagLoginLocation.getId());
							tagLoginLocation.setName(psiTagLoginLocation.getName());
							tagLoginLocation.setUuid(psiTagLoginLocation.getUuid());
							
							tagList.add(tagLoginLocation);
							
							PSILocationTag psiTagVisitLocation = Context.getService(PSIClinicManagementService.class)
							        .findLocationTagByName("Visit Location");
							LocationTag tagVisitLocation = new LocationTag();
							tagVisitLocation.setLocationTagId(psiTagVisitLocation.getId());
							tagVisitLocation.setName(psiTagVisitLocation.getName());
							tagVisitLocation.setUuid(psiTagVisitLocation.getUuid());
							
							tagList.add(tagVisitLocation);
							Location location = new Location();
							location.setTags(tagList);
							
							Location parentLocation = new Location();
							if (psiParentLocation != null) {
								parentLocation.setLocationId(psiParentLocation.getId());
							} else {
								parentLocation = null;
							}
							
							PSILocation lastLocation = Context.getService(PSIClinicManagementService.class)
							        .findLastLocation();
							if (lastLocation != null) {
								int id = lastLocation.getId() + 1;
								location.setLocationId(id);
							} else {
								location.setLocationId(1);
							}
							location.setName(locationName);
							location.setUuid(UUID.randomUUID().toString());
							location.setAddress2(locationCode);
							location.setParentLocation(parentLocation);
							Context.getService(PSIClinicManagementService.class).save(location);
							
						} else {
							
						}
						//Context.getService(LocationService.class).saveLocation(location);
						
					}
				}
				position++;
			}
			msg = "Total successfully service uploaded: " + (position - 1);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg + ", and  got error at column : " + (position + 1)
			        + " due to " + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/product-upload", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProduct(@RequestParam MultipartFile file, HttpServletRequest request,
	                                                  ModelMap model, @RequestParam(required = false) int id)
	    throws Exception {
		String msg = "";
		String failedMessage = "";
		if (file.isEmpty()) {
			msg = "failed to upload file because its empty";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
			
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			msg = "failed to process file because : " + e.getMessage();
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int index = 0;
		int notUploded = 0;
		try {
			PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String[] service = line.split(cvsSplitBy);
				if (index != 0) {
					PSIServiceManagement psiServiceManagement = Context.getService(PSIServiceManagementService.class)
					        .findByCodeAndClinicId(service[1], id);
					if (psiServiceManagement == null) {
						psiServiceManagement = new PSIServiceManagement();
					}
					String name = "";
					if (!StringUtils.isBlank(service[0])) {
						name = service[0];
					}
					psiServiceManagement.setName(name);
					String code = "";
					if (!StringUtils.isBlank(service[1])) {
						code = service[1];
					}
					psiServiceManagement.setEligible("");
					psiServiceManagement.setCode(code);
					
					String brandName = "";
					if (!StringUtils.isBlank(service[2])) {
						brandName = service[2];
					}
					psiServiceManagement.setBrandName(brandName);
					String category = "";
					if (!StringUtils.isBlank(service[3])) {
						category = service[3];
					}
					psiServiceManagement.setCategory(category);


					Float unitCost = 0f;
					if (service[4] != null || !service[4].isEmpty()) {
						unitCost = Float.parseFloat(service[4]);
					}
					psiServiceManagement.setUnitCost(unitCost);
					
					Float purchasePrice = 0f;
					if (service[5] != null || !service[5].isEmpty()) {
						purchasePrice = Float.parseFloat(service[5]);
					}
					psiServiceManagement.setPurchasePrice(purchasePrice);
					
					psiServiceManagement.setPsiClinicManagement(psiClinicManagement);
					
					String provider = "";
					psiServiceManagement.setProvider(provider);
					psiServiceManagement.setType("PRODUCT");
					
					String gender = "";
					
					int yearTo = 0;

					int monthTo = 0;

					int dayTo = 0;

					int yearFrom = 0;

					int monthFrom = 0;

					int dayFrom = 0;
					
					int ageStart = ClinicServiceConverter.getDaysFromYMD(yearTo, monthTo, dayTo);
					int ageEnd = ClinicServiceConverter.getDaysFromYMD(yearFrom, monthFrom, dayFrom);
					if (ageStart != 0 && ageEnd == 0) {
						ageEnd = 43800;
					}
					psiServiceManagement.setGender(gender);
					
					psiServiceManagement.setYearTo(yearTo);
					psiServiceManagement.setYearFrom(yearFrom);
					
					psiServiceManagement.setMonthFrom(monthFrom);
					psiServiceManagement.setMonthTo(monthTo);
					
					psiServiceManagement.setDaysFrom(dayFrom);
					psiServiceManagement.setDaysTo(dayTo);
					psiServiceManagement.setAgeStart(ageStart);
					psiServiceManagement.setAgeEnd(ageEnd);
					
					psiServiceManagement.setDateCreated(new Date());
					psiServiceManagement.setCreator(Context.getAuthenticatedUser());
					psiServiceManagement.setTimestamp(System.currentTimeMillis());
					psiServiceManagement.setUuid(UUID.randomUUID().toString());
					Context.getService(PSIServiceManagementService.class).saveOrUpdate(psiServiceManagement);
					
				}
				index++;
			}
			msg = "Total successfully service uploaded: " + (index - 1);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg + ", and  got error at column : " + (index + 1) + " due to "
			        + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
}
