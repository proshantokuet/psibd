package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.AUHCClinicType;
import org.openmrs.module.PSI.api.db.AUHCClinicTypeDAO;

public class HibernateAUHCClinicTypeDAO implements AUHCClinicTypeDAO {
	
	protected final Log log = LogFactory.getLog(getClass());
	
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
	public AUHCClinicType saveOrUpdate(AUHCClinicType clinicType) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(clinicType);
		return clinicType;
		
//		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public AUHCClinicType findByCtId(int ctid) {
		// TODO Auto-generated method stub
		List<AUHCClinicType> clinicType = sessionFactory.getCurrentSession()
				.createQuery(" from AUHCClinicType where ctid = :ctid ").
				setInteger("ctid",ctid).
				list();
		if(clinicType.size() == 0) return null;
		return clinicType.get(0);
	}

	@Override
	public void deleteByCtId(int ctid) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AUHCClinicType> getAll() {
		// TODO Auto-generated method stub
		List<AUHCClinicType> clinicTypeList = sessionFactory.getCurrentSession()
		        .createQuery(" from AUHCClinicType  order by uuid desc ").list();
		return clinicTypeList;
	}

}
