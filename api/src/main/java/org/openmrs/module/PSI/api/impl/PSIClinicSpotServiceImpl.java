package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.api.PSIClinicSpotService;
import org.openmrs.module.PSI.api.db.PSIClinicSpotDAO;

public class PSIClinicSpotServiceImpl extends BaseOpenmrsService implements PSIClinicSpotService {
	
	private PSIClinicSpotDAO dao;
	
	public PSIClinicSpotDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIClinicSpotDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIClinicSpot saveOrUpdate(PSIClinicSpot psiClinicSpot) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psiClinicSpot);
	}
	
	@Override
	public List<PSIClinicSpot> findAll() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}
	
	@Override
	public PSIClinicSpot findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}
	
	@Override
	public List<PSIClinicSpot> findByClinicId(int id) {
		// TODO Auto-generated method stub
		return dao.findByClinicId(id);
	}
	
	@Override
	public void delete(int id) {
		dao.delete(id);
	}
	
	@Override
	public PSIClinicSpot findDuplicateSpot(int id, String code, int clinicCode) {
		// TODO Auto-generated method stub
		return dao.findDuplicateSpot(id, code, clinicCode);
	}
	
}
