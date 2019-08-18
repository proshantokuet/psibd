package org.openmrs.module.PSI;

import java.io.Serializable;
import java.sql.Date;

import org.openmrs.BaseOpenmrsData;

public class PSIClinicChild extends BaseOpenmrsData implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private int childId;
	
	private String motherUuid;
	
	private int outComeNo;
	
	private Date outComeDate; 
	
	private String result;
	
	private String complications;
	
	private String typeOfDelivery;
	
	private String sex;
	
	private float birthWeight;
	
	private String vaccine;
	
	private String lastHealthStatus;
	
	public int getChildID() {
		return childId;
	}

	public void setChildID(int childID) {
		this.childId = childID;
	}

	public String getMotherUuid() {
		return motherUuid;
	}

	public void setMotherUuid(String motherUuid) {
		this.motherUuid = motherUuid;
	}

	public int getOutComeNo() {
		return outComeNo;
	}

	public void setOutComeNo(int outComeNo) {
		this.outComeNo = outComeNo;
	}

	public Date getOutComeDate() {
		return outComeDate;
	}

	public void setOutComeDate(Date outComeDate) {
		this.outComeDate = outComeDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getComplications() {
		return complications;
	}

	public void setComplications(String complications) {
		this.complications = complications;
	}

	public String getTypeOfDelivery() {
		return typeOfDelivery;
	}

	public void setTypeOfDelivery(String typeOfDelivery) {
		this.typeOfDelivery = typeOfDelivery;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public float getBirthWeight() {
		return birthWeight;
	}

	public void setBirthWeight(float birthWeight) {
		this.birthWeight = birthWeight;
	}

	public String getVaccine() {
		return vaccine;
	}

	public void setVaccine(String vaccine) {
		this.vaccine = vaccine;
	}

	public String getLastHealthStatus() {
		return lastHealthStatus;
	}

	public void setLastHealthStatus(String lastHealthStatus) {
		this.lastHealthStatus = lastHealthStatus;
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
