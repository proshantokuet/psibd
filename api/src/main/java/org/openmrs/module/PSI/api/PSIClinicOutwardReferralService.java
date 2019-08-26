package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicOutwardReferral;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicOutwardReferralService extends OpenmrsService {
	
	public PSIClinicOutwardReferral saveOrUpdate(PSIClinicOutwardReferral psiClinicOutwardReferral);
	
	public PSIClinicOutwardReferral findByOutwardReferralId(int id);
	
	public List<PSIClinicOutwardReferral> findByPatientUuid(String patientUuid);
	
	public void delete(int id); 

}
