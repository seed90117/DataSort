package IO;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import Values.ExcelData;
import Values.SortStandard;


public class LoadFile {

	public boolean loadfile(JFileChooser open)
	{
		String loadPath = "";
		String fileName = "";
		
		//*****預設路徑*****//
		open.setCurrentDirectory(new File(loadPath));
		
		//*****設定Title*****//
		open.setDialogTitle("Choose dataset");
		
		//*****是否按下Load*****//
		if(open.showDialog(open, "Load") == JFileChooser.APPROVE_OPTION)
		{
			//*****取得路徑*****//
			File filepath = open.getSelectedFile();
			fileName = filepath.getName();
			
			//*****路徑轉為String*****//
			String path = filepath.getPath().toString();
			String[] typeArray = fileName.split("\\.");
			if (typeArray[1].equals("xls")) {
				getXlsData(path, typeArray[0]);
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
	}
	
	
	
	private void getXlsData(String filePath, String fileName) {
		ReadExcel readExcel = new ReadExcel();
		System.out.println(filePath);
		readExcel.chooseExcel(filePath);
		String[] sheetName = readExcel.getSheetName();
		for (int s=0;s<sheetName.length;s++) {
			readExcel.chooseSheet(sheetName[s]);
			int c = readExcel.getSheetColumns();
			int r = readExcel.getSheetRow();
			ExcelData excelData = ExcelData.getInstance();
			excelData.initial(c, r);
			excelData.setFileName(fileName);
			excelData.setSheetName(sheetName[s]);
			System.out.println("Columns: " + c);
			System.out.println("Row: " + r);
			getStandard(c, r, readExcel);
			
			// get data
			for (int x=0; x<c; x++) {
				if (readExcel.getData(x, 0).equals("測站")) {
					excelData.setStartColumn(x+1);
				}
				for (int y=0; y<r; y++) {
					excelData.setContent(x, y, readExcel.getData(x, y));
				}
			}
			readExcel.closeWorkbook();
		}
	}
	
	private void getStandard(int c, int r, ReadExcel readExcel) {
		SortStandard sortStandard = SortStandard.getInstance();
		int columnYear = 0;
		int columnMouth = 0;
		for (int x=0; x<c; x++) {
			if (readExcel.getData(x, 0).equals("年度")) {
				sortStandard.setYearColumn(x);
				columnYear = x;
			}
			if (readExcel.getData(x, 0).equals("月份")) {
				sortStandard.setMouthColumn(x);
				columnMouth = x;
			}
			if (readExcel.getData(x, 0).equals("縣市")) {
				sortStandard.setCityColumn(x);
			}
			if (readExcel.getData(x, 0).equals("測站")) {
				sortStandard.setStationColumn(x);
			}
		}
		ArrayList<String> year = new ArrayList<>();
		for (int y=1; y<r; y++) {
			String tmp = readExcel.getData(columnYear, y);
			if (!year.contains(tmp)) {
				year.add(tmp);
			}
		}
		sortStandard.setYear(getStringArray(year));
		
		ArrayList<String> mouth = new ArrayList<>();
		for (int y=1; y<r; y++) {
			String tmp = readExcel.getData(columnMouth, y);
			if (!mouth.contains(tmp)) {
				mouth.add(tmp);
			}
		}
		sortStandard.setMouth(getStringArray(mouth));
	}
	
	private String[] getStringArray(ArrayList<String> list) {
		String[] array = new String[list.size()];
		for (int i=0;i<list.size(); i++) {
			array[i] = list.get(i);
		}
		return array;
	}

}
