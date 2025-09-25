package in.nit.view;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nit.model.ShipmentType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ShimenttypeExcelView extends AbstractXlsView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//download + file name
		response.addHeader("Content-Disposition", "attachment;filename=ShipmentTypes.xls");
		/*
		 * Sheet s =workbook.createSheet("TEST"); Row r=s.createRow(0); Cell
		 * c=r.createCell(0); c.setCellValue("Herllo");
		 */
		//read dara from controller
		
		@SuppressWarnings("unchecked")
		List<ShipmentType> list= (List<ShipmentType>)model.get("obs");
		//create new sheet
		Sheet sheet=workbook.createSheet("Shipment Type");
		setHead(sheet);
		SetBody(sheet,list);
	}
  private void setHead(Sheet sheet) {
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Mode");
		row.createCell(2).setCellValue("Code");
		row.createCell(3).setCellValue("Grade");
		row.createCell(4).setCellValue("Enable");
		row.createCell(5).setCellValue("Description");
		
	}
   private void SetBody(Sheet sheet, List<ShipmentType> list) 
   {
		int rowNum=1;
		for(ShipmentType st:list)
		{
			Row row=sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(st.getId());
			row.createCell(1).setCellValue(st.getShipmentMode());
			row.createCell(2).setCellValue(st.getShipmentCode());
			row.createCell(3).setCellValue(st.getShipmentGrade());
			row.createCell(4).setCellValue(st.getEnableShipment());
			row.createCell(5).setCellValue(st.getDescription());
			
					
		}
	}
}
