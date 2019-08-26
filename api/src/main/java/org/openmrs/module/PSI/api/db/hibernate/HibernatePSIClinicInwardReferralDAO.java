package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicInwardReferral;
import org.openmrs.module.PSI.api.db.PSIClinicInwardReferralDAO;

public class HibernatePSIClinicInwardReferralDAO implements PSIClinicInwardReferralDAO {
	
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
	public PSIClinicInwardReferral saveOrUpdate(PSIClinicInwardReferral psiClinicInwardReferral) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinicInwardReferral);
		return psiClinicInwardReferral;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicInwardReferral findByReferralNo(String referralNo) {
		List<PSIClinicInwardReferral> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicInwardReferral where referralNo = :refNo")
		        .setString("refNo", referralNo).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	} 
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicInwardReferral> findByPatientUuid(String patientUuid) {
		List<PSIClinicInwardReferral> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicInwardReferral where patientUuid = :pId ").setString("pId", patientUuid).list();
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicInwardReferral findById(int id) {
		List<PSIClinicInwardReferral> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicInwardReferral where inwardReferralId = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void delete(int id) {
		PSIClinicInwardReferral psiClinicInwardReferral = findById(id);
		if (psiClinicInwardReferral != null) {
			sessionFactory.getCurrentSession().delete(findById(id));
		} else {
			log.error("psiClinicInwardReferral is null with id" + id);
		}
		
	}

}
