package org.openmrs.module.PSI.api.impl;

import java.util.List;

import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.PSIClinicUserService;
import org.openmrs.module.PSI.api.db.PSIClinicUserDAO;
import org.openmrs.module.PSI.dto.UserDTO;

public class PSIClinicUserServiceImpl extends BaseOpenmrsService implements PSIClinicUserService {
	
	private PSIClinicUserDAO dao;
	
	public PSIClinicUserDAO getDao() {
		return dao;
	}
	
	public void setDao(PSIClinicUserDAO dao) {
		this.dao = dao;
	}
	
	@Override
	public PSIClinicUser saveOrUpdate(PSIClinicUser psiClinicUser) {
		return dao.saveOrUpdate(psiClinicUser);
	}
	
	@Override
	public List<PSIClinicUser> getAll() {
		return dao.getAll();
	}
	
	@Override
	public PSIClinicUser findById(int id) {
		return dao.findById(id);
	}
	
	@Override
	public void delete(int id) {
		dao.delete(id);
		
	}
	
	@Override
	public PSIClinicUser findByUserName(String username) {
		// TODO Auto-generated method stub
		return dao.findByUserName(username);
	}
	
	@Override
	public UserDTO findByUserNameFromOpenmrs(String username) {
		// TODO Auto-generated method stub
		return dao.findByUserNameFromOpenmrs(username);
	}
	
}
