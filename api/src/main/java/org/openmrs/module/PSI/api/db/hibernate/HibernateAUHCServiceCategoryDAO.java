package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.AUHCServiceCategory;
import org.openmrs.module.PSI.PSIClinicUser;
import org.openmrs.module.PSI.api.db.AUHCServiceCategoryDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class HibernateAUHCServiceCategoryDAO implements AUHCServiceCategoryDAO {
	
	private SessionFactory sessionFactory;
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
	public AUHCServiceCategory saveOrUpdate(
			AUHCServiceCategory aUHCServiceCategory) {
		// TODO Auto-generated method stub
		 sessionFactory.getCurrentSession().saveOrUpdate(aUHCServiceCategory);
		 return aUHCServiceCategory;
	}

	@Override
	public List<AUHCServiceCategory> getAll() {
		// TODO Auto-generated method stub

		List<AUHCServiceCategory> serviceCategories = sessionFactory.getCurrentSession()
	        .createQuery("from AUHCServiceCategory  order by uuid desc ").list();
		return serviceCategories;
	}

	@Override
	public AUHCServiceCategory findBySctId(int sctid) {
		// TODO Auto-generated method stub
		List<AUHCServiceCategory> serviceCategory = sessionFactory.getCurrentSession()
				.createQuery("from AUHCServiceCategory where sctid = :sctid").
				setInteger("sctid",sctid).
				list();
		if(serviceCategory.size() == 0) return null;
		return serviceCategory.get(0);
	}

	@Override
	public void deleteCategory(int id) {
		// TODO Auto-generated method stub
//		sessionFactory.getCurrentSession()
//		 .createQuery().setInteger("sctid",id).list();
		
	}





	
}
