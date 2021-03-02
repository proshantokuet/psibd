package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNDhisEncounterException;

public interface SHNDhisEncounterExceptionDAO {
	
	public SHNDhisEncounterException saveOrUpdate(SHNDhisEncounterException dhisEncounterException);
	
	public List<SHNDhisEncounterException> findAllFailedEncounterByStatus(int status);
	
	public SHNDhisEncounterException findAllById(int markerId);
	
	public SHNDhisEncounterException findAllBymarkerIdAndFormName(int markerId, String formsName,String encounterUuid);
	
	public SHNDhisEncounterException findEncByFormAndEncId(String encounterId, String formsName);
	
	public SHNDhisEncounterException findEncByFormAndEncIdForFailedEvent(String encounterId, String formsName);

}
