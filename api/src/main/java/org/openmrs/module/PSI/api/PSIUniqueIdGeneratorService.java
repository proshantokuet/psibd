package org.openmrs.module.PSI.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIUniqueIdGeneratorService extends OpenmrsService {
	
	public PSIUniqueIdGenerator saveOrUpdate(PSIUniqueIdGenerator psiUniqueIdGenerator);
	
	public PSIUniqueIdGenerator findByClinicCodeAndDate(String date, String clinicCode);
	
}
