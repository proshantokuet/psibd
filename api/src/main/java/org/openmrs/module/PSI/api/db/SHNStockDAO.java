package org.openmrs.module.PSI.api.db;

import org.openmrs.module.PSI.SHNStock;

public interface SHNStockDAO {
	
	public SHNStock saveOrUpdate(SHNStock shnStock);
	
	public SHNStock findById(int id);

}
