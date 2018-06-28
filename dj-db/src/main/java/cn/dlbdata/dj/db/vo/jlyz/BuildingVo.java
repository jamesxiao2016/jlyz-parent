package cn.dlbdata.dj.db.vo.jlyz;

public class BuildingVo {
	// 党员人数
	Integer dy_number;
	// 楼宇编号
	String id;
	// 楼宇名称
	String name;
	//
	String offset;
	//总层数
	Integer floors_total;
	//金领驿站
	StageVo stage;
	
	public Integer getDy_number() {
		return dy_number;
	}
	public void setDy_number(Integer dy_number) {
		this.dy_number = dy_number;
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
	public String getOffset() {
		return offset;
	}
	public void setOffset(String offset) {
		this.offset = offset;
	}
	public Integer getFloors_total() {
		return floors_total;
	}
	public void setFloors_total(Integer floors_total) {
		this.floors_total = floors_total;
	}
	public StageVo getStage() {
		return stage;
	}
	public void setStage(StageVo stage) {
		this.stage = stage;
	}

}
