package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class SHNRefundedMoneyReceiptDetails extends BaseOpenmrsData implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	private int refundedServicesId;
	
	private int serviceProvisionId;
	
	private String item;
	
	private String description;
	
	private String dhisId;
	
	private float unitCost;
	
	private int quantity;
	
	private float totalAmount;
	
	private float discount;
	
	private float netPayable;
	
	private Date moneyReceiptDate;
	
	private String patientUuid;
	
	private SHNRefundedMoneyReceipt refundedMoneyReceiptId;
	
	private long timestamp;
	
	private String field1;
	
	private String field2;
	
	private int field3;
	
	private String code;
	
	private String category;
	
	private String provider;
	
	private int isComplete;
	
	private int isSendToDHIS;
	
	private String error;
	
	private String serviceType;
	
	private String packageUuid;

	private float financialDiscount;


	public int getRefundedServicesId() {
		return refundedServicesId;
	}

	public void setRefundedServicesId(int refundedServicesId) {
		this.refundedServicesId = refundedServicesId;
	}

	public int getServiceProvisionId() {
		return serviceProvisionId;
	}

	public void setServiceProvisionId(int serviceProvisionId) {
		this.serviceProvisionId = serviceProvisionId;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDhisId() {
		return dhisId;
	}

	public void setDhisId(String dhisId) {
		this.dhisId = dhisId;
	}

	public float getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(float unitCost) {
		this.unitCost = unitCost;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public float getNetPayable() {
		return netPayable;
	}

	public void setNetPayable(float netPayable) {
		this.netPayable = netPayable;
	}

	public Date getMoneyReceiptDate() {
		return moneyReceiptDate;
	}

	public void setMoneyReceiptDate(Date moneyReceiptDate) {
		this.moneyReceiptDate = moneyReceiptDate;
	}

	public String getPatientUuid() {
		return patientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		this.patientUuid = patientUuid;
	}

	public SHNRefundedMoneyReceipt getRefundedMoneyReceiptId() {
		return refundedMoneyReceiptId;
	}

	public void setRefundedMoneyReceiptId(
			SHNRefundedMoneyReceipt refundedMoneyReceiptId) {
		this.refundedMoneyReceiptId = refundedMoneyReceiptId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public int getField3() {
		return field3;
	}

	public void setField3(int field3) {
		this.field3 = field3;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public int getIsComplete() {
		return isComplete;
	}

	public void setIsComplete(int isComplete) {
		this.isComplete = isComplete;
	}

	public int getIsSendToDHIS() {
		return isSendToDHIS;
	}

	public void setIsSendToDHIS(int isSendToDHIS) {
		this.isSendToDHIS = isSendToDHIS;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getPackageUuid() {
		return packageUuid;
	}

	public void setPackageUuid(String packageUuid) {
		this.packageUuid = packageUuid;
	}

	public float getFinancialDiscount() {
		return financialDiscount;
	}

	public void setFinancialDiscount(float financialDiscount) {
		this.financialDiscount = financialDiscount;
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
