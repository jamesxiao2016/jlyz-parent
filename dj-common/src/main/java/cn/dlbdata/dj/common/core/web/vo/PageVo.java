/**
 *  <p>Title: PageVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月23日 
 */
package cn.dlbdata.dj.common.core.web.vo;

import java.util.List;

/**
 * <p>
 * Title: PageVo
 * </p>
 * 
 * @author zhouxuan
 *         <p>
 *         Description:
 *         </p>
 * @date 2018年5月23日
 */
public class PageVo<T> extends BaseVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 367251578695199589L;
	
	// 每页显示数量
	private int pageSize;
	// 当前页
	private int pageNum;
	// 记录总数
	private long total;
	// 总页数
	private int pageTotal;
	// 返回的数据（code == 200时有数据）
	protected List<T> data;

	public PageVo() {

	}

	public PageVo(int pageNum, int pageSize, int total, List<T> data) {
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		this.total = total;
		this.data = data;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

}
