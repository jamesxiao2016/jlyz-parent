package cn.dlbdata.dj.common.core.bean;

public class Bean {
	private String id;
	private String text;
	private String value;

	public Bean() {

	}

	public Bean(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public Bean(String id, String text, String value) {
		this.id = id;
		this.text = text;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
