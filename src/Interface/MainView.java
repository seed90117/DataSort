package Interface;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import IO.LoadFile;
import IO.SaveFile;
import Values.ExcelData;
import Values.MouthData;
import Values.SortStandard;
import Values.ValueData;
import Values.YearData;

public class MainView extends JFrame {

	YearData[] yearDatas = null;
	ExcelData excelData = ExcelData.getInstance();
	SortStandard sortStandard = SortStandard.getInstance();
	
	String[] sortBy = new String[]{"測站","縣市"};
	Container cp = this.getContentPane();
	JFileChooser open = new JFileChooser();
	JComboBox<String> sortByComboBox = new JComboBox<String>(sortBy);
	JButton sortButton = new JButton("Starts Sort");
	
	public MainView() {
		this.setSize(200, 150);
		this.setLayout(null);
		this.setTitle("Sort Data");
		
		sortByComboBox.setBounds(20, 20, 120, 30);
		sortButton.setBounds(20, 60, 120, 30);
		
		cp.add(sortByComboBox);
		cp.add(sortButton);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		sortButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadFile loadFile = new LoadFile();
				if (loadFile.loadfile(open)) {
					Classification();
					getRowTitle();
					sorting(sortBy[sortByComboBox.getSelectedIndex()]);
					replace();
					SaveFile saveFile = new SaveFile();
					saveFile.saveExcel();
				}
			}
		});
	}
	
	public static void main(String[] args) {
		new MainView();
	}
	
	private void getRowTitle() {
		int titleCount = 4+sortStandard.getDataSize();
		String[] title = new String[titleCount];
		int c = 0;
		title[c] = excelData.getContent(sortStandard.getYearColumn(), 0);
		c++;
		title[c] = excelData.getContent(sortStandard.getMouthColumn(), 0);
		c++;
		title[c] = excelData.getContent(sortStandard.getCityColumn(), 0);
		c++;
		title[c] = excelData.getContent(sortStandard.getStationColumn(), 0);
		c++;
		for (int i=sortStandard.getStationColumn()+1;i<excelData.getColumn();i++) {
			title[c] = excelData.getContent(i, 0);
			c++;
		}
		excelData.setRowTitle(title);
	}
	
	private void Classification() {
		int yearPosition = sortStandard.getYearColumn();
		int mouthPosition = sortStandard.getMouthColumn();
		int cityPosition = sortStandard.getCityColumn();
		int stationPosition = sortStandard.getStationColumn();
		yearDatas = new YearData[sortStandard.getYearTotal()];
		for (int r=1; r<excelData.getRow(); r++) {//excelData.getRow()
			String[] tmpRow = excelData.getRow(r); // 取得一列
			
			// 該筆資料以年分類
			int ypnum = sortStandard.getYearPosition(tmpRow[yearPosition]);// 找到年的陣列位置
			if (yearDatas[ypnum]==null) { // 判斷是否產生過該年
				yearDatas[ypnum] = new YearData();
				yearDatas[ypnum].year = tmpRow[yearPosition]; // 年的名稱
			}
			
			// 資料放入該年的月份中
			int mpnum = sortStandard.getMouthPosition(tmpRow[mouthPosition]);// 找到月的陣列位置
			if (yearDatas[ypnum].mouths[mpnum]==null) { // 判斷是否產生過該月
				yearDatas[ypnum].mouths[mpnum] = new MouthData();
				yearDatas[ypnum].mouths[mpnum].mouth = tmpRow[mouthPosition]; // 月的名稱
			}
			
			// 放入資料
			ValueData valueData = new ValueData();
			valueData.city = tmpRow[cityPosition];
			valueData.station = tmpRow[stationPosition];
			valueData.data = getData(stationPosition+1, tmpRow);
			sortStandard.setDataSize(valueData.data.length);
			yearDatas[ypnum].mouths[mpnum].datas.add(valueData);
		}
	}

	private void sorting(String standard) {
		for(int y=0;y<yearDatas.length;y++) { // 依照年份開始
			for (int m=0;m<yearDatas[y].mouths.length;m++) { // 依照月份開始
				// 依照基準排序
				yearDatas[y].mouths[m].datas = sortByStanard(yearDatas[y].mouths[m].datas, standard);
			}
		}
	}
	
	private void replace() {
		ArrayList<String>[] newData = null;
		String[][] newContent = null;
		int row = 0;
		for(int y=0;y<yearDatas.length;y++) { // 依照年份開始
			for (int m=0;m<yearDatas[y].mouths.length;m++) { // 依照月份開始
				ArrayList<ValueData> tmpData = yearDatas[y].mouths[m].datas;
				if (newContent == null) {
					newContent = new String[4+sortStandard.getDataSize()]
										[yearDatas.length*yearDatas[y].mouths.length*tmpData.size()];
				}
				for (int n=0;n<tmpData.size();n++) {
					int column = 0;
					newContent[column][row] = sortStandard.getYear(y); // 年
					column++;
					newContent[column][row] = sortStandard.getMouth(m); // 月
					column++;
					newContent[column][row] = tmpData.get(n).city; // 縣市
					column++;
					newContent[column][row] = tmpData.get(n).station; // 測站
					column++;
					
					for (int i=0;i<sortStandard.getDataSize();i++){
						newContent[column][row] = tmpData.get(n).data[i];
						column++;
					}
					
					row++;
				}
			}
		}
		excelData.setContent(newContent);
	}
	
	private String[] getData(int startPosition, String[] data) {
		String[] newData = new String[data.length-startPosition];
		for (int i=startPosition;i<data.length;i++) {
			newData[i-startPosition] = data[i];
		}
		return newData;
	}
	
	private ArrayList<ValueData> sortByStanard(ArrayList<ValueData> datas, String standard) {
		ArrayList<ValueData> newdatas = new ArrayList<>();
		if (standard.equals("測站")) {
			for (int i=0;i<sortStandard.getStationTotal();i++) {
				ValueData tmpData = null;
				// 尋找測站
				for (int j=0;j<datas.size();j++) {
					tmpData = datas.get(j);
					if (tmpData.station.equals(sortStandard.getStation(i))) {
						break;
					}
				}
				// ---
				if (tmpData == null) {
					tmpData = new ValueData();
					tmpData.station = sortStandard.getStation(i);
					tmpData.data = new String[sortStandard.getDataSize()];
				}
				newdatas.add(tmpData);
			}
		} else {
			for (int i=0;i<sortStandard.getCityTotal();i++) {
				ValueData tmpData = null;
				// 尋找縣市
				for (int j=0;j<datas.size();j++) {
					tmpData = datas.get(j);
					if (tmpData.city.equals(sortStandard.getCity(i))) {
						break;
					}
				}
				// ---
				if (tmpData == null) {
					tmpData = new ValueData();
					tmpData.station = sortStandard.getStation(i);
					tmpData.data = new String[sortStandard.getDataSize()];
				}
				newdatas.add(tmpData);
			}
		}
		return newdatas;
	}
}
