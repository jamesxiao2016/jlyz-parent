package cn.dlbdata.dj.serviceimpl.base;

import java.util.ArrayList;
import java.util.Collections;
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
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.mapper.DjDeptMapper;
import cn.dlbdata.dj.db.mapper.DjDictMapper;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.pojo.DjDept;
import cn.dlbdata.dj.db.pojo.DjDict;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.service.base.IComponentService;
import tk.mybatis.mapper.entity.Example;

@Service
public class ComponentServiceImpl implements IComponentService {
	// 日志器
	protected Logger logger = Logger.getLogger(BaseServiceImpl.class);

	@Autowired
	protected SqlSessionTemplate sqlSessionTemplate;
	
	@Autowired
	private DjDictMapper dictMapper;
	
	@Autowired
	private DjDeptMapper deptMapper;
	
	@Autowired
	private DjSectionMapper sectionMapper;

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
		bean.setPage(pageNum);
		bean.setRecords(page.getTotal());
		bean.setTotal(page.getPages());
		bean.setRows(rows);
		return bean;
	}

	@Override
	public List<SelectVo> getDictListByDictType(String dictType) {
		if (StringUtils.isEmpty(dictType)) {
			return Collections.emptyList();
		}

		List<SelectVo> rlist = new ArrayList<>();
		DjDict condition = new DjDict();
		condition.setDictType(dictType);
		List<DjDict> list = dictMapper.select(condition);
		if (list != null && list.size() > 0) {
			for (DjDict dict : list) {
				rlist.add(new SelectVo(dict.getDictCode(), dict.getDictName()));
			}
		}

		return rlist;
	}

	/* (non-Javadoc)
	 * <p>Title: getDeptNameList</p>
	 * <p>Description: </p> 
	 * @return  
	 * @see cn.dlbdata.dj.service.base.IComponentService#getDeptNameList()
	 */
	@Override
	public List<SelectVo> getDeptNameList(Long sectionId) {
		List<SelectVo> rlist = new ArrayList<>();
//		Example example = new Example(DjDept.class);
//		example.createCriteria().andEqualTo("djSectionId", sectionId);
		DjDept condition = new DjDept();
		condition.setDjSectionId(sectionId);
		List<DjDept> list = deptMapper.select(condition);
		if (list != null && list.size() > 0) {
			for (DjDept dept : list) {
				rlist.add(new SelectVo(String.valueOf(dept.getId()), dept.getName()));
			}
		}
		return rlist;
	}
}
