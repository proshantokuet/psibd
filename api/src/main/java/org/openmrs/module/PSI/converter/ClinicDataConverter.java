package org.openmrs.module.PSI.converter;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.dto.ClinicDTO;

public class ClinicDataConverter {
	
	public PSIClinicManagement toConvert(ClinicDTO clinicDTO) {
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
		return psiClinicManagement;
		
	}
	
public JSONObject toConvert(PSIClinicManagement clinic) throws JSONException {
		
		JSONObject clinicObject = new JSONObject();
		clinicObject.putOpt("cid", clinic.getCid());
		clinicObject.putOpt("name", clinic.getName());
		clinicObject.putOpt("clinicId", clinic.getClinicId());
		clinicObject.putOpt("category", clinic.getCategory());
		clinicObject.putOpt("address", clinic.getAddress());
		
		clinicObject.putOpt("dhisId", clinic.getDhisId());
		clinicObject.putOpt("description", clinic.getDescription());
		clinicObject.putOpt("division", clinic.getDivision());
		clinicObject.putOpt("divisionId", clinic.getDivisionId());
		clinicObject.putOpt("divisionUuid", clinic.getDivisionUuid());
		
		clinicObject.putOpt("district", clinic.getDistrict());
		clinicObject.putOpt("districtId", clinic.getDistrictId());
		clinicObject.putOpt("districtUuid", clinic.getDistrictUuid());
		
		clinicObject.putOpt("upazila", clinic.getUpazila());
		clinicObject.putOpt("upazilaId", clinic.getUpazilaId());
		clinicObject.putOpt("upazilaUuid", clinic.getUpazilaUuid());
		
		clinicObject.putOpt("unionName", clinic.getUnionName());
		clinicObject.putOpt("timestamp", clinic.getTimestamp());
		clinicObject.putOpt("uuid", clinic.getUuid());
		clinicObject.putOpt("dateCreated", clinic.getDateCreated());
		
		clinicObject.putOpt("dateChanged", clinic.getDateChanged());
		
		return clinicObject;
	}
	
}
