package org.openmrs.module.PSI.api;

import java.util.List;
import org.openmrs.module.PSI.SHNDhisEncounterException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNDhisEncounterExceptionService {
	
	public SHNDhisEncounterException saveOrUpdate(SHNDhisEncounterException dhisEncounterException);
	
	public List<SHNDhisEncounterException> findAllFailedEncounterByStatus(int status);
	
	public SHNDhisEncounterException findAllById(int markerId);
	
	public SHNDhisEncounterException findAllBymarkerIdAndFormName(int markerId, String formsName);
	
	public SHNDhisEncounterException findEncByFormAndEncId(String encounterId, String formsName);

}
