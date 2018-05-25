package cn.dlbdata.dj.service;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.vo.PicVo;
public interface IPictureService {
	
	public ResultVo<Long>  insert(PicVo vo);

	public ResultVo<DjPic> selectThumbnailPath(long id);
	/**
	 * 
	 * <p>Title: deleteActivePicById</p> 
	 * <p>Description: 删除活动图片</p> 
	 * @param id
	 * @return
	 */
	public int deleteActivePicById(Long id);
}
