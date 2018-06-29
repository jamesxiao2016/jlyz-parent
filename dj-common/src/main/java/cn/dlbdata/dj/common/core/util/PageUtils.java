package cn.dlbdata.dj.common.core.util;

import com.github.pagehelper.Page;

public class PageUtils {
	/**
	 * 从 Mybatis Page 和数据集合中构造 Paged 对象.
	 *
	 * @param page
	 *            该页信息
	 * @param content
	 *            数据
	 * @param <T>
	 *            数据类型
	 * @return Paged 对象
	 */
	public static <T> Paged<T> toPaged(Page<T> page) {
		return new Paged<T>(page.getPageSize(), page.getPageNum(), page.getTotal(), page.getPages(), page.getResult());
	}

	public static int normalizePageSize(Integer pageSize) {
		return pageSize == null ? 100 : pageSize;
	}

	public static int normalizePageIndex(Integer pageIndex) {
		return pageIndex == null ? 1 : pageIndex;
	}
}
