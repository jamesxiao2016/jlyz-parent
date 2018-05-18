package cn.dlbdata.dangjian.api;

import cn.dlbdata.dangjian.db.pojo.DjUser;

/**
 * 主要处理用户相关的业务逻辑
 * @author xiaowei
 *
 */
public interface IUserService extends IBaseService {
	public DjUser getUserInfoById(Long id);
}
