package org.openmrs.module.PSI.web.controller.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.PSI.PSIMoneyReceipt;
import org.openmrs.module.PSI.PSIServiceProvision;
import org.openmrs.module.PSI.SHNMoneyReceiptPaymentLog;
import org.openmrs.module.PSI.api.PSIMoneyReceiptService;
import org.openmrs.module.PSI.dto.MoneyReceiptPdfDTO;
import org.openmrs.module.PSI.dto.UserDTO;
import org.openmrs.module.PSI.utils.HeaderFooterPageEventMoneyReceipt;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@RequestMapping("/rest/v1/generate/pdf")
@RestController
public class SHNMoneyReceiptPdfGeneratorRestController {

	Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
	
	Font textFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 13, BaseColor.BLACK);
	
	Font conditionFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
	
	Font paraFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK);
	
	Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
	
	protected final Log log = LogFactory.getLog(getClass());
	
//	@RequestMapping(value = "/moneyreceipt", method = RequestMethod.GET)
//	public void downloadFile(HttpServletResponse response,
//	                         HttpServletRequest request) throws IOException
//	{
//		//Document document = new Document();
//		byte [] finalByte;
//        Document document = new Document(PageSize.A4, 36, 36, 36, 72);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		try {
////			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/opt/bahmni/bahmni-emr/"
////			        + "money receipt" + ".pdf"));
//		                
//	        PdfWriter writer = PdfWriter.getInstance(document, baos);
//	        HeaderFooterPageEventMoneyReceipt event = new HeaderFooterPageEventMoneyReceipt();
//			writer.setPageEvent(event);
//			document.open();
//			PdfPTable patientInformtionTable = new PdfPTable(88);
//			patientInformtionTable.setWidthPercentage(100);
//			addPatientRows(patientInformtionTable);
//			document.add(patientInformtionTable);
//			document.add(new Paragraph("\n"));
//			document.add(new Paragraph(new Phrase("14. Service and Payable Amount",textFont)));
//			document.add(new Paragraph("\n"));
//	        float[] columnWidths = {1,4,6,3,3,3,3,4};
//			PdfPTable serviceInformtionTable = new PdfPTable(columnWidths);
//			serviceInformtionTable.setWidthPercentage(100);
//			serviceInformtionTable.setHeaderRows(8);
//			addServiceRow(serviceInformtionTable);
//			document.add(serviceInformtionTable);
//			PdfPTable receiveAmountTable = new PdfPTable(8);
//			receiveAmountTable.setWidthPercentage(100);
//			receiveAmountDetails(receiveAmountTable);
//			document.add(receiveAmountTable);
//			
//			
//			document.add(new Paragraph("\n"));
//			document.add(new Paragraph("\n"));
//			PdfPTable footerTable = new PdfPTable(5);
//			footerTable.setWidthPercentage(100);
//			footerRow(footerTable);
//			document.add(footerTable);
//			document.close();
//            response.setHeader("Expires", "0");
//            response.setHeader("Cache-Control",
//                "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Pragma", "public");
//            // setting the content type
//            response.setContentType("application/pdf");
//            // the contentlength
//            response.setContentLength(baos.size());
//            // write ByteArrayOutputStream to the ServletOutputStream
//            OutputStream os = response.getOutputStream();
//            baos.writeTo(os);
//            os.flush();
//            os.close();
//			
//		}
//		catch (DocumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

	
    @RequestMapping(value = "/moneyreceiptdirectpdf",consumes = "application/json", method = RequestMethod.POST)
    public ResponseEntity<byte[]> generatePdf(@RequestBody MoneyReceiptPdfDTO dto) throws IOException {
    	
        byte[] pdf = createPdf(dto);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "pdf"));
        headers.setContentDispositionFormData("attachment", "x.pdf");
        headers.setContentLength(pdf.length);
        return new ResponseEntity<byte[]>(pdf, headers, HttpStatus.OK);
    }
	
	
	private byte[] createPdf(MoneyReceiptPdfDTO dto) {
		//Document document = new Document();
		PSIMoneyReceipt psiMoneyReceipt = Context.getService(PSIMoneyReceiptService.class).findById(dto.getMid());
        Document document = new Document(PageSize.A4, 36, 36, 36, 72);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
//			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/opt/bahmni/bahmni-emr/"
//			        + "money receipt" + ".pdf"));
		                
	        PdfWriter writer = PdfWriter.getInstance(document, out);
	        HeaderFooterPageEventMoneyReceipt event = new HeaderFooterPageEventMoneyReceipt(dto);
			writer.setPageEvent(event);
			document.open();
			PdfPTable patientInformtionTable = new PdfPTable(88);
			patientInformtionTable.setWidthPercentage(100);
			addPatientRows(patientInformtionTable,dto,psiMoneyReceipt);
			document.add(patientInformtionTable);
			//document.add(new Paragraph("\n"));
			PdfPTable labelTable = new PdfPTable(1);
			labelTable.setWidthPercentage(100);
			PdfPCell labelCell = new PdfPCell(new Paragraph(new Phrase("14. Service and Payable Amount",textFont)));
			labelCell.disableBorderSide(Rectangle.LEFT);
			labelCell.disableBorderSide(Rectangle.RIGHT);
			labelCell.disableBorderSide(Rectangle.BOTTOM);
			labelCell.setExtraParagraphSpace(2);
			labelTable.addCell(labelCell);
			document.add(labelTable);
			//document.add(new Paragraph(new Phrase("14. Service and Payable Amount",textFont)));
			//document.add(new Paragraph("\n"));
	        float[] columnWidths = {1,4,6,3,3,3,3,4};
			PdfPTable serviceInformtionTable = new PdfPTable(columnWidths);
			serviceInformtionTable.setWidthPercentage(100);
			serviceInformtionTable.setHeaderRows(1);
			addServiceRow(serviceInformtionTable,psiMoneyReceipt,dto.getInWordsTaka());
			document.add(serviceInformtionTable);
			if(psiMoneyReceipt.getPayments().size() > 0) {
			PdfPTable receiveAmountTable = new PdfPTable(8);
			receiveAmountTable.setWidthPercentage(100);
			receiveAmountDetails(receiveAmountTable,psiMoneyReceipt);
			document.add(receiveAmountTable);
			}
			
			//document.add(new Paragraph("\n"));
			//document.add(new Paragraph("\n"));
			PdfPTable footerTable = new PdfPTable(5);
			footerTable.setWidthPercentage(100);
			footerRow(footerTable,dto.getGetDataCollectorFullname(),psiMoneyReceipt.getDesignation());
			document.add(footerTable);
			
		}
		catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			
			document.close();
		}
		 return out.toByteArray();
	}
	
	
	private void addPatientRows(PdfPTable table,MoneyReceiptPdfDTO dto,PSIMoneyReceipt psiMoneyReceipt){
		
		String clinicName = "Clinic Name: ";
		
		PdfPCell patientCell = new PdfPCell(new Paragraph(new Phrase(clinicName, paraFont)));
		//patientCell.setExtraParagraphSpace(2);
		patientCell.disableBorderSide(Rectangle.RIGHT);
		//patientCell.disableBorderSide(Rectangle.BOTTOM);
		patientCell.setColspan(15);
		patientCell.setLeading(3, 1);
		table.addCell(patientCell);
		
		String clinicNameAnswer = "Jatrabari Clinic";
		
		PdfPCell patientCellAnswer = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getClinicName(), textFont)));
		//patientCellAnswer.setExtraParagraphSpace(2);
		patientCellAnswer.disableBorderSide(Rectangle.LEFT);
		patientCellAnswer.setColspan(15);
		patientCellAnswer.disableBorderSide(Rectangle.RIGHT);
		//patientCellAnswer.disableBorderSide(Rectangle.BOTTOM);
		patientCellAnswer.setLeading(3, 1);
		table.addCell(patientCellAnswer);
		
		String clinicId = "Clinic ID: ";
		
		PdfPCell clinicIdCell = new PdfPCell(new Paragraph(new Phrase(clinicId, paraFont)));
		//clinicIdCell.setExtraParagraphSpace(2);
		clinicIdCell.disableBorderSide(Rectangle.LEFT);
		clinicIdCell.disableBorderSide(Rectangle.RIGHT);
		//clinicIdCell.disableBorderSide(Rectangle.BOTTOM);
		clinicIdCell.setLeading(3, 1);
		clinicIdCell.setColspan(10);
		table.addCell(clinicIdCell);
		
		String clinicIdAnswer = "000";
		
		PdfPCell clinicIdCellAnswer = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getClinicCode(), textFont)));
		//clinicIdCellAnswer.setExtraParagraphSpace(2);
		clinicIdCellAnswer.disableBorderSide(Rectangle.LEFT);
		clinicIdCellAnswer.disableBorderSide(Rectangle.RIGHT);
		//clinicIdCellAnswer.disableBorderSide(Rectangle.BOTTOM);
		clinicIdCellAnswer.setLeading(3, 1);
		clinicIdCellAnswer.setColspan(5);
		table.addCell(clinicIdCellAnswer);
		
		
		String sateliteClinicId = "Satellite Clinic ID: ";
		
		PdfPCell satelliteCLinicCell = new PdfPCell(new Paragraph(new Phrase(sateliteClinicId, paraFont)));
		//satelliteCLinicCell.setExtraParagraphSpace(2);
		satelliteCLinicCell.disableBorderSide(Rectangle.LEFT);
		satelliteCLinicCell.disableBorderSide(Rectangle.RIGHT);
		//satelliteCLinicCell.disableBorderSide(Rectangle.BOTTOM);
		satelliteCLinicCell.setLeading(3, 1);
		satelliteCLinicCell.setColspan(19);
		table.addCell(satelliteCLinicCell);
		
		String sateliteClinicIdAnswer = "10";
		
		PdfPCell sateliteClinicIdAnswerCell = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getSateliteClinicId(), textFont)));
		//sateliteClinicIdAnswerCell.setExtraParagraphSpace(2);
		sateliteClinicIdAnswerCell.disableBorderSide(Rectangle.LEFT);
		sateliteClinicIdAnswerCell.disableBorderSide(Rectangle.RIGHT);
		//sateliteClinicIdAnswerCell.disableBorderSide(Rectangle.BOTTOM);
		sateliteClinicIdAnswerCell.setLeading(3, 1);
		sateliteClinicIdAnswerCell.setColspan(5);
		table.addCell(sateliteClinicIdAnswerCell);
		
		String teamNo = "Team No: ";
		
		PdfPCell teamNoCell = new PdfPCell(new Paragraph(new Phrase(teamNo, paraFont)));
		//teamNoCell.setExtraParagraphSpace(2);
		teamNoCell.disableBorderSide(Rectangle.LEFT);
		teamNoCell.disableBorderSide(Rectangle.RIGHT);
		//teamNoCell.disableBorderSide(Rectangle.BOTTOM);
		teamNoCell.setLeading(3, 1);
		teamNoCell.setColspan(11);
		table.addCell(teamNoCell);
		
		String teamNoAnswer = "10";
		
		PdfPCell teamNoCellAnswer = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getTeamNo(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		teamNoCellAnswer.disableBorderSide(Rectangle.LEFT);
		//teamNoCellAnswer.disableBorderSide(Rectangle.BOTTOM);
		teamNoCellAnswer.setLeading(3, 1);
		teamNoCellAnswer.setColspan(8);
		table.addCell(teamNoCellAnswer);
		
		
		
		PdfPCell blankRow = new PdfPCell(new Phrase("\n"));
		blankRow.setFixedHeight(5f);
		blankRow.disableBorderSide(Rectangle.TOP);
		blankRow.disableBorderSide(Rectangle.BOTTOM);
		blankRow.setColspan(88);
		table.addCell(blankRow);
		
		PdfPCell name1 = new PdfPCell(new Paragraph(new Phrase("1. Name: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		name1.disableBorderSide(Rectangle.RIGHT);
		name1.disableBorderSide(Rectangle.TOP);
		name1.disableBorderSide(Rectangle.BOTTOM);
		name1.setLeading(3, 1);
		name1.setColspan(10);
		table.addCell(name1);
		
		PdfPCell name2 = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getPatientName(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		name2.disableBorderSide(Rectangle.LEFT);
		name2.disableBorderSide(Rectangle.RIGHT);
		name2.disableBorderSide(Rectangle.TOP);
		name2.disableBorderSide(Rectangle.BOTTOM);
		name2.setLeading(3, 1);
		name2.setColspan(15);
		table.addCell(name2);
		
		String maleFont = "";
		String femaleFont = "";
		String othersFont = "";
		if(psiMoneyReceipt.getGender().equalsIgnoreCase("M")) {
			maleFont = "\u0033";
			femaleFont = "o";
			othersFont = "o";
		}
		else if(psiMoneyReceipt.getGender().equalsIgnoreCase("F")) {
			
			femaleFont = "\u0033";
			maleFont = "o";
			othersFont = "o";
		}
		else if(psiMoneyReceipt.getGender().equalsIgnoreCase("O")) {
			maleFont = "o";
			femaleFont = "o";
			othersFont = "\u0033";
		}
		
		PdfPCell name3 = new PdfPCell(new Paragraph(new Phrase("2. Gender: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		name3.disableBorderSide(Rectangle.LEFT);
		name3.disableBorderSide(Rectangle.RIGHT);
		name3.disableBorderSide(Rectangle.TOP);
		name3.disableBorderSide(Rectangle.BOTTOM);
		name3.setColspan(15);
		name3.setLeading(3, 1);
		table.addCell(name3);
		
		
		Paragraph p = new Paragraph(new Phrase("Male ", textFont));
		Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
		p.add(new Chunk(maleFont, zapfdingbats));
		PdfPCell name4 = new PdfPCell(new Paragraph(p));
		
		//Chunk chunk = new Chunk("o", zapfdingbats);
		//p.add(chunk);
		
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		name4.disableBorderSide(Rectangle.LEFT);
		name4.disableBorderSide(Rectangle.RIGHT);
		name4.disableBorderSide(Rectangle.TOP);
		name4.disableBorderSide(Rectangle.BOTTOM);
		name4.setColspan(15);
		name4.setLeading(3, 1);
		table.addCell(name4);
		
		Paragraph femalep = new Paragraph(new Phrase("Female ", textFont));
		femalep.add(new Chunk(femaleFont, zapfdingbats));
		PdfPCell name = new PdfPCell(new Paragraph(femalep));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		name.disableBorderSide(Rectangle.LEFT);
		name.disableBorderSide(Rectangle.RIGHT);
		name.disableBorderSide(Rectangle.TOP);
		name.disableBorderSide(Rectangle.BOTTOM);
		name.setColspan(15);
		name.setLeading(3, 1);
		table.addCell(name);
		
		Paragraph othersp = new Paragraph(new Phrase("Others ", textFont));
		othersp.add(new Chunk(othersFont, zapfdingbats));
		PdfPCell name5 = new PdfPCell(new Paragraph(othersp));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		name5.disableBorderSide(Rectangle.LEFT);
		name5.disableBorderSide(Rectangle.TOP);
		name5.disableBorderSide(Rectangle.BOTTOM);
		name5.setLeading(3, 1);
		name5.setColspan(18);
		table.addCell(name5);
		
		
		
		PdfPCell blankRow1 = new PdfPCell(new Phrase("\n"));
		blankRow1.setFixedHeight(5f);
		blankRow1.disableBorderSide(Rectangle.TOP);
		blankRow1.disableBorderSide(Rectangle.BOTTOM);
		blankRow1.setColspan(88);
		table.addCell(blankRow1);
		
		PdfPCell dateOfBirth = new PdfPCell(new Paragraph(new Phrase("3.DoB: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		dateOfBirth.disableBorderSide(Rectangle.TOP);
		dateOfBirth.disableBorderSide(Rectangle.RIGHT);
		dateOfBirth.disableBorderSide(Rectangle.BOTTOM);
		dateOfBirth.setLeading(3, 1);
		dateOfBirth.setColspan(8);
		table.addCell(dateOfBirth);
		
		PdfPCell dateOfBirthAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getBirthDate(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		dateOfBirthAnswer.disableBorderSide(Rectangle.TOP);
		dateOfBirthAnswer.disableBorderSide(Rectangle.RIGHT);
		dateOfBirthAnswer.disableBorderSide(Rectangle.LEFT);
		dateOfBirthAnswer.disableBorderSide(Rectangle.BOTTOM);
		dateOfBirthAnswer.setLeading(3, 1);
		dateOfBirthAnswer.setColspan(11);
		table.addCell(dateOfBirthAnswer);
		
		
		PdfPCell age = new PdfPCell(new Paragraph(new Phrase("4.Age: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		age.disableBorderSide(Rectangle.TOP);
		age.disableBorderSide(Rectangle.RIGHT);
		age.disableBorderSide(Rectangle.LEFT);
		age.disableBorderSide(Rectangle.BOTTOM);
		age.setColspan(7);
		age.setLeading(3, 1);
		table.addCell(age);
		
		PdfPCell ageAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getAge(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		ageAnswer.disableBorderSide(Rectangle.TOP);
		ageAnswer.disableBorderSide(Rectangle.RIGHT);
		ageAnswer.disableBorderSide(Rectangle.LEFT);
		ageAnswer.disableBorderSide(Rectangle.BOTTOM);
		ageAnswer.setLeading(3, 1);
		ageAnswer.setColspan(14);
		table.addCell(ageAnswer);
		
		
		PdfPCell Classification = new PdfPCell(new Paragraph(new Phrase("5.Classification: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		Classification.disableBorderSide(Rectangle.TOP);
		Classification.disableBorderSide(Rectangle.RIGHT);
		Classification.disableBorderSide(Rectangle.LEFT);
		Classification.disableBorderSide(Rectangle.BOTTOM);
		Classification.setLeading(3, 1);
		Classification.setColspan(15);
		table.addCell(Classification);
		
		String ableToPayFont = "";
		String poorFont = "";
		String popFont = "";
		
		if(psiMoneyReceipt.getWealth().equalsIgnoreCase("Able to Pay")) {
			ableToPayFont = "\u0033";
			poorFont = "o";
			popFont = "o";
		}
		else if(psiMoneyReceipt.getWealth().equalsIgnoreCase("Poor")) {
			
			poorFont = "\u0033";
			ableToPayFont = "o";
			popFont = "o";
		}
		else if(psiMoneyReceipt.getWealth().equalsIgnoreCase("PoP")) {
			ableToPayFont = "o";
			poorFont = "o";
			popFont = "\u0033";
		}
		
		
		Paragraph abletopayparagraph = new Paragraph(new Phrase("1.Able to Pay ", textFont));
		abletopayparagraph.add(new Chunk(ableToPayFont, zapfdingbats));
		PdfPCell abletoPay = new PdfPCell(new Paragraph(abletopayparagraph));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		abletoPay.disableBorderSide(Rectangle.TOP);
		abletoPay.disableBorderSide(Rectangle.RIGHT);
		abletoPay.disableBorderSide(Rectangle.LEFT);
		abletoPay.disableBorderSide(Rectangle.BOTTOM);
		abletoPay.setLeading(3, 1);
		abletoPay.setColspan(15);
		table.addCell(abletoPay);
		
		Paragraph poorparagraph = new Paragraph(new Phrase("2.Poor ", textFont));
		poorparagraph.add(new Chunk(poorFont, zapfdingbats));
		PdfPCell poor = new PdfPCell(new Paragraph(poorparagraph));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		poor.disableBorderSide(Rectangle.TOP);
		poor.disableBorderSide(Rectangle.RIGHT);
		poor.disableBorderSide(Rectangle.LEFT);
		poor.disableBorderSide(Rectangle.BOTTOM);
		poor.setLeading(3, 1);
		poor.setColspan(9);
		table.addCell(poor);
		
		Paragraph poparagraph = new Paragraph(new Phrase("3.PoP ", textFont));
		poparagraph.add(new Chunk(popFont, zapfdingbats));
		PdfPCell pop = new PdfPCell(new Paragraph(poparagraph));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		pop.disableBorderSide(Rectangle.TOP);
		pop.disableBorderSide(Rectangle.LEFT);
		pop.disableBorderSide(Rectangle.BOTTOM);
		pop.setLeading(3, 1);
		pop.setColspan(9);
		table.addCell(pop);
		
		
		PdfPCell blankRow2 = new PdfPCell(new Phrase("\n"));
		blankRow2.setFixedHeight(5f);
		blankRow2.disableBorderSide(Rectangle.TOP);
		blankRow2.disableBorderSide(Rectangle.BOTTOM);
		blankRow2.setColspan(88);
		table.addCell(blankRow2);
		
		PdfPCell address = new PdfPCell(new Paragraph(new Phrase("6. Address: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		address.disableBorderSide(Rectangle.TOP);
		address.disableBorderSide(Rectangle.RIGHT);
		address.disableBorderSide(Rectangle.BOTTOM);
		address.setLeading(3, 1);
		address.setColspan(15);
		table.addCell(address);
		
		PdfPCell addressAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getAddress(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		addressAnswer.disableBorderSide(Rectangle.TOP);
		addressAnswer.disableBorderSide(Rectangle.RIGHT);
		addressAnswer.disableBorderSide(Rectangle.BOTTOM);
		addressAnswer.disableBorderSide(Rectangle.LEFT);
		addressAnswer.setLeading(3, 1);
		addressAnswer.setColspan(45);
		table.addCell(addressAnswer);
		
		PdfPCell mobileNo = new PdfPCell(new Paragraph(new Phrase("7. Mobile No: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		mobileNo.disableBorderSide(Rectangle.TOP);
		mobileNo.disableBorderSide(Rectangle.RIGHT);
		mobileNo.disableBorderSide(Rectangle.BOTTOM);
		mobileNo.disableBorderSide(Rectangle.LEFT);
		mobileNo.setLeading(3, 1);
		mobileNo.setColspan(15);
		table.addCell(mobileNo);
		
		PdfPCell mobileNoAnswer = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getContact(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		mobileNoAnswer.disableBorderSide(Rectangle.TOP);
		mobileNoAnswer.disableBorderSide(Rectangle.BOTTOM);
		mobileNoAnswer.disableBorderSide(Rectangle.LEFT);
		mobileNoAnswer.setLeading(3, 1);
		mobileNoAnswer.setColspan(13);
		table.addCell(mobileNoAnswer);
		
		PdfPCell blankRow3 = new PdfPCell(new Phrase("\n"));
		blankRow3.setFixedHeight(5f);
		blankRow3.disableBorderSide(Rectangle.TOP);
		blankRow3.disableBorderSide(Rectangle.BOTTOM);
		blankRow3.setColspan(88);
		table.addCell(blankRow3);
		
		PdfPCell session = new PdfPCell(new Paragraph(new Phrase("8. Session:  ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		session.disableBorderSide(Rectangle.TOP);
		session.disableBorderSide(Rectangle.BOTTOM);
		session.disableBorderSide(Rectangle.RIGHT);
		session.setLeading(3, 1);
		session.setColspan(20);
		table.addCell(session);
		
		PdfPCell sessionAnswer = new PdfPCell(new Paragraph(new Phrase(psiMoneyReceipt.getSession(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		sessionAnswer.disableBorderSide(Rectangle.TOP);
		sessionAnswer.disableBorderSide(Rectangle.BOTTOM);
		sessionAnswer.disableBorderSide(Rectangle.LEFT);
		sessionAnswer.setLeading(3, 1);
		sessionAnswer.setColspan(68);
		table.addCell(sessionAnswer);
		
		PdfPCell blankRow4 = new PdfPCell(new Phrase("\n"));
		blankRow4.setFixedHeight(5f);
		blankRow4.disableBorderSide(Rectangle.TOP);
		blankRow4.disableBorderSide(Rectangle.BOTTOM);
		blankRow4.setColspan(88);
		table.addCell(blankRow4);
		
		PdfPCell reference = new PdfPCell(new Paragraph(new Phrase("9. Reference:  ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		reference.disableBorderSide(Rectangle.TOP);
		reference.disableBorderSide(Rectangle.BOTTOM);
		reference.disableBorderSide(Rectangle.RIGHT);
		reference.setLeading(3, 1);
		reference.setColspan(20);
		table.addCell(reference);
		
		PdfPCell referenceAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getReference(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		referenceAnswer.disableBorderSide(Rectangle.TOP);
		referenceAnswer.disableBorderSide(Rectangle.BOTTOM);
		referenceAnswer.disableBorderSide(Rectangle.LEFT);
		referenceAnswer.setLeading(3, 1);
		referenceAnswer.setColspan(68);
		table.addCell(referenceAnswer);
		
		PdfPCell blankRow5 = new PdfPCell(new Phrase("\n"));
		blankRow5.setFixedHeight(5f);
		blankRow5.disableBorderSide(Rectangle.TOP);
		blankRow5.disableBorderSide(Rectangle.BOTTOM);
		blankRow5.setColspan(88);
		table.addCell(blankRow5);
		
		PdfPCell birthDistrict = new PdfPCell(new Paragraph(new Phrase("10. Birth District:  ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthDistrict.disableBorderSide(Rectangle.TOP);
		birthDistrict.disableBorderSide(Rectangle.BOTTOM);
		birthDistrict.disableBorderSide(Rectangle.RIGHT);
		birthDistrict.setLeading(3, 1);
		birthDistrict.setColspan(20);
		table.addCell(birthDistrict);
		
		PdfPCell birthDistrictAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getBirthDistrict(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthDistrictAnswer.disableBorderSide(Rectangle.TOP);
		birthDistrictAnswer.disableBorderSide(Rectangle.BOTTOM);
		birthDistrictAnswer.disableBorderSide(Rectangle.RIGHT);
		birthDistrictAnswer.disableBorderSide(Rectangle.LEFT);
		birthDistrictAnswer.setLeading(3, 1);
		birthDistrictAnswer.setColspan(20);
		table.addCell(birthDistrictAnswer);
		
		PdfPCell birthUpazila = new PdfPCell(new Paragraph(new Phrase("11. Birth Upazila: ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthUpazila.disableBorderSide(Rectangle.TOP);
		birthUpazila.disableBorderSide(Rectangle.BOTTOM);
		birthUpazila.disableBorderSide(Rectangle.RIGHT);
		birthUpazila.disableBorderSide(Rectangle.LEFT);
		birthUpazila.setLeading(3, 1);
		birthUpazila.setColspan(20);
		table.addCell(birthUpazila);
		
		PdfPCell birthUpazilaAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getBirthUpazilla(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthUpazilaAnswer.disableBorderSide(Rectangle.TOP);
		birthUpazilaAnswer.disableBorderSide(Rectangle.BOTTOM);
		birthUpazilaAnswer.disableBorderSide(Rectangle.LEFT);
		birthUpazilaAnswer.setLeading(3, 1);
		birthUpazilaAnswer.setColspan(28);
		table.addCell(birthUpazilaAnswer);
		
		PdfPCell blankRow6 = new PdfPCell(new Phrase("\n"));
		blankRow6.setFixedHeight(5f);
		blankRow6.disableBorderSide(Rectangle.TOP);
		blankRow6.disableBorderSide(Rectangle.BOTTOM);
		blankRow6.setColspan(88);
		table.addCell(blankRow6);
		
		PdfPCell birthrank = new PdfPCell(new Paragraph(new Phrase("12. Birth Rank:  ", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthrank.disableBorderSide(Rectangle.TOP);
		birthrank.disableBorderSide(Rectangle.RIGHT);
		birthrank.disableBorderSide(Rectangle.BOTTOM);
		birthrank.setLeading(3, 1);
		birthrank.setColspan(15);
		table.addCell(birthrank);
		
		PdfPCell birthrankAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getBirthRank(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthrankAnswer.disableBorderSide(Rectangle.TOP);
		birthrankAnswer.disableBorderSide(Rectangle.RIGHT);
		birthrankAnswer.disableBorderSide(Rectangle.BOTTOM);
		birthrankAnswer.disableBorderSide(Rectangle.LEFT);
		
		birthrankAnswer.setLeading(3, 1);
		birthrankAnswer.setColspan(20);
		table.addCell(birthrankAnswer);
		
		PdfPCell birthMontherName = new PdfPCell(new Paragraph(new Phrase("13. Birth Mother's First Name:", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthMontherName.disableBorderSide(Rectangle.TOP);
		birthMontherName.disableBorderSide(Rectangle.RIGHT);
		birthMontherName.disableBorderSide(Rectangle.LEFT);
		birthMontherName.disableBorderSide(Rectangle.BOTTOM);
		birthMontherName.setLeading(3, 1);
		birthMontherName.setColspan(30);
		table.addCell(birthMontherName);
		
		PdfPCell birthMontherNameAnswer = new PdfPCell(new Paragraph(new Phrase(dto.getBirthMothersFirstName(), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		birthMontherNameAnswer.disableBorderSide(Rectangle.TOP);
		birthMontherNameAnswer.disableBorderSide(Rectangle.LEFT);
		birthMontherNameAnswer.disableBorderSide(Rectangle.BOTTOM);
		birthMontherNameAnswer.setLeading(3, 1);
		birthMontherNameAnswer.setColspan(23);
		table.addCell(birthMontherNameAnswer);
		
		PdfPCell blankRow7 = new PdfPCell(new Phrase("\n"));
		blankRow7.setFixedHeight(5f);
		blankRow7.disableBorderSide(Rectangle.TOP);
		blankRow7.setColspan(88);
		table.addCell(blankRow7);
		
		
	}
	
	
	private void addServiceRow(PdfPTable table,PSIMoneyReceipt psiMoneyReceipt,String inwordsTaka) {
		
		log.error("Entering service row" + psiMoneyReceipt.getServices().size());
		PdfPCell serialHeader = new PdfPCell(new Paragraph(new Phrase("SL", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		serialHeader.setLeading(3, 1);
		serialHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//serialHeader.setColspan(10);
		table.addCell(serialHeader);
		
		PdfPCell serviceCodeHeader = new PdfPCell(new Paragraph(new Phrase("Service Code" + "\n" + "     (A)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		serviceCodeHeader.setLeading(3, 1);
		serviceCodeHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//serviceCodeHeader.setColspan(20);
		table.addCell(serviceCodeHeader);
		
		PdfPCell serviceNameHeader = new PdfPCell(new Paragraph(new Phrase("Service/Medicine/ Others" + "\n" + "  (B)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		serviceNameHeader.setLeading(3, 1);
		serviceNameHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//serviceNameHeader.setColspan(40);
		table.addCell(serviceNameHeader);
		
		PdfPCell quantityHeader = new PdfPCell(new Paragraph(new Phrase("Quantity" + "\n" + "    (C)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		quantityHeader.setLeading(3, 1);
		quantityHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//quantityHeader.setColspan(15);
		table.addCell(quantityHeader);
		
		PdfPCell UnitCostHeader = new PdfPCell(new Paragraph(new Phrase("Unit Cost" + "\n" + "    (D)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		UnitCostHeader.setLeading(3, 1);
		UnitCostHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//UnitCostHeader.setColspan(10);
		table.addCell(UnitCostHeader);
		
		PdfPCell amountHeader = new PdfPCell(new Paragraph(new Phrase("Total Amount" + "\n" + "   (E)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		amountHeader.setLeading(3, 1);
		amountHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//amountHeader.setColspan(20);
		table.addCell(amountHeader);
		
		
		PdfPCell discountHeader = new PdfPCell(new Paragraph(new Phrase("Discount" + "\n" + "    (F)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		discountHeader.setLeading(3, 1);
		discountHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//discountHeader.setColspan(15);
		table.addCell(discountHeader);
		
		PdfPCell payableAmountHeader = new PdfPCell(new Paragraph(new Phrase("Net Payable Amount" + "\n" + "    (G)", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		payableAmountHeader.setLeading(3, 1);
		payableAmountHeader.setBackgroundColor(BaseColor.LIGHT_GRAY);
		//payableAmountHeader.setColspan(30);
		table.addCell(payableAmountHeader);
		
		log.error("Completed header making" + psiMoneyReceipt.getServices().size());
		
		 int count = 0;
		 float totalDiscount = 0;
		 float totalAmount = 0;
		 float totalNetPayable = 0;
		for (PSIServiceProvision psiServiceProvision : psiMoneyReceipt.getServices()) {
			log.error("loop entered" + psiServiceProvision.getSpid());
			count = count + 1;
			totalAmount = totalAmount + psiServiceProvision.getTotalAmount();
			totalDiscount = totalDiscount + psiServiceProvision.getDiscount();
			totalNetPayable = totalNetPayable + psiServiceProvision.getNetPayable();
			log.error("totalAmount psiServiceProvision" + totalAmount);
			log.error("totalDiscount psiServiceProvision" + totalDiscount);
			log.error("totalNetPayable psiServiceProvision" + totalNetPayable);
			
			PdfPCell serialNo = new PdfPCell(new Paragraph(new Phrase(String.valueOf(count), textFont)));
			serialNo.setVerticalAlignment(Element.ALIGN_MIDDLE);
			serialNo.setExtraParagraphSpace(2);
			table.addCell(serialNo);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(count),textFont))));
			
			PdfPCell code = new PdfPCell(new Paragraph(new Phrase(psiServiceProvision.getCode(), textFont)));
			code.setVerticalAlignment(Element.ALIGN_MIDDLE);
			code.setExtraParagraphSpace(2);
			table.addCell(code);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(psiServiceProvision.getCode(),textFont))));
			
			PdfPCell item = new PdfPCell(new Paragraph(new Phrase(psiServiceProvision.getItem(), textFont)));
			item.setVerticalAlignment(Element.ALIGN_MIDDLE);
			item.setExtraParagraphSpace(2);
			table.addCell(item);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(psiServiceProvision.getItem(),textFont))));
			
			PdfPCell quantity = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getQuantity()), textFont)));
			quantity.setVerticalAlignment(Element.ALIGN_MIDDLE);
			quantity.setHorizontalAlignment(Element.ALIGN_RIGHT);
			quantity.setExtraParagraphSpace(2);
			table.addCell(quantity);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getQuantity()),textFont))));
			
			PdfPCell unitCost = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getUnitCost()), textFont)));
			unitCost.setVerticalAlignment(Element.ALIGN_MIDDLE);
			unitCost.setHorizontalAlignment(Element.ALIGN_RIGHT);
			unitCost.setExtraParagraphSpace(2);
			table.addCell(unitCost);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getUnitCost()),textFont))));
			
			PdfPCell totalAmountCell = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getTotalAmount()), textFont)));
			totalAmountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			totalAmountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			totalAmountCell.setExtraParagraphSpace(2);
			table.addCell(totalAmountCell);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getTotalAmount()),textFont))));
			
			PdfPCell discountCell = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getDiscount()), textFont)));
			discountCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			discountCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			discountCell.setExtraParagraphSpace(2);
			table.addCell(discountCell);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getDiscount()),textFont))));
			
			PdfPCell netpayableCell = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getNetPayable()), textFont)));
			netpayableCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			netpayableCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			netpayableCell.setExtraParagraphSpace(2);
			table.addCell(netpayableCell);
			//table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiServiceProvision.getNetPayable()),textFont))));
		}
		
//		for(int i = 0; i< 5; i++) {
//			for(int j = 0;j< 8; j++) {
//				table.addCell(new PdfPCell(new Paragraph(new Phrase(String.valueOf(j),textFont))));
//			}
//			
//		}
		log.error("Completed Entering cell" + psiMoneyReceipt.getServices().size());
		PdfPCell totalTk = new PdfPCell(new Paragraph(new Phrase("Total Taka  ", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		totalTk.setLeading(3, 1);
		totalTk.setVerticalAlignment(Element.ALIGN_MIDDLE);
		totalTk.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalTk.setExtraParagraphSpace(2);
		totalTk.setColspan(5);
		table.addCell(totalTk);
		
		log.error("totalAmount" + totalAmount);
		PdfPCell totalAmountTk = new PdfPCell(new Paragraph(new Phrase(String.valueOf(totalAmount), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		totalAmountTk.setVerticalAlignment(Element.ALIGN_MIDDLE);
		totalAmountTk.setHorizontalAlignment(Element.ALIGN_RIGHT);
		totalAmountTk.setLeading(3, 1);
		totalAmountTk.setExtraParagraphSpace(2);
		table.addCell(totalAmountTk);
		
		log.error("totalDiscount" + totalDiscount);
		PdfPCell discount = new PdfPCell(new Paragraph(new Phrase(String.valueOf(totalDiscount), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		discount.setVerticalAlignment(Element.ALIGN_MIDDLE);
		discount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		discount.setExtraParagraphSpace(2);
		discount.setLeading(3, 1);
		table.addCell(discount);
		
		log.error("totalNetPayable" + totalNetPayable);
		PdfPCell netpayableTk = new PdfPCell(new Paragraph(new Phrase(String.valueOf(totalNetPayable), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		netpayableTk.setVerticalAlignment(Element.ALIGN_MIDDLE);
		netpayableTk.setHorizontalAlignment(Element.ALIGN_RIGHT);
		netpayableTk.setExtraParagraphSpace(2);
		netpayableTk.setLeading(3, 1);
		table.addCell(netpayableTk);
		
		
		PdfPCell overallDiscount = new PdfPCell(new Paragraph(new Phrase("Overall Discount   ", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		overallDiscount.setVerticalAlignment(Element.ALIGN_MIDDLE);
		overallDiscount.setLeading(3, 1);
		overallDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		overallDiscount.setExtraParagraphSpace(2);
		overallDiscount.setColspan(5);
		table.addCell(overallDiscount);
		
		log.error("overallDiscount" + psiMoneyReceipt.getOverallDiscount());
		PdfPCell overallDiscountTaka = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiMoneyReceipt.getOverallDiscount()), textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		overallDiscountTaka.setLeading(3, 1);
		overallDiscountTaka.setVerticalAlignment(Element.ALIGN_MIDDLE);
		overallDiscountTaka.setHorizontalAlignment(Element.ALIGN_RIGHT);
		overallDiscountTaka.setExtraParagraphSpace(2);
		overallDiscountTaka.setColspan(3);
		table.addCell(overallDiscountTaka);
		
		PdfPCell netPayableDiscount = new PdfPCell(new Paragraph(new Phrase("Net Payable After Discount ", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		netPayableDiscount.setVerticalAlignment(Element.ALIGN_MIDDLE);
		netPayableDiscount.setLeading(3, 1);
		netPayableDiscount.setHorizontalAlignment(Element.ALIGN_RIGHT);
		netPayableDiscount.setExtraParagraphSpace(2);
		netPayableDiscount.setColspan(5);
		table.addCell(netPayableDiscount);
		
		log.error("total amount" + psiMoneyReceipt.getTotalAmount());
		PdfPCell netPayableDiscountTaka = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiMoneyReceipt.getTotalAmount()), paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		
		netPayableDiscountTaka.setLeading(3, 1);
		netPayableDiscountTaka.setHorizontalAlignment(Element.ALIGN_RIGHT);
		netPayableDiscountTaka.setVerticalAlignment(Element.ALIGN_MIDDLE);
		netPayableDiscountTaka.setExtraParagraphSpace(2);
		netPayableDiscountTaka.setColspan(3);
		table.addCell(netPayableDiscountTaka);
		
		log.error("inwordsTaka" + inwordsTaka);
		PdfPCell takainWords = new PdfPCell(new Paragraph(new Phrase("In Words (Taka): " + inwordsTaka, paraFont)));
		takainWords.setExtraParagraphSpace(2);
		takainWords.setLeading(3, 1);
		takainWords.setHorizontalAlignment(Element.ALIGN_LEFT);
		takainWords.setVerticalAlignment(Element.ALIGN_MIDDLE);
		takainWords.setColspan(8);
		table.addCell(takainWords);
	}
	
	private void footerRow(PdfPTable table,String name,String designationDataCollector) {
		
		PdfPCell collectorName = new PdfPCell(new Paragraph(new Phrase(name, paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		collectorName.setLeading(3, 1);
		collectorName.setHorizontalAlignment(Element.ALIGN_CENTER);
		collectorName.disableBorderSide(Rectangle.TOP);
		collectorName.disableBorderSide(Rectangle.LEFT);
		collectorName.disableBorderSide(Rectangle.RIGHT);
		table.addCell(collectorName);
		
		PdfPCell blankcell = new PdfPCell(new Paragraph(new Phrase("", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		blankcell.setLeading(3, 1);
		blankcell.disableBorderSide(Rectangle.TOP);
		blankcell.disableBorderSide(Rectangle.LEFT);
		blankcell.disableBorderSide(Rectangle.RIGHT);
		blankcell.disableBorderSide(Rectangle.BOTTOM);
		table.addCell(blankcell);
		
		PdfPCell designationName = new PdfPCell(new Paragraph(new Phrase(designationDataCollector, paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		designationName.setLeading(3, 1);
		designationName.disableBorderSide(Rectangle.TOP);
		designationName.disableBorderSide(Rectangle.LEFT);
		designationName.disableBorderSide(Rectangle.RIGHT);
		designationName.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(designationName);
		
		PdfPCell blankcell2 = new PdfPCell(new Paragraph(new Phrase("", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		blankcell2.setLeading(3, 1);
		blankcell2.disableBorderSide(Rectangle.TOP);
		blankcell2.disableBorderSide(Rectangle.LEFT);
		blankcell2.disableBorderSide(Rectangle.RIGHT);
		blankcell2.disableBorderSide(Rectangle.BOTTOM);
		table.addCell(blankcell2);
		
		PdfPCell signatureName = new PdfPCell(new Paragraph(new Phrase("", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		signatureName.setLeading(3, 1);
		signatureName.setHorizontalAlignment(Element.ALIGN_CENTER);
		signatureName.disableBorderSide(Rectangle.TOP);
		signatureName.disableBorderSide(Rectangle.LEFT);
		signatureName.disableBorderSide(Rectangle.RIGHT);
		table.addCell(signatureName);
		
		PdfPCell collector = new PdfPCell(new Paragraph(new Phrase("Name of data collector", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		collector.setLeading(3, 1);
		collector.setHorizontalAlignment(Element.ALIGN_CENTER);
		collector.disableBorderSide(Rectangle.BOTTOM);
		collector.disableBorderSide(Rectangle.LEFT);
		collector.disableBorderSide(Rectangle.RIGHT);
		collector.disableBorderSide(Rectangle.TOP);
		table.addCell(collector);
		
		PdfPCell blankcell3 = new PdfPCell(new Paragraph(new Phrase("", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		blankcell3.setLeading(3, 1);
		blankcell3.disableBorderSide(Rectangle.TOP);
		blankcell3.disableBorderSide(Rectangle.LEFT);
		blankcell3.disableBorderSide(Rectangle.RIGHT);
		blankcell3.disableBorderSide(Rectangle.BOTTOM);
		table.addCell(blankcell3);
		
		PdfPCell designation = new PdfPCell(new Paragraph(new Phrase("Designation", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		designation.setLeading(3, 1);
		designation.setHorizontalAlignment(Element.ALIGN_CENTER);
		designation.disableBorderSide(Rectangle.BOTTOM);
		designation.disableBorderSide(Rectangle.LEFT);
		designation.disableBorderSide(Rectangle.RIGHT);
		designation.disableBorderSide(Rectangle.TOP);
		table.addCell(designation);
		
		PdfPCell blankcell4 = new PdfPCell(new Paragraph(new Phrase("", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		blankcell4.setLeading(3, 1);
		blankcell4.disableBorderSide(Rectangle.TOP);
		blankcell4.disableBorderSide(Rectangle.LEFT);
		blankcell4.disableBorderSide(Rectangle.RIGHT);
		blankcell4.disableBorderSide(Rectangle.BOTTOM);
		table.addCell(blankcell4);
		
		PdfPCell signature = new PdfPCell(new Paragraph(new Phrase("Signature", textFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		signature.setLeading(3, 1);
		signature.setHorizontalAlignment(Element.ALIGN_CENTER);
		signature.disableBorderSide(Rectangle.BOTTOM);
		signature.disableBorderSide(Rectangle.LEFT);
		signature.disableBorderSide(Rectangle.RIGHT);
		signature.disableBorderSide(Rectangle.TOP);
		table.addCell(signature);
	}
	
	private void receiveAmountDetails(PdfPTable table,PSIMoneyReceipt psiMoneyReceipt) {
		
		Set<SHNMoneyReceiptPaymentLog> payments = psiMoneyReceipt.getPayments();
		List<SHNMoneyReceiptPaymentLog> paymentList = new ArrayList<SHNMoneyReceiptPaymentLog>();
		paymentList.addAll(payments);
		paymentList.sort(Comparator.comparing(SHNMoneyReceiptPaymentLog::getReceiveDate).reversed());
		
		for (SHNMoneyReceiptPaymentLog shnMoneyReceiptPaymentLog : paymentList) {
			
			PdfPCell recieveDate = new PdfPCell(new Paragraph(new Phrase("Receive Date:", paraFont)));
			//teamNoCellAnswer.setExtraParagraphSpace(2);
			recieveDate.setLeading(3, 1);
			recieveDate.setHorizontalAlignment(Element.ALIGN_RIGHT);
			recieveDate.setColspan(3);
			recieveDate.disableBorderSide(Rectangle.BOTTOM);
			recieveDate.disableBorderSide(Rectangle.LEFT);
			recieveDate.disableBorderSide(Rectangle.RIGHT);
			recieveDate.disableBorderSide(Rectangle.TOP);
			table.addCell(recieveDate);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			String receivedDate = dateFormat.format(shnMoneyReceiptPaymentLog.getReceiveDate());
			
			PdfPCell recieveDateAnswer = new PdfPCell(new Paragraph(new Phrase(receivedDate, paraFont)));
			//teamNoCellAnswer.setExtraParagraphSpace(2);
			recieveDateAnswer.setLeading(3, 1);
			recieveDateAnswer.setColspan(2);
			recieveDateAnswer.disableBorderSide(Rectangle.BOTTOM);
			recieveDateAnswer.disableBorderSide(Rectangle.LEFT);
			recieveDateAnswer.disableBorderSide(Rectangle.RIGHT);
			recieveDateAnswer.disableBorderSide(Rectangle.TOP);
			table.addCell(recieveDateAnswer);
			
			PdfPCell receiveAmount = new PdfPCell(new Paragraph(new Phrase("Receive Amount :", paraFont)));
			//teamNoCellAnswer.setExtraParagraphSpace(2);
			receiveAmount.setLeading(3, 1);
			receiveAmount.disableBorderSide(Rectangle.BOTTOM);
			receiveAmount.disableBorderSide(Rectangle.LEFT);
			receiveAmount.setHorizontalAlignment(Element.ALIGN_RIGHT);
			receiveAmount.disableBorderSide(Rectangle.RIGHT);
			receiveAmount.disableBorderSide(Rectangle.TOP);
			receiveAmount.setColspan(2);
			table.addCell(receiveAmount);
			
			PdfPCell receiveAmountAnswer = new PdfPCell(new Paragraph(new Phrase(String.valueOf(shnMoneyReceiptPaymentLog.getReceiveAmount()), paraFont)));
			//teamNoCellAnswer.setExtraParagraphSpace(2);
			receiveAmountAnswer.setLeading(3, 1);
			
			receiveAmountAnswer.disableBorderSide(Rectangle.BOTTOM);
			receiveAmountAnswer.disableBorderSide(Rectangle.LEFT);
			receiveAmountAnswer.disableBorderSide(Rectangle.RIGHT);
			receiveAmountAnswer.disableBorderSide(Rectangle.TOP);
			table.addCell(receiveAmountAnswer);
		
		}
		
		PdfPCell due = new PdfPCell(new Paragraph(new Phrase("Due: ", paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		due.setLeading(3, 1);
		due.setHorizontalAlignment(Element.ALIGN_RIGHT);
		due.setColspan(7);
		due.disableBorderSide(Rectangle.BOTTOM);
		due.disableBorderSide(Rectangle.LEFT);
		due.disableBorderSide(Rectangle.RIGHT);
		due.disableBorderSide(Rectangle.TOP);
		table.addCell(due);
		
		PdfPCell dueAnswer = new PdfPCell(new Paragraph(new Phrase(String.valueOf(psiMoneyReceipt.getDueAmount()), paraFont)));
		//teamNoCellAnswer.setExtraParagraphSpace(2);
		dueAnswer.setLeading(3, 1);
		dueAnswer.setHorizontalAlignment(Element.ALIGN_LEFT);
		//dueAnswer.setColspan(4);
		dueAnswer.disableBorderSide(Rectangle.BOTTOM);
		dueAnswer.disableBorderSide(Rectangle.LEFT);
		dueAnswer.disableBorderSide(Rectangle.RIGHT);
		dueAnswer.disableBorderSide(Rectangle.TOP);
		table.addCell(dueAnswer);
	}
	
}
