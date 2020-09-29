package org.openmrs.module.PSI.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.api.db.SHNStockDAO;

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

}
