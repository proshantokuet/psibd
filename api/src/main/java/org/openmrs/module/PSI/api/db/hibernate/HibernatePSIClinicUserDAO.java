/**
 * 
 */
package org.openmrs.module.PSI.api.db.hibernate;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.db.PSIClinicUserDAO;
import org.openmrs.module.PSI.dto.UserDTO;

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
	
}
