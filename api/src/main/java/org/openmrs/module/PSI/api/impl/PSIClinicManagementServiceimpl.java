package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.db.PSIClinicManagementDAO;

public class PSIClinicManagementServiceimpl extends BaseOpenmrsService implements PSIClinicManagementService {
	
	private PSIClinicManagementDAO dao;
	
	public PSIClinicManagementDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIClinicManagementDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic) {
		return dao.saveOrUpdateClinic(psiClinic);
	}
	
	@Override
	public List<PSIClinicManagement> getAllClinic() {
		return dao.getAllClinic();
	}
	
	@Override
	public PSIClinicManagement findById(int id) {
		return dao.findById(id);
	}
	
	@Override
	public void delete(int id) {
		dao.delete(id);
		
	}
	
}
