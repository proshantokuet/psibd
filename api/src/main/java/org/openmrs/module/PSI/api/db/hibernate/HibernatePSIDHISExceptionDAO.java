package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.db.PSIDHISExceptionDAO;
import org.openmrs.module.PSI.dto.SHNDataSyncStatusDTO;
import org.openmrs.module.PSI.utils.PSIConstants;

public class HibernatePSIDHISExceptionDAO implements PSIDHISExceptionDAO {
	
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
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisException) {
		sessionFactory.getCurrentSession().saveOrUpdate(psidhisException);
		return psidhisException;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIDHISException findReferenceIdOfPatient(String patientUuid,
			int status) {
		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIDHISException where patientUuid = :id and status = :flag order by rid desc").setString("id", patientUuid)
		        .setInteger("flag", status).setMaxResults(1).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PSIDHISException> findAllByStatus(int status) {
		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIDHISException where (status = :id0 OR status = :id3)").setInteger("id0", status)
		        .setInteger("id3", PSIConstants.CONNECTIONTIMEOUTSTATUS).list();
		
		return lists;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIDHISException findAllById(int patientId) {
		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIDHISException where markId = :id").setInteger("id", patientId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNDataSyncStatusDTO findStatusToSendDataDhis(String type, String uuid) {
		// TODO Auto-generated method stub
		
		String patientOriginSql = ""
				+ "SELECT patient_origin as patientOrigin,patient_uuid as patientUuid,encounter_uuid as encounterUuid,is_send_to_dhis as sendToDhisFromGlobal  from openmrs.shr_patient_origin "
				+ "where "+type+" = '"+uuid+"'";
		
		List<SHNDataSyncStatusDTO> shrPatientOrigins = new ArrayList<SHNDataSyncStatusDTO>();
		
		try {
			shrPatientOrigins = sessionFactory
					.getCurrentSession()
					.createSQLQuery(patientOriginSql)
					.addScalar("patientUuid", StandardBasicTypes.STRING)
					.addScalar("sendToDhisFromGlobal", StandardBasicTypes.INTEGER)
					.addScalar("patientOrigin", StandardBasicTypes.STRING)
					.addScalar("encounterUuid", StandardBasicTypes.STRING)
					.setResultTransformer(
							new AliasToBeanResultTransformer(
									SHNDataSyncStatusDTO.class)).list();
			if (shrPatientOrigins.size() > 0) {
				return shrPatientOrigins.get(0);
			} 
			else {
				return null;
			}
		} 
		catch (Exception e) {
			return null;
		}
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<PSIDHISException> findAllFailedEncounterByStatus(int status) {
//		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
//		        .createQuery("from PSIDHISException where (status = :id0 OR status = :id3) and type = 'Encounter'").setInteger("id0", status)
//		        .setInteger("id3", PSIConstants.CONNECTIONTIMEOUTSTATUS).list();
//		
//		return lists;
//	}
//
//	@SuppressWarnings("unchecked")
//	@Override
//	public PSIDHISException findAllBymarkerIdAndFormName(int markerId,String formsName) {
//		List<PSIDHISException> lists = sessionFactory.getCurrentSession()
//		        .createQuery("from PSIDHISException where markId = :id and formsName = :formname").setInteger("id", markerId).setString("formname", formsName).list();
//		if (lists.size() != 0) {
//			return lists.get(0);
//		} else {
//			return null;
//		}
//	}
	
}
