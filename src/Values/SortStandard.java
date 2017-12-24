package Values;

public class SortStandard {
	
	private int yearColumn = 0;
	private int mouthColumn = 0;
	private int cityColumn = 0;
	private int stationColumn = 0;
	private int dataSize = 0;
	
	private String[] year = new String[]{"2006年","2007年","2008年","2009年","2010年","2011年","2012年",
										 "2013年","2014年","2015年","2016年"};
	private String[] mouth = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月",
										  "11月","12月"};
	private String[] city = new String[]{"基隆市","臺北市","新北市","桃園市","新竹市","新竹縣","苗栗縣",
										 "臺中市","彰化縣","南投縣","雲林縣","嘉義市","嘉義縣","臺南市",
										 "高雄市","屏東縣","宜蘭縣","花蓮縣","臺東縣","澎湖縣","連江縣",
										 "金門縣"};
	private String[] station = new String[]{"松山","古亭","中山","大同","萬華","陽明","士林","板橋","三重",
											"菜寮","永和","新莊","新店","淡水","汐止","土城","林口","萬里",
											"西屯","忠明","豐原","沙鹿","大里","新營","善化","安南","臺南",
											"左營","楠梓","前金","前鎮","復興","小港","鳳山","林園","大寮",
											"仁武","橋頭","美濃","基隆","新竹","嘉義","宜蘭","冬山","桃園",
											"中壢","大園","龍潭","平鎮","觀音","竹東","湖口","苗栗","頭份",
											"三義","彰化","線西","二林","南投","埔里","竹山","斗六","崙背",
											"麥寮","臺西","朴子","新港","屏東","潮州","恆春","臺東","關山",
											"花蓮","馬公","金門","馬祖"};
	private static SortStandard instance = null;
	private SortStandard(){}
	
	public static synchronized SortStandard getInstance() {
		if (instance == null) {
			instance = new SortStandard();
		}
		return instance;
	}
	
	public void setYearColumn(int yearColumn) {
		this.yearColumn = yearColumn;
	}
	
	public void setMouthColumn(int mouthColumn) {
		this.mouthColumn = mouthColumn;
	}
	
	public void setCityColumn(int cityColumn) {
		this.cityColumn = cityColumn;
	}
	
	public void setStationColumn(int stationColumn) {
		this.stationColumn = stationColumn;
	}
	
	public void setYear(String[] year) {
		this.year = year;
	}
	
	public void setMouth(String[] mouth) {
		this.mouth = mouth;
	}
	
	public void setDataSize(int dateSize){
		this.dataSize = dateSize;
	}
	
	public int getYearTotal() {
		return this.year.length;
	}
	
	public int getMouthTotal() {
		return this.mouth.length;
	}
	
	public int getCityTotal() {
		return this.city.length;
	}
	
	public int getStationTotal() {
		return this.station.length;
	}
	
	public String getYear(int position) {
		return this.year[position];
	}
	
	public String getMouth(int position) {
		return this.mouth[position];
	}
	
	public String getCity(int position) {
		return this.city[position];
	}
	
	public String getStation(int position) {
		return this.station[position];
	}
	
	public int getYearColumn() {
		return this.yearColumn;
	}
	
	public int getMouthColumn() {
		return this.mouthColumn;
	}
	
	public int getCityColumn() {
		return this.cityColumn;
	}
	
	public int getStationColumn() {
		return this.stationColumn;
	}
	
	public int getDataSize() {
		return this.dataSize;
	}
	
	public int getYearPosition(String value) {
		int re = 0;
		for (int i=0;i<year.length;i++) {
			if (year[i].equals(value)) {
				re = i;
				break;
			}
		}
		return re;
	}
	
	public int getMouthPosition(String value) {
		int re = 0;
		for (int i=0;i<mouth.length;i++) {
			if (mouth[i].equals(value)) {
				re = i;
				break;
			}
		}
		return re;
	}
}
