package cn.dlbdata.dj.constant;

/**
 * 角色枚举 Created by Administrator on 2018/5/24.
 */
public enum RoleEnum {
	PARTY(1, "党员"), //
	BRANCH_PARTY(2, "党支部书记"), //
	BRANCH_SECRETARY(3, "党总支书记"), //
	BRANCH_COMMITTEE(4, "党委书记"), //
	HEADER_OF_DISTRICT(5, "片区负责人"), //
	GENERAL_BRANCH(6, "总支书记");

	RoleEnum(long id, String name) {
		this.id = id;
		this.name = name;
	}

	private long id;
	private String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
