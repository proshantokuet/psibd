package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.db.PSIClinicManagementDAO;
import org.openmrs.module.PSI.dto.PSILocation;

public class HibernatePSIClinicManagementDAO implements PSIClinicManagementDAO {
	
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
	public PSIClinicManagement saveOrUpdateClinic(PSIClinicManagement psiClinic) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinic);
		
		return psiClinic;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIClinicManagement> getAllClinic() {
		List<PSIClinicManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement  order by cid desc").list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicManagement findById(int id) {
		// TODO Auto-generated method stub
		List<PSIClinicManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement where cid = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@Override
	public void delete(int id) {
		PSIClinicManagement psiClinicManagement = findById(id);
		if (psiClinicManagement != null) {
			sessionFactory.getCurrentSession().delete(psiClinicManagement);
		} else {
			log.error("psiClinicManagement is null with id" + id);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicManagement findByClinicId(String clinicId) {
		// TODO Auto-generated method stub
		List<PSIClinicManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement where clinicId = :clinicId").setString("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIClinicManagement findByIdNotByClinicId(int id, String clinicId) {
		// TODO Auto-generated method stub
		List<PSIClinicManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIClinicManagement where cid !=:id and  clinicId = :clinicId")
		        .setString("clinicId", clinicId).setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSILocation> findLocationByTag(String tagName) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		String sql = "select location_id as id,name,uuid,address2 from location where location_id =any("
		        + "	select location_id from openmrs.location_tag_map where location_tag_id= (select "
		        + " location_tag_id from openmrs.location_tag where name = :tagName limit 1))";
		locations = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("name", StandardBasicTypes.STRING).addScalar("uuid", StandardBasicTypes.STRING)
		        .addScalar("address2", StandardBasicTypes.STRING).setString("tagName", tagName)
		        .setResultTransformer(new AliasToBeanResultTransformer(PSILocation.class)).list();
		
		return locations;
	}
	
	@Override
	public PSILocation findLocationById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PSILocation> findByparentLocation(int parentLocationId) {
		// TODO Auto-generated method stub
		
		/*select location_id as id,name,uuid,address2 as code from openmrs.location where  location_id = 50;

		select location_id as id,name from openmrs.location where parent_location = 2;*/
		return null;
	}
	
}
