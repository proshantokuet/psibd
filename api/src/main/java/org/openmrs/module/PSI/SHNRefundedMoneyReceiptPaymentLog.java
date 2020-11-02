package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class SHNRefundedMoneyReceiptPaymentLog extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int refundPaymentId;
	
	private String eslipNo;
	
	private SHNRefundedMoneyReceipt refundedMoneyReceiptId;
	
	private Date receiveDate;
	
	private float receiveAmount;

	public int getRefundPaymentId() {
		return refundPaymentId;
	}

	public void setRefundPaymentId(int refundPaymentId) {
		this.refundPaymentId = refundPaymentId;
	}

	public String getEslipNo() {
		return eslipNo;
	}

	public void setEslipNo(String eslipNo) {
		this.eslipNo = eslipNo;
	}

	public SHNRefundedMoneyReceipt getRefundedMoneyReceiptId() {
		return refundedMoneyReceiptId;
	}

	public void setRefundedMoneyReceiptId(
			SHNRefundedMoneyReceipt refundedMoneyReceiptId) {
		this.refundedMoneyReceiptId = refundedMoneyReceiptId;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public float getReceiveAmount() {
		return receiveAmount;
	}

	public void setReceiveAmount(float receiveAmount) {
		this.receiveAmount = receiveAmount;
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
