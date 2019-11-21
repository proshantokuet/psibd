package org.openmrs.module.PSI.api.impl;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.api.PSIUniquePatientService;
import org.openmrs.module.PSI.api.db.PSIUniquePatientDAO;

public class PSIUniquePatientServiceImpl extends BaseOpenmrsService implements PSIUniquePatientService {
	
	private PSIUniquePatientDAO dao;
	
	public PSIUniquePatientDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIUniquePatientDAO dao) {
		this.dao = dao;
	}
	@Override
	public Boolean findPatientByUicandMobileNo(String patientUic,
			String mobileNo) {
		// TODO Auto-generated method stub
		return dao.findPatientByUicandMobileNo(patientUic, mobileNo);
	}

}
