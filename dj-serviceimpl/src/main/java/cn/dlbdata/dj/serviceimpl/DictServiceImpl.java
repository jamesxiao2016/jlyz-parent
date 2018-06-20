package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.mapper.DjDictMapper;
import cn.dlbdata.dj.db.pojo.DjDict;
import cn.dlbdata.dj.service.IDictService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

@Service
public class DictServiceImpl extends BaseServiceImpl implements IDictService {

	@Autowired
	private DjDictMapper dictMapper;

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

	@Override
	public List<SelectVo> getAllDictList() {
		List<SelectVo> rlist = new ArrayList<SelectVo>();
		List<DjDict> list = dictMapper.selectAll();
		if(list != null) {
			for(DjDict dict : list) {
				rlist.add(new SelectVo(dict.getDictCode(), dict.getDictName(), dict.getDictType()));
			}
		}
		
		return rlist;
	}

}
