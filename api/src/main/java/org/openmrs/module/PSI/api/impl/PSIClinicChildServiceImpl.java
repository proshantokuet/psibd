package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicChild;
import org.openmrs.module.PSI.api.PSIClinicChildService;
import org.openmrs.module.PSI.api.db.PSIClinicChildDAO;

public class PSIClinicChildServiceImpl extends BaseOpenmrsService implements PSIClinicChildService {
	
	private PSIClinicChildDAO dao;
	
	public PSIClinicChildDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIClinicChildDAO dao) {
		this.dao = dao;
	}

	@Override
	public PSIClinicChild saveOrUpdate(PSIClinicChild psiClinicAddChild) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(psiClinicAddChild);
	}

	@Override
	public PSIClinicChild findById(int id) {
		// TODO Auto-generated method stub
		return dao.findById(id);
	}

	@Override
	public List<PSIClinicChild> findByMotherUuid(String motherUuid) {
		// TODO Auto-generated method stub
		return dao.findByMotherUuid(motherUuid);
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}
