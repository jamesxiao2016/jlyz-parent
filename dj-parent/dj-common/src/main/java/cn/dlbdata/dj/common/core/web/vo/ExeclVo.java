package cn.dlbdata.dj.common.core.web.vo;

import java.util.List;

public class ExeclVo {
	String sheetName;
	String[] title;
	int[] titleWidth;
	List<List<String>> data;

	public ExeclVo() {

	}

	public ExeclVo(String sheetName, String[] title) {
		this.sheetName = sheetName;
		this.title = title;
	}

	public ExeclVo(String sheetName, String[] title, int[] titleWidth) {
		this.sheetName = sheetName;
		this.title = title;
		this.titleWidth = titleWidth;
	}

	public ExeclVo(String sheetName, String[] title, List<List<String>> data) {
		this.sheetName = sheetName;
		this.title = title;
		this.data = data;
	}

	public String getSheetName() {
		return sheetName;
	}

	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public List<List<String>> getData() {
		return data;
	}

	public void setData(List<List<String>> data) {
		this.data = data;
	}

	public int[] getTitleWidth() {
		return titleWidth;
	}

	public void setTitleWidth(int[] titleWidth) {
		this.titleWidth = titleWidth;
	}

}
