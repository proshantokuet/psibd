package org.openmrs.module.PSI.web.controller.rest;

import java.util.Date;
import java.util.UUID;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.dto.ClinicDTO;
import org.openmrs.module.PSI.dto.PSILocation;
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
	
}
