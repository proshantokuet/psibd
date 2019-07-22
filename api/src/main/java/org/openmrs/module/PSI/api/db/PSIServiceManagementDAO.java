package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIServiceManagement;

public interface PSIServiceManagementDAO {
	
	public PSIServiceManagement saveOrUpdate(PSIServiceManagement psiServiceManagement);
	
	public List<PSIServiceManagement> getAllByClinicId(int clinicId);
	
	public List<PSIServiceManagement> getAll();
	
	public PSIServiceManagement findById(int id);
	
	public float getUnitCostByName(String naem);
	
	public PSIServiceManagement findByCodeAndClinicId(String code, int clinicId);
	
	public PSIServiceManagement findByIdNotByClinicId(int id, String code, int clinicId);
	
	public void delete(int id);
	
}
