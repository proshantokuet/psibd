package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.api.db.SHNStockDAO;

public class HibernateSHNStockDAO implements SHNStockDAO {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public SHNStock saveOrUpdate(SHNStock shnStock) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnStock);
		return shnStock;
	}


	@SuppressWarnings("unchecked")
	@Override
	public SHNStock findById(int id) {
		List<SHNStock> lists = sessionFactory.getCurrentSession().createQuery("from SHNStock where stkid = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

}
