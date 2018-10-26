package com.roje.game.cluster.interceptor;

import com.roje.game.cluster.utils.ResponseUtil;
import com.roje.game.core.redis.service.UserRedisService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    private final UserRedisService userRedisService;

    @Autowired
    public AuthHandlerInterceptor(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        String account = request.getParameter("account");
        String token = request.getParameter("token");

        PrintWriter writer = response.getWriter();
        if (StringUtils.isEmpty(account)){
            writer.write(ResponseUtil.error(ResponseUtil.ResponseData.USER_NAME_NOT_EMPTY));
            return false;
        }
        if (StringUtils.isBlank(account)){
            writer.write(ResponseUtil.error(ResponseUtil.ResponseData.USER_NAME_ERROR));
            return false;
        }
        if (!userRedisService.contain(account)){
            writer.write(ResponseUtil.error(ResponseUtil.ResponseData.USER_NOT_EXIST));
            return false;
        }
        if (!StringUtils.isBlank(token) || !StringUtils.equals(token,userRedisService.getToken(account))){
            writer.write(ResponseUtil.error(ResponseUtil.ResponseData.INVALID_TOKEN));
            return false;
        }
        return true;
    }
}
