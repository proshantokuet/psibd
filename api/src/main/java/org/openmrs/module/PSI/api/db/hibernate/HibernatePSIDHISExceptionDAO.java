package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.db.PSIDHISExceptionDAO;
import org.openmrs.module.PSI.utils.DHISMapper;

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
	public List<PSIDHISException> findAllByStatus(int status) {
		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIDHISException where (status = :id0 OR status = :id3)").setInteger("id0", status)
		        .setInteger("id3", DHISMapper.CONNECTIONTIMEOUTSTATUS).list();
		
		return lists;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIDHISException findAllById(int patientId) {
		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIDHISException where markId = :id").setInteger("id", patientId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
}
