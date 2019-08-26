package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicOutwardReferral;
import org.openmrs.module.PSI.api.db.PSIClinicOutwardReferralDAO;

public class HibernatePSIClinicOutwardReferralDAO implements PSIClinicOutwardReferralDAO {
	
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
	public PSIClinicOutwardReferral saveOrUpdate(PSIClinicOutwardReferral psiClinicOutwardReferral) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinicOutwardReferral);
		return psiClinicOutwardReferral;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicOutwardReferral findByOutwardReferralId(int id) {
		List<PSIClinicOutwardReferral> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicOutwardReferral where outwardReferralId = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicOutwardReferral> findByPatientUuid(String patientUuid) {
		List<PSIClinicOutwardReferral> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicOutwardReferral where patientUuid = :pId ").setString("pId", patientUuid).list();
		return lists;
	}
	
	@Override
	public void delete(int id) {
		PSIClinicOutwardReferral psiClinicOutwardReferral = findByOutwardReferralId(id);
		if (psiClinicOutwardReferral != null) {
			sessionFactory.getCurrentSession().delete(findByOutwardReferralId(id));
		} else {
			log.error("psiClinicOutwardReferral is null with id" + id);
		}
		
	}
	
}
