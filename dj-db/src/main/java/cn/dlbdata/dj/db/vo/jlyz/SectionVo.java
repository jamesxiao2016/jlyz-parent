package cn.dlbdata.dj.db.vo.jlyz;

import java.util.List;

public class SectionVo {
	//片区ID
	String id;
	//片区名称
	String name;
	//负责人ID
	Long sj_id;
	//负责人名称
	String sj_name;
	//党员数量
	Integer dy_number;
	//党支部
	Integer dzb_number;
	//大楼数量
	Integer ld_number;
	//企业数量
	Integer qy_number;
	String camera;
	String mesh;
	//楼宇列表
	List<BuildingVo> buildings;
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
	public Long getSj_id() {
		return sj_id;
	}
	public void setSj_id(Long sj_id) {
		this.sj_id = sj_id;
	}
	public String getSj_name() {
		return sj_name;
	}
	public void setSj_name(String sj_name) {
		this.sj_name = sj_name;
	}
	public Integer getDy_number() {
		return dy_number;
	}
	public void setDy_number(Integer dy_number) {
		this.dy_number = dy_number;
	}
	public Integer getDzb_number() {
		return dzb_number;
	}
	public void setDzb_number(Integer dzb_number) {
		this.dzb_number = dzb_number;
	}
	public Integer getLd_number() {
		return ld_number;
	}
	public void setLd_number(Integer ld_number) {
		this.ld_number = ld_number;
	}
	public Integer getQy_number() {
		return qy_number;
	}
	public void setQy_number(Integer qy_number) {
		this.qy_number = qy_number;
	}
	public String getCamera() {
		return camera;
	}
	public void setCamera(String camera) {
		this.camera = camera;
	}
	public String getMesh() {
		return mesh;
	}
	public void setMesh(String mesh) {
		this.mesh = mesh;
	}
	public List<BuildingVo> getBuildings() {
		return buildings;
	}
	public void setBuildings(List<BuildingVo> buildings) {
		this.buildings = buildings;
	}
	
}
