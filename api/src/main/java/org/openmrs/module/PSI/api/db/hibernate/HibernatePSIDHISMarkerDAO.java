package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.PSIDHISMarker;
import org.openmrs.module.PSI.api.db.PSIDHISMarkerDAO;
import org.openmrs.module.PSI.dto.EventReceordDTO;
import org.openmrs.module.PSI.dto.SHNDataSyncStatusDTO;

public class HibernatePSIDHISMarkerDAO implements PSIDHISMarkerDAO {
	
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
	public PSIDHISMarker saveOrUpdate(PSIDHISMarker psidhisMarker) {
		sessionFactory.getCurrentSession().saveOrUpdate(psidhisMarker);
		return psidhisMarker;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIDHISMarker findByType(String type) {
		List<PSIDHISMarker> lists = sessionFactory.getCurrentSession().createQuery("from PSIDHISMarker where type = :type")
		        .setString("type", type).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
		
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public List<EventReceordDTO> rawQuery(int id) {
		//List<Object[]> data = null;
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		
		String sql = "SELECT id,object as url,dhis_response as dhisResponse FROM openmrs.event_records where title= :title and id > :id  order by id asc limit 100";
//		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
//		
//		eventReceordDTOs = query.setString("title", "Patient").setInteger("id", id).list();
		
		eventReceordDTOs = sessionFactory
				.getCurrentSession()
				.createSQLQuery(sql)
				.addScalar("id", StandardBasicTypes.INTEGER)
				.addScalar("url", StandardBasicTypes.STRING)
				.addScalar("dhisResponse", StandardBasicTypes.STRING)
				.setResultTransformer(new AliasToBeanResultTransformer(EventReceordDTO.class))				.setString("title", "Patient")
				.setString("title", "Patient").setInteger("id", id).list();
		
/*		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			EventReceordDTO eventReceordDTO = new EventReceordDTO();
			Object[] objects = (Object[]) iterator.next();
			eventReceordDTO.setId(Integer.parseInt(objects[0].toString()));
			eventReceordDTO.setUrl(objects[1].toString());
			eventReceordDTO.setDhisResponse(objects[2] == null ? objects[2].toString() : null);
			eventReceordDTOs.add(eventReceordDTO);
		}*/
		return eventReceordDTOs;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EventReceordDTO> getEventRecordsOfEncounter(int id) {
		List<Object[]> data = null;
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		
		String sql = "SELECT id,object FROM openmrs.event_records where title= :title and id > :id  order by id asc limit 200";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.setString("title", "Encounter").setInteger("id", id).list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			EventReceordDTO eventReceordDTO = new EventReceordDTO();
			Object[] objects = (Object[]) iterator.next();
			eventReceordDTO.setId(Integer.parseInt(objects[0].toString()));
			eventReceordDTO.setUrl(objects[1].toString());
			eventReceordDTOs.add(eventReceordDTO);
		}
		return eventReceordDTOs;
	}

	@Override
	public List<EventReceordDTO> getEventRecordsOfDrug(int id) {
		List<Object[]> data = null;
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		
		String sql = "SELECT id,object FROM openmrs.event_records where title= :title and id > :id  order by id asc";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.setString("title", "drug").setInteger("id", id).list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			EventReceordDTO eventReceordDTO = new EventReceordDTO();
			Object[] objects = (Object[]) iterator.next();
			eventReceordDTO.setId(Integer.parseInt(objects[0].toString()));
			eventReceordDTO.setUrl(objects[1].toString());
			eventReceordDTOs.add(eventReceordDTO);
		}
		return eventReceordDTOs;
	}

	@Override
	public List<EventReceordDTO> getEventRecordsOfConcept(int id) {
		// TODO Auto-generated method stub
		List<Object[]> data = null;
		List<EventReceordDTO> eventReceordDTOs = new ArrayList<EventReceordDTO>();
		
		String sql = "SELECT id,object FROM openmrs.event_records where title= :title and id > :id  order by id asc";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.setString("title", "concepts").setInteger("id", id).list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			EventReceordDTO eventReceordDTO = new EventReceordDTO();
			Object[] objects = (Object[]) iterator.next();
			eventReceordDTO.setId(Integer.parseInt(objects[0].toString()));
			eventReceordDTO.setUrl(objects[1].toString());
			eventReceordDTOs.add(eventReceordDTO);
		}
		return eventReceordDTOs;
	}
}
