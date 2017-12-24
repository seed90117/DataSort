package Values;

public class ExcelData {
	private static ExcelData instance = null;
	private ExcelData(){}
	
	public static synchronized ExcelData getInstance() {
		if (instance == null) {
			instance = new ExcelData();
		}
		return instance;
	}
	private int totalColumn = 0;
	private int totalRow = 0;
	private int startColumn = 0;
	private String fileName = "";
	private String sheetName = "";
	private String[] rowTitle = null;
	private String[][] content = null;
	
	public void initial(int column, int row) {
		this.content = new String[column][row];
		this.totalColumn = column;
		this.totalRow = row;
	}
	
	public void setContent(String[][] newData) {
		this.content = newData;
		this.totalColumn = this.content.length;
		this.totalRow = this.content[0].length;
	}
	
	public void setContent(int column, int row, String value) {
		this.content[column][row] = value;
	}
	
	public void setStartColumn(int startColumn) {
		this.startColumn = startColumn;
	}
	
	public void setRowTitle(String[] rowTitle) {
		this.rowTitle = rowTitle;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	
	public int getColumn() {
		return this.totalColumn;
	}
	
	public int getRow() {
		return this.totalRow;
	}
	
	public int getStartColumn() {
		return this.startColumn;
	}
	
	public String getContent(int column, int row) {
		return this.content[column][row];
	}
	
	public String[] getRowTitle() {
		return this.rowTitle;
	}
	
	public String getRowTitle(int position) {
		return this.rowTitle[position];
	}
	
	public String getFileName() {
		return this.fileName;
	}
	
	public String getSheetName() {
		return this.sheetName;
	}
	
	public String[] getRow(int row) {
		String[] tmp = new String[this.totalColumn];
		for (int i=0; i<this.totalColumn;i++) {
			tmp[i] = this.content[i][row];
		}
		return tmp;
	}
}
