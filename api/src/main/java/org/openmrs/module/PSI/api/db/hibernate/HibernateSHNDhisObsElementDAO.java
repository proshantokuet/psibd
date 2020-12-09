package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.SHNDhisIndicatorDetails;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;
import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.api.db.SHNDhisObsElementDAO;

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
	public int calculateCountOfFpConraceptiveMethod() {
		String sql = "select calculateCountOfFpConraceptiveMethod()";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.list();

		return data.get(0);
	}

	@Override
	public int calculateCountOfFphypertensionAndDiabetic() {
		// TODO Auto-generated method stub
		String sql = "select calculateCountOfFphypertensionAndDiabetic()";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.list();

		return data.get(0);
	}

	@Override
	public int calculateCountOfFpPermanentMethod() {
		// TODO Auto-generated method stub
		String sql = "select calculateCountOfFpPermanentMethod()";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.list();

		return data.get(0);
	}

	@Override
	public int calculateCountOfFpAncTakenAtleastOne() {
		// TODO Auto-generated method stub
		String sql = "select calculateCountOfFpAncTakenAtleastOne()";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.list();

		return data.get(0);
	}

	@Override
	public int calculatePercentageOfFp() {
		// TODO Auto-generated method stub
		String sql = "select calculatePercentageOfFp()";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.list();

		return data.get(0);
	}

	@Override
	public int getCompletedAncFullCountFromMoneyReceipt() {
		// TODO Auto-generated method stub
		String sql = "select calculateFpfourAncCompletionCount()";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.list();

		return data.get(0);
	}

	@Override
	public SHNDhisIndicatorDetails saveOrupdate(
			SHNDhisIndicatorDetails shnDhisIndicatorDetails) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnDhisIndicatorDetails);
		return shnDhisIndicatorDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNDhisIndicatorDetails getDhisIndicatorByType(String indicatorType) {
		// TODO Auto-generated method stub
		List<SHNDhisIndicatorDetails> lists = sessionFactory.getCurrentSession().createQuery("from SHNDhisIndicatorDetails where indicatorType = :id")
		        .setString("id", indicatorType).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
}
