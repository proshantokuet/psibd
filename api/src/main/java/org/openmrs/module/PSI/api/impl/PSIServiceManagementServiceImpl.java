package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.db.PSIServiceManagementDAO;

public class PSIServiceManagementServiceImpl extends BaseOpenmrsService implements PSIServiceManagementService {
	
	private PSIServiceManagementDAO dao;
	
	public PSIServiceManagementDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIServiceManagementDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIServiceManagement saveOrUpdate(PSIServiceManagement psiServiceManagement) {
		
		return dao.saveOrUpdate(psiServiceManagement);
	}
	
	@Override
	public List<PSIServiceManagement> getAllByClinicId(int clinicId) {
		
		return dao.getAllByClinicId(clinicId);
	}
	
	@Override
	public PSIServiceManagement findById(int id) {
		
		return dao.findById(id);
	}
	
	@Override
	public float getUnitCostByName(String name) {
		return dao.getUnitCostByName(name);
	}
	
	@Override
	public void delete(int id) {
		dao.delete(id);
		
	}
	
	@Override
	public PSIServiceManagement findByCodeAndClinicId(String code, int clinicId) {
		// TODO Auto-generated method stub
		return dao.findByCodeAndClinicId(code, clinicId);
	}
	
	@Override
	public PSIServiceManagement findByIdNotByClinicId(int id, String code, int clinicId) {
		// TODO Auto-generated method stub
		return dao.findByIdNotByClinicId(id, code, clinicId);
	}
	
	@Override
	public List<PSIServiceManagement> getAll() {
		// TODO Auto-generated method stub
		return dao.getAll();
	}
	
	@Override
	public List<PSIServiceManagement> getAllByClinicIdAgeGender(int clinicId, int age, String gender) {
		// TODO Auto-generated method stub
		return dao.getAllByClinicIdAgeGender(clinicId, age, gender);
	}
	
}
