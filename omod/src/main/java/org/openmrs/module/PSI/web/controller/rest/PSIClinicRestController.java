package org.openmrs.module.PSI.web.controller.rest;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.converter.ClinicDataConverter;
import org.openmrs.module.PSI.dto.ClinicDTO;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RequestMapping("/rest/v1/clinic/")
@RestController
public class PSIClinicRestController extends MainResourceController {
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public ResponseEntity<String> saveClinic(@RequestBody ClinicDTO clinicDTO, ModelMap model) throws Exception {
		PSIClinicManagement psiClinicManagement = ClinicDataConverter.toConvert(clinicDTO);
		String msg = "";
		PSIClinicManagement getClinicByClinicId = Context.getService(PSIClinicManagementService.class).findByClinicId(
		    psiClinicManagement.getClinicId());
		if (getClinicByClinicId != null) {
			msg = "This clinic id is already taken";
		} else {
			Context.openSession();
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(psiClinicManagement);
			Context.clearSession();
			msg = "ok";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public ResponseEntity<String> editClinic(@RequestBody ClinicDTO clinicDTO, ModelMap model) throws Exception {
		
		String msg = "";
		PSIClinicManagement getByIdAndClinicID = Context.getService(PSIClinicManagementService.class).findByIdNotByClinicId(
		    clinicDTO.getCid(), clinicDTO.getClinicId());
		if (getByIdAndClinicID != null) {
			getByIdAndClinicID.setName(clinicDTO.getName());
			getByIdAndClinicID.setCategory(clinicDTO.getCategory());
			getByIdAndClinicID.setClinicId(clinicDTO.getClinicId());
			getByIdAndClinicID.setAddress(clinicDTO.getAddress());
			getByIdAndClinicID.setDhisId(clinicDTO.getDhisId());
			Context.openSession();
			Context.getService(PSIClinicManagementService.class).saveOrUpdateClinic(getByIdAndClinicID);
			Context.clearSession();
			msg = "ok";
		} else {
			msg = "This clinic id is already taken";
		}
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}
	
}
