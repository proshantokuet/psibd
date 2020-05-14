package org.openmrs.module.PSI.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.SHNEslipNoGenerate;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIUniqueIdGeneratorService extends OpenmrsService {
	
	public PSIUniqueIdGenerator saveOrUpdate(PSIUniqueIdGenerator psiUniqueIdGenerator);
	
	public PSIUniqueIdGenerator findByClinicCodeAndDate(String date, String clinicCode);
	
	public SHNEslipNoGenerate saveOrUpdate (SHNEslipNoGenerate shnEslipNoGenerate);
	
	public SHNEslipNoGenerate findEslipByClinicCodeAndDate(String date, String clinicCode);
	
}
