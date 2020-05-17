package org.openmrs.module.PSI;

import java.io.Serializable;

import org.openmrs.BaseOpenmrsData;

public class SHNFormPdfDetails extends BaseOpenmrsData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String question;
	
	private String answer;
	
	private String visit_uuid;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getVisit_uuid() {
		return visit_uuid;
	}

	public void setVisit_uuid(String visit_uuid) {
		this.visit_uuid = visit_uuid;
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
