package cn.dlbdata.dj.db.mapper;

import java.util.Map;

import cn.dlbdata.dj.common.core.web.vo.ResultVo;
import cn.dlbdata.dj.db.pojo.DjActiveUser;
import tk.mybatis.mapper.common.Mapper;

public interface DjActiveUserMapper extends Mapper<DjActiveUser> {
	/**
	 * 
	 * <p>Title: insertActiveSignUp</p> 
	 * <p>Description: 金领驿站活动报名</p> 
	 * @param activeSignUpRequest
	 * @return
	 */
	public ResultVo<String> insertActiveSignUp(Map<String,String> map);

}