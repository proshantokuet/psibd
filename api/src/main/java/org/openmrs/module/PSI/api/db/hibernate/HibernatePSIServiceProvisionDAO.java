package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.api.db.PSIServiceProvisionDAO;
import org.openmrs.module.PSI.dto.PSIReport;

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
		lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceProvision where timestamp > :timestamp order by timestamp asc ")
		        .setLong("timestamp", timestamp).setMaxResults(500)
		        
		        .list();
		return lists;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<PSIReport> servicePointWiseReport(String startDate, String endDate, String code) {
		List<Object[]> data = null;
		List<PSIReport> reportDTOs = new ArrayList<PSIReport>();
		
		String sql = "select code, item, category, sum(Clilic) as Clilic, sum(Satellite) as Satellite,  sum(CSP) as CSP , sum(Clilic)+sum(Satellite)+sum(CSP) as total from ( select code,item ,  category,service_point, sum(net_payable) as ttt,count(*), CASE WHEN service_point = 'Clinic' THEN sum(net_payable) ELSE 0 END Clilic,  CASE WHEN service_point = 'Satellite' THEN sum(net_payable) ELSE 0 END Satellite, CASE WHEN service_point = 'CSP' THEN sum(net_payable)  ELSE 0 END CSP from openmrs.psi_service_provision as sp  left join  openmrs.psi_money_receipt as mr on  sp.psi_money_receipt_id =mr.mid  where DATE(sp.money_receipt_date)  between  '"
		        + startDate
		        + "'  and  '"
		        + endDate
		        + "'  and clinic_code = '"
		        + code
		        + "'   group by code ,item,service_point,category order  by code) as Report  group by code, item ";
		SQLQuery query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		
		data = query.list();
		
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
	
	@Override
	public List<PSIReport> serviceProviderWiseReport(String startDate, String endDate, String code, int provider) {
		// TODO Auto-generated method stub
		return null;
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
}
