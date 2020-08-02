package com.banmingi.communityplus.contentcenter.auth;

import com.banmingi.communityplus.commons.annotations.CheckAuthorization;
import com.banmingi.communityplus.commons.exception.SecurityException;
import com.banmingi.communityplus.commons.utils.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author 半命i 2020/6/9
 * @description 认证授权切面.
 */
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {

    private final JwtOperator jwtOperator;

    /**
     * 检查登录状态
     * @param point
     * @return
     */
    @Around("@annotation(com.banmingi.communityplus.commons.annotations.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {
        this.checkToken();
        return point.proceed();
    }

    /**
     * 权限验证
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.banmingi.communityplus.commons.annotations.CheckAuthorization)")
    public Object checkAuthorization(ProceedingJoinPoint point) throws Throwable {
        try {
            //1. 验证token是否合法
            String token = this.checkToken();
            //2. 验证用户角色是否匹配
            //从token中获取用户角色
            Claims claims = jwtOperator.getClaimsFromToken(token);
            String roles = claims.get("roles", String.class);

            MethodSignature signature = (MethodSignature) point.getSignature();
            //拿到添加@CheckAuthorization注解的方法
            Method method = signature.getMethod();
            //拿到@CheckAuthorization注解
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);
            String value = annotation.value();
            if (!Objects.equals(roles,value)) {
                throw new SecurityException("用户无权访问！");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new SecurityException("用户无权访问！");
        }
        return point.proceed();
    }

    /**
     * 验证token.
     */
    private String checkToken() {
        //1. 从header里面获取token
        HttpServletRequest request = getHttpServletRequest();
        String token = request.getHeader("X-Token");
        try {
            //2. 校验token是否合法或在有效期内,如果不合法或已过期,直接抛异常;如果合法或未过期,放行
            Boolean isValid = jwtOperator.validateToken(token);

            if (!isValid) {
                throw new SecurityException("token 不合法！");
            }
            //3. 如果校验成功,就将用户的信息设置到request的attribute里面
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id",claims.get("id"));
            request.setAttribute("wxNickname",claims.get("wxNickname"));
            request.setAttribute("role",claims.get("role"));
        } catch (Throwable throwable) {
            throw new SecurityException("token 不合法！");
        }

        return token;
    }

    /**
     * 获取request
     * @return request.
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        assert attributes != null;
        return attributes.getRequest();
    }

}
