package org.openmrs.module.PSI.web.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
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
import org.springframework.web.bind.annotation.RestController;

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
}
