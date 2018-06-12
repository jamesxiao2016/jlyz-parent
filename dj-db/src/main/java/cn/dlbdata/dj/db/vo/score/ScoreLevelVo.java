/**
 *  <p>Title: ScoreLevelVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年6月12日 
 */
package cn.dlbdata.dj.db.vo.score;

import java.util.Date;

/**
 * <p>Title: ScoreLevelVo</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年6月12日  
 */
public class ScoreLevelVo {
	/**
	 * 积分等级id
	 */
	private Long id;
	/**
	 * 积分等级名称
	 */
	private String name;
	/**
	 * 最低分
	 */
	private Integer levelMin;
	/**
	 * 最高分
	 */
	private Integer levelMax;
	/**
	 * 创建时间
	 */
	private Date createTime;
	
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
	public Integer getLevelMin() {
		return levelMin;
	}
	public void setLevelMin(Integer levelMin) {
		this.levelMin = levelMin;
	}
	public Integer getLevelMax() {
		return levelMax;
	}
	public void setLevelMax(Integer levelMax) {
		this.levelMax = levelMax;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
