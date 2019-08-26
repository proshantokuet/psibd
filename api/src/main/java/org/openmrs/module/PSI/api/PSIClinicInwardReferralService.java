package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIClinicInwardReferral;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIClinicInwardReferralService extends OpenmrsService {
	
	public PSIClinicInwardReferral saveOrUpdate(PSIClinicInwardReferral psiClinicInwardReferral);
	
	public PSIClinicInwardReferral findByReferralNo(String referralNo);
	
	public PSIClinicInwardReferral findById(int id);
	
	public List<PSIClinicInwardReferral> findByPatientUuid(String patientUuid);
	
	public void delete(int id);
}
