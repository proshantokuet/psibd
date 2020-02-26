package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.AUHCDhisErrorVisualize;

public interface AUHCDhisErrorVisualizeService extends OpenmrsService{
	
	public String getPatientToDhisSyncInformation(String status, String clinicCode);
	
	public String getMoneyReceiptToDhisSyncInformation(String status, String clinicCode);
	
	public List<AUHCDhisErrorVisualize> getPatientDhisSyncReport (AUHCDhisErrorVisualize auhcDhisErrorVisualize);
	
	public List<AUHCDhisErrorVisualize> getMoneyReceiptDhisSyncReport (AUHCDhisErrorVisualize auhcDhisErrorVisualize);
	
}
