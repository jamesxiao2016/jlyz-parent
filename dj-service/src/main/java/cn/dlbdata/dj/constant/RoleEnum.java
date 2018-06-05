package cn.dlbdata.dj.constant;

/**
 * 角色枚举 Created by Administrator on 2018/5/24.
 */
public enum RoleEnum {
	PARTY(1L, "党员"), //
	BRANCH_PARTY(2L, "党支部书记"), //
	BRANCH_SECRETARY(3L, "党总支书记"), //
	BRANCH_COMMITTEE(4L, "党委书记"), //
	HEADER_OF_DISTRICT(5L, "片区负责人"), //
	GENERAL_BRANCH(6L, "总支书记");

	RoleEnum(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
