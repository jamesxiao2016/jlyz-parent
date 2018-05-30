package cn.dlbdata.dj.thirdparty.mp.sdk.service;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GetUserInfo;
import net.sf.json.JSONObject;

/**
 * @Description: 自定义菜单服务
 */
public interface UserInfoService {

    public JSONObject userInfo(GetUserInfo getUserInfo) throws DangjianException ;

}
