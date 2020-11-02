package org.openmrs.module.PSI.api.impl;

import java.util.Date;
import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.SHNRefundedMoneyReceipt;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.api.db.PSIMoneyReceiptDAO;

public class PSIMoneyReceiptServiceImpl extends BaseOpenmrsService implements PSIMoneyReceiptService {
	
	private PSIMoneyReceiptDAO dao;
	
	public PSIMoneyReceiptDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIMoneyReceiptDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIMoneyReceipt saveOrUpdate(PSIMoneyReceipt psiMoneyReceipt) {
		return dao.saveOrUpdate(psiMoneyReceipt);
		
	}
	
	@Override
	public List<PSIMoneyReceipt> getAll() {
		return dao.getAll();
	}
	
	@Override
	public List<PSIMoneyReceipt> getAllByPatient(String patientUuid) {
		
		return dao.getAllByPatient(patientUuid);
	}
	
	@Override
	public PSIMoneyReceipt findById(int id) {
		return dao.findById(id);
	}
	
	@Override
	public PSIMoneyReceipt getAllBetweenDateAndPatient(Date start, Date end, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PSIMoneyReceipt getAllByDateAndPatient(Date date, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Boolean checkExistingMoneyReceipt(String slipNo, String date,
			String clinicCode) {
		// TODO Auto-generated method stub
		return dao.checkExistingMoneyReceipt(slipNo, date, clinicCode);
	}

	@Override
	public void delete(int id) {
		dao.delete(id);
		
	}
	
	@Override
	public PSIMoneyReceipt getAllBetweenDate(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PSIMoneyReceipt getMoneyReceiptByESlipNo(String eslipNo) {
		// TODO Auto-generated method stub
		return dao.getMoneyReceiptByESlipNo(eslipNo);
	}

	@Override
	public SHNRefundedMoneyReceipt saveOrUpdateRefund(
			SHNRefundedMoneyReceipt shnRefundedMoneyReceipt) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdateRefund(shnRefundedMoneyReceipt);
	}
}
