package in.nit.view;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import in.nit.model.WhUserType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WhUserTypeExcelView extends AbstractXlsView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//download + file name
		response.addHeader("Content-Disposition", "attachment;filename=WhTypeUSers.xls");
		/*
		 * Sheet s =workbook.createSheet("TEST"); Row r=s.createRow(0); Cell
		 * c=r.createCell(0); c.setCellValue("Herllo");
		 */
		//read dara from controller
		
		@SuppressWarnings("unchecked")
		List<WhUserType> list= (List<WhUserType>)model.get("obs");
		//create new sheet
		Sheet sheet=workbook.createSheet("WhUser Type");
		setHead(sheet);
		SetBody(sheet,list);
	}
  private void setHead(Sheet sheet) {
		Row row=sheet.createRow(0);
		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("Type");
		row.createCell(2).setCellValue("Code");
		row.createCell(3).setCellValue("UserFor");
		row.createCell(4).setCellValue("Email");
		row.createCell(5).setCellValue("Contact");
		row.createCell(6).setCellValue("ID Type");
		row.createCell(7).setCellValue("If Other");
		row.createCell(8).setCellValue("ID Number");
		
	}
   private void SetBody(Sheet sheet, List<WhUserType> list) 
   {
		int rowNum=1;
		for(WhUserType st:list)
		{
			Row row=sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(st.getId());
			row.createCell(1).setCellValue(st.getUserType());
			row.createCell(2).setCellValue(st.getUserCode());
			row.createCell(3).setCellValue(st.getUserFor());
			row.createCell(4).setCellValue(st.getUserMail());
			row.createCell(5).setCellValue(st.getUserContact());
			row.createCell(6).setCellValue(st.getUserIdType());
			row.createCell(7).setCellValue(st.getIfOther());
			row.createCell(8).setCellValue(st.getIdNumber());
			
			
					
		}
	}
}
