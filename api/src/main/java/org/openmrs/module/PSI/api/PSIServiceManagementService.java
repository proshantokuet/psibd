package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIServiceManagementService extends OpenmrsService {
	
	public PSIServiceManagement saveOrUpdate(PSIServiceManagement psiServiceManagement);
	
	public List<PSIServiceManagement> getAllByClinicId(int clinicId);
	
	public List<PSIServiceManagement> getAllByClinicIdAgeGender(int clinicId, int age, String gender);
	
	public List<PSIServiceManagement> getAll();
	
	public PSIServiceManagement findById(int id);
	
	public float getUnitCostByName(String naem);
	
	public PSIServiceManagement findByCodeAndClinicId(String code, int clinicId);
	
	public PSIServiceManagement findByIdNotByClinicId(int id, String code, int clinicId);
	
	public void delete(int id);
	
	public List<String> getCategoryList(Integer clinicId);
	
	public int updatePrimaryKey(int oldId, int currentId);
	
	public PSIServiceManagement findByClinicIdDescending();
	
	public int updateTableAutoIncrementValue(int autoIncrementNo);
	
}
