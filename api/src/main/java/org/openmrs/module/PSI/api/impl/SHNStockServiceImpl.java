package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.api.db.SHNStockDAO;
import org.openmrs.module.PSI.dto.SHNStockAdjustDTO;
import org.openmrs.module.PSI.dto.SHNStockDTO;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;
import org.openmrs.module.PSI.dto.SHNStockReportDTO;

public class SHNStockServiceImpl extends BaseOpenmrsService implements SHNStockService {
	
	private SHNStockDAO dao;
	
	public SHNStockDAO getDao() {
		return dao;
	}

	public void setDao(SHNStockDAO dao) {
		this.dao = dao;
	}

	@Override
	public SHNStock saveOrUpdate(SHNStock shnStock) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(shnStock);
	}

	@Override
	public SHNStock findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public List<SHNStock> getAllStockByClinicCode(String clinincCode) {
		// TODO Auto-generated method stub
		return dao.getAllStockByClinicCode(clinincCode);
	}

	@Override
	public List<SHNStockDetailsDTO> getStockDetailsByStockId(int stockId) {
		// TODO Auto-generated method stub
		return dao.getStockDetailsByStockId(stockId);
	}

	@Override
	public List<SHNStockDTO> findStockByPrductIdInvoiceAndExpiryDate(int productId,
			String invoiceNo, String expiryDate) {
		// TODO Auto-generated method stub
		return dao.findStockByPrductIdInvoiceAndExpiryDate(productId, invoiceNo, expiryDate);
	}

	@Override
	public String updateStockByEarliestExpiryDate(String eslipNo,String clinicCode) {
		// TODO Auto-generated method stub
		return dao.updateStockByEarliestExpiryDate(eslipNo, clinicCode);
	}

	@Override
	public SHNStockAdjust saveOrUpdateStockAdjust(SHNStockAdjust shnStockAdjust) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdateStockAdjust(shnStockAdjust);
	}

	@Override
	public SHNStockAdjustDTO getAdjustHistoryById(int adjustId,int clinicId) {
		// TODO Auto-generated method stub
		return dao.getAdjustHistoryById(adjustId,clinicId);
	}

	@Override
	public List<SHNStockAdjustDTO> getAdjustHistoryAllByClinic(int clinicId) {
		// TODO Auto-generated method stub
		return dao.getAdjustHistoryAllByClinic(clinicId);
	}

	@Override
	public String adjustStockByEarliestExpiryDate(int quantity,
			String clinicCode, int productId) {
		// TODO Auto-generated method stub
		return dao.adjustStockByEarliestExpiryDate(quantity, clinicCode, productId);
	}

	@Override
	public SHNStockAdjust findAdjustById(int adjustId) {
		// TODO Auto-generated method stub
		return dao.findAdjustById(adjustId);
	}

	@Override
	public List<SHNStockReportDTO> getStockReportByClinic(String clinicCode,
			String category, int month, int year) {
		// TODO Auto-generated method stub
		return dao.getStockReportByClinic(clinicCode, category, month, year);
	}

	@Override
	public String deleteMoneyReceiptStockUpdate(String eslipNo,
			String clinicCode) {
		// TODO Auto-generated method stub
		return dao.deleteMoneyReceiptStockUpdate(eslipNo, clinicCode);
	}

}
