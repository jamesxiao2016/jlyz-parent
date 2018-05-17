package cn.dlbdata.dangjian.api;

import cn.dlbdata.dangjian.db.pojo.DjUser;

public interface IUserService extends IBaseService {
	public DjUser getUserInfoById(Long id);
}
