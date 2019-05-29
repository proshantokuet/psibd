package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.api.db.PSIServiceManagementDAO;

public class HibernateServiceManagementDAO implements PSIServiceManagementDAO {
	
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
	public PSIServiceManagement saveOrUpdate(PSIServiceManagement psiServiceManagement) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(psiServiceManagement);
		return psiServiceManagement;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceManagement> getAll(int clinicId) {
		List<PSIServiceManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where psiClinicManagement=:clinicId  order by name asc ")
		        .setInteger("clinicId", clinicId).list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceManagement findById(int id) {
		
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where sid = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public float getUnitCostByName(String name) {
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where name = :name").setString("name", name).list();
		if (lists.size() != 0) {
			return lists.get(0).getUnitCost();
		} else {
			return -1;
		}
	}
	
	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(findById(id));
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceManagement findByCode(String code, int clinicId) {
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where code = :code and  psiClinicManagement=:clinicId ")
		        .setString("code", code).setInteger("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceManagement findByIdNotByCode(int id, String code, int clinicId) {
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where sid !=:id and  psiClinicManagement=:clinicId and code=:code")
		        .setString("code", code).setInteger("id", id).setInteger("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceManagement> getAll() {
		List<PSIServiceManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement  order by sid desc").list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
}
