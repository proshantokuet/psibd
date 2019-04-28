/**
 * 
 */
package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.db.PSIClinicUserDAO;

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
		List<PSIClinicUser> psiClinicUsers = sessionFactory.getCurrentSession().createQuery("from PSIClinicUser ").list();
		
		return psiClinicUsers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicUser findById(int id) {
		List<PSIClinicUser> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicUser where id = :id")
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
	
}
