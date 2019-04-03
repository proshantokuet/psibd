package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIClinicManagement;

public interface PSIClinicManagementDAO {
	
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic);
	
	public List<PSIClinicManagement> getAllClinic();
	
	public PSIClinicManagement findById(int id);
	
	public void delete(int id);
	
}
