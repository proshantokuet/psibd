package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIServiceManagement;

public interface PSIServiceManagementDAO {
	
	public PSIServiceManagement saveOrUpdate(PSIServiceManagement psiServiceManagement);
	
	public List<PSIServiceManagement> getAll(int clinicId);
	
	public List<PSIServiceManagement> getAll();
	
	public PSIServiceManagement findById(int id);
	
	public float getUnitCostByName(String naem);
	
	public PSIServiceManagement findByCode(String code, int clinicId);
	
	public PSIServiceManagement findByIdNotByCode(int id, String code, int clinicId);
	
	public void delete(int id);
	
}
