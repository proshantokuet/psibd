package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.PSIServiceManagementService;
import org.openmrs.module.PSI.api.db.PSIServiceManagementDAO;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;

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
	
	@Override
	public List<String> getCategoryList(Integer clinicId) {
		// TODO Auto-generated method stub
		return dao.getCategoryList(clinicId);
	}
	
	@Override
	public int updatePrimaryKey(int oldId, int currentId) {
		// TODO Auto-generated method stub
		return dao.updatePrimaryKey(oldId, currentId);
	}

	@Override
	public PSIServiceManagement findByClinicIdDescending() {
		// TODO Auto-generated method stub
		return dao.findByClinicIdDescending();
	}

	@Override
	public int updateTableAutoIncrementValue(int autoIncrementNo) {
		// TODO Auto-generated method stub
		return dao.updateTableAutoIncrementValue(autoIncrementNo);
	}

	@Override
	public List<ClinicServiceDTO> getProductListAll(int clinicId,int productId) {
		// TODO Auto-generated method stub
		return dao.getProductListAll(clinicId,productId);
	}
	
}
