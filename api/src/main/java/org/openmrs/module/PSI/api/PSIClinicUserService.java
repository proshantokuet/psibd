package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicUser;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicUserService extends OpenmrsService {
	
	public PSIClinicUser saveOrUpdate(PSIClinicUser psiClinicUser);
	
	public List<PSIClinicUser> getAll();
	
	public PSIClinicUser findById(int id);
	
	public void delete(int id);
	
}
