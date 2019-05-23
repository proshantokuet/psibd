package org.openmrs.module.PSI.api.db;

import org.openmrs.module.PSI.PSIDHISException;

public interface PSIDHISExceptionDAO {
	
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisException);
	
	public PSIDHISException findByType(String type);
	
}
