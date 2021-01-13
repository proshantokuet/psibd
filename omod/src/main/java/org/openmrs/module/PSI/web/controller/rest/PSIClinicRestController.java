package org.openmrs.module.PSI.web.controller.rest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.AUHCClinicTypeService;
import org.openmrs.module.PSI.api.AUHCServiceCategoryService;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIClinicSpotService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.converter.ClinicDataConverter;
import org.openmrs.module.PSI.converter.PSIClinicSpotConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.ClinicDTO;
import org.openmrs.module.PSI.dto.PSIClinicSpotDTO;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.utils.DateTimeTypeConverter;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

@RequestMapping("/rest/v1/clinic/")
@RestController
public class PSIClinicRestController extends MainResourceController {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
	        .registerTypeAdapter(DateTime.class, new DateTimeTypeConverter()).create();
	
	private final String CLINIC_ENDPOINT = "/rest/v1/clinic/byClinicCode";
	
	private final String CLINIC_TYPE_ENDPOINT = "/rest/v1/clinic/type";
	
	private final String SERVICE_CATEGORY_ENDPOINT = "/rest/v1/clinic/servive/category";
	
	private final String SERVICE_ENDPOINT = "/rest/v1/clinic/service/byClinicId";
	
	private final String SPOT_ENDPOINT = "/rest/v1/clinic//spot/byClinicId";
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveClinic(@RequestBody ClinicDTO clinicDTO, ModelMap model) throws Exception {
		PSILocation getDivison = Context.getService(PSIClinicManagementService.class).findLocationById(
		    clinicDTO.getDivision());
		PSILocation getDistrict = Context.getService(PSIClinicManagementService.class).findLocationById(
		    clinicDTO.getDistrict());
		
		PSILocation getUpazila = Context.getService(PSIClinicManagementService.class).findLocationById(
		    clinicDTO.getUpazila());
		
		PSIClinicManagement psiClinicManagement = new PSIClinicManagement();
		
		psiClinicManagement.setAddress(clinicDTO.getAddress());
		psiClinicManagement.setCategory(clinicDTO.getCategory());
		psiClinicManagement.setClinicId(clinicDTO.getClinicId());
		psiClinicManagement.setName(clinicDTO.getName());
		psiClinicManagement.setDhisId(clinicDTO.getDhisId());
		psiClinicManagement.setDateCreated(new Date());
		psiClinicManagement.setCreator(Context.getAuthenticatedUser());
		psiClinicManagement.setUuid(UUID.randomUUID().toString());
		psiClinicManagement.setTimestamp(System.currentTimeMillis());
		psiClinicManagement.setDivision(getDivison.getName());
		psiClinicManagement.setDivisionUuid(getDivison.getUuid());
		psiClinicManagement.setDivisionId(getDivison.getId());
		
		psiClinicManagement.setDistrict(getDistrict.getName());
		psiClinicManagement.setDistrictUuid(getDistrict.getUuid());
		psiClinicManagement.setDistrictId(getDistrict.getId());
		
		psiClinicManagement.setUpazila(getUpazila.getName());
		psiClinicManagement.setUpazilaUuid(getUpazila.getUuid());
		psiClinicManagement.setUpazilaId(getUpazila.getId());
		if (clinicDTO.getCid() != 0) {
			psiClinicManagement.setCid(clinicDTO.getCid());
		} else {
			psiClinicManagement.setCid(0);
		}
		String msg = "";
		PSIClinicManagement getClinicByClinicId = Context.getService(PSIClinicManagementService.class)
		        .findByIdNotByClinicId(clinicDTO.getCid(), psiClinicManagement.getClinicId());
		if (getClinicByClinicId != null) {
			msg = "This clinic id is already taken";
		} else {
			Context.openSession();
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(psiClinicManagement);
			Context.clearSession();
			msg = "";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<String> editClinic(@RequestBody ClinicDTO clinicDTO, ModelMap model) throws Exception {
		
		String msg = "";
		PSIClinicManagement getByIdAndClinicID = Context.getService(PSIClinicManagementService.class).findByIdNotByClinicId(
		    clinicDTO.getCid(), clinicDTO.getClinicId());
		if (getByIdAndClinicID == null) {
			PSIClinicManagement findById = Context.getService(PSIClinicManagementService.class).findById(clinicDTO.getCid());
			findById.setName(clinicDTO.getName());
			findById.setCategory(clinicDTO.getCategory());
			findById.setClinicId(clinicDTO.getClinicId());
			findById.setAddress(clinicDTO.getAddress());
			findById.setDhisId(clinicDTO.getDhisId());
			Context.openSession();
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(findById);
			Context.clearSession();
			msg = "";
		} else {
			msg = "This clinic id is already taken";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/spot/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveClinicSpot(@RequestBody PSIClinicSpotDTO psiClinicSpotDTO, ModelMap model)
	    throws Exception {
		
		String msg = "";
		PSIClinicSpot psiClinicSpot = null;
		if (psiClinicSpotDTO.getCsid() != 0) {
			int ccsid = 0;
			ccsid = psiClinicSpotDTO.getCsid();
			psiClinicSpot = Context.getService(PSIClinicSpotService.class).findById(ccsid);
		} else {
			psiClinicSpot = new PSIClinicSpot();
			psiClinicSpot.setUuid(UUID.randomUUID().toString());
			psiClinicSpot.setDateCreated(new Date());
		}
		
		PSIClinicManagement psiClinicManagementId = Context.getService(PSIClinicManagementService.class).findById(
		    psiClinicSpotDTO.getPsiClinicManagementId());
		
		PSIClinicSpot getClinicByClinicId = Context.getService(PSIClinicSpotService.class).findDuplicateSpot(0,
		    psiClinicSpotDTO.getCode(), psiClinicSpotDTO.getPsiClinicManagementId());
		
		if (getClinicByClinicId != null && psiClinicSpotDTO.getCsid() != getClinicByClinicId.getCcsid()) {
			msg = "This clinic Spot code is already taken";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		} else {
			try {
				psiClinicSpot.setCreator(Context.getAuthenticatedUser());
				psiClinicSpot.setName(psiClinicSpotDTO.getName());
				psiClinicSpot.setCode(psiClinicSpotDTO.getCode());
				psiClinicSpot.setAddress(psiClinicSpotDTO.getAddress());
				psiClinicSpot.setCcsid(psiClinicSpotDTO.getCsid());
				psiClinicSpot.setDhisId(psiClinicSpotDTO.getDhisId());
				psiClinicSpot.setPsiClinicManagement(psiClinicManagementId);
				Context.openSession();
				Context.getService(PSIClinicSpotService.class).saveOrUpdate(psiClinicSpot);
				Context.clearSession();
				msg = "";
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				return new ResponseEntity<>(new Gson().toJson(e.getMessage()), HttpStatus.OK);
			}
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		// return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/spot/get-all-spotlist-by-id/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getAllChildByPatient(@PathVariable int id) throws Exception {
		List<PSIClinicSpot> psiClinicSpots = new ArrayList<PSIClinicSpot>();
		JSONArray psiClinicSpotObject = new JSONArray();
		try {
			psiClinicSpots = Context.getService(PSIClinicSpotService.class).findByClinicId(id);
			if (psiClinicSpots != null) {
				psiClinicSpotObject = new PSIClinicSpotConverter().toConvert(psiClinicSpots);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiClinicSpotObject.toString(), HttpStatus.OK);
	}
	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/spot/upload", method = RequestMethod.POST)
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
		try {
			PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(id);
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String[] spot = line.split(cvsSplitBy);
				if (index != 0) {
					PSIClinicSpot psiClinicSpot = new PSIClinicSpot();
					String name = "";
					if (!StringUtils.isBlank(spot[0])) {
						name = spot[0];
					}
					psiClinicSpot.setName(name);
					String code = "";
					if (!StringUtils.isBlank(spot[1])) {
						code = spot[1];
					}
					psiClinicSpot.setCode(code);
					String address = "";
					if (!StringUtils.isBlank(spot[2])) {
						address = spot[2];
					}
					psiClinicSpot.setAddress(address);
					psiClinicSpot.setPsiClinicManagement(psiClinicManagement);
					String orgId = "";
					if (!StringUtils.isBlank(spot[3])) {
						orgId = spot[3];
					}
					psiClinicSpot.setDhisId(orgId);
					
					psiClinicSpot.setDateCreated(new Date());
					psiClinicSpot.setCreator(Context.getAuthenticatedUser());
					psiClinicSpot.setUuid(UUID.randomUUID().toString());
					Context.getService(PSIClinicSpotService.class).saveOrUpdate(psiClinicSpot);
					
				}
				index++;
			}
			msg = "Total successfully Spot uploaded: " + (index - 1);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg + ", and  got error at column : " + (index + 1) + " due to "
			        + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/byClinicCode/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> getClinicByClinicId(@PathVariable String code) throws Exception {
		PSIClinicManagement psiClinic = new PSIClinicManagement();
		
		JSONObject clinicObject = new JSONObject();
		try {
			psiClinic = Context.getService(PSIClinicManagementService.class).findByClinicId(code);
			if (psiClinic != null) {
				clinicObject = new ClinicDataConverter().toConvert(psiClinic);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(clinicObject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/type", method = RequestMethod.GET)
	public ResponseEntity<String> getClinicType() throws Exception {
		List<AUHCClinicType> psiClinicTypes = new ArrayList<AUHCClinicType>();
		JSONArray psiClinicTypesArray = new JSONArray();
		try {
			psiClinicTypes = Context.getService(AUHCClinicTypeService.class).getAll();
			if (psiClinicTypes.size() != 0) {
				for (AUHCClinicType auhcClinicType : psiClinicTypes) {
					JSONObject psiClinicTypesObject = new JSONObject();
					psiClinicTypesObject.put("ctid", auhcClinicType.getCtid());
					psiClinicTypesObject.put("clinicTypeName", auhcClinicType.getClinicTypeName());
					psiClinicTypesObject.putOpt("uuid", auhcClinicType.getUuid());
					psiClinicTypesObject.putOpt("dateCreated", auhcClinicType.getDateCreated());
					psiClinicTypesObject.putOpt("dateChanged", auhcClinicType.getDateChanged());
					
					psiClinicTypesArray.put(psiClinicTypesObject);
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(psiClinicTypesArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/servive/category", method = RequestMethod.GET)
	public ResponseEntity<String> getClinicServiceCategory() throws Exception {
		List<AUHCServiceCategory> clinicServiceCategories = new ArrayList<AUHCServiceCategory>();
		JSONArray clinicServiceCategoriesArray = new JSONArray();
		try {
			clinicServiceCategories = Context.getService(AUHCServiceCategoryService.class).getAll();
			
			if (clinicServiceCategories.size() != 0) {
				for (AUHCServiceCategory auhcServiceCategory : clinicServiceCategories) {
					JSONObject clinicServiceCategoriesObject = new JSONObject();
					clinicServiceCategoriesObject.put("sctid", auhcServiceCategory.getSctid());
					clinicServiceCategoriesObject.put("categoryName", auhcServiceCategory.getCategoryName());
					clinicServiceCategoriesObject.putOpt("uuid", auhcServiceCategory.getUuid());
					clinicServiceCategoriesObject.putOpt("dateCreated", auhcServiceCategory.getDateCreated());
					clinicServiceCategoriesObject.putOpt("dateChanged", auhcServiceCategory.getDateChanged());
					clinicServiceCategoriesArray.put(clinicServiceCategoriesObject);
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(clinicServiceCategoriesArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/spot/byClinicId/{clinicId}", method = RequestMethod.GET)
	public ResponseEntity<String> getSpotByClinicId(@PathVariable int clinicId) throws Exception {
		List<PSIClinicSpot> clinicSpots = new ArrayList<PSIClinicSpot>();
		JSONArray clinicSpotsArray = new JSONArray();
		try {
			clinicSpots = Context.getService(PSIClinicSpotService.class).findByClinicId(clinicId);
			clinicSpots.sort(Comparator.comparing(PSIClinicSpot::getCcsid));

			if (clinicSpots.size() != 0) {
				for (PSIClinicSpot psiClinicSpot : clinicSpots) {
					JSONObject clinicSpotsObject = new JSONObject();
					clinicSpotsObject.put("ccsid", psiClinicSpot.getCcsid());
					clinicSpotsObject.put("name", psiClinicSpot.getName());
					
					clinicSpotsObject.put("code", psiClinicSpot.getCode());
					clinicSpotsObject.put("address", psiClinicSpot.getAddress());
					clinicSpotsObject.put("dhisId", psiClinicSpot.getDhisId());
					
					clinicSpotsObject.putOpt("uuid", psiClinicSpot.getUuid());
					clinicSpotsObject.putOpt("dateCreated", psiClinicSpot.getDateCreated());
					clinicSpotsObject.putOpt("dateChanged", psiClinicSpot.getDateChanged());
					clinicSpotsArray.put(clinicSpotsObject);
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(clinicSpotsArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/service/byClinicId/{clinicId}", method = RequestMethod.GET)
	public ResponseEntity<String> getServiceByClinicId(@PathVariable int clinicId) throws Exception {
		List<PSIServiceManagement> clinicServices = new ArrayList<PSIServiceManagement>();
		JSONArray clinicServicesArray = new JSONArray();
		try {
			clinicServices = Context.getService(PSIServiceManagementService.class).getAllByClinicId(clinicId);
			if (clinicServices.size() != 0) {
				clinicServices.sort(Comparator.comparing(PSIServiceManagement::getSid));
				for (PSIServiceManagement psiServiceManagement : clinicServices) {
					JSONObject clinicServicesObject = new JSONObject();
					clinicServicesObject.put("sid", psiServiceManagement.getSid());
					clinicServicesObject.put("name", psiServiceManagement.getName());
					
					clinicServicesObject.put("code", psiServiceManagement.getCode());
					clinicServicesObject.put("category", psiServiceManagement.getCategory());
					clinicServicesObject.put("provider", psiServiceManagement.getProvider());
					clinicServicesObject.put("unitCost", psiServiceManagement.getUnitCost());
					
					clinicServicesObject.put("timestamp", psiServiceManagement.getTimestamp());
					clinicServicesObject.put("field1", psiServiceManagement.getField1());
					clinicServicesObject.put("field2", psiServiceManagement.getField2());
					clinicServicesObject.put("field3", psiServiceManagement.getField3());
					
					clinicServicesObject.put("eligible", psiServiceManagement.getEligible());
					clinicServicesObject.put("ageStart", psiServiceManagement.getAgeStart());
					clinicServicesObject.put("ageEnd", psiServiceManagement.getAgeEnd());
					clinicServicesObject.put("yearTo", psiServiceManagement.getYearTo());
					
					clinicServicesObject.put("monthTo", psiServiceManagement.getMonthTo());
					clinicServicesObject.put("daysTo", psiServiceManagement.getDaysTo());
					clinicServicesObject.put("yearFrom", psiServiceManagement.getYearFrom());
					clinicServicesObject.put("monthFrom", psiServiceManagement.getMonthFrom());
					
					clinicServicesObject.put("daysFrom", psiServiceManagement.getDaysFrom());
					clinicServicesObject.put("gender", psiServiceManagement.getGender());
					clinicServicesObject.put("type", psiServiceManagement.getType());
					clinicServicesObject.put("purchasePrice", psiServiceManagement.getPurchasePrice());
					clinicServicesObject.put("discountPop", psiServiceManagement.getDiscountPop());
					clinicServicesObject.put("discountPoor", psiServiceManagement.getDiscountPoor());
					clinicServicesObject.put("discountAblePay", psiServiceManagement.getDiscountAblePay());

					clinicServicesObject.putOpt("uuid", psiServiceManagement.getUuid());
					clinicServicesObject.putOpt("dateCreated", psiServiceManagement.getDateCreated());
					clinicServicesObject.putOpt("dateChanged", psiServiceManagement.getDateChanged());
					clinicServicesObject.putOpt("voided", psiServiceManagement.getVoided());
					clinicServicesArray.put(clinicServicesObject);
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(clinicServicesArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/sync/byClinicCode/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> syncClinic(@PathVariable String code) throws Exception {
		
		JSONObject clinicJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRS("", "",
		    CLINIC_ENDPOINT + "/" + code);
		if(clinicJson.length() < 1) {
			return new ResponseEntity<String>("No Clinic Found For This Clinic ID in Global Server", HttpStatus.OK);
		}
		else {
		//String _clinicsStr = "{\"dateChanged\":\"2019-08-28 14:03:04.0\",\"clinicId\":\"177\",\"address\":\"Bashabo SH Clinic, 43,Maddyha Bashabo, Dhaka-1214\",\"dhisId\":\"yzZ2cGq8cj9\",\"districtUuid\":\"89ceb342-3578-40a0-afe8-220aa00cd986\",\"upazila\":\"DHAKA SOUTH CITY CORPORATION\",\"uuid\":\"6faffd63-6d0e-4bcb-b234-5fe0ce5a3e9f\",\"division\":\"Dhaka\",\"districtId\":85,\"dateCreated\":\"2019-08-28 14:03:04.0\",\"upazilaId\":86,\"district\":\"DHAKA\",\"name\":\"Bashabo\",\"upazilaUuid\":\"4405a4f9-92a5-44b6-86a1-109a1b49efef\",\"divisionId\":16,\"divisionUuid\":\"6bbee4e4-daeb-4f3f-bb35-0be72f982a33\",\"category\":\"Vital\",\"cid\":27,\"timestamp\":1566979384733}";
			PSIClinicManagement clinic = gson.fromJson(clinicJson.toString(), new TypeToken<PSIClinicManagement>() {}.getType());
			//clinic.setCid(5);
			try {
				PSIClinicManagement psiClinicManagement = Context.getService(PSIClinicManagementService.class).findByClinicId(
				    code);
				Context.getService(PSIClinicManagementService.class).updateClinicPrimaryKey(psiClinicManagement.getCid(),
				    clinic.getCid());
				psiClinicManagement = Context.getService(PSIClinicManagementService.class).findById(clinic.getCid());
				psiClinicManagement.setAddress(clinic.getAddress());
				psiClinicManagement.setCategory(clinic.getCategory());
				psiClinicManagement.setClinicId(clinic.getClinicId());
				psiClinicManagement.setName(clinic.getName());
				psiClinicManagement.setDhisId(clinic.getDhisId());
				psiClinicManagement.setDateCreated(clinic.getDateCreated());
				psiClinicManagement.setDateChanged(clinic.getDateChanged());
				psiClinicManagement.setCreator(Context.getAuthenticatedUser());
				psiClinicManagement.setUuid(clinic.getUuid());
				psiClinicManagement.setTimestamp(clinic.getTimestamp());
				psiClinicManagement.setDivision(clinic.getDivision());
				psiClinicManagement.setDivisionUuid(clinic.getDivisionUuid());
				psiClinicManagement.setDivisionId(clinic.getDivisionId());
				
				psiClinicManagement.setDistrict(clinic.getDistrict());
				psiClinicManagement.setDistrictUuid(clinic.getDistrictUuid());
				psiClinicManagement.setDistrictId(clinic.getDistrictId());
				
				psiClinicManagement.setUpazila(clinic.getUpazila());
				psiClinicManagement.setUpazilaUuid(clinic.getUpazilaUuid());
				psiClinicManagement.setUpazilaId(clinic.getUpazilaId());
				// if this not work then need extra method for update primary key cid
				psiClinicManagement.setCid(clinic.getCid());
				
				Context.openSession();
				Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(psiClinicManagement);
				Context.clearSession();
				
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
			}
			
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/type/sync", method = RequestMethod.GET)
	public ResponseEntity<String> syncClinicType() throws Exception {
		
		JSONArray clinicTypeJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
		    CLINIC_TYPE_ENDPOINT);
		
		List<AUHCClinicType> auhcClinicTypes = gson.fromJson(clinicTypeJson.toString(),
		    new TypeToken<ArrayList<AUHCClinicType>>() {}.getType());
		try {
			for (AUHCClinicType auhcClinicType : auhcClinicTypes) {
				int currentId = auhcClinicType.getCtid();
				auhcClinicType.setCreator(Context.getAuthenticatedUser());
				AUHCClinicType getType = Context.getService(AUHCClinicTypeService.class)
				        .findByCtId(auhcClinicType.getCtid());
				if (getType != null) {
					Context.getService(AUHCClinicTypeService.class).saveOrUpdate(
					    convertAUHCClinicType(getType, auhcClinicType));
				} else {
					auhcClinicType.setCtid(0);
					AUHCClinicType created = Context.getService(AUHCClinicTypeService.class).saveOrUpdate(auhcClinicType);
					Context.getService(AUHCClinicTypeService.class).updatePrimaryKey(created.getCtid(), currentId);
					
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/serviceCategory/sync", method = RequestMethod.GET)
	public ResponseEntity<String> syncServiceCategory() throws Exception {
		
		JSONArray servicecategories = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
		    SERVICE_CATEGORY_ENDPOINT);
		
		List<AUHCServiceCategory> auhcServiceCategories = gson.fromJson(servicecategories.toString(),
		    new TypeToken<ArrayList<AUHCServiceCategory>>() {}.getType());
		try {
			for (AUHCServiceCategory auhcServiceCategory : auhcServiceCategories) {
				
				int currentId = auhcServiceCategory.getSctid();
				auhcServiceCategory.setCreator(Context.getAuthenticatedUser());
				AUHCServiceCategory getCategory = Context.getService(AUHCServiceCategoryService.class).findBySctId(
				    auhcServiceCategory.getSctid());
				if (getCategory != null) {
					Context.getService(AUHCServiceCategoryService.class).saveOrUpdate(
					    convertAUHCServiceCategory(getCategory, auhcServiceCategory));
					
				} else {
					auhcServiceCategory.setSctid(0);
					AUHCServiceCategory created = Context.getService(AUHCServiceCategoryService.class).saveOrUpdate(
					    auhcServiceCategory);
					Context.getService(AUHCServiceCategoryService.class).updatePrimaryKey(created.getSctid(), currentId);
					
				}
				
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/service/sync/{clinicId}/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> syncService(@PathVariable int clinicId,@PathVariable String code) throws Exception {
		
		JSONObject clinicJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRS("", "",
			    CLINIC_ENDPOINT + "/" + code);
		if(clinicJson.length() < 1) {
			return new ResponseEntity<String>("No Clinic Found For This Clinic ID in Global Server", HttpStatus.OK);
		}
		else {		
			JSONArray services = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
			    SERVICE_ENDPOINT + "/" + clinicId);
			
			List<PSIServiceManagement> psiServiceManagements = gson.fromJson(services.toString(),
			    new TypeToken<ArrayList<PSIServiceManagement>>() {}.getType());
			
			try {
				
				for (PSIServiceManagement psiServiceManagement : psiServiceManagements) {
					int currentId = psiServiceManagement.getSid();
					PSIServiceManagement getPsiServiceManagement = Context.getService(PSIServiceManagementService.class)
					        .findById(currentId);
					psiServiceManagement.setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(
					    clinicId));
					psiServiceManagement.setCreator(Context.getAuthenticatedUser());
					
					if (getPsiServiceManagement != null) {
						Context.getService(PSIServiceManagementService.class).saveOrUpdate(
						    convertPSIServiceManagement(getPsiServiceManagement, psiServiceManagement, clinicId));
					} else {
						PSIServiceManagement lastServiceInfo = Context.getService(PSIServiceManagementService.class).findByClinicIdDescending();
						if(lastServiceInfo != null) {
							Context.getService(PSIServiceManagementService.class).updateTableAutoIncrementValue(lastServiceInfo.getSid() + 1);
						}
						psiServiceManagement.setSid(0);
						PSIServiceManagement created = Context.getService(PSIServiceManagementService.class).saveOrUpdate(
						    psiServiceManagement);
						Context.getService(PSIServiceManagementService.class).updatePrimaryKey(created.getSid(), currentId);
					}
				}				
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
			}
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
	}
	
	@RequestMapping(value = "/spot/sync/{clinicId}/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> syncSpot(@PathVariable int clinicId,@PathVariable String code) throws Exception {
		JSONObject clinicJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRS("", "",
			    CLINIC_ENDPOINT + "/" + code);
		if(clinicJson.length() < 1) {
			return new ResponseEntity<String>("No Clinic Found For This Clinic ID in Global Server", HttpStatus.OK);
		}
		else {
			JSONArray spots = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
			    SPOT_ENDPOINT + "/" + clinicId);
			
			List<PSIClinicSpot> psiClinicSpots = gson.fromJson(spots.toString(),
			    new TypeToken<ArrayList<PSIClinicSpot>>() {}.getType());
			
			try {
				
				for (PSIClinicSpot psiClinicSpot : psiClinicSpots) {
					psiClinicSpot.setCreator(Context.getAuthenticatedUser());
					psiClinicSpot
					        .setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(clinicId));
					int currentId = psiClinicSpot.getCcsid();
					PSIClinicSpot getPsiClinicSpot = Context.getService(PSIClinicSpotService.class).findById(currentId);
					if (getPsiClinicSpot != null) {
						Context.getService(PSIClinicSpotService.class).saveOrUpdate(
						    convertPSIClinicSpot(getPsiClinicSpot, psiClinicSpot, clinicId));
					} else {
						psiClinicSpot.setCcsid(0);
						PSIClinicSpot created = Context.getService(PSIClinicSpotService.class).saveOrUpdate(psiClinicSpot);
						Context.getService(PSIClinicSpotService.class).updatePrimaryKey(created.getCcsid(), currentId);
					}
					
				}
				
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
			}
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
	}
	
	private AUHCServiceCategory convertAUHCServiceCategory(AUHCServiceCategory response, AUHCServiceCategory get) {
		response.setCategoryName(get.getCategoryName());
		response.setDateCreated(get.getDateCreated());
		response.setDateChanged(get.getDateChanged());
		response.setCreator(get.getCreator());
		response.setUuid(get.getUuid());
		return response;
		
	}
	
	private PSIServiceManagement convertPSIServiceManagement(PSIServiceManagement response, PSIServiceManagement get,
	                                                         int clinicId) {
		
		response.setName(get.getName());
		
		response.setCode(get.getCode());
		response.setCategory(get.getCategory());
		
		response.setProvider(get.getProvider());
		
		response.setUnitCost(get.getUnitCost());
		
		response.setTimestamp(get.getTimestamp());
		
		response.setField1(get.getField1());
		
		response.setField2(get.getField2());
		
		response.setField3(get.getField3());
		
		response.setEligible(get.getEligible());
		
		response.setAgeStart(get.getAgeStart());
		
		response.setAgeEnd(get.getAgeEnd());
		
		response.setYearTo(get.getYearTo());
		
		response.setMonthTo(get.getMonthTo());
		
		response.setDaysTo(get.getDaysTo());
		
		response.setYearFrom(get.getYearFrom());
		
		response.setMonthFrom(get.getMonthFrom());
		
		response.setDaysFrom(get.getDaysFrom());
		response.setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(clinicId));
		response.setGender(get.getGender());
		response.setDateCreated(get.getDateCreated());
		response.setDateChanged(get.getDateChanged());
		response.setCreator(get.getCreator());
		response.setUuid(get.getUuid());
		response.setVoided(get.getVoided());
		return response;
	}
	
	private PSIClinicSpot convertPSIClinicSpot(PSIClinicSpot response, PSIClinicSpot get, int clinicId) {
		
		response.setName(get.getName());
		response.setCode(get.getCode());
		response.setAddress(get.getAddress());
		response.setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(clinicId));
		response.setDhisId(get.getDhisId());
		response.setDateCreated(get.getDateCreated());
		response.setDateChanged(get.getDateChanged());
		response.setCreator(get.getCreator());
		response.setUuid(get.getUuid());
		return response;
	}
	
	private AUHCClinicType convertAUHCClinicType(AUHCClinicType response, AUHCClinicType get) {
		response.setClinicTypeName(get.getClinicTypeName());
		response.setDateCreated(get.getDateCreated());
		response.setDateChanged(get.getDateChanged());
		response.setCreator(get.getCreator());
		response.setUuid(get.getUuid());
		return response;
	}
	
	@RequestMapping(value = "/product/byClinicId/{clinicId}", method = RequestMethod.GET)
	public ResponseEntity<String> getProductByClinicId(@PathVariable int clinicId) throws Exception {
		List<PSIServiceManagement> clinicServices = new ArrayList<PSIServiceManagement>();
		JSONArray clinicServicesArray = new JSONArray();
		try {
			clinicServices = Context.getService(PSIServiceManagementService.class).getAllServiceByClinicIdAndType(clinicId, "PRODUCT");
			if (clinicServices.size() != 0) {
				clinicServices.sort(Comparator.comparing(PSIServiceManagement::getSid));
				for (PSIServiceManagement psiServiceManagement : clinicServices) {
					JSONObject clinicServicesObject = new JSONObject();
					clinicServicesObject.put("sid", psiServiceManagement.getSid());
					clinicServicesObject.put("name", psiServiceManagement.getName());
					
					clinicServicesObject.put("code", psiServiceManagement.getCode());
					clinicServicesObject.put("category", psiServiceManagement.getCategory());
					clinicServicesObject.put("provider", psiServiceManagement.getProvider());
					clinicServicesObject.put("unitCost", psiServiceManagement.getUnitCost());
					
					clinicServicesObject.put("timestamp", psiServiceManagement.getTimestamp());
					clinicServicesObject.put("field1", psiServiceManagement.getField1());
					clinicServicesObject.put("field2", psiServiceManagement.getField2());
					clinicServicesObject.put("field3", psiServiceManagement.getField3());
					
					clinicServicesObject.put("eligible", psiServiceManagement.getEligible());
					clinicServicesObject.put("ageStart", psiServiceManagement.getAgeStart());
					clinicServicesObject.put("ageEnd", psiServiceManagement.getAgeEnd());
					clinicServicesObject.put("yearTo", psiServiceManagement.getYearTo());
					
					clinicServicesObject.put("monthTo", psiServiceManagement.getMonthTo());
					clinicServicesObject.put("daysTo", psiServiceManagement.getDaysTo());
					clinicServicesObject.put("yearFrom", psiServiceManagement.getYearFrom());
					clinicServicesObject.put("monthFrom", psiServiceManagement.getMonthFrom());
					
					clinicServicesObject.put("daysFrom", psiServiceManagement.getDaysFrom());
					clinicServicesObject.put("gender", psiServiceManagement.getGender());
					clinicServicesObject.put("type", psiServiceManagement.getType());
					clinicServicesObject.put("brandName", psiServiceManagement.getBrandName());
					clinicServicesObject.put("purchasePrice", psiServiceManagement.getPurchasePrice());
					clinicServicesObject.put("discountPop", psiServiceManagement.getDiscountPop());
					clinicServicesObject.put("discountPoor", psiServiceManagement.getDiscountPoor());
					clinicServicesObject.put("discountAblePay", psiServiceManagement.getDiscountAblePay());

					clinicServicesObject.putOpt("uuid", psiServiceManagement.getUuid());
					clinicServicesObject.putOpt("dateCreated", psiServiceManagement.getDateCreated());
					clinicServicesObject.putOpt("dateChanged", psiServiceManagement.getDateChanged());
					clinicServicesObject.putOpt("dateChanged", psiServiceManagement.getDateChanged());
					clinicServicesObject.putOpt("voided", psiServiceManagement.getVoided());
					clinicServicesArray.put(clinicServicesObject);
				}
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
		}
		return new ResponseEntity<String>(clinicServicesArray.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/product/sync/{clinicId}/{code}", method = RequestMethod.GET)
	public ResponseEntity<String> syncProduct(@PathVariable int clinicId,@PathVariable String code) throws Exception {
		
		JSONObject clinicJson = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRS("", "",
			    CLINIC_ENDPOINT + "/" + code);
		if(clinicJson.length() < 1) {
			return new ResponseEntity<String>("No Clinic Found For This Clinic ID in Global Server", HttpStatus.OK);
		}
		else {		
			JSONArray services = psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRSAsArray("", "",
					"/rest/v1/clinic/product/byClinicId" + "/" + clinicId);
			
			List<PSIServiceManagement> psiServiceManagements = gson.fromJson(services.toString(),
			    new TypeToken<ArrayList<PSIServiceManagement>>() {}.getType());
			
			try {
				
				for (PSIServiceManagement psiServiceManagement : psiServiceManagements) {
					int currentId = psiServiceManagement.getSid();
					PSIServiceManagement getPsiServiceManagement = Context.getService(PSIServiceManagementService.class)
					        .findById(currentId);
					psiServiceManagement.setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(
					    clinicId));
					psiServiceManagement.setCreator(Context.getAuthenticatedUser());
					
					if (getPsiServiceManagement != null) {
						Context.getService(PSIServiceManagementService.class).saveOrUpdate(
						    convertPSIServiceManagement(getPsiServiceManagement, psiServiceManagement, clinicId));
					} else {
						PSIServiceManagement lastServiceInfo = Context.getService(PSIServiceManagementService.class).findByClinicIdDescending();
						if(lastServiceInfo != null) {
							Context.getService(PSIServiceManagementService.class).updateTableAutoIncrementValue(lastServiceInfo.getSid() + 1);
						}
						psiServiceManagement.setSid(0);
						PSIServiceManagement created = Context.getService(PSIServiceManagementService.class).saveOrUpdate(
						    psiServiceManagement);
						Context.getService(PSIServiceManagementService.class).updatePrimaryKey(created.getSid(), currentId);
					}
				}				
			}
			catch (Exception e) {
				return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.OK);
			}
			return new ResponseEntity<String>("Success", HttpStatus.OK);
		}
	}
}
