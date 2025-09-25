package in.nit.view;

import java.awt.Color;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import in.nit.model.PurchaseDtl;
import in.nit.model.PurchaseOrder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class VendorInvoicePdf extends AbstractPdfView {

	@Override
	protected void buildPdfDocument(Map<String, Object> model,
			Document document, 
			PdfWriter writer,
			HttpServletRequest request,
			HttpServletResponse response) 
					throws Exception {
		
		
		//read po object from model)
		PurchaseOrder po=(PurchaseOrder)model.get("po");
		response.addHeader("Content-Disposition", "attachment;filename=PO-"+po.getPurchaseCode()+".pdf");
		
		Font font=new Font(Font.HELVETICA,20,Font.BOLD,Color.BLACK);
		Paragraph p=new Paragraph("VENDOR INOVIDE "+po.getWhUserType().getUserCode()+"-"+po.getPurchaseCode(),font);
		p.setAlignment(Element.ALIGN_CENTER);
		document.add(p);
		
		List<PurchaseDtl> dtls=po.getDtls();
		double finalcost = dtls.stream().mapToDouble(
				ob->ob.getQty()*ob.getPart().getPartCost())
		        .sum();	
		PdfPTable table=new PdfPTable(4);
		
		table.addCell("VENDOR CODE");
		table.addCell(po.getWhUserType().getUserCode());
		table.addCell("ORDER CODE");
		table.addCell(po.getPurchaseCode());
		
		table.addCell("FINAL COST");
		table.addCell(String.valueOf(finalcost));
		table.addCell("SHIPMENT TYPE");
		table.addCell(po.getShipmentType().getShipmentCode());
		
		document.add(new Paragraph("HEADER DETAILS"));
		document.add(table);
		document.add(new Paragraph("ITEMS DETAILS"));
		
		PdfPTable items=new PdfPTable(4);
		items.addCell("PART CODE");
		items.addCell("BASE COST");
		items.addCell("QTY");
		items.addCell("LINE TOTAL");
		
		for(PurchaseDtl dtl:dtls) {
		
		items.addCell(dtl.getPart().getPartCode());
		items.addCell(String.valueOf(dtl.getPart().getPartCost()));
		items.addCell(dtl.getQty().toString());
		items.addCell(String.valueOf(dtl.getPart().getPartCost()*dtl.getQty()));
		}
		
		document.add(items);
		document.add(new Paragraph(new Date().toString()));
	}
 
}
