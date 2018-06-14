package cn.dlbdata.dj.service;

import java.util.List;

import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.pojo.DjSection;
import cn.dlbdata.dj.service.base.IBaseService;

public interface ISectionService extends IBaseService {
	/**
	 * 保存或更新
	 * 
	 * @param section
	 * @return
	 */
	public Long saveOrUpdate(DjSection section);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);
	
	/**
	 * 获取所有片区列表（下拉框使用）
	 * @return
	 */
	public List<SelectVo> getSectionList();
}
