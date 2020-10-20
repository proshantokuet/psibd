package org.openmrs.module.PSI;

import java.io.Serializable;
import java.util.Date;

import org.openmrs.BaseOpenmrsData;

public class SHNMoneyReceiptPaymentLog extends BaseOpenmrsData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int paymentId;
	
	private String eslipNo;
	
	private PSIMoneyReceipt psiMoneyReceiptId;
	
	private Date receiveDate;
	
	private float receiveAmount;

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public String getEslipNo() {
		return eslipNo;
	}

	public void setEslipNo(String eslipNo) {
		this.eslipNo = eslipNo;
	}

	public Date getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(Date receiveDate) {
		this.receiveDate = receiveDate;
	}

	public PSIMoneyReceipt getPsiMoneyReceiptId() {
		return psiMoneyReceiptId;
	}

	public void setPsiMoneyReceiptId(PSIMoneyReceipt psiMoneyReceiptId) {
		this.psiMoneyReceiptId = psiMoneyReceiptId;
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
