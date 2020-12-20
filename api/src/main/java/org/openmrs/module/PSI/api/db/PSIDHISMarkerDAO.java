package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.dto.EventReceordDTO;

public interface PSIDHISMarkerDAO {
	
	public PSIDHISMarker saveOrUpdate(PSIDHISMarker psidhisMarker);
	
	public PSIDHISMarker findByType(String type);
	
	public List<EventReceordDTO> rawQuery(int id);
	
	public List<EventReceordDTO> getEventRecordsOfEncounter(int id);
	
	public List<EventReceordDTO> getEventRecordsOfDrug(int id);
}
