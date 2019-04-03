package org.openmrs.module.PSI.api.db;

import java.util.Date;
import java.util.List;

import org.openmrs.module.PSI.PSIServiceProvision;

public interface PSIServiceProvisionDAO {
	
	public PSIServiceProvision saveOrUpdate(PSIServiceProvision psiServiceProvision);
	
	public List<PSIServiceProvision> getAll();
	
	public List<PSIServiceProvision> getAllByPatient(String patientUuid);
	
	public PSIServiceProvision findById(int id);
	
	public List<PSIServiceProvision> getAllBetweenDateAndPatient(Date start, Date end, String patientUuid);
	
	public List<PSIServiceProvision> getAllByDateAndPatient(Date date, String patientUuid);
	
	public void delete(int id);
	
}
