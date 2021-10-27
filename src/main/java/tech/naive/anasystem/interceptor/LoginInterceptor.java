package tech.naive.anasystem.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import tech.naive.anasystem.domain.User;
import tech.naive.anasystem.service.UserService;
import tech.naive.anasystem.utils.JWTUtil;
import tech.naive.anasystem.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "token");
        // 如果是OPTIONS请求则结束
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return false;
        }

        String token = request.getHeader("token");
        if(!StringUtils.isNoneBlank(token)){
            LoginInterceptor.falseHandler(response,Result.build(200,"TOKEN_INVALID","token无效"));
            return false;
        }
        Boolean verify;
        try {
            verify = JWTUtil.verify(token);
            if(verify){
                Long requestUserId = JWTUtil.decodeUserId(token);
                User user = userService.getUserById(requestUserId);
                if(user == null || user.getDeleted() == 1){
                    LoginInterceptor.falseHandler(response,Result.build(200,"NOT_FOUND_USER","请求用户不存在"));
                    return false;
                }else if(user.getState() == 1){
                    LoginInterceptor.falseHandler(response,Result.build(200,"BLOCKED","用户已被冻结"));
                    return false;
                }else{
                    request.setAttribute("requestUserId",requestUserId);
                    return true;
                }
            }else {
                LoginInterceptor.falseHandler(response,Result.build(200,"TOKEN_INVALID","token无效"));
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
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