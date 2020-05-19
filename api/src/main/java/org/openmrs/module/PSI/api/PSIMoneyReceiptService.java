package org.openmrs.module.PSI.api;

import java.util.Date;
import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIMoneyReceiptService extends OpenmrsService {
	
	public PSIMoneyReceipt saveOrUpdate(PSIMoneyReceipt psiMoneyReceipt);
	
	public List<PSIMoneyReceipt> getAll();
	
	public List<PSIMoneyReceipt> getAllByPatient(String patientUuid);
	
	public PSIMoneyReceipt findById(int id);
	
	public PSIMoneyReceipt getAllBetweenDate(Date start, Date end);
	
	public PSIMoneyReceipt getAllBetweenDateAndPatient(Date start, Date end, String patientUuid);
	
	public PSIMoneyReceipt getAllByDateAndPatient(Date date, String patientUuid);
	
	public void delete(int id);
	
	public Boolean checkExistingMoneyReceipt(String slipNo, String date, String clinicCode);
	
	public PSIMoneyReceipt getMoneyReceiptByESlipNo(String eslipNo);
	
}
