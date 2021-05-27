package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.AUHCDhisErrorVisualize;

public interface AUHCDhisErrorVisualizeDAO {
	
	public String getPatientToDhisSyncInformation(String status, String clinicCode);
	
	public String getMoneyReceiptToDhisSyncInformation(String status, String clinicCode);
	
	public List<AUHCDhisErrorVisualize> getPatientDhisSyncReport (AUHCDhisErrorVisualize auhcDhisErrorVisualize);
	
	public List<AUHCDhisErrorVisualize> getMoneyReceiptDhisSyncReport (AUHCDhisErrorVisualize auhcDhisErrorVisualize);
	
	public String getDataToGlobalSyncInformationByType(String type);
	
	public List<AUHCDhisErrorVisualize> getDataToGLobalSyncReport(String type);

}
