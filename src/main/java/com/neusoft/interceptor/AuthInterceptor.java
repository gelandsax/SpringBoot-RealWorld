package com.neusoft.interceptor;

import com.neusoft.exception.AuthFailException;
import com.neusoft.mapper.UsersMapper;
import com.neusoft.model.Users;
import com.neusoft.utils.JwtService;
import com.neusoft.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    JwtService jwtService;

    @Autowired
    UsersMapper usersMapper;

    private void AuthFail(){
        throw new AuthFailException("认证失败");
    }
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String method = request.getMethod();

        String token = request.getHeader("Authorization");
        if(token != null){
            token = token.replace("Token ","");
            if(jwtService.validateToken(token)){
                String userId = jwtService.getSub(token);

                Users users = usersMapper.selectById(Integer.parseInt(userId));

                if(users != null){
                    //将userid保存到一个全局位置，在其他任何类中都可以访问到。
                    UserUtils.setLoginUser(users);
                    System.out.println(users.toString());
                    return true;
                }
                else{
                    AuthFail();
                    return false;
                }
            }
            else
            {
                AuthFail();
                return false;
            }
        }
        else
        {
            if(method.equalsIgnoreCase("GET")
                    && url.startsWith("/api/articles")){
                return true;
            }
            AuthFail();
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        UserUtils.removeUser();
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
