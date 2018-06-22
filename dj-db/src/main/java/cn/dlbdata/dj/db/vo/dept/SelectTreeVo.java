package cn.dlbdata.dj.db.vo.dept;

import java.util.List;

public class SelectTreeVo {
	private String text;
	private String id;
	private String parentId;
	private List<SelectTreeVo> children;

	public SelectTreeVo() {

	}

	public SelectTreeVo(String id, String text, String parentId) {
		this.id = id;
		this.text = text;
		this.parentId = parentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<SelectTreeVo> getChildren() {
		return children;
	}

	public void setChildren(List<SelectTreeVo> children) {
		this.children = children;
	}
}
