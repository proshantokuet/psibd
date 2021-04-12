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
		String sql = "";
		if(clinicId == 0) {
			sql = "from SHNVoidedMoneyReceiptLog where isDeleteFromLocal = 0";
		}
		else {
			sql = "from SHNVoidedMoneyReceiptLog where clinicId = "+clinicId+" and isDeleteFromLocal = 0";
		}
		List<SHNVoidedMoneyReceiptLog> lists = sessionFactory.getCurrentSession().createQuery(sql).list();
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


	@Override
	public SHNVoidedMoneyReceiptLog updateStatusColumnInVoidedMoneyReceipt(
			String columnName, int status, int voidId) {
		// TODO Auto-generated method stub
		String updateSql = "update openmrs.shn_voidedmoneyreceipt_log set "+columnName+" = "+status+" where void_id = "+voidId+"";
		log.error("Print update SQL " + updateSql);
		
		try{
			sessionFactory.getCurrentSession().createSQLQuery(updateSql).executeUpdate();
		}catch(Exception e){

		}
		
		return null;
		
	}




}
