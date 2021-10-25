package tech.naive.anasystem.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.naive.anasystem.utils.JWTUtil;
import tech.naive.anasystem.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 2018034605 冯天阳
 * @version 1.0
 * @date 10/25/2021 11:10 AM
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!StringUtils.isNoneBlank(token)){
            LoginInterceptor.falseHandler(response,Result.build(200,"TOKEN_INVALID","token无效"));
            return false;
        }
        Boolean verify;
        try {
            verify = JWTUtil.verify(token);
            if(verify){
                request.setAttribute("userName",JWTUtil.decodeUser(token));
                return true;
            }else {
                LoginInterceptor.falseHandler(response,Result.build(200,"TOKEN_INVALID","token无效"));
                return false;
            }
        } catch (Exception e) {
            LoginInterceptor.falseHandler(response,Result.build(200,"TOKEN_INVALID","token无效"));
            return false;
        }
    }

    private static void falseHandler(HttpServletResponse response,Result result) throws Exception{
        response.setStatus(200);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(result));
        response.getWriter().flush();
    }
}
