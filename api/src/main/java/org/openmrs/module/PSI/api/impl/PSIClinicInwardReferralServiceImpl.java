package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicInwardReferral;
import org.openmrs.module.PSI.api.PSIClinicInwardReferralService;
import org.openmrs.module.PSI.api.db.PSIClinicInwardReferralDAO;

public class PSIClinicInwardReferralServiceImpl extends BaseOpenmrsService implements PSIClinicInwardReferralService  {
	
	private PSIClinicInwardReferralDAO dao;

	public PSIClinicInwardReferralDAO getDao() {
		return dao;
	}

	public void setDao(PSIClinicInwardReferralDAO dao) {
		this.dao = dao;
	}

	@Override
	public PSIClinicInwardReferral saveOrUpdate(
			PSIClinicInwardReferral psiClinicInwardReferral) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psiClinicInwardReferral);
	}

	@Override
	public PSIClinicInwardReferral findByReferralNo(String referralNo) {
		// TODO Auto-generated method stub
		return dao.findByReferralNo(referralNo);
	}

	@Override
	public PSIClinicInwardReferral findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public List<PSIClinicInwardReferral> findByPatientUuid(String patientUuid) {
		// TODO Auto-generated method stub
		return dao.findByPatientUuid(patientUuid);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		dao.delete(id);
		
	}

}
