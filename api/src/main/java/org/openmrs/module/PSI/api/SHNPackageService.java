package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNPackage;
import org.openmrs.module.PSI.SHNPackageDetails;
import org.openmrs.module.PSI.dto.SHNPackageReportDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNPackageService extends OpenmrsService {

	public SHNPackage saveOrUpdate(SHNPackage shnPackage);
	
	public SHNPackage findById(int packageId);
	
	public List<SHNPackage> getAllPackageByClinicId(int clinicId);
	
	public List<SHNPackage> getAllPackageByClinicIdWithVoided(int clinicId);

	public List<SHNPackage> getAllPackageByClinicCode(String clinicCode);
	
	public List<SHNPackageReportDTO> getPackageListForViewByCLinic(int clinicId);
	
	public List<SHNPackageReportDTO> getPackageByPackageIdForEdit(int packageId);
	
	public SHNPackageDetails findPackageDetailsById (int packageDetailsId);
	
	public SHNPackage findbyPackageCode(String packageCode, int clinicId, int packageId);
	
	public SHNPackage findPackageByUuid(String uuid);
	
	public SHNPackageDetails findPackageDetailsByUuid(String uuid);

	public void deletePackageHavingNullPackageId();
	
	public List<SHNPackageReportDTO> getstockStatusFromPackage(int clinicId, int quantity, int packageId);
	
	public SHNPackage findpackageByPackageCodeAndClinic(String packageCode,String clinicCode);
	
}
