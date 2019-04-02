package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinic;
import org.openmrs.module.PSI.api.PSIClinicService;
import org.openmrs.module.PSI.api.db.PSIClinicDAO;

public class PSIClinicServiceimpl extends BaseOpenmrsService implements PSIClinicService {
	
	private PSIClinicDAO dao;
	
	public PSIClinicDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIClinicDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIClinic saveOrUpdateClinic(PSIClinic psiClinic) {
		return dao.saveOrUpdateClinic(psiClinic);
	}
	
	@Override
	public List<PSIClinic> getAllClinic() {
		return dao.getAllClinic();
	}
	
	@Override
	public PSIClinic findById(int id) {
		return dao.findById(id);
	}
	
	@Override
	public void delete(int id) {
		dao.delete(id);
		
	}
	
}
