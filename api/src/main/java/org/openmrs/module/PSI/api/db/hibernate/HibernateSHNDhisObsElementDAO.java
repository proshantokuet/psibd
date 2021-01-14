package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.HnqisToShnConfigMapping;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;
import org.openmrs.module.PSI.api.db.SHNDhisObsElementDAO;
import org.springframework.util.StringUtils;

class HibernateSHNDhisObsElementDAO implements SHNDhisObsElementDAO {

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

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNDhisObsElement> getAllDhisElement(String formName) {
		// TODO Auto-generated method stub
		List<SHNDhisObsElement> lists = sessionFactory.getCurrentSession()
				.createQuery("from SHNDhisObsElement where voided = 0 and formsName = :formname")
				.setString("formname", formName).list();
        return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNDhisMultipleChoiceObsElement> getAllMultipleChoiceDhisElement(String formName) {
		// TODO Auto-generated method stub
		List<SHNDhisMultipleChoiceObsElement> lists = sessionFactory.getCurrentSession()
				.createQuery("from SHNDhisMultipleChoiceObsElement where voided = 0 and formsName = :formname")
				.setString("formname", formName).list();
		return lists;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HnqisToShnConfigMapping> getAllConfigMappingData() {
		// TODO Auto-generated method stub
		List<HnqisToShnConfigMapping> lists = sessionFactory.getCurrentSession()
				.createQuery("from HnqisToShnConfigMapping where voided = 0").list();
        return lists;
	}

	@Override
	public String getAncCountForGovtDHis2(int month, int year) {
		// TODO Auto-generated method stub
		String sqlString = "";
		sqlString = "SELECT calculateAncForGovtDhis2("+ month +","+year+")";
		try {
			log.error(sqlString);
			String ancCountForDhis2 = sessionFactory.getCurrentSession().createSQLQuery(sqlString).list().get(0).toString();
			return ancCountForDhis2;
			
		} catch (Exception e) {
			return null;
		}
	}
	
}
