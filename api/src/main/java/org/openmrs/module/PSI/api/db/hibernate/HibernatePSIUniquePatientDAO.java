package org.openmrs.module.PSI.api.db.hibernate;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.api.db.PSIUniquePatientDAO;

public class HibernatePSIUniquePatientDAO implements PSIUniquePatientDAO {
	
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

	@SuppressWarnings("unchecked")
	@Override
	public Boolean findPatientByUicandMobileNo(String patientUic,
			String mobileNo) {
		
		Boolean isPatientAvailable = false;
		String sql = "SELECT temp1.pat_id, \n" +
				"       temp1.mobileno, \n" +
				"       temp2.pa_id, \n" +
				"       temp2.uic \n" +
				"FROM   (SELECT pat.person_attribute_type_id as pat_id, \n" +
				"               pat.value AS mobileNo, \n" +
				"               pat.person_id as pat_person_id \n" +
				"        FROM   person_attribute pat \n" +
				"        WHERE  pat.person_attribute_type_id = 42) AS temp1 \n" +
				"       JOIN (SELECT pa.person_attribute_type_id as pa_id, \n" +
				"                    pa.value AS UIC, \n" +
				"                    pa.person_id as pa_person_id \n" +
				"             FROM   person_attribute pa \n" +
				"             WHERE  pa.person_attribute_type_id = 34) AS temp2 \n" +
				"         ON temp1.pat_person_id = temp2.pa_person_id \n" +
				"WHERE  temp1.mobileno = '"+ mobileNo +"' \n" +
				"       AND temp2.uic = '"+patientUic+"';";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Object[]> data = query.list();
		
		if (data.size() > 0) {
			isPatientAvailable = true;
		}

		return isPatientAvailable;
	}

}
