package org.openmrs.module.PSI.converter;

import java.util.Date;
import java.util.UUID;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.dto.ClinicDTO;

public class ClinicDataConverter {
	
	public static PSIClinicManagement toConvert(ClinicDTO clinicDTO) {
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
	
}
