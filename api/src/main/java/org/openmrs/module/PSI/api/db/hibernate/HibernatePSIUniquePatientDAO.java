package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.Concept;
import org.openmrs.module.PSI.AUHCDhisErrorVisualize;
import org.openmrs.module.PSI.PSIClinicChild;
import org.openmrs.module.PSI.PSIUniqueIdGenerator;
import org.openmrs.module.PSI.SHNFormPdfDetails;
import org.openmrs.module.PSI.SHnPrescriptionMetaData;
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
//		String sql = "SELECT temp1.pat_id, \n" +
//				"       temp1.mobileno, \n" +
//				"       temp2.pa_id, \n" +
//				"       temp2.uic \n" +
//				"FROM   (SELECT pat.person_attribute_type_id as pat_id, \n" +
//				"               pat.value AS mobileNo, \n" +
//				"               pat.person_id as pat_person_id \n" +
//				"        FROM   person_attribute pat \n" +
//				"        WHERE  pat.person_attribute_type_id = 42) AS temp1 \n" +
//				"       JOIN (SELECT pa.person_attribute_type_id as pa_id, \n" +
//				"                    pa.value AS UIC, \n" +
//				"                    pa.person_id as pa_person_id \n" +
//				"             FROM   person_attribute pa \n" +
//				"             WHERE  pa.person_attribute_type_id = 34) AS temp2 \n" +
//				"         ON temp1.pat_person_id = temp2.pa_person_id \n" +
//				"WHERE  temp1.mobileno = '"+ mobileNo +"' \n" +
//				"       AND temp2.uic = '"+patientUic+"';";
		String sql = "select p.person_id from person p where p.contact_no = '"+mobileNo+"' AND p.uic = '"+patientUic+"'";
		log.error("check patient sql " + sql);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Object[]> data = query.list();
		
		if (data.size() > 0) {
			isPatientAvailable = true;
		}

		return isPatientAvailable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHnPrescriptionMetaData> getAllPrescriptionMetaData() {
//		List<SHnPrescriptionMetaData> lists = sessionFactory.getCurrentSession()
//		        .createQuery("from SHnPrescriptionMetaData where voided = 0 ").list();
//		return lists;
		String dischargeQuery = "select field_name as fieldName,service_name as serviceName from openmrs.shn_prescription_metadata";
		
		List<SHnPrescriptionMetaData> report = new ArrayList<SHnPrescriptionMetaData>();
		try{
			report = sessionFactory.getCurrentSession().createSQLQuery(dischargeQuery).
					addScalar("fieldName",StandardBasicTypes.STRING).
					addScalar("serviceName",StandardBasicTypes.STRING).
					setResultTransformer(new AliasToBeanResultTransformer(SHnPrescriptionMetaData.class)).
					list();
			return report;
		}catch(Exception e){
			return report;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNFormPdfDetails> getDischargeInformationByVisit(
			String patientUuid, String visitUuid) {
		String dischargeQuery = ""
				+ "SELECT "
				+ "  obs_fscn.name as question, "
				+ "  coalesce(o.value_numeric, o.value_text, o.value_datetime, coded_scn.name, coded_fscn.name) AS answer, "
				+ "  v.uuid as visit_uuid, "
				+ " o.obs_group_id as groupId"
				+ " FROM obs o "
				+ "  JOIN concept obs_concept ON obs_concept.concept_id=o.concept_id AND obs_concept.retired is false "
				+ "  JOIN concept_name obs_fscn on o.concept_id=obs_fscn.concept_id AND obs_fscn.concept_name_type=\"FULLY_SPECIFIED\" AND obs_fscn.voided is false "
				+ "  LEFT JOIN concept_name obs_scn on o.concept_id=obs_scn.concept_id AND obs_scn.concept_name_type=\"SHORT\" AND obs_scn.voided is false "
				+ "  JOIN person p ON p.person_id = o.person_id AND p.voided is false "
				+ "  JOIN encounter e ON o.encounter_id=e.encounter_id AND e.voided is false "
				+ "  JOIN visit v ON v.visit_id=e.visit_id AND v.voided is false "
				+ "  LEFT OUTER JOIN obs parent_obs ON parent_obs.obs_id=o.obs_group_id "
				+ "  LEFT OUTER JOIN concept_name parent_cn ON parent_cn.concept_id=parent_obs.concept_id AND parent_cn.concept_name_type=\"FULLY_SPECIFIED\" "
				+ "  LEFT JOIN concept_name coded_fscn on coded_fscn.concept_id = o.value_coded AND coded_fscn.concept_name_type=\"FULLY_SPECIFIED\" AND coded_fscn.voided is false "
				+ "  LEFT JOIN concept_name coded_scn on coded_scn.concept_id = o.value_coded AND coded_fscn.concept_name_type=\"SHORT\" AND coded_scn.voided is false "
				+ "WHERE o.voided is false and o.form_namespace_and_path like '%Discharge Certificate%' and p.uuid = '"+patientUuid+"' and v.uuid= '"+visitUuid+"'";
		log.error(dischargeQuery);
		List<SHNFormPdfDetails> report = new ArrayList<SHNFormPdfDetails>();
		try{
			report = sessionFactory.getCurrentSession().createSQLQuery(dischargeQuery).
					addScalar("question",StandardBasicTypes.STRING).
					addScalar("answer",StandardBasicTypes.STRING).
					addScalar("visit_uuid",StandardBasicTypes.STRING).
					addScalar("groupId",StandardBasicTypes.INTEGER).
					setResultTransformer(new AliasToBeanResultTransformer(SHNFormPdfDetails.class)).
					list();
			return report;
		}catch(Exception e){
			return report;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SHNFormPdfDetails> getbirthInformationByVisit(String patientUuid, String visitUuid) {
		String birthQuery = ""
				+ "SELECT "
				+ "  obs_fscn.name as question, "
				+ "  coalesce(o.value_numeric, o.value_text, o.value_datetime, coded_scn.name, coded_fscn.name) AS answer, "
				+ "  v.uuid as visit_uuid "
				+ "FROM obs o "
				+ "  JOIN concept obs_concept ON obs_concept.concept_id=o.concept_id AND obs_concept.retired is false "
				+ "  JOIN concept_name obs_fscn on o.concept_id=obs_fscn.concept_id AND obs_fscn.concept_name_type=\"FULLY_SPECIFIED\" AND obs_fscn.voided is false "
				+ "  LEFT JOIN concept_name obs_scn on o.concept_id=obs_scn.concept_id AND obs_scn.concept_name_type=\"SHORT\" AND obs_scn.voided is false "
				+ "  JOIN person p ON p.person_id = o.person_id AND p.voided is false "
				+ "  JOIN patient_identifier pi ON p.person_id = pi.patient_id AND pi.voided is false "
				+ "  JOIN encounter e ON o.encounter_id=e.encounter_id AND e.voided is false "
				+ "  JOIN visit v ON v.visit_id=e.visit_id AND v.voided is false "
				+ "  LEFT OUTER JOIN obs parent_obs ON parent_obs.obs_id=o.obs_group_id "
				+ "  LEFT OUTER JOIN concept_name parent_cn ON parent_cn.concept_id=parent_obs.concept_id AND parent_cn.concept_name_type=\"FULLY_SPECIFIED\" "
				+ "  LEFT JOIN concept_name coded_fscn on coded_fscn.concept_id = o.value_coded AND coded_fscn.concept_name_type=\"FULLY_SPECIFIED\" AND coded_fscn.voided is false "
				+ "  LEFT JOIN concept_name coded_scn on coded_scn.concept_id = o.value_coded AND coded_fscn.concept_name_type=\"SHORT\" AND coded_scn.voided is false "
				+ "WHERE o.voided is false and o.form_namespace_and_path like '%Delivery%' and p.uuid = '"+patientUuid+"' and v.uuid = '"+visitUuid+"' "
				+ "and obs_fscn.name in('Baby Delivery Date','Baby Length (cm)','Baby Weight (Kg)','Sex','Newborn Length (cm)') order by o.obs_group_id ASC;";
		
		List<SHNFormPdfDetails> report = new ArrayList<SHNFormPdfDetails>();
		try{
			report = sessionFactory.getCurrentSession().createSQLQuery(birthQuery).
					addScalar("question",StandardBasicTypes.STRING).
					addScalar("answer",StandardBasicTypes.STRING).
					addScalar("visit_uuid",StandardBasicTypes.STRING).
					setResultTransformer(new AliasToBeanResultTransformer(SHNFormPdfDetails.class)).
					list();
			return report;
		}catch(Exception e){
			return report;
		}
	}

	@Override
	public String getLastProviderName(String visitUuid) {
		// TODO Auto-generated method stub
		String providerQuery = ""
				+ "SELECT Concat(pn.given_name, ' ', pn.family_name) AS fullname "
				+ "FROM   encounter e "
				+ "       JOIN visit v "
				+ "         ON v.visit_id = e.visit_id "
				+ "       JOIN encounter_provider ep "
				+ "         ON ep.encounter_id = e.encounter_id "
				+ "       JOIN provider p "
				+ "         ON ep.provider_id = p.provider_id "
				+ "       JOIN person_name pn "
				+ "         ON pn.person_id = p.person_id "
				+ "WHERE  v.uuid = '"+visitUuid+"' "
				+ "ORDER  BY e.encounter_datetime DESC";
		
		List<String> providerList = new ArrayList<String>();
		try{
			providerList = sessionFactory.getCurrentSession().createSQLQuery(providerQuery).list();
			if(providerList.size() < 1) {
				return "No Provider Found";
			}
			else {
				return providerList.get(0);
			}
		}catch(Exception e){
			 return e.toString();
		}
	}

	@Override
	public Boolean findPatientByUicandMobileNoWhileEdit(String patientUic,
			String mobileNo, String patientUuid) {
		Boolean isPatientAvailable = false;
//		String sql = ""
//				+ "SELECT temp1.pat_id, "
//				+ "       temp1.mobileno, "
//				+ "       temp2.pa_id, "
//				+ "       temp2.uic , "
//				+ "       p.uuid "
//				+ "FROM   (SELECT pat.person_attribute_type_id as pat_id, "
//				+ "               pat.value AS mobileNo, "
//				+ "               pat.person_id as pat_person_id "
//				+ "        FROM   person_attribute pat "
//				+ "        WHERE  pat.person_attribute_type_id = 42) AS temp1 "
//				+ "       JOIN (SELECT pa.person_attribute_type_id as pa_id, "
//				+ "                    pa.value AS UIC, "
//				+ "                    pa.person_id as pa_person_id "
//				+ "             FROM   person_attribute pa "
//				+ "             WHERE  pa.person_attribute_type_id = 34) AS temp2 "
//				+ "         ON temp1.pat_person_id = temp2.pa_person_id "
//				+ "         JOIN person p on p.person_id = temp2.pa_person_id "
//				+ "WHERE  temp1.mobileno = '"+mobileNo+"' "
//				+ "       AND temp2.uic = '"+patientUic+"' "
//				+ "      AND p.uuid NOT IN('"+patientUuid+"');";
		String sql = "select p.person_id from person p where p.contact_no = '"+mobileNo+"' AND p.uic = '"+patientUic+"' AND p.uuid NOT IN('"+patientUuid+"')";
		log.error("check patient sql " + sql);
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		List<Object[]> data = query.list();
		
		if (data.size() > 0) {
			isPatientAvailable = true;
		}

		return isPatientAvailable;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Concept> getconceptListGreaterthanCurrentConcept(int conceptId) {
		List<Concept> lists = sessionFactory.getCurrentSession()
		        .createQuery("from Concept where conceptId > :conceptid order by conceptId ASC").setInteger("conceptid", conceptId).list();
		return lists;

	}
}
