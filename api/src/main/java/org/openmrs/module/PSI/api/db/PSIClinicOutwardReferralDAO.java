package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIClinicOutwardReferral;

public interface PSIClinicOutwardReferralDAO {
	
	public PSIClinicOutwardReferral saveOrUpdate(PSIClinicOutwardReferral psiClinicOutwardReferral);
	
	public PSIClinicOutwardReferral findByOutwardReferralId(int id);
	
	public List<PSIClinicOutwardReferral> findByPatientUuid(String patientUuid);
	
	public void delete(int id); 
}
