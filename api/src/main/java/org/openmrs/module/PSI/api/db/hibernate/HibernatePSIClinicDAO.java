package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinic;
import org.openmrs.module.PSI.api.db.PSIClinicDAO;

public class HibernatePSIClinicDAO implements PSIClinicDAO {
	
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
	public PSIClinic saveOrUpdateClinic(PSIClinic psiClinic) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinic);
		return psiClinic;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinic> getAllClinic() {
		List<PSIClinic> clinics = sessionFactory.getCurrentSession().createQuery("from PSIClinic ").list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinic findById(int id) {
		// TODO Auto-generated method stub
		List<PSIClinic> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinic where id = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@Override
	public void delete(PSIClinic psiClinic) {
		sessionFactory.getCurrentSession().delete(psiClinic);
	}
	
}
