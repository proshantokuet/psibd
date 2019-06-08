package org.openmrs.module.PSI.api.db;

import java.util.Date;
import java.util.List;

import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.dto.PSIReport;

public interface PSIServiceProvisionDAO {
	
	public PSIServiceProvision saveOrUpdate(PSIServiceProvision psiServiceProvision);
	
	public List<PSIServiceProvision> getAll();
	
	public List<PSIServiceProvision> getAllByPatient(String patientUuid);
	
	public PSIServiceProvision findById(int id);
	
	public List<PSIServiceProvision> getAllBetweenDateAndPatient(Date start, Date end, String patientUuid);
	
	public List<PSIServiceProvision> getAllByDateAndPatient(Date date, String patientUuid);
	
	public List<PSIServiceProvision> findAllByTimestamp(long timestamp);
	
	public List<PSIReport> servicePointWiseReport(String startDate, String endDate, String code);
	
	public String servicePointWiseRepor(String startDate, String endDate, String code);
	
	public List<PSIReport> serviceProviderWiseReport(String startDate, String endDate, String code, int provider);
	
	public void delete(int id);
	
}
