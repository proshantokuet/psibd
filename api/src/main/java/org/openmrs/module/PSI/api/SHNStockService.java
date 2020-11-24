package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.SHNStockDetails;
import org.openmrs.module.PSI.dto.SHNStockAdjustDTO;
import org.openmrs.module.PSI.dto.SHNStockDTO;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;
import org.openmrs.module.PSI.dto.SHNStockReportDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNStockService extends OpenmrsService {
	
	public SHNStock saveOrUpdate(SHNStock shnStock);
	
	public SHNStock findById(int id);
	
	public List<SHNStock> getAllStockByClinicCode(String clinincCode);
	
	public List<SHNStockDetailsDTO> getStockDetailsByStockId(int stockId);
	
	public List<SHNStockDTO> findStockByPrductIdInvoiceAndExpiryDate(int productId, String invoiceNo, String expiryDate);
	
	public String updateStockByEarliestExpiryDate(String eslipNo, String clinicCode);
	
	public SHNStockAdjust saveOrUpdateStockAdjust(SHNStockAdjust shnStockAdjust);
	
	public SHNStockAdjust findAdjustById(int adjustId);

	public SHNStockAdjustDTO getAdjustHistoryById(int adjustId,int clinicId);
	
	public List<SHNStockAdjustDTO> getAdjustHistoryAllByClinic(int clinicId);
	
	public String adjustStockByEarliestExpiryDate(int quantity, int clinicId, int productId);
	
	public List<SHNStockReportDTO> getStockReportByClinic(String clinicCode, String category, int month, int year);
	
	public String deleteMoneyReceiptStockUpdate(String eslipNo, String clinicCode);
	
	public String stockUpdateAfterRefund(String eslipNo, String clinicCode,int clinicId);
	
	public List<SHNStock> getAllStockByClinicIdForSync(int clinicId);
	
	public List<SHNStockAdjust> getAllAdjustHistoryForSync(int clinicId);
	
	public SHNStock findStockByUuidAndClinicId(String uuid, int clinicId);
	
	public SHNStockDetails findStockDetailsByUuid(String uuid);

	public SHNStockAdjust findAdjustByUuidAndCLinicId(String uuid, int clinicId);

}
