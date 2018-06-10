package cn.dlbdata.dj.serviceimpl.base;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import cn.dlbdata.dj.common.core.bean.JqGridBean;
import cn.dlbdata.dj.common.core.util.constant.CoreConst;
import cn.dlbdata.dj.service.base.IComponentService;

@Service
public class ComponentServiceImpl implements IComponentService {
	// 日志器
	protected Logger logger = Logger.getLogger(BaseServiceImpl.class);

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	@Override
	public JqGridBean<Object> queryJqData(String selectId, Map<String, Object> params, Integer pageNum,
			Integer pageSize) {
		if (StringUtils.isEmpty(selectId)) {
			return null;
		}
		if (pageNum == null) {
			pageNum = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		int offset = (pageNum - 1) * pageSize;
		logger.info("selectId:" + selectId);
		// 封装返回值
		JqGridBean<Object> bean = new JqGridBean<Object>(CoreConst.SUCCESS);
		Page<Map<String, Object>> page = PageHelper.startPage(pageNum, pageSize);
		// 获取记录
		List<Object> rows = sqlSessionTemplate.selectList(selectId, params, new RowBounds(offset, pageSize));
		// // 获取countSql
		// String countSql = MyBatisHelper.getSmartCountSql(sqlSessionTemplate, selectId, params);
		// logger.info("countSql:" + countSql);
		// SqlMapper sqlMapper = new SqlMapper(sqlSessionTemplate);
		// 获取记录总数
		// Integer count = sqlMapper.selectOne(countSql, params, Integer.class);
		bean.setPage(pageNum);
		// int total = 1;
		// if (count % limit == 0) {
		// total = count / limit;
		// } else {
		// total = count / limit + 1;
		// }
		// bean.setRecords(count);
		// bean.setTotal(total);
		bean.setRows(rows);
		return bean;
	}

}
