package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.Location;
import org.openmrs.LocationTag;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.api.db.PSIClinicManagementDAO;
import org.openmrs.module.PSI.dto.PSILocation;
import org.openmrs.module.PSI.dto.PSILocationTag;

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
	
	@SuppressWarnings("unchecked")
	@Override
	public PSILocation findLocationById(int id) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		String sql = "select location_id as id,name,uuid,address2 from location where location_id = :id ";
		locations = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("name", StandardBasicTypes.STRING).addScalar("uuid", StandardBasicTypes.STRING)
		        .addScalar("address2", StandardBasicTypes.STRING).setInteger("id", id)
		        .setResultTransformer(new AliasToBeanResultTransformer(PSILocation.class)).list();
		if (locations.size() != 0) {
			return locations.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSILocation> findByparentLocation(int parentLocationId) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		String sql = "select location_id as id,name,uuid,address2 from location where parent_location = :parentLocationId";
		locations = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("name", StandardBasicTypes.STRING).addScalar("uuid", StandardBasicTypes.STRING)
		        .addScalar("address2", StandardBasicTypes.STRING).setInteger("parentLocationId", parentLocationId)
		        .setResultTransformer(new AliasToBeanResultTransformer(PSILocation.class)).list();
		
		return locations;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSILocation findLocationByNameCode(String name, String code) {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		String sql = "select location_id as id,name,uuid,address2 from location where name = :name and  address2 = :address2";
		locations = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("name", StandardBasicTypes.STRING).addScalar("uuid", StandardBasicTypes.STRING)
		        .addScalar("address2", StandardBasicTypes.STRING).setString("name", name).setString("address2", code)
		        .setResultTransformer(new AliasToBeanResultTransformer(PSILocation.class)).list();
		if (locations.size() != 0) {
			return locations.get(0);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSILocationTag findLocationTagByName(String name) {
		List<PSILocationTag> tags = new ArrayList<PSILocationTag>();
		String sql = "select location_tag_id as id,name,uuid from location_tag where name = :name ";
		tags = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("name", StandardBasicTypes.STRING).addScalar("uuid", StandardBasicTypes.STRING)
		        .setString("name", name).setResultTransformer(new AliasToBeanResultTransformer(PSILocationTag.class)).list();
		if (tags.size() != 0) {
			return tags.get(0);
		}
		return null;
	}
	
	@Override
	public int save(Location location) {
		Query query = sessionFactory
		        .getCurrentSession()
		        .createSQLQuery(
		            "INSERT INTO location (location_id, name, uuid,address2,date_created,creator,retired,parent_location) "
		                    + " VALUES (:location_id, :name, :uuid, :address2, :date_created, :creator, :retired, :parent_location)");
		query.setParameter("location_id", location.getLocationId());
		query.setParameter("name", location.getName());
		query.setParameter("uuid", location.getUuid());
		query.setParameter("address2", location.getAddress2());
		query.setParameter("date_created", new Date());
		query.setParameter("creator", 1);
		query.setParameter("retired", 0);
		
		if (location.getParentLocation() != null) {
			query.setParameter("parent_location", location.getParentLocation().getLocationId());
		} else {
			query.setParameter("parent_location", null);
		}
		int s = query.executeUpdate();
		
		Set<LocationTag> tagList = location.getTags();
		for (LocationTag locationTag : tagList) {
			Query queryTag = sessionFactory.getCurrentSession().createSQLQuery(
			    "INSERT INTO location_tag_map (location_id, location_tag_id )  VALUES (:location_id, :location_tag_id)");
			queryTag.setParameter("location_id", location.getLocationId());
			queryTag.setParameter("location_tag_id", locationTag.getLocationTagId());
			
			queryTag.executeUpdate();
		}
		
		return s;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSILocation findLastLocation() {
		List<PSILocation> locations = new ArrayList<PSILocation>();
		String sql = "select location_id as id,name,uuid,address2 from location order by location_id desc limit 1 ";
		locations = sessionFactory.getCurrentSession().createSQLQuery(sql).addScalar("id", StandardBasicTypes.INTEGER)
		        .addScalar("name", StandardBasicTypes.STRING).addScalar("uuid", StandardBasicTypes.STRING)
		        .addScalar("address2", StandardBasicTypes.STRING)
		        .setResultTransformer(new AliasToBeanResultTransformer(PSILocation.class)).list();
		if (locations.size() != 0) {
			return locations.get(0);
		}
		return null;
	}
	
}
