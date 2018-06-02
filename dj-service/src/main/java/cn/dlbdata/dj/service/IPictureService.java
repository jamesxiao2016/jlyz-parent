package cn.dlbdata.dj.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.vo.PicVo;
public interface IPictureService {
	
	public ResultVo<Long>  insert(PicVo vo);

	public ResultVo<DjPic> selectThumbnailPath(Long id);
	
	/**
	 * 获取图片路径
	 * @param id
	 * @return
	 */
	public ResultVo<DjPic> getPicInfoById(Long id);
	/**
	 * 
	 * <p>Title: deleteActivePicById</p> 
	 * <p>Description: 删除活动图片</p> 
	 * @param id
	 * @return
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public int deleteActivePicById(Long djActiveId , Long djPicId);
}
