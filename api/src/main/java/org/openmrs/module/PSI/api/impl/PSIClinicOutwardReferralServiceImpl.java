package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicOutwardReferral;
import org.openmrs.module.PSI.api.PSIClinicOutwardReferralService;
import org.openmrs.module.PSI.api.db.PSIClinicOutwardReferralDAO;

public class PSIClinicOutwardReferralServiceImpl extends BaseOpenmrsService implements PSIClinicOutwardReferralService {
	
	private PSIClinicOutwardReferralDAO dao;

	public PSIClinicOutwardReferralDAO getDao() {
		return dao;
	}

	public void setDao(PSIClinicOutwardReferralDAO dao) {
		this.dao = dao;
	}

	@Override
	public PSIClinicOutwardReferral saveOrUpdate(
			PSIClinicOutwardReferral psiClinicOutwardReferral) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psiClinicOutwardReferral);
	}

	@Override
	public PSIClinicOutwardReferral findByOutwardReferralId(int id) {
		// TODO Auto-generated method stub
		return dao.findByOutwardReferralId(id);
	}

	@Override
	public List<PSIClinicOutwardReferral> findByPatientUuid(String patientUuid) {
		// TODO Auto-generated method stub
		return dao.findByPatientUuid(patientUuid);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		dao.delete(id);
		
	}
	

}
