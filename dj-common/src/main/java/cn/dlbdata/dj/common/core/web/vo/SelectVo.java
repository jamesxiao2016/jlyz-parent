package cn.dlbdata.dj.common.core.web.vo;

public class SelectVo {
	private String id;
	private String name;
	private String text;

	public SelectVo() {

	}

	public SelectVo(String id, String text) {
		this.id = id;
		this.text = text;
	}

	public SelectVo(String id, String name, String text) {
		this.id = id;
		this.name = name;
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
