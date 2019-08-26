package org.openmrs.module.PSI.api;


import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicChild;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicChildService extends OpenmrsService {
	
	public PSIClinicChild saveOrUpdate(PSIClinicChild psiClinicAddChild);
	
	public PSIClinicChild findById(int id);
	
	public List<PSIClinicChild> findByMotherUuid(String motherUuid);
	
	public void delete(int id);

}
