package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.record.formula.functions.Isblank;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.AUHCDhisErrorVisualize;
import org.openmrs.module.PSI.api.db.AUHCDhisErrorVisualizeDAO;
import org.openmrs.module.PSI.dto.AUHCRegistrationReport;
import org.springframework.util.StringUtils;

import ca.uhn.hl7v2.util.StringUtil;

public class HibernateAUHCDhisErrorVisualizeDAO implements AUHCDhisErrorVisualizeDAO{
	
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
	public String getPatientToDhisSyncInformation(String status, String clinicCode) {
		// TODO Auto-generated method stub
		/*String sqlQuery = "select Count(pe.rid)  from psi_exception pe where pe.status = '"+ status +"'";*/
		String conditionStringForPatient = "";
		if (!StringUtils.isEmpty(clinicCode)) {
			conditionStringForPatient = " and temp1.cliniccode = '"+clinicCode+"'";
		}
		
		String sqlQuery = ""
				+ "SELECT Count(pe.rid) "
				+ "FROM   psi_exception pe "
				+ "       LEFT JOIN person p "
				+ "              ON Substring(pe.url, 29, 36) = p.uuid "
				+ "       LEFT JOIN (SELECT pa.person_id, "
				+ "                         pa.value AS cliniccode "
				+ "                  FROM   person_attribute pa "
				+ "                  WHERE  pa.person_attribute_type_id = '32') temp1 "
				+ "              ON p.person_id = temp1.person_id "
				+ "WHERE  pe.status = '"+status+"'" + conditionStringForPatient + ";";
		try {
			String patientCount = sessionFactory.getCurrentSession().createSQLQuery(sqlQuery).list().get(0).toString();
			return patientCount;
			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getMoneyReceiptToDhisSyncInformation(String moneyReceiptKey, String clinicCode) {
		String sqlString = "";
		String conditionString = "";
		if (!StringUtils.isEmpty(clinicCode)) {
			conditionString = "AND pmr.clinic_code = '"+clinicCode+"'";
		}
		if(moneyReceiptKey.equalsIgnoreCase("money_receipt_transferred")) {
			
			sqlString = "select COUNT(spid) from psi_service_provision where dhis_id is not NULL and is_complete = 1 and is_send_to_dhis = 1";
			sqlString = ""
					+ "SELECT Count(psp.spid) "
					+ "FROM   psi_service_provision psp "
					+ "       JOIN psi_money_receipt pmr "
					+ "         ON pmr.mid = psp.psi_money_receipt_id "
					+ "WHERE  psp.dhis_id IS not NULL "
					+ "       AND psp.is_complete = 1 "
					+ "       AND psp.is_send_to_dhis = 1 " + conditionString + ";";
		}
		else if(moneyReceiptKey.equalsIgnoreCase("money_receipt_to_sync")) {
			
			sqlString = ""
					+ "SELECT Count(psp.spid) "
					+ "FROM   psi_service_provision psp "
					+ "       JOIN psi_money_receipt pmr "
					+ "         ON pmr.mid = psp.psi_money_receipt_id "
					+ "WHERE  psp.dhis_id IS NULL "
					+ "       AND psp.is_complete = 1 "
					+ "       AND psp.is_send_to_dhis = 0 " + conditionString + ";";
		}
		else if(moneyReceiptKey.equalsIgnoreCase("sync_failed")) {
			
			sqlString = ""
					+ "SELECT Count(psp.spid) "
					+ "FROM   psi_service_provision psp "
					+ "       JOIN psi_money_receipt pmr "
					+ "         ON pmr.mid = psp.psi_money_receipt_id "
					+ "WHERE  (psp.dhis_id IS NULL OR psp.dhis_id = '') "
					+ "       AND psp.is_complete = 1 "
					+ "       AND psp.is_send_to_dhis = 2 " + conditionString + ";";
		}
		try {
			log.error(sqlString);
			String moneyReceiptCount = sessionFactory.getCurrentSession().createSQLQuery(sqlString).list().get(0).toString();
			return moneyReceiptCount;
			
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AUHCDhisErrorVisualize> getPatientDhisSyncReport(
			AUHCDhisErrorVisualize auhcDhisErrorVisualize) {
		// TODO Auto-generated method stub
		String conditionString = "";
		if(!StringUtils.isEmpty(auhcDhisErrorVisualize.getClinic_code())) {
			conditionString= "where temp2.cliniccode = '"+auhcDhisErrorVisualize.getClinic_code()+"'";
		}
		
		String patientreportSQL = ""
				+ "SELECT p.person_id, "
				+ "       temp1.clinicname AS clinic_name, "
				+ "       temp2.cliniccode AS clinic_code, "
				+ "       pid.identifier, "
				+ "       temp3.error, "
				+ "       temp3.date_created, "
				+ "       temp3.date_changed, "
				+ "       p.uuid "
				+ "FROM   openmrs.person p "
				+ "       JOIN patient_identifier pid "
				+ "         ON pid.patient_id = p.person_id "
				+ "       JOIN (SELECT pat.person_attribute_type_id, "
				+ "                    pat.value AS clinicName, "
				+ "                    pat.person_id "
				+ "             FROM   person_attribute pat "
				+ "             WHERE  pat.person_attribute_type_id = 31) AS temp1 "
				+ "         ON p.person_id = temp1.person_id "
				+ "       JOIN (SELECT pa.person_attribute_type_id, "
				+ "                    pa.value AS cliniccode, "
				+ "                    pa.person_id "
				+ "             FROM   person_attribute pa "
				+ "             WHERE  pa.person_attribute_type_id = 32) AS temp2 "
				+ "         ON p.person_id = temp2.person_id "
				+ "       JOIN (SELECT psi.url, "
				+ "                    psi.error, "
				+ "                    psi.date_created, "
				+ "                    psi.date_changed "
				+ "             FROM   psi_exception psi "
				+ "             WHERE  psi.status = '2') temp3 "
				+ "         ON Substring(temp3.url, 29, 36) = p.uuid " + conditionString + ";";
		
		log.error(patientreportSQL);
		
		List<AUHCDhisErrorVisualize> report = new ArrayList<AUHCDhisErrorVisualize>();
		try{
			report = sessionFactory.getCurrentSession().createSQLQuery(patientreportSQL).
					addScalar("person_id",StandardBasicTypes.STRING).
					addScalar("clinic_name",StandardBasicTypes.STRING).
					addScalar("clinic_code",StandardBasicTypes.STRING).
					addScalar("identifier",StandardBasicTypes.STRING).
					addScalar("error",StandardBasicTypes.STRING).
					addScalar("date_created",StandardBasicTypes.STRING).
					addScalar("date_changed",StandardBasicTypes.STRING).
					addScalar("uuid",StandardBasicTypes.STRING).
					setResultTransformer(new AliasToBeanResultTransformer(AUHCDhisErrorVisualize.class)).
					list();
			return report;
		}catch(Exception e){
			return report;
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AUHCDhisErrorVisualize> getMoneyReceiptDhisSyncReport(
			AUHCDhisErrorVisualize auhcDhisErrorVisualize) {
		// TODO Auto-generated method stub
		String conditionString = "";
		if(!StringUtils.isEmpty(auhcDhisErrorVisualize.getClinic_code())) {
			conditionString= "and pmr.clinic_code = '"+auhcDhisErrorVisualize.getClinic_code()+"'";
		}
		
		String moneyReceiptReportSql = ""
				+ "SELECT p.person_id, "
				+ "       pi.identifier, "
				+ "       pmr.clinic_code, "
				+ "       pmr.clinic_name, "
				+ "       pmr.mid, "
				+ "       psp.error, "
				+ "       psp.date_created, "
				+ "       psp.date_changed "
				+ "FROM   psi_money_receipt pmr "
				+ "       JOIN psi_service_provision psp "
				+ "         ON pmr.mid = psp.psi_money_receipt_id "
				+ "       JOIN person p "
				+ "         ON pmr.patient_uuid = p.uuid "
				+ "       JOIN patient_identifier pi "
				+ "         ON p.person_id = pi.patient_id "
				+ "WHERE  (psp.dhis_id IS NULL OR psp.dhis_id = '') "
				+ "       AND psp.is_complete = 1 "
				+ "       AND psp.is_send_to_dhis = 2 " + conditionString + ";";
		
		log.error(moneyReceiptReportSql);
		
		List<AUHCDhisErrorVisualize> report = new ArrayList<AUHCDhisErrorVisualize>();
		try{
			report = sessionFactory.getCurrentSession().createSQLQuery(moneyReceiptReportSql).
					addScalar("person_id",StandardBasicTypes.STRING).
					addScalar("identifier",StandardBasicTypes.STRING).
					addScalar("clinic_code",StandardBasicTypes.STRING).
					addScalar("clinic_name",StandardBasicTypes.STRING).
					addScalar("mid",StandardBasicTypes.STRING).
					addScalar("error",StandardBasicTypes.STRING).
					addScalar("date_created",StandardBasicTypes.STRING).
					addScalar("date_changed",StandardBasicTypes.STRING).
					setResultTransformer(new AliasToBeanResultTransformer(AUHCDhisErrorVisualize.class)).
					list();
			return report;
		}catch(Exception e){
			return report;
		}
	}

}
