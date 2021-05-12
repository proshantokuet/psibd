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
public class ExceptionDataSyncListner {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	
	private static final ReentrantLock lock = new ReentrantLock();

	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendExceptionSyncata() throws Exception {
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
					sendExceptionData();
					Thread.sleep(1000);
				}
				catch (Exception e) {
					
				}
				finally {
					lock.unlock();
					log.error("complete listener exceptiondata sync at:" +new Date());
				}
		}

	}
	
	
	public synchronized void sendExceptionData() {
		List<PSIDHISException> psidhisExceptions = Context.getService(PSIDHISExceptionService.class).getListOfDataToBeSynced(0);
			try {
				for (PSIDHISException psidhisException : psidhisExceptions) {
					JSONObject psiexceptionObject = new JSONObject();
					psiexceptionObject.putOpt("json", psidhisException.getJson());
					psiexceptionObject.putOpt("markId", 0);
					psiexceptionObject.putOpt("url", psidhisException.getUrl());
					psiexceptionObject.putOpt("status", psidhisException.getStatus());
					psiexceptionObject.putOpt("error", psidhisException.getError());
					psiexceptionObject.putOpt("response", psidhisException.getResponse());
					psiexceptionObject.putOpt("patientUuid", psidhisException.getPatientUuid());
					psiexceptionObject.putOpt("referenceId", psidhisException.getReferenceId());
					String url = "/rest/v1/exception-sync/save-update";
					JSONObject response = psiapiServiceFactory.getAPIType("openmrs").postInRemoteOpenMRS("", psiexceptionObject, url);
					if(response.has("isSuccess")) {
						Boolean successResult = response.getBoolean("isSuccess");
						log.error("successResult" + successResult);
						if(successResult) {
							psidhisException.setIsSync(1);
							Context.openSession();
							Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
							Context.clearSession();
						}
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		 
	}
	
