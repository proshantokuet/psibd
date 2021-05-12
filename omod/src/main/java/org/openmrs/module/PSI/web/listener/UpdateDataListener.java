package org.openmrs.module.PSI.web.listener;

import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.api.PSIDHISExceptionService;
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
public class UpdateDataListener {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	private static final ReentrantLock lock = new ReentrantLock();

	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void updateData() throws Exception {
		if (!lock.tryLock()) {
			log.error("It is already in progress.");
	        return;
		}
			try {
				updateMoneReceiptStatus();
				Thread.sleep(1000);
			}
			catch (Exception e) {
				
			}
			finally {
				lock.unlock();
				log.error("complete listener UpdateDataListener sync at:" +new Date());
			}
	}
	
	
	public synchronized void updateMoneReceiptStatus() {

			String updateSql = "update psi_service_provision set dhis_id = '' where dhis_id = 'null' and is_send_to_dhis = 2";
			Context.openSession();
			Context.getService(PSIDHISExceptionService.class).updateExecuteInDatabase(updateSql);
			Context.clearSession();
			
			String statusChangeSql = "update psi_service_provision set is_send_to_dhis = 3  where is_send_to_dhis = 2 and error like '%No Track Entity Instances found in DHIS2%'";
			Context.openSession();
			Context.getService(PSIDHISExceptionService.class).updateExecuteInDatabase(statusChangeSql);
			Context.clearSession();
		}
	}
	
