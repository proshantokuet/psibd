package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;
import org.openmrs.module.PSI.api.SHNDhisObsElementService;
import org.openmrs.module.PSI.api.db.SHNDhisObsElementDAO;

public class SHNDhisObsElementServiceImpl extends BaseOpenmrsService implements SHNDhisObsElementService {

	private SHNDhisObsElementDAO dao;
	
	public SHNDhisObsElementDAO getDao() {
		return dao;
	}

	public void setDao(SHNDhisObsElementDAO dao) {
		this.dao = dao;
	}

	@Override
	public List<SHNDhisObsElement> getAllDhisElement(String formName) {
		// TODO Auto-generated method stub
		return dao.getAllDhisElement(formName);
	}

	@Override
	public List<SHNDhisMultipleChoiceObsElement> getAllMultipleChoiceDhisElement(String formName) {
		// TODO Auto-generated method stub
		return dao.getAllMultipleChoiceDhisElement(formName);
	}

}
