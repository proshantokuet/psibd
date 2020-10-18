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
import org.openmrs.module.PSI.dto.SHNStockReportDTO;

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
	public SHNStockAdjustDTO getAdjustHistoryById(int adjustId,int clinicId) {
		List<SHNStockAdjustDTO> stocks = new ArrayList<SHNStockAdjustDTO>();
		String StockHql = ""
				+ "select sa.adjust_id as adjustId,Date(sa.adjust_date) as adjustDate, "
				+ "sa.product_id as productId, "
				+ "psm.name as productName, "
				+ "sa.previous_stock as previousStock, "
				+ "sa.changed_stock as changedStock, "
				+ "sa.adjust_reason as adjustReason "
				+ "from shn_stock_adjust sa "
				+ "join psi_service_management psm on sa.product_id = psm.sid and psm.service_type = 'PRODUCT' "
				+ "where sa.clinic_id = "+clinicId+" and sa.adjust_id = "+adjustId+"";	
		log.error("Query" + StockHql);
		try {
			stocks = sessionFactory.getCurrentSession().createSQLQuery(StockHql)
					 .addScalar("adjustId",StandardBasicTypes.INTEGER)
					 .addScalar("adjustDate",StandardBasicTypes.DATE)
					 .addScalar("productId",StandardBasicTypes.INTEGER)
					 .addScalar("productName",StandardBasicTypes.STRING)
					 .addScalar("previousStock",StandardBasicTypes.INTEGER)
					 .addScalar("changedStock",StandardBasicTypes.INTEGER)
					 .addScalar("adjustReason",StandardBasicTypes.STRING)
					 .setResultTransformer(new AliasToBeanResultTransformer(SHNStockAdjustDTO.class)).list();
			log.error("Query Size" + stocks.size());
			if(stocks.size() > 0) {
				return stocks.get(0);
			}
			else return null;
			
		} catch (Exception e) {
			log.error(e.toString());
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNStockAdjustDTO> getAdjustHistoryAllByClinic(int clinicId) {
		List<SHNStockAdjustDTO> stocks = new ArrayList<SHNStockAdjustDTO>();
		String StockHql = ""
				+ "SELECT sa.adjust_id         AS adjustId, "
				+ "       Date(sa.adjust_date) AS adjustDate, "
				+ "       sa.product_id        AS productId, "
				+ "       psm.NAME             AS productName, "
				+ "       sa.previous_stock    AS previousStock, "
				+ "       sa.changed_stock AS changedStock, "
				+ "       sa.adjust_reason     AS adjustReason "
				+ "FROM   shn_stock_adjust sa "
				+ "       JOIN psi_service_management psm "
				+ "         ON sa.product_id = psm.sid "
				+ "            AND psm.service_type = 'PRODUCT' "
				+ "WHERE  sa.clinic_id = "+clinicId+"";	
		log.error("Query" + StockHql);
		try {
			stocks = sessionFactory.getCurrentSession().createSQLQuery(StockHql)
					 .addScalar("adjustId",StandardBasicTypes.INTEGER)
					 .addScalar("adjustDate",StandardBasicTypes.DATE)
					 .addScalar("productId",StandardBasicTypes.INTEGER)
					 .addScalar("productName",StandardBasicTypes.STRING)
					 .addScalar("previousStock",StandardBasicTypes.INTEGER)
					 .addScalar("changedStock",StandardBasicTypes.INTEGER)
					 .addScalar("adjustReason",StandardBasicTypes.STRING)
					 .setResultTransformer(new AliasToBeanResultTransformer(SHNStockAdjustDTO.class)).list();
			log.error("Query Size" + stocks.size());
			return stocks;
		} catch (Exception e) {
			log.error(e.toString());
			return stocks;
		}
	}


	@Override
	public String adjustStockByEarliestExpiryDate(int quantity,String clinicCode, int productId) {
		List<String> updatesTockDetailsId = new ArrayList<String>();
		String StockHql = "CALL adjustStockByEarliestExpiryDate("+quantity+",'"+clinicCode+"',"+productId+")";					
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


	@SuppressWarnings("unchecked")
	@Override
	public SHNStockAdjust findAdjustById(int adjustId) {
		List<SHNStockAdjust> lists = sessionFactory.getCurrentSession().createQuery("from SHNStockAdjust where adjustId = :id")
		        .setInteger("id", adjustId).list();
		if (lists.size() != 0) {
			return lists.get(0);
		} else {
			return null;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<SHNStockReportDTO> getStockReportByClinic(String clinicCode,
			String category, int month, int year) {
		List<SHNStockReportDTO> stockReport = new ArrayList<SHNStockReportDTO>();
		String StockHql = "CALL getMonthlyStockReport('"+clinicCode+"', '"+category+"', "+month+", "+year+")";					
		log.error("Query" + StockHql);
		try {
			stockReport = sessionFactory.getCurrentSession().createSQLQuery(StockHql)
						 .addScalar("clinicname",StandardBasicTypes.STRING)
						 .addScalar("clinic_id",StandardBasicTypes.STRING)
						 .addScalar("productid",StandardBasicTypes.INTEGER)
						 .addScalar("productname",StandardBasicTypes.STRING)
						 .addScalar("category",StandardBasicTypes.STRING)
						 .addScalar("brandname",StandardBasicTypes.STRING)
						 .addScalar("earliestExpiry",StandardBasicTypes.STRING)
						 .addScalar("starting_balance",StandardBasicTypes.BIG_DECIMAL)
						 .addScalar("Sales",StandardBasicTypes.BIG_DECIMAL)
						 .addScalar("adjust",StandardBasicTypes.BIG_DECIMAL)
						 .addScalar("supply",StandardBasicTypes.BIG_DECIMAL)
						 .addScalar("endBalance",StandardBasicTypes.BIG_DECIMAL)
						 .setResultTransformer(new AliasToBeanResultTransformer(SHNStockReportDTO.class)).list();
			log.error("Query size" + stockReport.size());

			return stockReport;
			
		} catch (Exception e) {
			log.error(e.toString());
			return stockReport;
		}
	}


	@SuppressWarnings("unchecked")
	@Override
	public String deleteMoneyReceiptStockUpdate(String eslipNo,String clinicCode) {
		List<String> updatesTockDetailsId = new ArrayList<String>();
		String StockHql = "CALL deleteMoneyReceiptStockUpdate('"+eslipNo+"','"+clinicCode+"')";					
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
	
	

}
