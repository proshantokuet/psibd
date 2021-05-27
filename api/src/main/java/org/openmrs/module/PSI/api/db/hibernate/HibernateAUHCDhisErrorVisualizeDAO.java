package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.AUHCDhisErrorVisualize;
import org.openmrs.module.PSI.api.db.AUHCDhisErrorVisualizeDAO;
import org.springframework.util.StringUtils;

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
		String sqlString = "";	
		if (StringUtils.isEmpty(clinicCode)) {
			sqlString = "SELECT SyncAggValOfPatient()";
		}
		else {
			sqlString = "SELECT SyncAggValOfPatientWithClinic('"+ clinicCode +"')";
		}
		try {
			String patientCount = sessionFactory.getCurrentSession().createSQLQuery(sqlString).list().get(0).toString();
			return patientCount;
			
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public String getMoneyReceiptToDhisSyncInformation(String moneyReceiptKey, String clinicCode) {
		String sqlString = "";
		if (StringUtils.isEmpty(clinicCode)) {
			sqlString = "SELECT SyncAggVal()";
		}
		else {
			sqlString = "SELECT SyncAggValWithClinicCode('"+ clinicCode +"')";
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
				+ "                    psi.date_changed, "
				+ "                    psi.patient_uuid "
				+ "             FROM   psi_exception psi "
				+ "             WHERE  psi.status = '2') temp3 "
				+ "         ON temp3.patient_uuid = p.uuid " + conditionString + ";";
		
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

	@Override
	public String getDataToGlobalSyncInformationByType(String type) {
		String sqlString = "";
		// TODO Auto-generated method stub
		if(type.equalsIgnoreCase("Money Receipt")) {
			sqlString = "SELECT globalServerSyncInfoMoneyReceipt('"+ type +"')";
		}
		else {
			 sqlString = "SELECT globalServerSyncInfoPatientAndENcounter('"+ type +"')";
		}
		try {
			String patientCount = sessionFactory.getCurrentSession().createSQLQuery(sqlString).list().get(0).toString();
			return patientCount;
			
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AUHCDhisErrorVisualize> getDataToGLobalSyncReport(String type) {
		String queryString = "";
		if(type.equalsIgnoreCase("Patient")) {
			
			queryString = ""
					+ "SELECT ael.action_type as actionType, "
					+ "       p.identifier as identifier, "
					+ "       error_message as error "
					+ "FROM   shr_action_error_log ael "
					+ "       JOIN person p "
					+ "         ON p.uuid = ael.uuid "
					+ "WHERE  ael.sent_status = 0 "
					+ "       AND ael.action_type = 'Patient'";
		}
		else if(type.equalsIgnoreCase("Money Receipt")) {
			queryString = ""
					+ "SELECT ael.action_type as actionType, "
					+ "       pmr.eslip_no as identifier, "
					+ "       error_message as error "
					+ "FROM   shr_action_error_log ael "
					+ "       JOIN psi_money_receipt pmr "
					+ "         ON ael.uuid = pmr.mid "
					+ "WHERE  ael.sent_status = 0 "
					+ "       AND ael.action_type = 'Money Receipt'";
		}
		else if(type.equalsIgnoreCase("Encounter")) {
			
			queryString = ""
					+ "select "
					+ "	ael.action_type as actionType, "
					+ "	ael.uuid as identifier, "
					+ "	error_message as error "
					+ "from "
					+ "	shr_action_error_log ael "
					+ "where "
					+ "	ael.sent_status = 0 "
					+ "	and ael.action_type = 'Encounter'";
		}
		// TODO Auto-generated method stub

		
		log.error(queryString);
		
		List<AUHCDhisErrorVisualize> report = new ArrayList<AUHCDhisErrorVisualize>();
		try{
			 report = sessionFactory.getCurrentSession().createSQLQuery(queryString).
					addScalar("actionType",StandardBasicTypes.STRING).
					addScalar("identifier",StandardBasicTypes.STRING).
					addScalar("error",StandardBasicTypes.STRING).
					setResultTransformer(new AliasToBeanResultTransformer(AUHCDhisErrorVisualize.class)).
					list();
			return report;
		}catch(Exception e){
			return report;
		}
	}

}
