package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.db.PSIDHISExceptionDAO;

public class HibernatePSIDHISExceptionDAO implements PSIDHISExceptionDAO {
	
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
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisException) {
		sessionFactory.getCurrentSession().saveOrUpdate(psidhisException);
		return psidhisException;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIDHISException findByType(String type) {
		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIDHISException where type = :type").setString("type", type).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
}
