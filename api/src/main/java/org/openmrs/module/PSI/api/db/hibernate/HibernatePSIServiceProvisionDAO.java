package org.openmrs.module.PSI.api.db.hibernate;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.User;
import org.openmrs.api.UserService;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.db.PSIServiceProvisionDAO;
import org.openmrs.module.PSI.dto.DashboardDTO;
import org.openmrs.module.PSI.dto.PSIReport;
import org.openmrs.module.PSI.utils.PSIConstants;

public class HibernatePSIServiceProvisionDAO implements PSIServiceProvisionDAO {
	
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
	public PSIServiceProvision saveOrUpdate(PSIServiceProvision psiServiceProvision) {
		sessionFactory.getCurrentSession().saveOrUpdate(psiServiceProvision);
		return psiServiceProvision;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> getAll() {
		List<PSIServiceProvision> lists = new ArrayList<PSIServiceProvision>();
		lists = sessionFactory.getCurrentSession().createQuery("from PSIServiceProvision ").list();
		return lists;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> getAllByPatient(String patientUuid) {
		List<PSIServiceProvision> lists = new ArrayList<PSIServiceProvision>();
		lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision where patientUuid = :id  order by spid desc")
		        .setString("id", patientUuid).list();
		
		return lists;
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceProvision findById(int id) {
		List<PSIServiceProvision> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision where spid = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public List<PSIServiceProvision> getAllBetweenDateAndPatient(Date start, Date end, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<PSIServiceProvision> getAllByDateAndPatient(Date date, String patientUuid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void delete(int id) {
		PSIServiceProvision psiServiceProvision = findById(id);
		if (psiServiceProvision != null) {
			sessionFactory.getCurrentSession().delete(psiServiceProvision);
		} else {
			log.error("psiServiceProvision is null with id" + id);
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> findAllByTimestamp(long timestamp) {
		List<PSIServiceProvision> lists = new ArrayList<PSIServiceProvision>();
		lists = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		            "from PSIServiceProvision where timestamp > :timestamp and is_complete = :complete order by timestamp asc ")
		        .setLong("timestamp", timestamp).setInteger("complete", 1).setMaxResults(500)
		        
		        .list();
		return lists;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PSIReport> servicePointWiseReport(String startDate, String endDate, String code) {
		List<Object[]> data = null;
		List<PSIReport> reportDTOs = new ArrayList<PSIReport>();
		String clinicCondition = "";
		if (!code.equalsIgnoreCase("0")) {
			clinicCondition = " and clinic_code = :code ";
		}
		String sql = "select code, item, category, sum(Static) as Static, sum(Satellite) as Satellite,  sum(CSP) as CSP , sum(Static)+sum(Satellite)+sum(CSP) as total from ( select code,item ,  category,service_point, sum(net_payable) as ttt,count(*), CASE WHEN service_point = 'Static' THEN sum(net_payable) ELSE 0 END Static,  CASE WHEN service_point = 'Satellite' THEN sum(net_payable) ELSE 0 END Satellite, CASE WHEN service_point = 'CSP' THEN sum(net_payable)  ELSE 0 END CSP from openmrs.psi_service_provision as sp  left join  openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid  where sp.is_complete = 1 and DATE(sp.money_receipt_date)  between  '"
		        + startDate
		        + "'  and  '"
		        + endDate
		        + "'"
		        + clinicCondition
		        + "  group by code ,item,service_point,category order  by code) as Report  group by code, item ";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		if (!code.equalsIgnoreCase("0")) {
			data = query.setString("code", code).list();
		} else {
			data = query.list();
		}
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			PSIReport report = new PSIReport();
			Object[] objects = (Object[]) iterator.next();
			report.setCode(objects[0] + "");
			report.setItem(objects[1] + "");
			report.setCategory(objects[2] + "");
			report.setClinic(Float.parseFloat(objects[3].toString()));
			report.setSatelite(Float.parseFloat(objects[4].toString()));
			report.setCsp(Float.parseFloat(objects[5].toString()));
			report.setTotal(Float.parseFloat(objects[6].toString()));
			reportDTOs.add(report);
		}
		return reportDTOs;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIReport> serviceProviderWiseReport(String startDate, String endDate, String code, String dataCollector) {
		List<Object[]> data = null;
		List<PSIReport> reportDTOs = new ArrayList<PSIReport>();
		String clinicCondition = "";
		String dataCollectorCondition = "";
		if (!code.equalsIgnoreCase("0")) {
			clinicCondition = " and clinic_code = :code ";
		}
		
		if (!"".equalsIgnoreCase(dataCollector)) {
			dataCollectorCondition = " and data_collector = :dataCollector";
		}
		String sql = "select code,item ,category, count(*) as serviceCount ,sum(net_payable) as total from openmrs.psi_service_provision as sp left join openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid where sp.is_complete = 1 and DATE(sp.money_receipt_date)  between '"
		        + startDate
		        + "' and '"
		        + endDate
		        + "' "
		        + clinicCondition
		        + dataCollectorCondition
		        + "  group by code ,item,category order  by code";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		if (!code.equalsIgnoreCase("0")) {
			query = (SQLQuery) query.setString("code", code);
		}
		if (!"".equalsIgnoreCase(dataCollector)) {
			query = (SQLQuery) query.setString("dataCollector", dataCollector);
		}
		
		data = query.list();
		
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			PSIReport report = new PSIReport();
			Object[] objects = (Object[]) iterator.next();
			report.setCode(objects[0] + "");
			report.setItem(objects[1] + "");
			report.setCategory(objects[2] + "");
			report.setServiceCount(Integer.parseInt(objects[3].toString()));
			report.setTotal(Float.parseFloat(objects[4].toString()));
			reportDTOs.add(report);
		}
		return reportDTOs;
	}
	
	@Override
	public String servicePointWiseRepor(String startDate, String endDate, String code) {
		List<Object[]> data = null;
		List<PSIReport> reportDTOs = new ArrayList<PSIReport>();
		/*String sql = "select code, item, category, sum(Clilic) as Clilic, sum(Satellite) as Satellite, "
		        + " sum(CSP) as CSP , sum(Clilic)+sum(Satellite)+sum(CSP) as total from ( select code,item , "
		        + " category,service_point, sum(net_payable) as ttt,count(*), CASE WHEN service_point = 'Clinic' THEN sum(net_payable) ELSE 0 END Clilic, "
		        + " CASE WHEN service_point = 'Satellite' THEN sum(net_payable) ELSE 0 END Satellite, "
		        + " CASE WHEN service_point = 'CSP' THEN sum(net_payable)  ELSE 0 END CSP from openmrs.psi_service_provision as sp "
		        + " left join  openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid "
		        + " where DATE(sp.money_receipt_date)  between  '" + startDate + "'  and  '" + endDate
		        + "'  and clinic_code = '" + code + "'"
		        + "  group by code ,item,service_point,category order  by code) as Report  group by code,item ";
		*/
		String sql = "select code, item, category, sum(Clilic) as Clilic, sum(Satellite) as Satellite,  sum(CSP) as CSP , sum(Clilic)+sum(Satellite)+sum(CSP) as total from ( select code,item ,  category,service_point, sum(net_payable) as ttt,count(*), CASE WHEN service_point = 'Clinic' THEN sum(net_payable) ELSE 0 END Clilic,  CASE WHEN service_point = 'Satellite' THEN sum(net_payable) ELSE 0 END Satellite, CASE WHEN service_point = 'CSP' THEN sum(net_payable)  ELSE 0 END CSP from openmrs.psi_service_provision as sp  left join  openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid  where DATE(sp.money_receipt_date)  between  '2019-02-06'  and  '2019-06-04'  and clinic_code = 'mouha84s'   group by code ,item,service_point,category order  by code) as Report  group by code, item ";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.list();
		String s = "";
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			s = objects[0].toString();
		}
		return data.toString() + data.size() + s;
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public DashboardDTO dashboardReport(String start, String end, String code, String dataCollector) {
		String servedPatienClinicCondition = "";
		String servedPatienDataCollectorCondition = "";
		if (!"0".equalsIgnoreCase(code)) {
			servedPatienClinicCondition = " clinic_code = :code and";
		}
		
		if (!"".equalsIgnoreCase(dataCollector)) {
			servedPatienDataCollectorCondition = " data_collector = :dataCollector and";
		}
		
		DashboardDTO dashboardDTO = new DashboardDTO();
		String servedPatientSql = "SELECT count(distinct(patient_uuid)) as count FROM openmrs.psi_money_receipt where "
		        + servedPatienClinicCondition + servedPatienDataCollectorCondition + " DATE(money_receipt_date) between '"
		        + start + "' and '" + end + "'";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(servedPatientSql);
		
		List<BigInteger> servedPatientData = new ArrayList<BigInteger>();
		if (!"0".equalsIgnoreCase(code)) {
			query = (SQLQuery) query.setString("code", code);
			//servedPatientData = query.setString("code", code).list();
		}
		
		if (!"".equalsIgnoreCase(dataCollector)) {
			query = (SQLQuery) query.setString("dataCollector", dataCollector);
		}
		
		servedPatientData = query.list();
		
		for (BigInteger servedValue : servedPatientData) {
			dashboardDTO.setServedPatient(servedValue.intValue());
		}
		
		String earnedPatientClinicCondition = "";
		String earnedPatientDataCollectorCondition = "";
		if (!"0".equalsIgnoreCase(code)) {
			earnedPatientClinicCondition = " and mr.clinic_code = :code ";
		}
		
		if (!"".equalsIgnoreCase(dataCollector)) {
			earnedPatientDataCollectorCondition = " and data_collector = :dataCollector ";
		}
		String earnedPatientSql = "SELECT sum(net_payable) FROM openmrs.psi_service_provision as sp left join openmrs.psi_money_receipt as mr  on sp.psi_money_receipt_id = mr.mid where sp.is_complete = 1 and DATE(sp.money_receipt_date) between '"
		        + start + "' and '" + end + "'" + earnedPatientClinicCondition + earnedPatientDataCollectorCondition + "";
		SQLQuery earnedQuery = sessionFactory.getCurrentSession().createSQLQuery(earnedPatientSql);
		List<Double> earnedData = new ArrayList<Double>();
		if (!"0".equalsIgnoreCase(code)) {
			earnedQuery = (SQLQuery) earnedQuery.setString("code", code);
		}
		if (!"".equalsIgnoreCase(dataCollector)) {
			earnedQuery = (SQLQuery) earnedQuery.setString("dataCollector", dataCollector);
		}
		earnedData = earnedQuery.list();
		
		if (earnedData.size() != 0) {
			for (Double double1 : earnedData) {
				if (double1 != null) {
					dashboardDTO.setEarned(double1.intValue());
				}
			}
		} else {
			dashboardDTO.setEarned(0);
		}
		
		String newPatienClinicCondition = "";
		String newPatienDataCollectorCondition = "";
		if (!"0".equalsIgnoreCase(code)) {
			newPatienClinicCondition = " person_attribute_type_id = :typeId  and value = :code and ";
		}
		int creator = 0;
		if (!"".equalsIgnoreCase(dataCollector)) {
			User findUser = Context.getService(UserService.class).getUserByUsername(dataCollector);
			creator = findUser.getId();
			newPatienDataCollectorCondition = " p.creator = :creator   and ";
		}
		String newPatientSql = "SELECT count(distinct(p.patient_id)) FROM openmrs.patient as p left join openmrs.person_attribute  as pa on p.patient_id = pa.person_id where "
		        + newPatienClinicCondition
		        + newPatienDataCollectorCondition
		        + "  DATE(p.date_created) between '"
		        + start
		        + "' and '" + end + "'";
		SQLQuery newPatientQuery = sessionFactory.getCurrentSession().createSQLQuery(newPatientSql);
		List<BigInteger> newPatientData = new ArrayList<BigInteger>();
		if (!"0".equalsIgnoreCase(code)) {
			newPatientQuery = (SQLQuery) newPatientQuery.setString("code", code).setInteger("typeId",
			    PSIConstants.attributeTypeClinicCode);
			
		}
		if (!"".equalsIgnoreCase(dataCollector)) {
			newPatientQuery = (SQLQuery) newPatientQuery.setInteger("creator", creator);
		}
		newPatientData = newPatientQuery.list();
		
		for (BigInteger newPatient : newPatientData) {
			dashboardDTO.setNewPatient(newPatient.intValue());
		}
		
		return dashboardDTO;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> findAllByTimestampNotSending(long timestamp) {
		List<PSIServiceProvision> lists = new ArrayList<PSIServiceProvision>();
		lists = sessionFactory
		        .getCurrentSession()
		        .createQuery("from PSIServiceProvision where  dhisId IS NULL and is_complete = :complete  order by spid asc")
		        .setLong("timestamp", timestamp).setInteger("complete", 1).list();
		
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> findAllResend() {
		List<PSIServiceProvision> lists = new ArrayList<PSIServiceProvision>();
		lists = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		            "from PSIServiceProvision where  (isSendToDHIS = :isSendToDHIS0 OR isSendToDHIS= :isSendToDHIS3) and is_complete = :complete  order by spid asc")
		        .setInteger("isSendToDHIS0", PSIConstants.DEFAULTERRORSTATUS)
		        .setInteger("isSendToDHIS3", PSIConstants.CONNECTIONTIMEOUTSTATUS).setInteger("complete", 1)
		        .setMaxResults(500).list();
		
		return lists;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceProvision> findAllByMoneyReceiptId(int moneyReceiptId) {
		List<PSIServiceProvision> lists = new ArrayList<PSIServiceProvision>();
		lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision where  psiMoneyReceiptId = :moneyReceiptId")
		        .setInteger("moneyReceiptId", moneyReceiptId).list();
		
		return lists;
	}
}
