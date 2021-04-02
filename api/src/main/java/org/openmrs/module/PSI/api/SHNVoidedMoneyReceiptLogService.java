package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNVoidedMoneyReceiptLogService extends OpenmrsService {

	public SHNVoidedMoneyReceiptLog saveOrUpdate(SHNVoidedMoneyReceiptLog shnVoidedLog);
	
	List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceiptByClinic(int clinicId);
	
	List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceipt();
	
	public SHNVoidedMoneyReceiptLog getVoidedMoneyReceiptByEslipNo(String eslipNo);
	
}
