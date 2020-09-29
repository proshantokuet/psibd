package org.openmrs.module.PSI.api;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNStock;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNStockService extends OpenmrsService {
	
	public SHNStock saveOrUpdate(SHNStock shnStock);
	
	public SHNStock findById(int id);
	
}
