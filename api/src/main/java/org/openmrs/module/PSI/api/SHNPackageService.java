package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNPackageService extends OpenmrsService {

	public SHNPackage saveOrUpdate(SHNPackage shnPackage);
	
	public SHNPackage findById(int packageId);

	public List<SHNPackage> getAllPackageByClinic(String clinicCode);
	
	public SHNPackageDetails finPackageDetailsById (int packageDetailsId);
	
	public SHNPackage findbyPackageCode(String packageCode,String clinicCode,int packageId);
	
}
