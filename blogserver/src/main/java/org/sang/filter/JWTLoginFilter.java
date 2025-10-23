package org.sang.filter;

import org.sang.pojo.po.User;
import org.sang.service.UserService;
import org.sang.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTLoginFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userService;
    private AntPathMatcher antPathMatcher=new AntPathMatcher();
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if(isLoginRequest(httpServletRequest.getRequestURI())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String token = httpServletRequest.getHeader("token");
        if(token!=null&&!token.isEmpty()){
            // 解析令牌
            Long userId = JWTUtils.getUserId(token);
            // 加载用户信息
            User user = userService.getUserById(userId);
            UserDetails userDetails = userService.loadUserByUsername(user.getUsername());
            // 将用户信息存入SpringSecurity上下文
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 判断是否是登录请求
     * @param url
     * @return
     */
    private Boolean isLoginRequest(String url) {
        return antPathMatcher.match("/login", url)&&antPathMatcher.match("/login/**", url);
    }
}
