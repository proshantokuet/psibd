package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;

public interface SHNStockDAO {
	
	public SHNStock saveOrUpdate(SHNStock shnStock);
	
	public SHNStock findById(int id);
	
	public List<SHNStock> getAllStockByClinicCode(String clinincCode);
	
	public List<SHNStockDetailsDTO> getStockDetailsByStockId(int stockId);


}
