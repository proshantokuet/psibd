package org.openmrs.module.PSI.api.db;

import org.openmrs.module.PSI.PSIDHISMarker;

public interface PSIDHISMarkerDAO {
	
	public PSIDHISMarker saveOrUpdate(PSIDHISMarker psidhisMarker);
	
	public PSIDHISMarker findByType(String type);
	
}
