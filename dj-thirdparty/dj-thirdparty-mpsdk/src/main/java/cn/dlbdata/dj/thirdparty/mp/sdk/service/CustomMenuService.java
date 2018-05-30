package cn.dlbdata.dj.thirdparty.mp.sdk.service;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.thirdparty.mp.sdk.constant.RequestUrls;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.AccessTokenResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.access.GetaAccessTokenParam;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.BaseMpApiResponse;

/**
 * @Description: 自定义菜单服务
 */
public interface CustomMenuService {
    
    public BaseMpApiResponse createMenu(String json) throws DangjianException ;

}
