package org.openmrs.module.PSI.api.impl;

import java.util.Date;
import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.PSIServiceProvisionService;
import org.openmrs.module.PSI.api.db.PSIServiceProvisionDAO;
import org.openmrs.module.PSI.dto.AUHCComprehensiveReport;
import org.openmrs.module.PSI.dto.AUHCDashboardCard;
import org.openmrs.module.PSI.dto.AUHCDraftTrackingReport;
import org.openmrs.module.PSI.dto.AUHCRegistrationReport;
import org.openmrs.module.PSI.dto.AUHCVisitReport;
import org.openmrs.module.PSI.dto.DashboardDTO;
import org.openmrs.module.PSI.dto.PSIReport;
import org.openmrs.module.PSI.dto.PSIReportSlipTracking;
import org.openmrs.module.PSI.dto.SearchFilterDraftTracking;
import org.openmrs.module.PSI.dto.SearchFilterReport;
import org.openmrs.module.PSI.dto.SearchFilterSlipTracking;

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
	public List<PSIReport> serviceProviderWiseReport(String startDate, String endDate, String code, String dataCollector) {
		// TODO Auto-generated method stub
		return dao.serviceProviderWiseReport(startDate, endDate, code, dataCollector);
	}
	
	@Override
	public String servicePointWiseRepor(String startDate, String endDate, String code) {
		// TODO Auto-generated method stub
		return dao.servicePointWiseRepor(startDate, endDate, code);
	}
	
	@Override
	public DashboardDTO dashboardReport(String start, String end, String code, String dataCollector) {
		// TODO Auto-generated method stub
		return dao.dashboardReport(start, end, code, dataCollector);
	}
	
	@Override
	public List<PSIServiceProvision> findAllByTimestampNotSending(long timestamp) {
		// TODO Auto-generated method stub
		return dao.findAllByTimestampNotSending(timestamp);
	}
	
	@Override
	public List<PSIServiceProvision> findAllResend() {
		// TODO Auto-generated method stub
		return dao.findAllResend();
	}
	
	@Override
	public List<PSIServiceProvision> findAllByMoneyReceiptId(int moneyReceiptId) {
		// TODO Auto-generated method stub
		return dao.findAllByMoneyReceiptId(moneyReceiptId);
	}

	@Override
	public void deleteByPatientUuidAndMoneyReceiptIdNull(String patientUuid) {
		dao.deleteByPatientUuidAndMoneyReceiptIdNull(patientUuid);
		
	}

	@Override
	public String getTotalDiscount(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getTotalDiscount(startDate, endDate);
	}

	@Override
	public String getTotalServiceContract(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getTotalServiceContact(startDate, endDate);
	}

	@Override
	public List<PSIReportSlipTracking> getSlipTrackingReport(
			SearchFilterSlipTracking filter) {
		// TODO Auto-generated method stub
		return dao.getSlipTrackingReport(filter);
	}

	@Override
	public List<Object[]> getSlip(SearchFilterSlipTracking filter) {
		// TODO Auto-generated method stub
		return dao.getSlip(filter);
	}

	@Override
	public String getNoOfDraft(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getNoOfDraft(startDate, endDate);
	}

	@Override
	public String getTotalPayableDraft(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getTotalPayableDraft(startDate, endDate);
	}

	@Override
	public List<AUHCDraftTrackingReport> getDraft(
			SearchFilterDraftTracking filter) {
		// TODO Auto-generated method stub
		return dao.getDraftTrackingReport(filter);
	}

	@Override
	public String getDashboardNewReg(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getDashboardNewReg(startDate, endDate);
	}

	@Override
	public String getDashboardOldClients(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getDashboardOldClients(startDate, endDate);
	}

	@Override
	public String getDashboardNewClients(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getDashboardNewClients(startDate, endDate);
	}

	@Override
	public List<AUHCComprehensiveReport> getComprehensiveReport(SearchFilterReport filter) {
		// TODO Auto-generated method stub
		return dao.getComprehensiveReport(filter);
	}

	@Override
	public List<AUHCRegistrationReport> getRegistrationReport(
			SearchFilterReport filter) {
		// TODO Auto-generated method stub
		return dao.getRegistrationReport(filter);
	}

	@Override
	public String getTotalPayableDraft(SearchFilterDraftTracking filter) {
		// TODO Auto-generated method stub
		return dao.getTotalPayableDraft(filter);
	}

	@Override
	public String getTotalDiscount(SearchFilterSlipTracking filter) {
		// TODO Auto-generated method stub
		return dao.getTotalDiscount(filter);
	}

	@Override
	public String getTotalServiceContact(SearchFilterSlipTracking filter) {
		// TODO Auto-generated method stub
		return dao.totalServiceContact(filter);
	}

	@Override
	public String getPatientsServed(SearchFilterSlipTracking filter) {
		// TODO Auto-generated method stub
		return dao.getPatientsServed(filter);
	}

	@Override
	public String getRevenueEarned(SearchFilterSlipTracking filter) {
		// TODO Auto-generated method stub
		return dao.getRevenueEarned(filter);
	}

	@Override
	public List<AUHCRegistrationReport> getRegistrationReport(String startDate,
			String endDate, String gender,String code) {
		// TODO Auto-generated method stub
		return dao.getRegistrationReport(startDate,endDate,gender,code);
	}

	@Override
	public List<AUHCVisitReport> getVisitReport(String startDate, String endDate) {
		// TODO Auto-generated method stub
		return dao.getVisitReport(startDate, endDate);
	}

	@Override
	public AUHCDashboardCard getComprehensiveDashboardCard(
			List<AUHCComprehensiveReport> report, SearchFilterReport filter) {
		// TODO Auto-generated method stub
		return dao.getComprehensiveDashboardCard(report, filter);
	}

	@Override
	public AUHCDashboardCard getProviderDashboardCard(List<PSIReport> report,
			SearchFilterReport filter) {
		// TODO Auto-generated method stub
		return dao.getProviderDashboardCard(report, filter);
	}
	
	
	
	
}
