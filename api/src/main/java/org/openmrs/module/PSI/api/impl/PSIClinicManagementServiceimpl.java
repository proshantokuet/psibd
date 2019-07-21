package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.db.PSIClinicManagementDAO;
import org.openmrs.module.PSI.dto.PSILocation;

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
	
	@Override
	public PSIClinicManagement findByClinicId(String clinicId) {
		// TODO Auto-generated method stub
		return dao.findByClinicId(clinicId);
	}
	
	@Override
	public PSIClinicManagement findByIdNotByClinicId(int id, String clinicId) {
		// TODO Auto-generated method stub
		return dao.findByIdNotByClinicId(id, clinicId);
	}
	
	@Override
	public List<PSILocation> findLocationByTag(String tagName) {
		// TODO Auto-generated method stub
		return dao.findLocationByTag(tagName);
	}
	
	@Override
	public PSILocation findLocationById(int id) {
		// TODO Auto-generated method stub
		return dao.findLocationById(id);
	}
	
	@Override
	public List<PSILocation> findByparentLocation(int parentLocationId) {
		// TODO Auto-generated method stub
		return dao.findByparentLocation(parentLocationId);
	}
	
}
