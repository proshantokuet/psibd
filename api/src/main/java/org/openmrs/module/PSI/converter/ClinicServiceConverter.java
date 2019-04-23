package org.openmrs.module.PSI.converter;

import java.util.Date;
import java.util.UUID;

import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;

public class ClinicServiceConverter {
	
	public static PSIServiceManagement toConvert(ClinicServiceDTO clinicServiceDTO) {
		PSIServiceManagement psiServiceManagement = new PSIServiceManagement();
		
		psiServiceManagement.setCategory(clinicServiceDTO.getCategory());
		psiServiceManagement.setName(clinicServiceDTO.getName());
		psiServiceManagement.setCode(clinicServiceDTO.getCode());
		psiServiceManagement.setProvider(clinicServiceDTO.getProvider());
		psiServiceManagement.setUnitCost(clinicServiceDTO.getUnitCost());
		psiServiceManagement.setTimestamp(System.currentTimeMillis());
		psiServiceManagement.setCreator(Context.getAuthenticatedUser());
		psiServiceManagement.setUuid(UUID.randomUUID().toString());
		psiServiceManagement.setDateCreated(new Date());
		return psiServiceManagement;
		
	}
}
