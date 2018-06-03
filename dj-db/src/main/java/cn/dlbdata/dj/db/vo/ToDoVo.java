package cn.dlbdata.dj.db.vo;

/**
 * 支书和片区负责人代办列表展示Vo
 */
public class ToDoVo {
    private Integer tag;//用来标记是活动还是代办
    private Integer status;//活动的状态 1:未开始 2：已开始/已结束未上传图片的
    private String id;//活动Id
    private String name;//活动或代办名称
    private Long subTypeId;//二级分类Id

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Long getSubTypeId() {
        return subTypeId;
    }

    public void setSubTypeId(Long subTypeId) {
        this.subTypeId = subTypeId;
    }
}
