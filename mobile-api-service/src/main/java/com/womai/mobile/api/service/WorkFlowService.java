package com.womai.mobile.api.service;

import com.womai.mobile.api.common.constant.Constant;
import com.womai.mobile.api.common.util.ResponseUtil;
import com.womai.mobile.api.common.util.prop.*;
import com.womai.mobile.api.common.util.prop.CacheGetProp;
import com.womai.mobile.api.domain.HeaderInfo;
import com.womai.mobile.api.domain.ValidationInfo;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.service.operate.cacheget.ICacheGet;
import com.womai.mobile.api.service.operate.cacheput.ICachePut;
import com.womai.mobile.api.service.operate.paramsopt.IParamsOperate;
import com.womai.mobile.api.service.operate.request.IServerRequest;
import com.womai.mobile.api.service.operate.response.IServerResponse;
import com.womai.mobile.api.service.util.BeanObtain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.service.request
 * 创建者: Chang Jinan
 * 日  期: 2016/5/12
 * 时  间: 16:24
 * 描  述：此类可用责任链的方式实现。但是考虑到栈会比较深所以用常规实现方式
 */
@Service("workFlowService")
public class WorkFlowService extends BaseService {

    @Autowired
    private BeanObtain beanObtain;

    public ResponseVO execute(ValidationInfo validationInfo, Map<String, String> paramsMap, String requestURI) throws Exception {

        ResponseVO responseVO = new ResponseVO();
        //1、是否处理请求参数
        String paramsOptBean = OptParamsProp.getMapValue(requestURI, "/service/params-operation.properties");
        if(paramsOptBean != null) {
            IParamsOperate operate = beanObtain.obtainBean(paramsOptBean);
            paramsMap = operate.execute(paramsMap);
        }
        //2、是否从缓存获取数据
        String cacheGetBean = CacheGetProp.getMapValue(requestURI, "/service/cache-get.properties");
        if(cacheGetBean != null) {
            ICacheGet operate = beanObtain.obtainBean(cacheGetBean);
            operate.execute(validationInfo, paramsMap);
        }
        //3、访问后台服务器获取数据
        String requestPath = ReqPathProp.getMapValue(requestURI, "/service/uri-requestpath.properties");
        if(requestPath != null) {
            String[] requestConfig = requestPath.split(":");
            IServerRequest requestBean = beanObtain.obtainBean(requestConfig[0]);
            responseVO = requestBean.httpRequest(validationInfo, paramsMap, requestConfig[1], requestConfig[2]);
        }
        else {
            String msg = ResponseUtil.getMessage(Constant.PATH_ISNULL);
            responseVO = ResponseUtil.getResponseVO(Constant.PATH_ISNULL, msg);
        }
        //4、处理返回结果
        String responseOptBean = ResponseProp.getMapValue(requestURI, "/service/response-operation.properties");
        if(responseOptBean != null) {
            IServerResponse operate = beanObtain.obtainBean(responseOptBean);
            operate.execute(responseVO);
        }
        //5、数据是否存入缓存
        String cachePutBean = CachePutProp.getMapValue(requestURI, "/service/cache-put.properties");
        if(cachePutBean != null) {
            ICachePut operate = beanObtain.obtainBean(cachePutBean);
            operate.execute(paramsMap);
        }

        return responseVO;
    }
}
