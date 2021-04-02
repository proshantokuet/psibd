package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;
import org.openmrs.module.PSI.api.SHNVoidedMoneyReceiptLogService;
import org.openmrs.module.PSI.api.db.SHNVoidedMoneyReceiptLogDAO;

public class SHNVoidedMoneyReceiptServiceImpl extends BaseOpenmrsService implements SHNVoidedMoneyReceiptLogService {
	
	private SHNVoidedMoneyReceiptLogDAO dao;
	
	public SHNVoidedMoneyReceiptLogDAO getDao() {
		return dao;
	}

	public void setDao(SHNVoidedMoneyReceiptLogDAO dao) {
		this.dao = dao;
	}

	@Override
	public SHNVoidedMoneyReceiptLog saveOrUpdate(SHNVoidedMoneyReceiptLog shnVoidedLog) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(shnVoidedLog);
	}

	@Override
	public List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceiptByClinic(
			int clinicId) {
		// TODO Auto-generated method stub
		return dao.getAllVoidedMoneyReceiptByClinic(clinicId);
	}

	@Override
	public List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceipt() {
		// TODO Auto-generated method stub
		return dao.getAllVoidedMoneyReceipt();
	}

	@Override
	public SHNVoidedMoneyReceiptLog getVoidedMoneyReceiptByEslipNo(String eslipNo) {
		// TODO Auto-generated method stub
		return dao.getVoidedMoneyReceiptByEslipNo(eslipNo);
	}

	

}
