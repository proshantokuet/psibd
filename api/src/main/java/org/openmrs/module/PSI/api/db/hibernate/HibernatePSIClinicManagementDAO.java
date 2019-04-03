package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.db.PSIClinicManagementDAO;

public class HibernatePSIClinicManagementDAO implements PSIClinicManagementDAO {
	
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
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinic);
		return psiClinic;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicManagement> getAllClinic() {
		List<PSIClinicManagement> clinics = sessionFactory.getCurrentSession().createQuery("from PSIClinicManagement ")
		        .list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicManagement findById(int id) {
		// TODO Auto-generated method stub
		List<PSIClinicManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement where id = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(findById(id));
	}
	
}
