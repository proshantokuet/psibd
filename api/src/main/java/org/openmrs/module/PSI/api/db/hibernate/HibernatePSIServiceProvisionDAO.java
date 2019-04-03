package org.openmrs.module.PSI.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.db.PSIServiceProvisionDAO;

public class HibernatePSIServiceProvisionDAO implements PSIServiceProvisionDAO {
	
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
	public PSIServiceProvision saveOrUpdate(PSIServiceProvision psiServiceProvision) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiServiceProvision);
		return psiServiceProvision;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> getAll() {
		List<PSIServiceProvision> psiServiceProvisions = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision ").list();
		if (psiServiceProvisions.size() != 0) {
			return psiServiceProvisions;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> getAllByPatient(String patientUuid) {
		List<PSIServiceProvision> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision where patientUuid = :id").setString("id", patientUuid).list();
		if (lists.size() != 0) {
			return lists;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceProvision findById(int id) {
		List<PSIServiceProvision> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision where spid = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<PSIServiceProvision> getAllBetweenDateAndPatient(Date start, Date end, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PSIServiceProvision> getAllByDateAndPatient(Date date, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(findById(id));
		
	}
	
}
