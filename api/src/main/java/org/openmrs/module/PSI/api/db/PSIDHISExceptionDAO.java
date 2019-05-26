package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIDHISException;

public interface PSIDHISExceptionDAO {
	
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisException);
	
	public List<PSIDHISException> findAllByStatus(int status);
	
}
