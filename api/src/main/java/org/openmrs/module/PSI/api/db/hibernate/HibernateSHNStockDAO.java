package org.openmrs.module.PSI.api.db.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.api.db.SHNStockDAO;
import org.openmrs.module.PSI.dto.ClinicServiceDTO;
import org.openmrs.module.PSI.dto.SHNStockAdjustDTO;
import org.openmrs.module.PSI.dto.SHNStockDTO;
import org.openmrs.module.PSI.dto.SHNStockDetailsDTO;

public class HibernateSHNStockDAO implements SHNStockDAO {
	
	protected final Log log = LogFactory.getLog(getClass());
	
	private SessionFactory sessionFactory;


	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}


	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	@Override
	public SHNStock saveOrUpdate(SHNStock shnStock) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnStock);
		return shnStock;
	}


	@SuppressWarnings("unchecked")
	@Override
	public SHNStock findById(int id) {
		List<SHNStock> lists = sessionFactory.getCurrentSession().createQuery("from SHNStock where stkid = :id")
		        .setInteger("id", id).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNStock> getAllStockByClinicCode(String clinincCode) {
		List<SHNStock> stocks = new ArrayList<SHNStock>();
		String stockSql = "select s.stkid,s.invoice_number as invoiceNumber,Date(s.recieve_date)receiveDate,s.stock_in_id as stockInId from shn_stock s where s.clinic_code = '" +clinincCode+ "'  order by s.recieve_date DESC";
		log.error("Query" + stockSql);
		try {
			stocks = sessionFactory.getCurrentSession().createSQLQuery(stockSql)
					 .addScalar("stkid",StandardBasicTypes.INTEGER)
					 .addScalar("invoiceNumber",StandardBasicTypes.STRING)
					 .addScalar("receiveDate",StandardBasicTypes.DATE)
					 .addScalar("stockInId", StandardBasicTypes.STRING)
					 .setResultTransformer(new AliasToBeanResultTransformer(SHNStock.class)).list();
			log.error("Query Size" + stocks.size());
			return stocks;
		} catch (Exception e) {
			log.error(e.toString());
			return stocks;
		}

	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNStockDetailsDTO> getStockDetailsByStockId(int stockId) {
		List<SHNStockDetailsDTO> stocks = new ArrayList<SHNStockDetailsDTO>();
		String stockDetailsSql = "select product_id as productID,product_name as productName,debit,Date(expiry_date)as expiryDate from shn_stock_details where shn_stock_id = "+stockId+"";
		log.error("Query" + stockDetailsSql);
		try {
			stocks = sessionFactory.getCurrentSession().createSQLQuery(stockDetailsSql)
					 .addScalar("productID",StandardBasicTypes.INTEGER)
					 .addScalar("productName",StandardBasicTypes.STRING)
					 .addScalar("expiryDate",StandardBasicTypes.DATE)
					 .addScalar("debit", StandardBasicTypes.INTEGER)
					 .setResultTransformer(new AliasToBeanResultTransformer(SHNStockDetailsDTO.class)).list();
			log.error("Query Size" + stocks.size());
			return stocks;
		} catch (Exception e) {
			log.error(e.toString());
			return stocks;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNStockDTO> findStockByPrductIdInvoiceAndExpiryDate(int productId,
			String invoiceNo, String expiryDate) {
		List<SHNStockDTO> stocks = new ArrayList<SHNStockDTO>();
		String StockHql = ""
				+ "SELECT s.stkid          AS stockId, "
				+ "       s.invoice_number AS invoiceNumber "
				+ "FROM   shn_stock s "
				+ "       JOIN shn_stock_details sd "
				+ "         ON s.stkid = sd.shn_stock_id "
				+ "WHERE  s.invoice_number = '"+invoiceNo+"' "
				+ "       AND sd.product_id = "+productId+" "
				+ "       AND Date(sd.expiry_date) = Date('"+expiryDate+"')";		
		log.error("Query" + StockHql);
		try {
			stocks = sessionFactory.getCurrentSession().createSQLQuery(StockHql)
					 .addScalar("stockId",StandardBasicTypes.INTEGER)
					 .addScalar("invoiceNumber",StandardBasicTypes.STRING)
					 .setResultTransformer(new AliasToBeanResultTransformer(SHNStockDTO.class)).list();
			log.error("Query Size" + stocks.size());
			return stocks;
		} catch (Exception e) {
			log.error(e.toString());
			return stocks;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public String updateStockByEarliestExpiryDate(String eslipNo,String clinicCode) {
		List<String> updatesTockDetailsId = new ArrayList<String>();
		String StockHql = "CALL stockUpdateByEarliestExpiryDate('"+eslipNo+"','"+clinicCode+"')";					
		log.error("Query" + StockHql);
		try {
			updatesTockDetailsId = sessionFactory.getCurrentSession().createSQLQuery(StockHql).list();
			log.error("Query size" + updatesTockDetailsId.size());
			if(updatesTockDetailsId.size() > 0) {
				log.error("Query result" + updatesTockDetailsId.get(0));
				return updatesTockDetailsId.get(0);
			}
			else {
				return "No Stock Updated";
			}
			
		} catch (Exception e) {
			log.error(e.toString());
			return e.toString();
		}
	}


	@Override
	public SHNStockAdjust saveOrUpdateStockAdjust(SHNStockAdjust shnStockAdjust) {
		// TODO Auto-generated method stub
		sessionFactory.getCurrentSession().saveOrUpdate(shnStockAdjust);
		return shnStockAdjust;
	}


	@SuppressWarnings("unchecked")
	@Override
	public SHNStockAdjust getAdjustHistoryById(int adjustId) {
		List<SHNStockAdjust> lists = sessionFactory.getCurrentSession().createQuery("from SHNStockAdjust where adjustId = :id")
		        .setInteger("id", adjustId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}


	@Override
	public List<SHNStockAdjustDTO> getAdjustHistoryAllByClinic(int clinicId) {
		// TODO Auto-generated method stub
		return null;
	}

}
