package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicManagementService extends OpenmrsService {
	
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic);
	
	public List<PSIClinicManagement> getAllClinic();
	
	public PSIClinicManagement findById(int id);
	
	public PSIClinicManagement findByClinicId(String clinicId);
	
	public PSIClinicManagement findByIdNotByClinicId(int id, String clinicId);
	
	public void delete(int id);
	
}
