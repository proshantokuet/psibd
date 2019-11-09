package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicSpotService extends OpenmrsService {
	
	public PSIClinicSpot saveOrUpdate(PSIClinicSpot psiClinicSpot);
	
	public List<PSIClinicSpot> findAll();
	
	public PSIClinicSpot findById(int id);
	
	public PSIClinicSpot findDuplicateSpot(int id, String code, int clinicCode);
	
	public List<PSIClinicSpot> findByClinicId(int id);
	
	public void delete(int id);
	
}
