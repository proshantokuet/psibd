package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.api.SHNFollowUpActionService;
import org.openmrs.module.PSI.api.db.SHNFollowUpActionDAO;
import org.openmrs.module.PSI.dto.SHNFollowUPReportDTO;

public class SHNFollowUpActionServiceImpl  extends BaseOpenmrsService implements SHNFollowUpActionService {

	private SHNFollowUpActionDAO dao;
	
	
	public SHNFollowUpActionDAO getDao() {
		return dao;
	}

	public void setDao(SHNFollowUpActionDAO dao) {
		this.dao = dao;
	}

	@Override
	public SHNFollowUpAction saveOrUpdate(SHNFollowUpAction shnFollowUpAction) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(shnFollowUpAction);
	}

	@Override
	public SHNFollowUpAction findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public SHNFollowUpAction findByUuid(String uuid) {
		// TODO Auto-generated method stub
		return dao.findByUuid(uuid);
	}

	@Override
	public SHNFollowUpAction findByCodedConceptAndEncounter(int conceptId,
			String encounterUuid) {
		// TODO Auto-generated method stub
		return dao.findByCodedConceptAndEncounter(conceptId, encounterUuid);
	}

	@Override
	public List<SHNFollowUPReportDTO> getfollowUpReprt(String visitStartDate,
			String visitEnd, String followUpStartDate, String followUpEndDate,
			String moileNo, String patientHid, String clinicCode) {
		// TODO Auto-generated method stub
		return dao.getfollowUpReprt(visitStartDate, visitEnd, followUpStartDate, followUpEndDate, moileNo, patientHid, clinicCode);
	}



}
