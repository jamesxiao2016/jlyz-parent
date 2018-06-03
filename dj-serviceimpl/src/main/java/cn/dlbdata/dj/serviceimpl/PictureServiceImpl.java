package cn.dlbdata.dj.serviceimpl;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.dlbdata.dj.common.core.util.ConfigUtil;
import cn.dlbdata.dj.common.core.util.DigitUtil;
import cn.dlbdata.dj.common.core.util.ImageUtil;
import cn.dlbdata.dj.common.core.util.constant.CoreConst.ResultCode;
import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.constant.DlbConstant;
import cn.dlbdata.dj.db.mapper.DjActivePicMapper;
import cn.dlbdata.dj.db.mapper.DjPicMapper;
import cn.dlbdata.dj.db.pojo.DjActivePic;
import cn.dlbdata.dj.db.pojo.DjPic;
import cn.dlbdata.dj.service.IPictureService;
import cn.dlbdata.dj.serviceimpl.base.BaseServiceImpl;
import cn.dlbdata.dj.vo.PicVo;
import tk.mybatis.mapper.entity.Example;

@Service
public class PictureServiceImpl extends BaseServiceImpl implements IPictureService {

	private String PICTURE_PATH = ConfigUtil.get("rootPath");
	private final String PREVFIX = "thumbnail_";
	@Autowired
	private DjActivePicMapper activePicMapper;
	@Autowired
	private DjPicMapper picMapper;

	@Override
	public ResultVo<Long> insert(PicVo vo) {
		ResultVo<Long> result = new ResultVo<>();
		DjPic pic = new DjPic();
		pic.setId(vo.getPictureId());
		pic.setCreateTime(new Date());
		pic.setPicUrl(vo.getPath());
		pic.setPicName(vo.getPictureId() + "");
		pic.setStatus(1);
		int count = picMapper.insert(pic);
		if (count > 0) {
			long pictureId = pic.getId();
			result.setMsg("上传图片成功！");
			result.setData(pictureId);
			result.setCode(ResultCode.OK.getCode());
			if (vo.getActiveId() != null && vo.getActiveId() > 0) {
				DjActivePic record = new DjActivePic();
				record.setId(DigitUtil.generatorLongId());
				record.setDjActiveId(vo.getActiveId());
				record.setCreateTime(new Date());
				record.setDjPicId(pictureId);
				record.setStatus(DlbConstant.BASEDATA_STATUS_VALID);
				record.setDjUserId(vo.getUserId());
				int res = activePicMapper.insert(record);
				if (res > 0) {
					result.setMsg("上传活动图片成功！");
				}
			}
		} else {
			result.setCode(ResultCode.Forbidden.getCode());
		}
		return result;
	}


	/**
	 * 获取缩略图的路径
	 */
	@Override
	public ResultVo<DjPic> selectThumbnailPath(Long id) {
		ResultVo<DjPic> result = new ResultVo<>();
		DjPic pic = picMapper.selectByPrimaryKey(id);
		if (pic == null) {
			logger.info("picture is null" + id);
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关图片");
			return result;
		}
		String path = pic.getPicUrl();
		logger.info("imgPath = " + path);
		File imgFile = new File(PICTURE_PATH + path);
		try {
			String thumbnailPath = imgFile.getCanonicalPath() + "_thumbnail.jpg";
			if (!new File(thumbnailPath).exists()) {
				ImageUtil.thumbnailImage(imgFile.getCanonicalPath(), 200, 200, PREVFIX, false);
			}
			pic.setPicUrl(thumbnailPath);
			result.setCode(ResultCode.OK.getCode());
			result.setData(pic);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	@Override
	public ResultVo<DjPic> getPicInfoById(Long id) {
		ResultVo<DjPic> result = new ResultVo<>();
		DjPic pic = picMapper.selectByPrimaryKey(id);
		if (pic == null) {
			logger.info("picture is null" + id);
			result.setCode(ResultCode.Forbidden.getCode());
			result.setMsg("没有相关图片");
			return result;
		}

		result.setCode(ResultCode.OK.getCode());
		result.setData(pic);

		return result;
	}

	/*
	 * (non-Javadoc) <p>Title: deleteActivePicById</p> <p>Description: 删除活动图片</p>
	 * 
	 * @param djActiveId, djPicId
	 * 
	 * @return
	 * 
	 * @see
	 * cn.dlbdata.dj.service.IPictureService#deleteActivePicById(java.lang.Long)
	 */
	@Override
	public int deleteActivePicById(Long djActiveId, Long djPicId) {
		if (djActiveId == null || djPicId == null) {
			return 0;
		}
		Example example = new Example(DjActivePic.class);
		example.createCriteria().andEqualTo("djActiveId", djActiveId).andEqualTo("djPicId", djPicId);
		int count = activePicMapper.deleteByExample(example);
		if (count > 0) {
			count = picMapper.deleteByPrimaryKey(djPicId);
		} else {
			return 0;
		}
		return count;
	}

}
