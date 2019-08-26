package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicChild;
import org.openmrs.module.PSI.api.db.PSIClinicChildDAO;

public class HibernatePSIClinicChildDAO implements PSIClinicChildDAO {

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
	public PSIClinicChild saveOrUpdate(PSIClinicChild psiClinicAddChild) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinicAddChild);
		return psiClinicAddChild;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicChild findById(int id) {
		List<PSIClinicChild> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicChild where childId = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicChild> findByMotherUuid(String motherUuid) {
		List<PSIClinicChild> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicChild where  motherUuid = :motherId ").setString("motherId", motherUuid).list();
		return lists;
	}
	
	@Override
	public void delete(int id) {
		PSIClinicChild psiClinicAddChild = findById(id);
		if (psiClinicAddChild != null) {
			sessionFactory.getCurrentSession().delete(findById(id));
		} else {
			log.error("psiClinicChild is null with id" + id);
		}
		
	}
}
