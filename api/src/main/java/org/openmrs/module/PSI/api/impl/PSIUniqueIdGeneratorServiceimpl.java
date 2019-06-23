package org.openmrs.module.PSI.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.api.PSIUniqueIdGeneratorService;
import org.openmrs.module.PSI.api.db.PSIUniqueIdGeneratorDAO;

public class PSIUniqueIdGeneratorServiceimpl extends BaseOpenmrsService implements PSIUniqueIdGeneratorService {
	
	private PSIUniqueIdGeneratorDAO dao;
	
	public PSIUniqueIdGeneratorDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIUniqueIdGeneratorDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIUniqueIdGenerator saveOrUpdate(PSIUniqueIdGenerator psiUniqueIdGenerator) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psiUniqueIdGenerator);
	}
	
	@Override
	public PSIUniqueIdGenerator findByClinicCodeAndDate(String date, String clinicCode) {
		// TODO Auto-generated method stub
		return dao.findByClinicCodeAndDate(date, clinicCode);
	}
	
}
