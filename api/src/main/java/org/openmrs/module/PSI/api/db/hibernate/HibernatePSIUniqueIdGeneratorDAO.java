package org.openmrs.module.PSI.api.db.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.SHNEslipNoGenerate;
import org.openmrs.module.PSI.api.db.PSIUniqueIdGeneratorDAO;

public class HibernatePSIUniqueIdGeneratorDAO implements PSIUniqueIdGeneratorDAO {
	
	public static DateFormat yyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
	
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
	public PSIUniqueIdGenerator saveOrUpdate(PSIUniqueIdGenerator psiClinicGenerator) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(psiClinicGenerator);
		
		return psiClinicGenerator;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIUniqueIdGenerator findByClinicCodeAndDate(String date, String clinicCode) {
		// List<Object[]> data = null;
		PSIUniqueIdGenerator psiUniqueIdGenerator = new PSIUniqueIdGenerator();
		psiUniqueIdGenerator.setGenerateId(0);
		String sql = "SELECT generate_id FROM openmrs.psi_unique_id_generator where "
		        + " clinic_code = :clinicCode and Date(date_created) = :date order by generate_id " + " desc limit 1";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.setString("clinicCode", clinicCode).setString("date", date).list();
		for (Integer newPatient : data) {
			psiUniqueIdGenerator.setGenerateId(newPatient.intValue());
		}
		
		return psiUniqueIdGenerator;
		
	}

	@Override
	public SHNEslipNoGenerate saveOrUpdate(SHNEslipNoGenerate shnEslipNoGenerate) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnEslipNoGenerate);
		return shnEslipNoGenerate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SHNEslipNoGenerate findEslipByClinicCodeAndDate(String date,String clinicCode) {
		SHNEslipNoGenerate shnEslipNoGenerate = new SHNEslipNoGenerate();
		shnEslipNoGenerate.setGenerateId(0);
		String sql = "SELECT generate_id FROM openmrs.shn_eslip_no_generate where "
		        + " clinic_code = :clinicCode and Date(date_created) = :date order by generate_id " + " desc limit 1";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Integer> data = query.setString("clinicCode", clinicCode).setString("date", date).list();
		for (Integer newslip : data) {
			shnEslipNoGenerate.setGenerateId(newslip.intValue());
		}
		
		return shnEslipNoGenerate;
	}
	
}
