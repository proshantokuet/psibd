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
		
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinic);
		
		return psiClinic;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicManagement> getAllClinic() {
		List<PSIClinicManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement  order by cid desc").list();
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
		        .createQuery("from PSIClinicManagement where cid = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@Override
	public void delete(int id) {
		PSIClinicManagement psiClinicManagement = findById(id);
		if (psiClinicManagement != null) {
			sessionFactory.getCurrentSession().delete(psiClinicManagement);
		} else {
			log.error("psiClinicManagement is null with id" + id);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicManagement findByClinicId(String clinicId) {
		// TODO Auto-generated method stub
		List<PSIClinicManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement where clinicId = :clinicId").setString("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicManagement findByIdNotByClinicId(int id, String clinicId) {
		// TODO Auto-generated method stub
		List<PSIClinicManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement where cid !=:id and  clinicId = :clinicId")
		        .setString("clinicId", clinicId).setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
}
