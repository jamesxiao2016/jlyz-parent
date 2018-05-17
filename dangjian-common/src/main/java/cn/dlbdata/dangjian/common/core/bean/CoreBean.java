package cn.dlbdata.dangjian.common.core.bean;

public class CoreBean {
	private String id;
	private String name;
	private String value;
	private String ext;

	public CoreBean() {

	}

	public CoreBean(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public CoreBean(String id, String name, String value, String ext) {
		this.id = id;
		this.name = name;
		this.value = value;
		this.ext = ext;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

}
