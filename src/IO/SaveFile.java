package IO;

import Values.ExcelData;

public class SaveFile {

	public void saveExcel() {
		WriteExcel writeExcel = new WriteExcel();
		ExcelData excelData = ExcelData.getInstance();
		
		writeExcel.createNewExcel(excelData.getFileName());
		writeExcel.createNewSheet(excelData.getSheetName(), 0);
		
		for (int c=0;c<excelData.getColumn();c++) {
			for (int r=0;r<excelData.getRow()+1;r++) {
				if (r == 0) {
					writeExcel.setCellContent(c, r, excelData.getRowTitle(c));
				} else {
					writeExcel.setCellContent(c, r, excelData.getContent(c, r-1));
				}
			}
		}
		writeExcel.writeData();
		writeExcel.closeWorkbook();
	}
}
