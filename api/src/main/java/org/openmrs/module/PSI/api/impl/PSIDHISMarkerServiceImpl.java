package org.openmrs.module.PSI.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.PSIDHISMarkerService;
import org.openmrs.module.PSI.api.db.PSIDHISMarkerDAO;

public class PSIDHISMarkerServiceImpl extends BaseOpenmrsService implements PSIDHISMarkerService {
	
	private PSIDHISMarkerDAO dao;
	
	public PSIDHISMarkerDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIDHISMarkerDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIDHISMarker saveOrUpdate(PSIDHISMarker psidhisMarker) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psidhisMarker);
	}
	
	@Override
	public PSIDHISMarker findByType(String type) {
		// TODO Auto-generated method stub
		return dao.findByType(type);
	}
	
}
