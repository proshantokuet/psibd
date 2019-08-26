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
	
	@Override
	public List<UserDTO> findUserByCode(String code) {
		// TODO Auto-generated method stub
		return dao.findUserByCode(code);
	}
	
	@Override
	public PSIClinicUser findByUserNameAndClinicCode(String username, int clinicId) {
		// TODO Auto-generated method stub
		return dao.findByUserNameAndClinicCode(username, clinicId);
	}
	
	@Override
	public List<UserDTO> findUsersNotInClinic(int clinicId) {
		// TODO Auto-generated method stub
		return dao.findUsersNotInClinic(clinicId);
	}
	
	@Override
	public UserDTO findOrgUnitFromOpenMRS(String uuid) {
		// TODO Auto-generated method stub
		return dao.findOrgUnitFromOpenMRS(uuid);
	}
	
	@Override
	public List<PSIClinicUser> findByClinicId(int clinicId) {
		// TODO Auto-generated method stub
		return dao.findByClinicId(clinicId);
	}
	
	@Override
	public List<UserDTO> findUserByClinicIdWithRawQuery(int clincicId) {
		// TODO Auto-generated method stub
		return dao.findUserByClinicIdWithRawQuery(clincicId);
	}
	
	@Override
	public UserDTO findUserByIdWithRawQuery(String userName) {
		// TODO Auto-generated method stub
		return dao.findUserByIdWithRawQuery(userName);
	}
	
}
