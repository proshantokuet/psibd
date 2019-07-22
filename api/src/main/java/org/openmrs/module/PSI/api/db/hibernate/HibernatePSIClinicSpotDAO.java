/**
 * 
 */
package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIClinicSpot;
import org.openmrs.module.PSI.api.db.PSIClinicSpotDAO;

/**
 * @author proshanto
 */
public class HibernatePSIClinicSpotDAO implements PSIClinicSpotDAO {
	
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
	public PSIClinicSpot saveOrUpdate(PSIClinicSpot psiClinicSpot) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinicSpot);
		return psiClinicSpot;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicSpot> findAll() {
		List<PSIClinicSpot> psiClinicUsers = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicUser  order by ccsid desc").list();
		
		return psiClinicUsers;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicSpot findById(int id) {
		List<PSIClinicSpot> lists = sessionFactory.getCurrentSession().createQuery("from PSIClinicSpot where ccsid = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicSpot> findByClinicId(int id) {
		List<PSIClinicSpot> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicSpot where  psiClinicManagement = :clinicId ").setInteger("clinicId", id).list();
		return lists;
	}
	
	@Override
	public void delete(int id) {
		PSIClinicSpot psiClinicUser = findById(id);
		if (psiClinicUser != null) {
			sessionFactory.getCurrentSession().delete(findById(id));
		} else {
			log.error("psiClinicUser is null with id" + id);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicSpot findDuplicateSpot(int id, String code) {
		// TODO Auto-generated method stub
		List<PSIClinicSpot> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicSpot where ccsid != :id and  code = :code").setString("code", code)
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
}
