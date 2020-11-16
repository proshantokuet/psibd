package org.openmrs.module.PSI.api;

import java.util.List;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.dto.SHNFollowUPReportDTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface SHNFollowUpActionService  extends OpenmrsService {
	
	public SHNFollowUpAction saveOrUpdate(SHNFollowUpAction shnFollowUpAction);
	
	public SHNFollowUpAction findById(int id);
	
	public SHNFollowUpAction findByUuid (String uuid);
	
	public SHNFollowUpAction findByCodedConceptAndEncounter (int conceptId, String encounterUuid);
	
	public List<SHNFollowUPReportDTO> getfollowUpReprt();
	
}
