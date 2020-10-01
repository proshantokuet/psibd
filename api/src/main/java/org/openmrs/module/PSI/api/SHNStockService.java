package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNStockService extends OpenmrsService {
	
	public SHNStock saveOrUpdate(SHNStock shnStock);
	
	public SHNStock findById(int id);
	
	public List<SHNStock> getAllStockByClinicCode(String clinincCode);
	
	public List<SHNStockDetailsDTO> getStockDetailsByStockId(int stockId);
	
}
