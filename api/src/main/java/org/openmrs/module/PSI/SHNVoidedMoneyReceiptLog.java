package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNVoidedMoneyReceiptLog extends BaseOpenmrsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int voidId;
	
	private int moneyReceiptId;
	
	private String eSlipNo;
	
	private String slipNo;
	
	private String clinicName;
	
	private String clinicCode;
	
	private String patientUuid;
	
	private int clinicId;
	
	private String dhisId;

	public int getVoidId() {
		return voidId;
	}

	public void setVoidId(int voidId) {
		this.voidId = voidId;
	}

	public int getMoneyReceiptId() {
		return moneyReceiptId;
	}

	public void setMoneyReceiptId(int moneyReceiptId) {
		this.moneyReceiptId = moneyReceiptId;
	}

	public String geteSlipNo() {
		return eSlipNo;
	}

	public void seteSlipNo(String eSlipNo) {
		this.eSlipNo = eSlipNo;
	}

	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public String getClinicName() {
		return clinicName;
	}

	public void setClinicName(String clinicName) {
		this.clinicName = clinicName;
	}



	public String getPatientUuid() {
		return patientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}

	public String getClinicCode() {
		return clinicCode;
	}

	public void setClinicCode(String clinicCode) {
		this.clinicCode = clinicCode;
	}

	public int getClinicId() {
		return clinicId;
	}

	public void setClinicId(int clinicId) {
		this.clinicId = clinicId;
	}

	public String getDhisId() {
		return dhisId;
	}

	public void setDhisId(String dhisId) {
		this.dhisId = dhisId;
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
