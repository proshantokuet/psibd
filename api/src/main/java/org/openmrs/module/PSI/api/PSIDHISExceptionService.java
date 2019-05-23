package org.openmrs.module.PSI.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIDHISException;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIDHISExceptionService extends OpenmrsService {
	
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisException);
	
	public PSIDHISException findByType(String type);
	
}
