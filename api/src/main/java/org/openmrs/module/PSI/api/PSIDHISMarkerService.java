package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIDHISMarkerService extends OpenmrsService {
	
	public PSIDHISMarker saveOrUpdate(PSIDHISMarker psidhisMarker);
	
	public PSIDHISMarker findByType(String type);
	
	public List<String> rawQuery(int id);
	
}
