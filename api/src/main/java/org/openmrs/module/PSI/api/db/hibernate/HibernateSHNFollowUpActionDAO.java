package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.api.db.SHNFollowUpActionDAO;
import org.openmrs.module.PSI.dto.SHNFollowUPReportDTO;
import org.openmrs.module.PSI.dto.SHNPackageReportDTO;

public class HibernateSHNFollowUpActionDAO implements SHNFollowUpActionDAO {


	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public SHNFollowUpAction saveOrUpdate(SHNFollowUpAction shnFollowUpAction) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnFollowUpAction);
		return shnFollowUpAction;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNFollowUpAction findById(int id) {
		List<SHNFollowUpAction> lists = sessionFactory.getCurrentSession().createQuery("from SHNFollowUpAction where followUpActionId = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNFollowUpAction findByUuid(String uuid) {
		List<SHNFollowUpAction> lists = sessionFactory.getCurrentSession().createQuery("from SHNFollowUpAction where uuid = :id")
		        .setString("id", uuid).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNFollowUpAction findByCodedConceptAndEncounter(int conceptId,
			String encounterUuid) {
		List<SHNFollowUpAction> lists = sessionFactory.getCurrentSession().createQuery("from SHNFollowUpAction where valueCoded = :codedid and encounterUuid = :encounterid")
		        .setInteger("codedid", conceptId).setString("encounterid", encounterUuid).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNFollowUPReportDTO> getfollowUpReprt() {
		List<SHNFollowUPReportDTO> reportFollowUp = new ArrayList<SHNFollowUPReportDTO>();
		String packageHql = "CALL followUpReport()";					
		log.error("Query" + packageHql);
		try {
			reportFollowUp = sessionFactory.getCurrentSession().createSQLQuery(packageHql)
						 .addScalar("identifier",StandardBasicTypes.STRING)
						 .addScalar("patientName",StandardBasicTypes.STRING)
						 .addScalar("age",StandardBasicTypes.INTEGER)
						 .addScalar("contactNumber",StandardBasicTypes.STRING)
						 .addScalar("clinicCode",StandardBasicTypes.STRING)
						 .addScalar("visitUuid",StandardBasicTypes.STRING)
						 .addScalar("encounterUuid",StandardBasicTypes.STRING)
						 .addScalar("visitType",StandardBasicTypes.STRING)
						 .addScalar("visitStart",StandardBasicTypes.STRING)
						 .addScalar("visitEnd",StandardBasicTypes.STRING)
						 .addScalar("followUpFor",StandardBasicTypes.STRING)
						 .addScalar("followUpDate",StandardBasicTypes.STRING)
						 .addScalar("valueCoded",StandardBasicTypes.INTEGER)
						 .setResultTransformer(new AliasToBeanResultTransformer(SHNFollowUPReportDTO.class)).list();
			log.error("Query size" + reportFollowUp.size());

			return reportFollowUp;
			
		} catch (Exception e) {
			log.error(e.toString());
			return reportFollowUp;
		}
	}

}
