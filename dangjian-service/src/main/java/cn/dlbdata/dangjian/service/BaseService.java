package cn.dlbdata.dangjian.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import cn.dlbdata.dangjian.api.IBaseService;
import cn.dlbdata.dangjian.common.core.bean.DataTableBean;
import cn.dlbdata.dangjian.common.core.bean.JqGridBean;
import cn.dlbdata.dangjian.common.core.bean.TableBean;
import cn.dlbdata.dangjian.common.core.util.constant.CoreConst;
import cn.dlbdata.dangjian.common.core.util.mybatis.MyBatisHelper;
import cn.dlbdata.dangjian.common.core.util.mybatis.SqlMapper;

public class BaseService implements IBaseService {
	protected Logger logger = Logger.getLogger(BaseService.class);

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;

	@Override
	public TableBean<Object> query(String selectId, Map<String, Object> params, int offset, int limit) {

		if (StringUtils.isEmpty(selectId)) {
			return null;
		}

		logger.info("selectId:" + selectId);
		// 封装返回值
		TableBean<Object> bean = new TableBean<Object>();
		// 获取记录
		List<Object> rows = sqlSessionTemplate.selectList(selectId, params, new RowBounds(offset, limit));
		// 获取countSql
		String countSql = MyBatisHelper.getSmartCountSql(sqlSessionTemplate, selectId, params);
		logger.info("countSql:" + countSql);
		SqlMapper sqlMapper = new SqlMapper(sqlSessionTemplate);
		// 获取记录总数
		Integer total = sqlMapper.selectOne(countSql, params, Integer.class);

		bean.setRows(rows);
		bean.setTotal(total);
		return bean;
	}

	@Override
	public TableBean<Object> query(Class<?> mapperClass, String selectId, Map<String, Object> params, int offset,
			int limit) {
		if (mapperClass == null || StringUtils.isEmpty(selectId)) {
			return null;
		}

		String fullSelectId = mapperClass.getCanonicalName() + "." + selectId;
		return query(fullSelectId, params, offset, limit);
	}

	@Override
	public DataTableBean<Object> queryData(String selectId, Map<String, Object> params, int offset, int limit) {

		if (StringUtils.isEmpty(selectId)) {
			return null;
		}

		logger.info("selectId:" + selectId);
		// 封装返回值
		DataTableBean<Object> bean = new DataTableBean<Object>();
		// 获取记录
		List<Object> rows = sqlSessionTemplate.selectList(selectId, params, new RowBounds(offset, limit));
		// 获取countSql
		String countSql = MyBatisHelper.getSmartCountSql(sqlSessionTemplate, selectId, params);
		logger.info("countSql:" + countSql);
		SqlMapper sqlMapper = new SqlMapper(sqlSessionTemplate);
		// 获取记录总数
		Integer total = sqlMapper.selectOne(countSql, params, Integer.class);

		bean.setData(rows);
		bean.setRecordsFiltered(total);
		bean.setRecordsTotal(total);
		return bean;
	}

	@Override
	public JqGridBean<Object> queryJqData(String selectId, Map<String, Object> params, int page, int limit) {
		if (StringUtils.isEmpty(selectId)) {
			return null;
		}
		if (page <= 0) {
			page = 1;
		}
		if (limit <= 0) {
			limit = 10;
		}
		int offset = (page - 1) * limit;
		logger.info("selectId:" + selectId);
		// 封装返回值
		JqGridBean<Object> bean = new JqGridBean<Object>(CoreConst.SUCCESS);
		// 获取记录
		List<Object> rows = sqlSessionTemplate.selectList(selectId, params, new RowBounds(offset, limit));
		// 获取countSql
		String countSql = MyBatisHelper.getSmartCountSql(sqlSessionTemplate, selectId, params);
		logger.info("countSql:" + countSql);
		SqlMapper sqlMapper = new SqlMapper(sqlSessionTemplate);
		// 获取记录总数
		Integer count = sqlMapper.selectOne(countSql, params, Integer.class);
		bean.setPage(page);
		int total = 1;
		if (count % limit == 0) {
			total = count / limit;
		} else {
			total = count / limit + 1;
		}
		bean.setTotal(total);
		bean.setRows(rows);
		bean.setRecords(count);
		return bean;
	}

	@Override
	public DataTableBean<Object> queryDataEx(String fullSelectId, Map<String, Object> params, int offset, int limit) {
		// TODO Auto-generated method stub
		return null;
	}
}
