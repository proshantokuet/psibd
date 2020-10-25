package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;

public interface SHNPackageDAO {
	
	public SHNPackage saveOrUpdate(SHNPackage shnPackage);
	
	public SHNPackage findById(int packageId);

	public List<SHNPackage> getAllPackageByClinic(String clinicCode);
	
	public SHNPackageDetails finPackageDetailsById (int packageDetailsId);
	
	public SHNPackage findbyPackageCode(String packageCode,String clinicCode,int packageId);



}
