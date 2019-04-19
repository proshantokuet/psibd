package org.openmrs.module.PSI.api;

import java.util.Date;
import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIServiceProvisionService extends OpenmrsService {
	
	public PSIServiceProvision saveOrUpdate(PSIServiceProvision psiServiceProvision);
	
	public List<PSIServiceProvision> getAll();
	
	public List<PSIServiceProvision> getAllByPatient(String patientUuid);
	
	public PSIServiceProvision findById(int id);
	
	public List<PSIServiceProvision> getAllBetweenDateAndPatient(Date start, Date end, String patientUuid);
	
	public List<PSIServiceProvision> getAllByDateAndPatient(Date date, String patientUuid);
	
	public List<PSIServiceProvision> findAllByTimestamp(long timestamp);
	
	public void delete(int id);
	
}
