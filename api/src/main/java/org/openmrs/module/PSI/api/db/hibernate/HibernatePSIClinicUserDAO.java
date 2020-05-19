/**
 * 
 */
package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.User;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.db.PSIClinicUserDAO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.PSIConstants;

/**
 * @author proshanto
 */
public class HibernatePSIClinicUserDAO implements PSIClinicUserDAO {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;
	
	/**
	 * @param sessionFactory the sessionFactory to set
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	@Override
	public PSIClinicUser saveOrUpdate(PSIClinicUser psiClinicUser) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinicUser);
		return psiClinicUser;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicUser> getAll() {
		List<PSIClinicUser> psiClinicUsers = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicUser  order by cuid desc").list();
		
		return psiClinicUsers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicUser findById(int id) {
		List<PSIClinicUser> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicUser where cuid = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void delete(int id) {
		PSIClinicUser psiClinicUser = findById(id);
		if (psiClinicUser != null) {
			sessionFactory.getCurrentSession().delete(findById(id));
		} else {
			log.error("psiClinicUser is null with id" + id);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicUser findByUserName(String username) {
		List<PSIClinicUser> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicUser where userName = :username order by cuid desc")
		        .setString("username", username).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicUser findByUserNameAndClinicCode(String username, int clinicId) {
		List<PSIClinicUser> lists = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		            "from PSIClinicUser where userName = :username and psiClinicManagementId = :code  order by cuid desc")
		        .setString("username", username).setInteger("code", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@Override
	public UserDTO findByUserNameFromOpenmrs(String username) {
		List<Object[]> data = null;
		UserDTO userDTO = new UserDTO();
		
		String sql = "SELECT user_id,username FROM openmrs.users where username= :username  limit 1";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.setString("username", username).list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			userDTO.setId(Integer.parseInt(objects[0].toString()));
			userDTO.setUsername(objects[1].toString());
			
		}
		return userDTO;
	}
	
	@Override
	public List<UserDTO> findUserByCode(String code) {
		List<Object[]> data = null;
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		String sql = "SELECT U.user_id,pclu.user_uame, role "
		        + "FROM openmrs.psi_clinic as pcl left join openmrs.psi_clinic_user pclu "
		        + "on pcl.cid = pclu.psi_clinic_management_id left join openmrs.users as U "
		        + "on pclu.user_uame = U.username left join openmrs.user_role as UL on "
		        + "U.user_id = UL.user_id where  pcl.clinic_id= :code and U.retired='0' and role in('Doctor','Counselor','Paramedic','Lab Technician','Clinic Aid','CSP','Pharmacist','Admin-Assistant','CRO','Paramedic(OPD)','Paramedic(IPD)')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.setString("code", code).list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			UserDTO userDTO = new UserDTO();
			userDTO.setId(Integer.parseInt(objects[0] + ""));
			userDTO.setUsername(objects[1] + "");
			userDTO.setUserRole(objects[1] + "-" + objects[2] + "");
			userDTO.setRole(objects[2] + "");
			users.add(userDTO);
		}
		return users;
		
	}
	
	@Override
	public List<UserDTO> findAllactiveAndInactiveUserByCode(String code) {
		List<Object[]> data = null;
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		String sql = "SELECT U.user_id,pclu.user_uame, role "
		        + "FROM openmrs.psi_clinic as pcl left join openmrs.psi_clinic_user pclu "
		        + "on pcl.cid = pclu.psi_clinic_management_id left join openmrs.users as U "
		        + "on pclu.user_uame = U.username left join openmrs.user_role as UL on "
		        + "U.user_id = UL.user_id where  pcl.clinic_id= :code and role in('Doctor','Counselor','Paramedic','Lab Technician','Clinic Aid','CSP','Pharmacist','Admin-Assistant','CRO','Paramedic(OPD)','Paramedic(IPD)')";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.setString("code", code).list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			UserDTO userDTO = new UserDTO();
			userDTO.setId(Integer.parseInt(objects[0] + ""));
			userDTO.setUsername(objects[1] + "");
			userDTO.setUserRole(objects[1] + "-" + objects[2] + "");
			userDTO.setRole(objects[2] + "");
			users.add(userDTO);
		}
		return users;
	}

	@Override
	public List<UserDTO> findUsersNotInClinic(int clinicId) {
		List<Object[]> data = null;
		List<UserDTO> users = new ArrayList<UserDTO>();
		
		String sql = "select UN.user_id,UN.username,UN.diaplay from (SELECT U.user_id,U.username ,CONCAT( username,' - ',given_name, ' ', family_name ) AS diaplay "
		        + " FROM openmrs.users as U left join openmrs.person_name as PN on U.person_id = PN.person_id) as UN left join "
		        + " openmrs.psi_clinic_user as PU on UN.username = PU.user_uame  where psi_clinic_management_id is null ";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			UserDTO userDTO = new UserDTO();
			userDTO.setId(Integer.parseInt(objects[0] + ""));
			userDTO.setUsername(objects[1] + "");
			userDTO.setFullName(objects[2] + "");
			users.add(userDTO);
		}
		return users;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UserDTO findOrgUnitFromOpenMRS(String uuid) {
		List<UserDTO> patients = new ArrayList<UserDTO>();
		String sql = "SELECT value as orgUnit FROM openmrs.person as p left join openmrs.person_attribute  as pa "
		        + " on p.person_id = pa.person_id where person_attribute_type_id = :person_attribute_type_id and "
		        + " p.uuid = :uuid ";
		patients = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("orgUnit", StandardBasicTypes.STRING)
		
		.setInteger("person_attribute_type_id", PSIConstants.attributeTypeOrgUnit).setString("uuid", uuid)
		        .setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class)).list();
		if (patients.size() != 0) {
			return patients.get(0);
		} else {
			return null;
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicUser> findByClinicId(int clinicId) {
		List<PSIClinicUser> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicUser where  psiClinicManagementId = :clinicId  order by cuid desc")
		        .setInteger("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserDTO> findUserByClinicIdWithRawQuery(int clincicId) {
		List<UserDTO> patients = new ArrayList<UserDTO>();
		String sql = "SELECT PCU.mobile,PCU.email,PCU.cuid,U.user_id as userId,U.username as userName,U.retired,P.gender,P.person_id as personId,PN.given_name as firstName,PN.family_name as lastName"
		        + " ,(select GROUP_CONCAT(role SEPARATOR ' , ')  from openmrs.user_role where user_id = U.user_id) role"
		        + " from openmrs.psi_clinic_user as PCU  left join"
		        + " openmrs.users as U on PCU.user_uame = U.username left join openmrs.person as P"
		        + " on U.person_id = P.person_id left join openmrs.person_name PN on  P.person_id =  PN.person_id"
		        + " where PCU.psi_clinic_management_id = :clincicId ";
		patients = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("mobile", StandardBasicTypes.STRING)
		        .addScalar("email", StandardBasicTypes.STRING).addScalar("cuid", StandardBasicTypes.INTEGER)
		        .addScalar("userId", StandardBasicTypes.INTEGER).addScalar("userName", StandardBasicTypes.STRING)
		        .addScalar("personId", StandardBasicTypes.INTEGER).addScalar("firstName", StandardBasicTypes.STRING)
		        .addScalar("lastName", StandardBasicTypes.STRING).addScalar("role", StandardBasicTypes.STRING)
		        .addScalar("gender", StandardBasicTypes.STRING).addScalar("retired", StandardBasicTypes.BOOLEAN)
		        
		        .setInteger("clincicId", clincicId).setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class))
		        .list();
		if (patients.size() != 0) {
			return patients;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UserDTO findUserByIdWithRawQuery(String userName) {
		List<UserDTO> user = new ArrayList<UserDTO>();
		String sql = "SELECT PCU.mobile,PCU.email,PCU.cuid,U.user_id as userId,U.username as userName,U.retired,P.gender,P.person_id as personId,PN.given_name as firstName,PN.family_name as lastName"
		        + " ,(select GROUP_CONCAT(role SEPARATOR ' , ')  from openmrs.user_role where user_id = U.user_id) role"
		        + " from openmrs.psi_clinic_user as PCU  left join"
		        + " openmrs.users as U on PCU.user_uame = U.username left join openmrs.person as P"
		        + " on U.person_id = P.person_id left join openmrs.person_name PN on  P.person_id =  PN.person_id"
		        + " where  U.username = :userName ";
		user = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("mobile", StandardBasicTypes.STRING)
		        .addScalar("email", StandardBasicTypes.STRING).addScalar("cuid", StandardBasicTypes.INTEGER)
		        .addScalar("userId", StandardBasicTypes.INTEGER).addScalar("userName", StandardBasicTypes.STRING)
		        .addScalar("personId", StandardBasicTypes.INTEGER).addScalar("firstName", StandardBasicTypes.STRING)
		        .addScalar("lastName", StandardBasicTypes.STRING).addScalar("role", StandardBasicTypes.STRING)
		        .addScalar("gender", StandardBasicTypes.STRING).addScalar("retired", StandardBasicTypes.BOOLEAN)
		        
		        .setString("userName", userName).setResultTransformer(new AliasToBeanResultTransformer(UserDTO.class))
		        .list();
		if (user.size() != 0) {
			return user.get(0);
		} else {
			return null;
		}
	}

	@Override
	public int updatePrimaryKey(int globalPrimaryKey, int localPrimaryKey) {
		// TODO Auto-generated method stub
		String sql = "update psi_clinic_user set cuid = :globalkey where cuid = :localkey";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("globalkey", globalPrimaryKey);
		query.setParameter("localkey", localPrimaryKey);
		
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public User getbyUsernameIcludedRetiure(String username) {
		Query query = sessionFactory.getCurrentSession().createQuery(
			    "from User u where (u.username = ? or u.systemId = ?)");
			query.setString(0, username);
			query.setString(1, username);
			List<User> users = query.list();
			
			if (users == null || users.isEmpty()) {
				log.warn("request for username '" + username + "' not found");
				return null;
			}
			
			return users.get(0);
	}
}
