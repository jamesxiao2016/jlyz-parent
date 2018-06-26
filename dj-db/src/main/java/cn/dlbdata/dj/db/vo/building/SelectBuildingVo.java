package cn.dlbdata.dj.db.vo.building;

import java.util.List;

public class SelectBuildingVo {
    private String text;
    private String id;
    private String parentId;
    private List<SelectBuildingVo> children;

    public SelectBuildingVo() {
    }

    public SelectBuildingVo(String text, String id, String parentId) {
        this.text = text;
        this.id = id;
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

    public List<SelectBuildingVo> getChildren() {
        return children;
    }

    public void setChildren(List<SelectBuildingVo> children) {
        this.children = children;
    }
}
