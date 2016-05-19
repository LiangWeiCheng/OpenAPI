package com.womai.mobile.api.web.entrance;

import com.womai.mobile.api.common.util.ResponseUtil;
import com.womai.mobile.api.common.util.prop.ControllerProp;
import com.womai.mobile.api.domain.response.ResponseVO;
import com.womai.mobile.api.web.controller.BaseController;
import com.womai.mobile.api.service.util.BeanObtain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 工  程：womai-mobile-api
 * 包  名：com.womai.mobile.api.web.controller
 * 创建者: Chang Jinan
 * 日  期: 2016/5/9
 * 时  间: 11:17
 * 描  述：
 */
@Controller
@Scope("prototype")
@RequestMapping("/")
public class ApiEntrance {

    protected transient final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BeanObtain beanObtain;

    private ResponseVO responseVO;

    /**
     * 前端公共接口，对应get方法，获取资源
     * @return
     */
    @RequestMapping(value = "*/*", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO getApi2Path(HttpServletRequest request) {
        String path = request.getRequestURI();
        try {
            String beanName = ControllerProp.getMapValue(path, "/controller/api-url-controller.properties");
            //如果没有配置前置机功能接口，则认为是请求后台服务器的接口
            if(beanName == null) {
                BaseController controller = beanObtain.obtainBean("apiController");
                responseVO = controller.execute(request);
            }
            else {
                BaseController controller = beanObtain.obtainBean(beanName);
                responseVO = controller.execute(request);
            }
        } catch (Exception e) {
            String code = ResponseUtil.exceptionCode(e);
            String msg = ResponseUtil.getMessage(code);
            responseVO = ResponseUtil.getResponseVO(code, msg);
            logger.error(path + " error" + e.getMessage(), e);
        }
        return responseVO;
    }

    /**
     * 前端公共接口，对应get方法，获取资源
     * @return
     */
    @RequestMapping(value = "*", method = RequestMethod.POST)
    public @ResponseBody
    ResponseVO getApi1Path(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        try {
            String beanName = ControllerProp.getMapValue(requestURI, "/controller/api-url-controller.properties");
            //如果没有配置前置机功能接口，则认为是请求后台服务器的接口
            if(beanName == null) {
                BaseController controller = beanObtain.obtainBean("apiController");
                responseVO = controller.execute(request);
            }
            else {
                BaseController controller = beanObtain.obtainBean(beanName);
                responseVO = controller.execute(request);
            }
        } catch (Exception e) {
            String code = ResponseUtil.exceptionCode(e);
            String msg = ResponseUtil.getMessage(code);
            responseVO = ResponseUtil.getResponseVO(code, msg);
            logger.error(requestURI + " error" + e.getMessage(), e);
        }
        return responseVO;
    }

    public ResponseVO getResponseVO() {
        return responseVO;
    }

    public void setResponseVO(ResponseVO responseVO) {
        this.responseVO = responseVO;
    }
}
