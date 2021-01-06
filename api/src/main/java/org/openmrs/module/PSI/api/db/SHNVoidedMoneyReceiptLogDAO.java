package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;

public interface SHNVoidedMoneyReceiptLogDAO {
	
	public SHNVoidedMoneyReceiptLog saveOrUpdate(SHNVoidedMoneyReceiptLog shnVoidedLog);
	
	List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceiptByClinic(int clinicId);
	
	List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceipt();



}
