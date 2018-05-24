/**
 *  <p>Title: PageVo.java</p>  
 *  <p>Description: </p>  
 *  @author zhouxuan
 *  @date 2018年5月23日 
 */
package cn.dlbdata.dj.vo;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;

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
public class PageVo<T> extends ResultVo<T> {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	// 每页显示数量
	private int pageSize;
	// 当前页
	private int pageNo;
	// 记录总数
	private int total;
	// 总页数
	private int pageTotal;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageTotal() {
		if (pageSize > 0) {
			if (total % pageSize == 0) {
				pageTotal = total / pageSize;
			} else {
				pageTotal = total / pageSize + 1;
			}
		}
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

}
