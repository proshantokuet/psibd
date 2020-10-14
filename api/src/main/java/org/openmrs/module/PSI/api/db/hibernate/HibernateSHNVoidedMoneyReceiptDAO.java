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
	public List<SHNVoidedMoneyReceiptLog> getAllVoidedMoneyReceiptByClinic(String clinicCode) {
		List<SHNVoidedMoneyReceiptLog> lists = sessionFactory.getCurrentSession().createQuery("from SHNVoidedMoneyReceiptLog where clinicCode = :id")
		        .setString("id", clinicCode).list();
		return lists;
	}

}
