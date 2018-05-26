package cn.dlbdata.dj.vo;

/**
 * 审核的VO
 * 
 * @author xiaowei
 *
 */
public class AuditVo {
	private Long id;
	private Integer result;
	private String content;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
