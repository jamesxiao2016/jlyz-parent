package cn.dlbdata.dj.service;

import cn.dlbdata.dj.common.core.web.vo.SelectVo;
import cn.dlbdata.dj.db.dto.section.SectionAddOrUpdateDto;
import cn.dlbdata.dj.service.base.IBaseService;
import cn.dlbdata.dj.vo.UserVo;

import java.util.List;

public interface ISectionService extends IBaseService {
	/**
	 * 新增片区
	 * @return
	 */
	public Long addSection(SectionAddOrUpdateDto dto, UserVo user);

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	public Long deleteById(Long id);

	/**
	 *更新片区信息.
	 * @param id 片区Id
	 * @return
	 */
	boolean updateSection(SectionAddOrUpdateDto dto,Long id,UserVo user);


    /**
     * 获取所有片区列表（下拉框使用）
     * @return
     */
    public List<SelectVo> getSectionList();
}

