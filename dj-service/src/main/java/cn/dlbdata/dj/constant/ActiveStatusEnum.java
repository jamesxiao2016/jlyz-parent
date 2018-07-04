/**
 *  <p>Title: ActiveStatusEnum.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年7月4日 
 */
package cn.dlbdata.dj.constant;

/**
 * <p>Title: ActiveStatusEnum</p>
 * @author zhouxuan
 * <p>Description: </p>
 * @date 2018年7月4日  
 */
public enum ActiveStatusEnum {
	 ACTIVE_VALID(1,"有效"),
	 ACTIVE_INVALID(0,"无效");

	ActiveStatusEnum(int value, String desc) {
       this.value = value;
       this.desc = desc;
   }
	private int value;
    private String desc;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
