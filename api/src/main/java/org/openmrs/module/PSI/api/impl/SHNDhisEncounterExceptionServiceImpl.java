package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNDhisEncounterException;
import org.openmrs.module.PSI.api.AUHCClinicTypeService;
import org.openmrs.module.PSI.api.SHNDhisEncounterExceptionService;
import org.openmrs.module.PSI.api.db.SHNDhisEncounterExceptionDAO;

public class SHNDhisEncounterExceptionServiceImpl extends BaseOpenmrsService implements SHNDhisEncounterExceptionService  {

	protected final Log log = LogFactory.getLog(getClass());
	
	private SHNDhisEncounterExceptionDAO dao;
	
	public SHNDhisEncounterExceptionDAO getDao() {
		return dao;
	}

	public void setDao(SHNDhisEncounterExceptionDAO dao) {
		this.dao = dao;
	}

	@Override
	public SHNDhisEncounterException saveOrUpdate(
			SHNDhisEncounterException dhisEncounterException) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(dhisEncounterException);
	}

	@Override
	public List<SHNDhisEncounterException> findAllFailedEncounterByStatus(
			int status) {
		// TODO Auto-generated method stub
		return dao.findAllFailedEncounterByStatus(status);
	}

	@Override
	public SHNDhisEncounterException findAllById(int markerId) {
		// TODO Auto-generated method stub
		return dao.findAllById(markerId);
	}

	@Override
	public SHNDhisEncounterException findAllBymarkerIdAndFormName(int markerId,
			String formsName,String encounterUuid) {
		// TODO Auto-generated method stub
		return dao.findAllBymarkerIdAndFormName(markerId, formsName,encounterUuid);
	}

	@Override
	public SHNDhisEncounterException findEncByFormAndEncId(String encounterId, String formsName) {
		// TODO Auto-generated method stub
		return dao.findEncByFormAndEncId(encounterId, formsName);
	}

	@Override
	public SHNDhisEncounterException findEncByFormAndEncIdForFailedEvent(
			String encounterId, String formsName) {
		// TODO Auto-generated method stub
		return dao.findEncByFormAndEncIdForFailedEvent(encounterId, formsName);
	}

}
