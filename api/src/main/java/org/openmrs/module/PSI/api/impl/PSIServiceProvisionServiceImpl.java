package org.openmrs.module.PSI.api.impl;

import java.util.Date;
import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.db.PSIServiceProvisionDAO;
import org.openmrs.module.PSI.dto.PSIReport;

public class PSIServiceProvisionServiceImpl extends BaseOpenmrsService implements PSIServiceProvisionService {
	
	private PSIServiceProvisionDAO dao;
	
	public PSIServiceProvisionDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIServiceProvisionDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIServiceProvision saveOrUpdate(PSIServiceProvision psiServiceProvision) {
		
		return dao.saveOrUpdate(psiServiceProvision);
	}
	
	@Override
	public List<PSIServiceProvision> getAll() {
		
		return dao.getAll();
	}
	
	@Override
	public List<PSIServiceProvision> getAllByPatient(String patientUuid) {
		
		return dao.getAllByPatient(patientUuid);
	}
	
	@Override
	public PSIServiceProvision findById(int id) {
		
		return dao.findById(id);
	}
	
	@Override
	public List<PSIServiceProvision> getAllBetweenDateAndPatient(Date start, Date end, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PSIServiceProvision> getAllByDateAndPatient(Date date, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void delete(int id) {
		dao.delete(id);
		
	}
	
	@Override
	public List<PSIServiceProvision> findAllByTimestamp(long timestamp) {
		// TODO Auto-generated method stub
		return dao.findAllByTimestamp(timestamp);
	}
	
	@Override
	public List<PSIReport> servicePointWiseReport(String startDate, String endDate, String code) {
		// TODO Auto-generated method stub
		return dao.servicePointWiseReport(startDate, endDate, code);
	}
	
	@Override
	public List<PSIReport> serviceProviderWiseReport(String startDate, String endDate, String code, int provider) {
		// TODO Auto-generated method stub
		return dao.serviceProviderWiseReport(startDate, endDate, code, provider);
	}
	
	@Override
	public String servicePointWiseRepor(String startDate, String endDate, String code) {
		// TODO Auto-generated method stub
		return dao.servicePointWiseRepor(startDate, endDate, code);
	}
	
}
