package org.openmrs.module.PSI.api.db.hibernate;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.api.db.PSIMoneyReceiptDAO;

public class HibernatePSIMoneyReceiptDAO implements PSIMoneyReceiptDAO {
	
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
	public PSIMoneyReceipt saveOrUpdate(PSIMoneyReceipt psiMoneyReceipt) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiMoneyReceipt);
		return psiMoneyReceipt;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIMoneyReceipt> getAll() {
		List<PSIMoneyReceipt> psiMoneyReceipts = sessionFactory.getCurrentSession().createQuery("from PSIMoneyReceipt ")
		        .list();
		if (psiMoneyReceipts.size() != 0) {
			return psiMoneyReceipts;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIMoneyReceipt> getAllByPatient(String patientUuid) {
		List<PSIMoneyReceipt> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIMoneyReceipt where patientUuid = :id").setString("id", patientUuid).list();
		if (lists.size() != 0) {
			return lists;
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIMoneyReceipt findById(int id) {
		// TODO Auto-generated method stub
		List<PSIMoneyReceipt> lists = sessionFactory.getCurrentSession().createQuery("from PSIMoneyReceipt where mid = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public PSIMoneyReceipt getAllBetweenDateAndPatient(Date start, Date end, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PSIMoneyReceipt getAllByDateAndPatient(Date date, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(findById(id));
		
	}
	
	@Override
	public PSIMoneyReceipt getAllBetweenDate(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
