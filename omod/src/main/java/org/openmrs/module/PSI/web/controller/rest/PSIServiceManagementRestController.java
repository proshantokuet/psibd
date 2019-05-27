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
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.converter.ClinicServiceConverter;
import org.openmrs.module.PSI.converter.PSIServiceManagementConverter;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
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
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<String>(psiServiceManagementJsonOject.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/get-all/{clinicId}", method = RequestMethod.GET)
	public ResponseEntity<String> getAll(@PathVariable int clinicId) throws Exception {
		List<PSIServiceManagement> psiServiceManagement = new ArrayList<PSIServiceManagement>();
		
		JSONArray psiServiceManagementArrayOject = new JSONArray();
		try {
			psiServiceManagement = Context.getService(PSIServiceManagementService.class).getAll(clinicId);
			if (psiServiceManagement != null) {
				psiServiceManagementArrayOject = new PSIServiceManagementConverter().toConvert(psiServiceManagement);
			}
		}
		catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
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
		PSIServiceManagement getByCode = Context.getService(PSIServiceManagementService.class).findByCode(
		    clinicServiceDTO.getCode(), clinicServiceDTO.getPsiClinicManagement());
		if (getByCode != null) {
			msg = "This code is already taken";
		} else {
			Context.openSession();
			Context.getService(PSIServiceManagementService.class).saveOrUpdate(psiServiceManagement);
			Context.clearSession();
			msg = "";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<String> editClinicService(@RequestBody ClinicServiceDTO clinicServiceDTO, ModelMap model)
	    throws Exception {
		
		String msg = "";
		PSIServiceManagement getByIdAndNotByCode = Context.getService(PSIServiceManagementService.class).findByIdNotByCode(
		    clinicServiceDTO.getSid(), clinicServiceDTO.getCode(), clinicServiceDTO.getPsiClinicManagement());
		if (getByIdAndNotByCode == null) {
			PSIServiceManagement getById = Context.getService(PSIServiceManagementService.class).findById(
			    clinicServiceDTO.getSid());
			getById.setName(clinicServiceDTO.getName());
			getById.setCategory(clinicServiceDTO.getCategory());
			getById.setCode(clinicServiceDTO.getCode());
			getById.setProvider(clinicServiceDTO.getProvider());
			getById.setUnitCost(clinicServiceDTO.getUnitCost());
			getById.setPsiClinicManagement(Context.getService(PSIClinicManagementService.class).findById(
			    clinicServiceDTO.getPsiClinicManagement()));
			getById.setTimestamp(System.currentTimeMillis());
			Context.openSession();
			Context.getService(PSIServiceManagementService.class).saveOrUpdate(getById);
			Context.clearSession();
			msg = "";
		} else {
			msg = "This Code is already taken";
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
					PSIServiceManagement findByCodeAndClinicId = Context.getService(PSIServiceManagementService.class)
					        .findByCode(service[1], id);
					if (findByCodeAndClinicId == null) {
						PSIServiceManagement pSIServiceManagement = new PSIServiceManagement();
						pSIServiceManagement.setName(service[0]);
						pSIServiceManagement.setEligible("");
						pSIServiceManagement.setCode(service[1]);
						pSIServiceManagement.setCategory(service[2]);
						pSIServiceManagement.setProvider(service[3]);
						pSIServiceManagement.setUnitCost(Float.parseFloat(service[4]));
						pSIServiceManagement.setPsiClinicManagement(psiClinicManagement);
						pSIServiceManagement.setDateCreated(new Date());
						pSIServiceManagement.setCreator(Context.getAuthenticatedUser());
						psiClinicManagement.setTimestamp(System.currentTimeMillis());
						pSIServiceManagement.setUuid(UUID.randomUUID().toString());
						Context.getService(PSIServiceManagementService.class).saveOrUpdate(pSIServiceManagement);
					} else {
						notUploded++;
					}
				}
				index++;
			}
			msg = "Total successfully service uploaded: " + (index - notUploded - 1);
			
		}
		catch (Exception e) {
			
			failedMessage = "failed to process file because : " + e.getCause();
			return new ResponseEntity<>(new Gson().toJson(msg + " and got error at position: " + index + 1 + " due to "
			        + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
}
