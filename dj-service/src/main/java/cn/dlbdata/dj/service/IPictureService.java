package cn.dlbdata.dj.service;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.vo.PicVo;

public interface IPictureService {
	
	public ResultVo<Long>  insert(PicVo vo);

	public ResultVo<DjPic> selectThumbnailPath(long id);
	
}
