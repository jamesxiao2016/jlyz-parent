package cn.dlbdata.dj.db.pojo;

import javax.persistence.*;

@Table(name = "t_section")
public class TSection {
    @Id
    private Integer id;

    /**
     * 片区名称
     */
    private String name;

    /**
     * 片区总支书记ID
     */
    @Column(name = "secretary_id")
    private Integer secretaryId;

    /**
     * 片区内企业数量
     */
    @Column(name = "enterprise_count")
    private Integer enterpriseCount;

    private String camera;

    private String mesh;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取片区名称
     *
     * @return name - 片区名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置片区名称
     *
     * @param name 片区名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取片区总支书记ID
     *
     * @return secretary_id - 片区总支书记ID
     */
    public Integer getSecretaryId() {
        return secretaryId;
    }

    /**
     * 设置片区总支书记ID
     *
     * @param secretaryId 片区总支书记ID
     */
    public void setSecretaryId(Integer secretaryId) {
        this.secretaryId = secretaryId;
    }

    /**
     * 获取片区内企业数量
     *
     * @return enterprise_count - 片区内企业数量
     */
    public Integer getEnterpriseCount() {
        return enterpriseCount;
    }

    /**
     * 设置片区内企业数量
     *
     * @param enterpriseCount 片区内企业数量
     */
    public void setEnterpriseCount(Integer enterpriseCount) {
        this.enterpriseCount = enterpriseCount;
    }

    /**
     * @return camera
     */
    public String getCamera() {
        return camera;
    }

    /**
     * @param camera
     */
    public void setCamera(String camera) {
        this.camera = camera;
    }

    /**
     * @return mesh
     */
    public String getMesh() {
        return mesh;
    }

    /**
     * @param mesh
     */
    public void setMesh(String mesh) {
        this.mesh = mesh;
    }
}