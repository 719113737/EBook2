package com.example.demo.security.config;

import com.example.demo.security.MyAccessDecisionManager;
import com.example.demo.security.MyAuthenticationProvider;
import com.example.demo.security.MyInvocationSecurityMetadataSourceService;
import com.example.demo.security.jwt.AuthTokenFilter;
import com.example.demo.service.UserService;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * SpringSecurity配置文件
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级别的权限认证
public class SecurityCofigure {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthTokenFilter authenticationTokenFilter() {
        return new AuthTokenFilter();
    }

    /**
     * api相关配置
     */
    @Configuration
    @Order(1)
    public static class apiSecurityConfig extends WebSecurityConfigurerAdapter {

        @Autowired
        private AuthTokenFilter jwtauthFilter;

        @Autowired
        UserService userService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("api/**")
                    .authorizeRequests()
                    .antMatchers("/api/auth/**").permitAll()
                    .antMatchers("/api/**").hasAnyRole("USER")
                    .and()
                    .addFilterBefore(jwtauthFilter, UsernamePasswordAuthenticationFilter.class);

            //表示不用session
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
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

    /**
     * 其他操作相关配置
     */
    @Configuration
    @Order(2)
    public static class webSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private UserService userService;

        @Bean
        public DaoAuthenticationProvider authenticationProvider() {
            DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
            auth.setUserDetailsService(userService);
            auth.setPasswordEncoder(passwordEncoder());
            return auth;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //todo 添加无需通过验证的路由
            http
                    .authorizeRequests()
                    .antMatchers("/js/**",
                            "/css/**",
                            "/img/**",
                            "webjars/**",
                            "swagger-ui/**").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                    .logout()
                    .invalidateHttpSession(true)
                    .clearAuthentication(true)
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .permitAll()
                    .and()
                    .csrf().disable();

        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.authenticationProvider(authenticationProvider());
        }
    }
}
