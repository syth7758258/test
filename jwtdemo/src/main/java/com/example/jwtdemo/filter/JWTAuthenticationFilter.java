package com.example.jwtdemo.filter;

import com.example.jwtdemo.JwtTokenUtil;
import com.example.jwtdemo.apply.LoginUser;
import com.example.jwtdemo.bean.JwtUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 该拦截器用于获取用户登录的信息，只需创建一个token并调用authenticationManager.authenticate()让spring-security去进行验证就可以了，不用自己查数据库再对比密码了，这一步交给spring去操作。
 这个操作有点像是shiro的subject.login(new UsernamePasswordToken())，验证的事情交给框架。
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        //这里可以设置一下登录的url地址
        super.setFilterProcessesUrl("/auth/login");
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try {
            LoginUser loginUser = new ObjectMapper().readValue(request.getInputStream(), LoginUser.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), new ArrayList<>())
            );
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 验证成功
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        // 所以就是JwtUser啦
        JwtUser jwtUser = (JwtUser) authResult.getPrincipal();
        System.out.println("jwtUser:" + jwtUser.toString());
        String token = JwtTokenUtil.createToken(jwtUser.getUsername(), false);
        // 返回创建成功的token
        // 但是这里创建的token只是单纯的token
        // 按照jwt的规定，最后请求的格式应该是 `Bearer token`
        response.setHeader("token", JwtTokenUtil.TOKEN_PREFIX + token);
    }

    /**
     * 验证失败
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("验证失败了: " + failed.getMessage());
    }
}
