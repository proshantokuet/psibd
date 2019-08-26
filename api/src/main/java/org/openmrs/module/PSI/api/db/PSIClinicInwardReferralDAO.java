package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.PSIClinicInwardReferral;

public interface PSIClinicInwardReferralDAO {
	
	public PSIClinicInwardReferral saveOrUpdate(PSIClinicInwardReferral psiClinicInwardReferral);
	
	public PSIClinicInwardReferral findByReferralNo(String referralNo);
	
	public PSIClinicInwardReferral findById(int id);
	
	public List<PSIClinicInwardReferral> findByPatientUuid(String patientUuid);
	
	public void delete(int id);

}
