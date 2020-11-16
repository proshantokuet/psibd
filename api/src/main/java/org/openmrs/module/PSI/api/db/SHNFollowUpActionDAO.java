package org.openmrs.module.PSI.api.db;

import java.util.List;

import org.openmrs.module.PSI.SHNFollowUpAction;
import org.openmrs.module.PSI.dto.SHNFollowUPReportDTO;

public interface SHNFollowUpActionDAO {

	public SHNFollowUpAction saveOrUpdate(SHNFollowUpAction shnFollowUpAction);
	
	public SHNFollowUpAction findById(int id);
	
	public SHNFollowUpAction findByUuid (String uuid);
	
	public SHNFollowUpAction findByCodedConceptAndEncounter (int conceptId, String encounterUuid);
	
	public List<SHNFollowUPReportDTO> getfollowUpReprt();

}
