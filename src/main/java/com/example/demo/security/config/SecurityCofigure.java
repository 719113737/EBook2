package com.example.demo.security.config;

import com.example.demo.entity.UserInfo;
import com.example.demo.security.MyAccessDecisionManager;
import com.example.demo.security.MyAuthenticationProvider;
import com.example.demo.security.MyInvocationSecurityMetadataSourceService;
import com.example.demo.security.jwt.AuthEntryPointJwt;
import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * SpringSecurity配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级别的权限认证
public class SecurityCofigure extends WebSecurityConfigurerAdapter{

//    @Bean
//    public static BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthTokenFilter authenticationTokenFilter() {
//        return new AuthTokenFilter();
//    }
//
//    /**
//     * api相关配置
//     */
//    @Configuration
//    @Order(1)
//    public static class apiSecurityConfig extends WebSecurityConfigurerAdapter {
//
//        @Autowired
//        private AuthTokenFilter jwtauthFilter;
//
//        @Autowired
//        UserService userService;
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http
//                    .csrf().disable()
//                    .antMatcher("api/**")
//                    .authorizeRequests()
//                    .antMatchers("/api/auth/**").permitAll()
//                    .antMatchers("/api/**").hasAnyRole("USER")
//                    .and()
//                    .addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);
//
//            //表示不用session
//            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//            .httpBasic().authenticationEntryPoint(new AuthEntryPointJwt());
//        }
//
//        @Override
//        public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws  Exception {
//            authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
//        }
//
//        @Bean
//        @Override
//        public AuthenticationManager authenticationManagerBean() throws Exception {
//            return super.authenticationManagerBean();
//        }
//    }
//
//    /**
//     * 其他操作相关配置
//     */
//    @Configuration
//    @Order(2)
//    public static class webSecurityConfiguration extends WebSecurityConfigurerAdapter {
//        @Autowired
//        private UserService userService;
//
//        @Autowired
//        private JwtUtils jwtUtils;
//
//        @Bean
//        public DaoAuthenticationProvider authenticationProvider() {
//            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
//            auth.setUserDetailsService(userService);
//            auth.setPasswordEncoder(passwordEncoder());
//            return auth;
//        }
//
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            //todo 添加无需通过验证的路由
//            http
//                    .authorizeRequests()
//                    .antMatchers("/js/**",
//                            "/css/**",
//                            "/img/**",
//                            "webjars/**",
//                            "swagger-ui/**").permitAll()
//                    .anyRequest().authenticated()
//                    .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .successHandler(new AuthenticationSuccessHandler() {
//                        @Override
//                        public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                            httpServletResponse.setContentType("application/json;charset=utf-8");
//                            PrintWriter out = httpServletResponse.getWriter();
//                            Map<String,Object> map = new HashMap<>();
//                            map.put("status",200);
//                            map.put("token",jwtUtils.generateJwtToken(authentication));
//                            map.put("msg","");
//                            UserInfo userInfo = (UserInfo)authentication.getPrincipal();
//                            map.put("data",userInfo.getUsername());
//                            out.write(new ObjectMapper().writeValueAsString(map));
//                            out.flush();
//                            out.close();
//                        }
//                    })
//                    .permitAll()
//                    .and()
//                    .logout()
//                    //.invalidateHttpSession(true)
//                    //.clearAuthentication(true)
//                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                    .permitAll()
//                    .and()
//                    .csrf().disable();
//
//        }
//
//        @Override
//        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//            auth.authenticationProvider(authenticationProvider());
//        }
//    }

    @Autowired
    private AuthEntryPointJwt authEntryPointJwt;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    //todo ConfigureAuthentication

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authTokenFilter() throws Exception {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/js/**",
                            "/css/**",
                            "/img/**",
                            "webjars/**",
                            "swagger-ui/**",
                            "/register"
                        ).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        Map<String,Object> map = new HashMap<>();
                        map.put("status",200);
                        map.put("token",jwtUtils.generateJwtToken(authentication));
                        map.put("msg","");
                        UserInfo userInfo = (UserInfo)authentication.getPrincipal();
                        map.put("data",userInfo.getUsername());
                        out.write(new ObjectMapper().writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                }).permitAll();

        http
                .addFilterBefore(authTokenFilter(),UsernamePasswordAuthenticationFilter.class);

        http.headers().cacheControl();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws  Exception {
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
