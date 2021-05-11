package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.PSIDHISException;
import org.openmrs.module.PSI.dto.SHNDataSyncStatusDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PSIDHISExceptionService extends OpenmrsService {
	
	public PSIDHISException saveOrUpdate(PSIDHISException psidhisException);
	
	public PSIDHISException findReferenceIdOfPatient(String patientUuid,int status);
	
	public List<PSIDHISException> findAllByStatus(int status);
	
	//public List<PSIDHISException> findAllFailedEncounterByStatus(int status);
	
	public PSIDHISException findAllById(int patientId);
	
	//public PSIDHISException findAllBymarkerIdAndFormName(int markerId, String formsName);
	
	public SHNDataSyncStatusDTO findStatusToSendDataDhis(String type, String uuid);
	
	public List<PSIDHISException> getListOfDataToBeSynced(int status);
	
	public int updateExecuteInDatabase(String sql);
	
}
