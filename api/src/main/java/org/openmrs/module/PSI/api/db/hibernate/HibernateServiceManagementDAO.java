package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.PSIServiceManagement;
import org.openmrs.module.PSI.SHNFormPdfDetails;
import org.openmrs.module.PSI.api.db.PSIServiceManagementDAO;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;

public class HibernateServiceManagementDAO implements PSIServiceManagementDAO {
	
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
	public PSIServiceManagement saveOrUpdate(PSIServiceManagement psiServiceManagement) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(psiServiceManagement);
		return psiServiceManagement;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceManagement> getAllByClinicId(int clinicId) {
		List<PSIServiceManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where psiClinicManagement=:clinicId and type='SERVICE' order by name asc ")
		        .setInteger("clinicId", clinicId).list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceManagement> getAllServiceByClinicIdAndType(
			int clinicId, String type) {
		List<PSIServiceManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where psiClinicManagement=:clinicId and type=:servicetype order by sid asc ")
		        .setInteger("clinicId", clinicId)
		        .setString("servicetype", type).list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceManagement findById(int id) {
		
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where sid = :id").setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public float getUnitCostByName(String name) {
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where name = :name").setString("name", name).list();
		if (lists.size() != 0) {
			return lists.get(0).getUnitCost();
		} else {
			return -1;
		}
	}
	
	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().delete(findById(id));
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceManagement findByCodeAndClinicId(String code, int clinicId) {
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where code = :code and  psiClinicManagement=:clinicId ")
		        .setString("code", code).setInteger("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PSIServiceManagement findByIdNotByClinicId(int id, String code, int clinicId) {
		List<PSIServiceManagement> lists = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement where sid !=:id and  psiClinicManagement=:clinicId and code=:code")
		        .setString("code", code).setInteger("id", id).setInteger("clinicId", clinicId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceManagement> getAll() {
		List<PSIServiceManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement  order by sid desc").list();
		if (clinics.size() != 0) {
			return clinics;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PSIServiceManagement> getAllByClinicIdAgeGender(int clinicId, int age, String gender) {
		// TODO Auto-generated method stub
		List<PSIServiceManagement> clinics = new ArrayList<PSIServiceManagement>();
		clinics = sessionFactory
		        .getCurrentSession()
		        .createQuery(
		            "  from PSIServiceManagement where ((gender = :gender AND (age_start = 0 and age_end= 0)) OR "
		                    + " (gender = '' AND (" + age + "  between age_start and  age_end)) OR "
		                    + " (gender = '' AND (age_start = 0 and age_end= 0)) OR  (gender = :gender and " + age
		                    + " between age_start and  age_end) ) "
		                    + " and  psi_clinic_management_id = :psi_clinic_management_id order by name asc ")
		        .setString("gender", gender).setInteger("psi_clinic_management_id", clinicId).list();
		
		return clinics;
		
	}
	
	@Override
	public List<String> getCategoryList(Integer clinicId) {
		// TODO Auto-generated method stub
		List<String> categoryList = new ArrayList<String>();
		String sql = "SELECT DISTINCT(category) " + " FROM psi_service_management " + " where psi_clinic_management_id = "
		        + clinicId;
		categoryList = sessionFactory.getCurrentSession().createSQLQuery(sql).list();
		return categoryList;
	}
	
	@Override
	public int updatePrimaryKey(int oldId, int currentId) {
		String sql = "update psi_service_management set sid= :currentId where sid= :oldId";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("currentId", currentId);
		query.setParameter("oldId", oldId);
		
		return query.executeUpdate();
	}

	@Override
	public PSIServiceManagement findByClinicIdDescending() {
		List<PSIServiceManagement> clinics = sessionFactory.getCurrentSession()
		        .createQuery("from PSIServiceManagement  order by sid desc").setMaxResults(1).list();
		if (clinics.size() != 0) {
			return clinics.get(0);
		}
		return null;
	}

	@Override
	public int updateTableAutoIncrementValue(int autoIncrementNo) {
		String sql = "ALTER TABLE psi_service_management AUTO_INCREMENT = :icrementalvalue";
		Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
		query.setParameter("icrementalvalue", autoIncrementNo);	
		return query.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClinicServiceDTO> getProductListAll(int clinicId,int productId) {
/*		String productIdString = "";
		if(productId != 0) {
			productIdString = " and p.sid ="+ productId;
		}
		*/
		List<ClinicServiceDTO> clinics = new ArrayList<ClinicServiceDTO>();
		String productListQuery = "CALL getProductListWithCurrentStock("+clinicId+", "+productId+")";
		/*String productListQuery = ""
				+ "SELECT p.sid as sid, "
				+ "       p.NAME as name, "
				+ "       p.code, "
				+ "       p.brand_name as brandName, "
				+ "       p.category as category, "
				+ "       c.NAME as clinicName, "
				+ "       p.purchase_price as purchasePrice, "
				+ "       p.unit_cost as unitCost, "
				+ "       COALESCE(stock.currentStock,0) as stock, "
				+ "       p.voided "
				+ "FROM   psi_service_management p "
				+ "       JOIN psi_clinic c ON p.psi_clinic_management_id = c.cid "
				+ "       LEFT JOIN (select s.clinic_code,s.clinic_name,CAST(SUM(sd.debit) AS UNSIGNED) as currentStock,sd.product_name,sd.product_id "
				+ "       from shn_stock s  join shn_stock_details sd on s.stkid = sd.shn_stock_id "
				+ "       GROUP by sd.product_id) as stock on stock.product_id = p.sid "
				+ "       where p.psi_clinic_management_id =  "+clinicId+"  and p.service_type = \"PRODUCT\" " + productIdString +" ";*/
		
		try{
			log.error("Query" + productListQuery);
			clinics = sessionFactory.getCurrentSession().createSQLQuery(productListQuery)
					.addScalar("sid",StandardBasicTypes.INTEGER)
					.addScalar("name",StandardBasicTypes.STRING)
					.addScalar("code",StandardBasicTypes.STRING)
					.addScalar("brandName",StandardBasicTypes.STRING)
					.addScalar("category",StandardBasicTypes.STRING)
					.addScalar("clinicName",StandardBasicTypes.STRING)
					.addScalar("purchasePrice",StandardBasicTypes.FLOAT)
					.addScalar("unitCost",StandardBasicTypes.FLOAT)
					.addScalar("stock",StandardBasicTypes.LONG)
					.addScalar("voided",StandardBasicTypes.BOOLEAN)
					.addScalar("earliestExpiry",StandardBasicTypes.STRING)
					.setResultTransformer(new AliasToBeanResultTransformer(ClinicServiceDTO.class)).
					list();
			log.error("Query Reuslt" + clinics.size());
			return clinics;
		}catch(Exception e){
			log.error(e.toString());
			return clinics;
		}
	}
}
