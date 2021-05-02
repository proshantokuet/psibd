package org.openmrs.module.PSI.web.listener;

import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIClinicManagement;
import org.openmrs.module.PSI.SHNStock;
import org.openmrs.module.PSI.SHNStockAdjust;
import org.openmrs.module.PSI.api.PSIClinicManagementService;
import org.openmrs.module.PSI.api.SHNStockService;
import org.openmrs.module.PSI.converter.SHNStockAdjustDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Configuration
@EnableAsync
@Controller
public class StockAndAdjustDataListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
			
	protected final Log log = LogFactory.getLog(getClass());
	
	private static final ReentrantLock lock = new ReentrantLock();

	
	@SuppressWarnings("rawtypes")
	public void sendData() throws Exception {
		if (!lock.tryLock()) {
			log.error("It is already in progress.");
	        return;
		}
		boolean status = true;
			try {
				String globalServerUrl = "/rest/v1/visittype";
				psiapiServiceFactory.getAPIType("openmrs").getFromRemoteOpenMRS("", "", globalServerUrl);	
				
			}
			catch (Exception e) {
				
				status = false;
			}
		if (status) {
				try {
					sendStockDataInGLobalServer();
				}
				catch (Exception e) {
					
				}
				try {
					sendAdjustStockDataInGLobalServer();
				}
				catch (Exception e) {
					
				}
				finally {
					lock.unlock();
					log.error("complete listener stock data sync at:" +new Date());
				}
		}

	}
	
	
	public synchronized void sendStockDataInGLobalServer() {
		List<PSIClinicManagement> clinic = Context.getService(PSIClinicManagementService.class).getAllClinic();
		if(clinic.size() > 0) {
			for (PSIClinicManagement psiClinicManagement : clinic) {
			List<SHNStock> getStockList = Context.getService(SHNStockService.class).getAllStockByClinicIdForSync(psiClinicManagement.getCid());
			log.error("getStockList Size "+ getStockList.size());
			try {
				log.error("ENtering in try catch "+ getStockList.size());
				String stockUrl = "/openmrs/ws/rest/v1/stock/getstock/get-converted-data/" + psiClinicManagement.getCid();
				JSONArray stockJsonArray = psiapiServiceFactory.getAPIType("openmrs").getJsonArray("", "", stockUrl);
				log.error("stockJsonArray Size "+ stockJsonArray.length());
				for (int i = 0; i < stockJsonArray.length(); i++) {
					log.error("ENtering in loop "+ stockJsonArray.length());
					JSONObject stockJsonObeject = stockJsonArray.getJSONObject(i);
					log.error("stockJsonObeject "+ stockJsonObeject.toString());
					String url = "/rest/v1/stock/save-update-inGlobal";
					JSONObject Stock = psiapiServiceFactory.getAPIType("openmrs").postInRemoteOpenMRS("", stockJsonObeject, url);
					log.error("response"+ Stock.toString());
				}
			} catch (JSONException e) {
				log.error("Exception Happened "+ getStockList.size());
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("Error in stock "+ e.getMessage().toString());
			}

			log.error("Leaving try catch "+ getStockList.size());
			
		   }
		}
		
	}
	
	public synchronized void sendAdjustStockDataInGLobalServer() {
		List<PSIClinicManagement> clinic = Context.getService(PSIClinicManagementService.class).getAllClinic();
		if(clinic.size() > 0) {
			for (PSIClinicManagement psiClinicManagement : clinic) {
			List<SHNStockAdjust> getStockList = Context.getService(SHNStockService.class).getAllAdjustHistoryForSync(psiClinicManagement.getCid());
			log.error("getAdjustStockList Size "+ getStockList.size());
			try {
				JSONArray stockJsonArray = new SHNStockAdjustDataConverter().toConvert(getStockList);
				for (int i = 0; i < stockJsonArray.length(); i++) {
					JSONObject stockJsonObeject = stockJsonArray.getJSONObject(i);
					log.error("Adjust Json Object"+ stockJsonObeject.toString());
					String url = "/rest/v1/stock/adjust-save-update-in-global";
					JSONObject Stock = psiapiServiceFactory.getAPIType("openmrs").postInRemoteOpenMRS("", stockJsonObeject, url);
					log.error("response"+ Stock.toString());
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
			
		}
		 
	}

}
	
