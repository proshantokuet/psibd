package org.openmrs.module.PSI.web.listener;

import java.util.List;
import java.util.ResourceBundle;

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
	
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@SuppressWarnings("rawtypes")
	public void sendExceptionSyncata() throws Exception {
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
				}
				catch (Exception e) {
					
				}
		}

	}
	
	
	public void sendExceptionData() {
		List<PSIDHISException> psidhisExceptions = Context.getService(PSIDHISExceptionService.class).getListOfDataToBeSynced(0);
			try {
				for (PSIDHISException psidhisException : psidhisExceptions) {
					JSONObject psiexceptionObject = new JSONObject();
					psiexceptionObject.putOpt("json", psidhisException.getJson());
					psiexceptionObject.putOpt("markId", psidhisException.getMarkId());
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
						if(successResult) {
							psidhisException.setIsSync(1);
							Context.getService(PSIDHISExceptionService.class).saveOrUpdate(psidhisException);
						}
					}
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		 
	}
	
