package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
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
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<String> rawQuery(int id) {
		List<Object[]> data = null;
		List<String> url = new ArrayList<String>();
		
		String sql = "SELECT * FROM openmrs.event_records";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		data = query.list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			url.add(objects[5].toString());
		}
		return url;
	}
}
