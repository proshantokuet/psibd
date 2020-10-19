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
		psiServiceManagement.setSid(clinicServiceDTO.getSid());
		psiServiceManagement.setProvider(clinicServiceDTO.getProvider());
		psiServiceManagement.setUnitCost(clinicServiceDTO.getUnitCost());
		
		psiServiceManagement.setGender(clinicServiceDTO.getGender());
		
		psiServiceManagement.setYearTo(clinicServiceDTO.getYearTo());
		psiServiceManagement.setYearFrom(clinicServiceDTO.getYearFrom());
		
		psiServiceManagement.setMonthFrom(clinicServiceDTO.getMonthFrom());
		psiServiceManagement.setMonthTo(clinicServiceDTO.getMonthTo());
		
		psiServiceManagement.setDaysFrom(clinicServiceDTO.getDaysFrom());
		psiServiceManagement.setDaysTo(clinicServiceDTO.getDaysTo());
		
		int ageTo = getDaysFromYMD(clinicServiceDTO.getYearTo(), clinicServiceDTO.getMonthTo(), clinicServiceDTO.getDaysTo());
		int ageFrom = getDaysFromYMD(clinicServiceDTO.getYearFrom(), clinicServiceDTO.getMonthFrom(),
		    clinicServiceDTO.getDaysFrom());
		psiServiceManagement.setAgeStart(ageTo);
		psiServiceManagement.setAgeEnd(ageFrom);
		psiServiceManagement.setType(clinicServiceDTO.getType());
		psiServiceManagement.setBrandName(clinicServiceDTO.getBrandName());
		psiServiceManagement.setPurchasePrice(clinicServiceDTO.getPurchasePrice());
		psiServiceManagement.setDiscountPop(clinicServiceDTO.getDiscountPop());
		psiServiceManagement.setDiscountPoor(clinicServiceDTO.getDiscountPoor());
		psiServiceManagement.setDiscountAblePay(clinicServiceDTO.getDiscountAblePay());
		psiServiceManagement.setTimestamp(System.currentTimeMillis());
		psiServiceManagement.setCreator(Context.getAuthenticatedUser());
		psiServiceManagement.setUuid(UUID.randomUUID().toString());
		psiServiceManagement.setDateCreated(new Date());
		psiServiceManagement.setVoided(clinicServiceDTO.isVoided());
		return psiServiceManagement;
		
	}
	
	public static int getDaysFromYMD(int y, int m, int d) {
		int days = y * 365 + m * 30 + d;
		int leapYearDaysApprox = (int) Math.ceil(y / 4f);
		int modMonth = (int) Math.ceil(m / 2f);
		return days + modMonth + leapYearDaysApprox;
	}
	
}
