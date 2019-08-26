package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class PSIClinicUser extends BaseOpenmrsData implements Serializable {
	
	/**
     * 
     */
	private static final long serialVersionUID = 1L;
	
	private int cuid;
	
	private String userName;
	
	private String userUuid;
	private String mobile;
	private String email;
	private int personId;
	
	private PSIClinicManagement psiClinicManagementId;
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserUuid() {
		return userUuid;
	}
	
	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	
	public PSIClinicManagement getPsiClinicManagementId() {
		return psiClinicManagementId;
	}
	
	public void setPsiClinicManagementId(PSIClinicManagement psiClinicManagementId) {
		this.psiClinicManagementId = psiClinicManagementId;
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
	
	public int getCuid() {
		return cuid;
	}
	
	public void setCuid(int cuid) {
		this.cuid = cuid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
	
}
