package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.SHNDhisEncounterException;
import org.openmrs.module.PSI.api.db.SHNDhisEncounterExceptionDAO;
import org.openmrs.module.PSI.utils.PSIConstants;

public class HibernateSHNDhisEncounterExceptionDAO implements SHNDhisEncounterExceptionDAO {
	
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
	public SHNDhisEncounterException saveOrUpdate(
			SHNDhisEncounterException dhisEncounterException) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(dhisEncounterException);
		return dhisEncounterException;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNDhisEncounterException> findAllFailedEncounterByStatus(
			int status) {
		List<SHNDhisEncounterException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from SHNDhisEncounterException where (status = :id0 OR status = :id3) order by expId asc").setInteger("id0", status)
		        .setInteger("id3", PSIConstants.CONNECTIONTIMEOUTSTATUS).list();
		
		return lists;
	}

	@Override
	public SHNDhisEncounterException findAllById(int markerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNDhisEncounterException findAllBymarkerIdAndFormName(int markerId,
			String formsName,String encounterUuid) {
		List<SHNDhisEncounterException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from SHNDhisEncounterException where encounterId = :id and formsName = :formname").setString("id", encounterUuid).setString("formname", formsName).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SHNDhisEncounterException findEncByFormAndEncId(String encounterId, String formsName) {
		List<SHNDhisEncounterException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from SHNDhisEncounterException where encounterId = :encid and formsName = :formname and status = 1").setString("encid", encounterId).setString("formname", formsName).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	

}
