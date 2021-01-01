package org.openmrs.module.PSI.utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooterPageEventMoneyReceipt extends PdfPageEventHelper {
	protected final Log log = LogFactory.getLog(getClass());
	Font titleFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
	
	Font textFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);
	
	Font textHeader = FontFactory.getFont(FontFactory.TIMES_BOLD, 15, BaseColor.BLACK);
	
	Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
	
	PdfTemplate total;
	String header;
	public HeaderFooterPageEventMoneyReceipt() {

	}
    public void setHeader(String header) {
        this.header = header;
    }
	@Override
	public void onOpenDocument(PdfWriter writer, Document document) {
           total = writer.getDirectContent().createTemplate(30, 16);
    }
	
	@Override
	public void onStartPage(PdfWriter writer, Document document) {
		//Path path = Paths.get("/opt/openmrs/openmrs/images/openmrs_logo_white_large.png");
		Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 14, BaseColor.BLACK);
		try {
/*			PdfPTable pageNumber = new PdfPTable(1);
			pageNumber.setWidthPercentage(100);
			pageNumber.setSpacingAfter(1f);
			totalNumberOfPages(pageNumber,writer);
			document.add(pageNumber);*/
			

			File file1 = new File("/opt/openmrs/openmrs/images/logo.jpg");
			String absolutePath1 = file1.getAbsolutePath();
			log.error("Image path first" + absolutePath1);
			
			Image img = Image.getInstance(absolutePath1);
			img.setAlignment(Image.ALIGN_LEFT);
			img.scalePercent(70);
			//document.add(img);
			
			PdfPTable h = new PdfPTable(3);
			h.setWidthPercentage(100);
			h.setSpacingAfter(1f);
			header(h, img,writer);
			document.add(h);
			
			PdfPTable secondHeaderTable = new PdfPTable(8);
			secondHeaderTable.setWidthPercentage(100);

			
			secondheader(secondHeaderTable);
			document.add(secondHeaderTable);
			
			document.add(new Paragraph("\n"));
			PdfPTable thirdHeaderTable = new PdfPTable(3);
			thirdHeaderTable.setWidthPercentage(100);
			
			thirdHeader(thirdHeaderTable);
			document.add(thirdHeaderTable);

			document.add(new Paragraph("\n"));
			
		}
		catch (DocumentException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}
	
	  public void onEndPage(PdfWriter writer, Document document) {
		  PdfPTable table = new PdfPTable(3);

              try {
				table.setWidths(new int[]{24, 24, 2});
			
              table.setTotalWidth(527);
              table.setLockedWidth(true);
              table.getDefaultCell().setFixedHeight(20);
              table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
              table.addCell(header);
              table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
              table.addCell(String.format("Page %d of", writer.getPageNumber()));
              System.out.println(String.format("Page %d", writer.getPageNumber()));
              PdfPCell cell = new PdfPCell(Image.getInstance(total));
              cell.setBorder(Rectangle.NO_BORDER);
              table.addCell(cell);
              table.writeSelectedRows(0, -1, 36, 64, writer.getDirectContent());
              
/*              PdfContentByte cb = writer.getDirectContent();
              Phrase footer = new Phrase("this is a footer", textFont);

             ColumnText.showTextAligned(cb, Element.ALIGN_LEFT,
                      footer,
                      (document.right() - document.left()) / 2 + document.leftMargin(),
                      document.bottom() - 10, 0);*/
              } catch (DocumentException e) {
  				// TODO Auto-generated catch block
  				e.printStackTrace();
  			}
 
      }
	
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
                new Phrase(String.valueOf(writer.getPageNumber())),
                2, 2, 0);
    }
	
	private void header(PdfPTable table, Image img,PdfWriter writer) {
		
		PdfPCell imageCell = new PdfPCell(img);
		imageCell.setBorder(Rectangle.NO_BORDER);
		imageCell.setHorizontalAlignment(Element.ALIGN_LEFT);
		/*imageCell.disableBorderSide(Rectangle.BOTTOM);*/
		table.addCell(imageCell);
		
		PdfPCell apponintmentCell = new PdfPCell(new Paragraph(new Phrase("Customer Record Sheet"  + "\n"
				+ "\n"  + "Surjer Hashi Network", textHeader)));
		apponintmentCell.setBorder(Rectangle.NO_BORDER);
		//apponintmentCell.disableBorderSide(Rectangle.RIGHT);
		//apponintmentCell.disableBorderSide(Rectangle.TOP);
		//apponintmentCell.disableBorderSide(Rectangle.BOTTOM);
		apponintmentCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		apponintmentCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		apponintmentCell.setNoWrap(true);
		apponintmentCell.setPaddingRight(50f);
		//apponintmentCell.setBorderColorBottom(BaseColor.BLUE);
		apponintmentCell.setLeading(3, 1);
		table.addCell(apponintmentCell);
		/*PdfPCell imageCell1 = new PdfPCell(img);
		imageCell1.setBorder(Rectangle.NO_BORDER);
		imageCell1.setHorizontalAlignment(Element.ALIGN_CENTER);
		imageCell.disableBorderSide(Rectangle.BOTTOM);
		table.addCell(imageCell1);*/
		 PdfContentByte pdfContentByte = writer.getDirectContent();
		 Barcode128 barcode128 = new Barcode128();
	     barcode128.setCode("201912300000001");
	     barcode128.setCodeType(Barcode128.CODE128);
	     barcode128.setBarHeight(50f);
	     barcode128.setX(1f);
	     Image code128Image = barcode128.createImageWithBarcode(pdfContentByte, null, null);
	     code128Image.setAbsolutePosition(30, 700);
	     code128Image.scalePercent(100);
		PdfPCell imageCell2 = new PdfPCell(code128Image);
		imageCell2.setBorder(Rectangle.NO_BORDER);
		imageCell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		imageCell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		/*imageCell.disableBorderSide(Rectangle.BOTTOM);*/
		table.addCell(imageCell2);
	}
	
	private void thirdHeader(PdfPTable table) {
		

		Paragraph p = new Paragraph(new Phrase("UIC: " + "M9061NA02UN",textFont));
		
		PdfPCell uicCell = new PdfPCell(new Paragraph(p));
		//staticCell.setExtraParagraphSpace(2);
		uicCell.setBorder(Rectangle.NO_BORDER);
		uicCell.setLeading(3, 1);
		uicCell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(uicCell);
		
		Paragraph p2 = new Paragraph(new Phrase("Health ID: " + "201912300000001",textFont) );

		
		PdfPCell healthId = new PdfPCell(new Paragraph(p2));
		healthId.setBorder(Rectangle.NO_BORDER);
		healthId.setLeading(3, 1);
		healthId.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(healthId);
		
		
		Paragraph p3 = new Paragraph(new Phrase("E-slip No: " + "2012100001000002",textFont));
		
		PdfPCell eslip = new PdfPCell(new Paragraph(p3));
		//csp.setExtraParagraphSpace(2);
		eslip.setBorder(Rectangle.NO_BORDER);
		eslip.setLeading(3, 1);
		eslip.setVerticalAlignment(Element.ALIGN_TOP);
		//csp.setPaddingLeft(10f);
		table.addCell(eslip);

	}
	
	
	private void secondheader(PdfPTable table) {
		
		Paragraph p = new Paragraph(new Phrase("Type:             Static ", textFont));
		Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
		//Chunk chunk = new Chunk("o", zapfdingbats);
		//p.add(chunk);
		p.add(new Chunk("\u0033", zapfdingbats));
//		Phrase phrase = new Phrase("A check mark: ");   
//		Font zapfdingbats = new Font(Font.FontFamily.ZAPFDINGBATS);
//		phrase.Add(new Chunk("\u0033", zapfdingbats));
//		phrase.Add(" and more text");
//		document.Add(phrase);
		
		PdfPCell staticCell = new PdfPCell(new Paragraph(p));
		//staticCell.setExtraParagraphSpace(2);
		staticCell.setBorder(Rectangle.NO_BORDER);
		staticCell.setLeading(3, 1);
		staticCell.setColspan(2);
		staticCell.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(staticCell);
		
		Paragraph p2 = new Paragraph(new Phrase("Satellite ",textFont));
		Font zapfdingbats2 = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
		Chunk chunk2 = new Chunk("o", zapfdingbats2);
		p2.add(chunk2);
		
		PdfPCell satellite = new PdfPCell(new Paragraph(p2));
		//satellite.setExtraParagraphSpace(2);
		satellite.setBorder(Rectangle.NO_BORDER);
		satellite.setLeading(3, 1);
		//satellite.setPaddingLeft(10f);
		satellite.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(satellite);
		
		
		Paragraph p3 = new Paragraph(new Phrase("CSP ",textFont));
		Font zapfdingbats3 = new Font(Font.FontFamily.ZAPFDINGBATS, 14);
		Chunk chunk3 = new Chunk("o", zapfdingbats3);
		p3.add(chunk3);
		
		PdfPCell csp = new PdfPCell(new Paragraph(p3));
		//csp.setExtraParagraphSpace(2);
		csp.setBorder(Rectangle.NO_BORDER);
		csp.setLeading(3, 1);
		csp.setVerticalAlignment(Element.ALIGN_TOP);
		//csp.setPaddingLeft(10f);
		table.addCell(csp);
		
		PdfPCell dateCell = new PdfPCell(new Paragraph(new Phrase("Date: 2020-12-23 24:12" , textFont)));
		//dateCell.setExtraParagraphSpace(2);
		dateCell.setBorder(Rectangle.NO_BORDER);
		dateCell.setLeading(3, 1);
		dateCell.setVerticalAlignment(Element.ALIGN_TOP);
		dateCell.setColspan(2);
		table.addCell(dateCell);
		
		PdfPCell slipNo = new PdfPCell(new Paragraph(new Phrase("Slip No: 2020123" , textFont)));
		//slipNo.setExtraParagraphSpace(2);
		slipNo.setBorder(Rectangle.NO_BORDER);
		slipNo.setLeading(3, 1);
		slipNo.setColspan(2);
		slipNo.setVerticalAlignment(Element.ALIGN_TOP);
		table.addCell(slipNo);
	}
	
	private void totalNumberOfPages(PdfPTable table,PdfWriter writer) {
        table.getDefaultCell().setBorder(Rectangle.NO_BORDER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table.addCell(String.format("Page %d", writer.getPageNumber()));
        //table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
	}
	
}
