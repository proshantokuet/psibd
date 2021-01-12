package org.openmrs.module.PSI.api;

import java.util.List;

import javax.transaction.Transactional;

import org.openmrs.api.OpenmrsService;
import org.openmrs.module.PSI.HnqisToShnConfigMapping;
import org.openmrs.module.PSI.SHNDhisMultipleChoiceObsElement;
import org.openmrs.module.PSI.SHNDhisObsElement;

@Transactional
public interface SHNDhisObsElementService extends OpenmrsService {
	
	public List<SHNDhisObsElement> getAllDhisElement(String formName);
	
	public List<SHNDhisMultipleChoiceObsElement> getAllMultipleChoiceDhisElement(String formName);
	
	public List<HnqisToShnConfigMapping> getAllConfigMappingData();
}
