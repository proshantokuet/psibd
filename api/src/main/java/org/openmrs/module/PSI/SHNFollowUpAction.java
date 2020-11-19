package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class SHNFollowUpAction extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int followUpActionId;
	
	private String encounterUuid;
	
	private String visitUuid;
	
	private int valueCoded;
	
	private String codedConceptName;
	
	private Date contactDate;
	
	private Date lastTimeStamp;
	
	private Boolean isResponded;
	
	private String respondResult;
	
	private Boolean valueChanged;
	
	private int followUpCounts;




	public int getFollowUpActionId() {
		return followUpActionId;
	}

	public void setFollowUpActionId(int followUpActionId) {
		this.followUpActionId = followUpActionId;
	}

	public String getEncounterUuid() {
		return encounterUuid;
	}

	public void setEncounterUuid(String encounterUuid) {
		this.encounterUuid = encounterUuid;
	}

	public String getVisitUuid() {
		return visitUuid;
	}

	public void setVisitUuid(String visitUuid) {
		this.visitUuid = visitUuid;
	}

	public int getValueCoded() {
		return valueCoded;
	}

	public void setValueCoded(int valueCoded) {
		this.valueCoded = valueCoded;
	}

	public String getCodedConceptName() {
		return codedConceptName;
	}

	public void setCodedConceptName(String codedConceptName) {
		this.codedConceptName = codedConceptName;
	}

	public Date getContactDate() {
		return contactDate;
	}

	public void setContactDate(Date contactDate) {
		this.contactDate = contactDate;
	}

	public Date getLastTimeStamp() {
		return lastTimeStamp;
	}

	public void setLastTimeStamp(Date lastTimeStamp) {
		this.lastTimeStamp = lastTimeStamp;
	}

	public Boolean getIsResponded() {
		return isResponded;
	}

	public void setIsResponded(Boolean isResponded) {
		this.isResponded = isResponded;
	}

	public String getRespondResult() {
		return respondResult;
	}

	public void setRespondResult(String respondResult) {
		this.respondResult = respondResult;
	}

	public Boolean getValueChanged() {
		return valueChanged;
	}

	public void setValueChanged(Boolean valueChanged) {
		this.valueChanged = valueChanged;
	}

	public int getFollowUpCounts() {
		return followUpCounts;
	}

	public void setFollowUpCounts(int followUpCounts) {
		this.followUpCounts = followUpCounts;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
