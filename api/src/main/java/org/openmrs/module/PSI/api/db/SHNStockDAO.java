package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.dto.SHNStockAdjustDTO;
import org.openmrs.module.PSI.dto.SHNStockDTO;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;
import org.openmrs.module.PSI.dto.SHNStockReportDTO;

public interface SHNStockDAO {
	
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



}
