package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
import org.openmrs.module.PSI.api.db.PSIDHISExceptionDAO;

public class PSIDHISEcxeptionServiceImpl extends BaseOpenmrsService implements PSIDHISExceptionService {
	
	private PSIDHISExceptionDAO dao;
	
	public PSIDHISExceptionDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIDHISExceptionDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisMarkException) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psidhisMarkException);
	}
	
	@Override
	public List<PSIDHISException> findAllByStatus(int status) {
		// TODO Auto-generated method stub
		return dao.findAllByStatus(status);
	}
	
}
