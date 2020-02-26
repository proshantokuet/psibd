package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.AUHCDhisErrorVisualize;
import org.openmrs.module.PSI.api.AUHCDhisErrorVisualizeService;
import org.openmrs.module.PSI.api.db.AUHCDhisErrorVisualizeDAO;

public class AUHCDhisErrorVisualizeServiceImpl extends BaseOpenmrsService  implements AUHCDhisErrorVisualizeService {

	protected final Log log = LogFactory.getLog(getClass());
	
	private AUHCDhisErrorVisualizeDAO dao;
	
	public AUHCDhisErrorVisualizeDAO getDao() {
		return dao;
	}

	public void setDao(AUHCDhisErrorVisualizeDAO dao) {
		this.dao = dao;
	}

	@Override
	public String getPatientToDhisSyncInformation(String status, String clinicCode) {
		// TODO Auto-generated method stub
		return dao.getPatientToDhisSyncInformation(status, clinicCode);
	}

	@Override
	public String getMoneyReceiptToDhisSyncInformation(String status, String clinicCode) {
		// TODO Auto-generated method stub
		return dao.getMoneyReceiptToDhisSyncInformation(status, clinicCode);
	}

	@Override
	public List<AUHCDhisErrorVisualize> getPatientDhisSyncReport(
			AUHCDhisErrorVisualize auhcDhisErrorVisualize) {
		// TODO Auto-generated method stub
		return dao.getPatientDhisSyncReport(auhcDhisErrorVisualize);
	}

	@Override
	public List<AUHCDhisErrorVisualize> getMoneyReceiptDhisSyncReport(
			AUHCDhisErrorVisualize auhcDhisErrorVisualize) {
		// TODO Auto-generated method stub
		return dao.getMoneyReceiptDhisSyncReport(auhcDhisErrorVisualize);
	}

}
