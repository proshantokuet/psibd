package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.SHNDhisIndicatorDetails;
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

	@Override
	public int calculateCountOfFpConraceptiveMethod() {
		// TODO Auto-generated method stub
		return dao.calculateCountOfFpConraceptiveMethod();
	}

	@Override
	public int calculateCountOfFphypertensionAndDiabetic() {
		// TODO Auto-generated method stub
		return dao.calculateCountOfFphypertensionAndDiabetic();
	}

	@Override
	public int calculateCountOfFpPermanentMethod() {
		// TODO Auto-generated method stub
		return dao.calculateCountOfFpPermanentMethod();
	}

	@Override
	public int calculateCountOfFpAncTakenAtleastOne() {
		// TODO Auto-generated method stub
		return dao.calculateCountOfFpAncTakenAtleastOne();
	}

	@Override
	public int calculatePercentageOfFp() {
		// TODO Auto-generated method stub
		return dao.calculatePercentageOfFp();
	}

	@Override
	public int getCompletedAncFullCountFromMoneyReceipt() {
		// TODO Auto-generated method stub
		return dao.getCompletedAncFullCountFromMoneyReceipt();
	}

	@Override
	public SHNDhisIndicatorDetails saveOrupdate(
			SHNDhisIndicatorDetails shnDhisIndicatorDetails) {
		// TODO Auto-generated method stub
		return dao.saveOrupdate(shnDhisIndicatorDetails);
	}

	@Override
	public SHNDhisIndicatorDetails getDhisIndicatorByType(String indicatorType) {
		// TODO Auto-generated method stub
		return dao.getDhisIndicatorByType(indicatorType);
	}

}
