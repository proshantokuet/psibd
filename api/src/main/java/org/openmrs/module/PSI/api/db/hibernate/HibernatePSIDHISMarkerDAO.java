package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.db.PSIDHISMarkerDAO;

public class HibernatePSIDHISMarkerDAO implements PSIDHISMarkerDAO {
	
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
	public PSIDHISMarker saveOrUpdate(PSIDHISMarker psidhisMarker) {
		sessionFactory.getCurrentSession().saveOrUpdate(psidhisMarker);
		return psidhisMarker;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIDHISMarker findByType(String type) {
		List<PSIDHISMarker> lists = sessionFactory.getCurrentSession().createQuery("from PSIDHISMarker where type = :type")
		        .setString("type", type).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
}
