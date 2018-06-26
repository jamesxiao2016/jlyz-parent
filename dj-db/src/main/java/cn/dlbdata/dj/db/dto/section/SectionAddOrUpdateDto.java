package cn.dlbdata.dj.db.dto.section;

public class SectionAddOrUpdateDto {
    private String name;
    private String description;
    private Long principalId;//新增时不需要

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public void setPrincipalId(Long principalId) {
        this.principalId = principalId;
    }
}
