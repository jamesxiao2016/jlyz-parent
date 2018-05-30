package cn.dlbdata.dj.thirdparty.mp.sdk.service;

import cn.dlbdata.dj.common.DangjianException;
import cn.dlbdata.dj.common.http.annotations.AnnotationExplainer;
import cn.dlbdata.dj.common.rest.HttpWebApi;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.BaseMpApiResponse;
import cn.dlbdata.dj.thirdparty.mp.sdk.model.common.BaseParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static cn.dlbdata.dj.common.http.ErrorHandler.missingRequiredParam;

import java.util.Map;

public abstract class BaseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseService.class);

    private HttpWebApi client;

    public BaseService(HttpWebApi client) {
        this.client = client;
    }

    protected <R extends BaseMpApiResponse, P extends BaseParam> R getResponse(String url, P param, Class<R> responseType) throws DangjianException {
        Map<String, Object> paramMap = AnnotationExplainer.explainParamMap(param);
        R response = client.getForObject(url, paramMap, responseType);
        return response;
    }

    protected <R extends BaseMpApiResponse, P extends BaseParam> R postEntity(String url, P param, Class<R> responseType) throws DangjianException {
        if (param == null) {
            LOGGER.error("==========创建的实体不能为空==========");
            throw missingRequiredParam();
        }
        Map<String, Object> paramMap = AnnotationExplainer.explainParamMap(param);
        return client.postForObject(url, paramMap, responseType);
    }

}
