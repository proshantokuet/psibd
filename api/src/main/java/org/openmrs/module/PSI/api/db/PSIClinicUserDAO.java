package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.User;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.dto.UserDTO;

public interface PSIClinicUserDAO {
	
	public PSIClinicUser saveOrUpdate(PSIClinicUser psiClinicUser);
	
	public List<PSIClinicUser> getAll();
	
	public PSIClinicUser findById(int id);
	
	public PSIClinicUser findByUserName(String username);
	
	public PSIClinicUser findByUserNameAndClinicCode(String username, int clinicId);
	
	public UserDTO findByUserNameFromOpenmrs(String username);
	
	public List<UserDTO> findUserByCode(String code);
	
	public List<UserDTO> findAllactiveAndInactiveUserByCode(String code);
	
	public List<UserDTO> findUsersNotInClinic(int clinicId);
	
	public UserDTO findOrgUnitFromOpenMRS(String uuid);
	
	public List<PSIClinicUser> findByClinicId(int clinicId);
	
	public void delete(int id);
	
	public List<UserDTO> findUserByClinicIdWithRawQuery(int clincicId);
	
	public UserDTO findUserByIdWithRawQuery(String userName);
	
	public int updatePrimaryKey (int globalPrimaryKey, int localPrimaryKey);
	
	public User getbyUsernameIcludedRetiure (String username);


}
