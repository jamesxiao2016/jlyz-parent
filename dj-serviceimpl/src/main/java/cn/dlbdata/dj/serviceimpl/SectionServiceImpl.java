package cn.dlbdata.dj.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.mapper.DjSectionMapper;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.service.ISectionService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;

@Service
public class SectionServiceImpl extends BaseServiceImpl implements ISectionService {

	@Autowired
	private DjSectionMapper sectionMapper;

	@Override
	public Long saveOrUpdate(DjSection section) {
		if (section == null) {
			return null;
		}
		DjSection record = null;
		Long id = section.getId();
		if (id != null) {
			record = sectionMapper.selectByPrimaryKey(id);
		}
		boolean isSave = false;
		if (record == null) {
			isSave = true;
			record = new DjSection();
		}
		BeanUtils.copyProperties(section, record);
		if (isSave) {
			id = DigitUtil.generatorLongId();
			record.setId(id);
			sectionMapper.insertSelective(record);
		} else {
			sectionMapper.updateByPrimaryKey(record);
		}

		return id;
	}

	@Override
	public Long deleteById(Long id) {
		if (id == null) {
			logger.error("id is null");
			return null;
		}
		sectionMapper.deleteByPrimaryKey(id);
		return id;
	}

	@Override
	public List<SelectVo> getSectionList() {
		List<SelectVo> rlist = new ArrayList<>();
		List<DjSection> list = sectionMapper.selectAll();
		if (list != null) {
			for (DjSection section : list) {
				rlist.add(new SelectVo(section.getId() + "", section.getName()));
			}
		}
		return rlist;
	}

}
