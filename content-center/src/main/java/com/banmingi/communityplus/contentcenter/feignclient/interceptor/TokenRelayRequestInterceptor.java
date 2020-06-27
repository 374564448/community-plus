package com.banmingi.communityplus.contentcenter.feignclient.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 半命i 2020/6/22
 * @description 在通过feign请求之前拦截,把token加入请求中
 */
public class TokenRelayRequestInterceptor implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
    //1. 从header里面获取token
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        String token = request.getHeader("X-Token");

        //2. 传递token
        if (StringUtils.isNoneBlank(token)) {
            requestTemplate.header("X-Token",token);
        }
    }
}
