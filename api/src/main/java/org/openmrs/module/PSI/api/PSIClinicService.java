package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinic;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicService extends OpenmrsService {
	
	public PSIClinic saveOrUpdateClinic(PSIClinic psiClinic);
	
	public List<PSIClinic> getAllClinic();
	
	public PSIClinic findById(int id);
	
	public void delete(int id);
	
}
