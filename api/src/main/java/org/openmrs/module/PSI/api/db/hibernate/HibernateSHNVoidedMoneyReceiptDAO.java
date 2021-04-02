package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNVoidedMoneyReceiptLog;
import org.openmrs.module.PSI.api.db.SHNVoidedMoneyReceiptLogDAO;

public class HibernateSHNVoidedMoneyReceiptDAO implements SHNVoidedMoneyReceiptLogDAO {

	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	
	@Override
	public SHNVoidedMoneyReceiptLog saveOrUpdate(SHNVoidedMoneyReceiptLog shnVoidedLog) {
		sessionFactory.getCurrentSession().saveOrUpdate(shnVoidedLog);
		return shnVoidedLog;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceiptByClinic(int clinicId) {
		List<SHNVoidedMoneyReceiptLog> lists = sessionFactory.getCurrentSession().createQuery("from SHNVoidedMoneyReceiptLog where clinicId = :id and isDeleteFromLocal = 0")
		        .setInteger("id", clinicId).list();
		return lists;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceipt() {
		// TODO Auto-generated method stub
		List<SHNVoidedMoneyReceiptLog> lists = sessionFactory.getCurrentSession().createQuery("from SHNVoidedMoneyReceiptLog where voided = 0")
		        .list();
		return lists;
	}


	@SuppressWarnings("unchecked")
	@Override
	public SHNVoidedMoneyReceiptLog getVoidedMoneyReceiptByEslipNo(String eslipNo) {
		// TODO Auto-generated method stub
		List<SHNVoidedMoneyReceiptLog> lists = sessionFactory.getCurrentSession().createQuery("from SHNVoidedMoneyReceiptLog where eSlipNo = :id")
		        .setString("id", eslipNo).list();
		if(lists.size() > 0) {
			return lists.get(0);
		}
		else {
			return null;
		}
	}

}
