package cn.jt.security;

import cn.jt.security.impl.*;
import cn.jt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    UserService userService;

    // 需要放行的路径
    public static final String[] AUTH_WHITELIST = {
            "/api/user/getCaptcha",
            "/logout",
            "/login",
            "/logout",
    };


    // 设置用户权限，从数据库中获取用户信息，将 userService 传给它，它就能自动的从数据库获取用户的信息
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
                .passwordEncoder(new Md5PasswordEncoderImpl());
    }

    /* 设置哪些请求需要什么权限才能访问，比如我们设置的普通用户只能访问自己的资源和公共资源，系统管理员可以访问所有资源 */
    @Override
    protected void configure(HttpSecurity http) throws Exception {


        // 基于 token，所以不需要 session
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()...

        // 跨域请求会先进行一次 options 请求
        // .antMatchers(HttpMethod.OPTIONS).permitAll()...

        http
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                 // 可以匿名访问的链接
                .antMatchers(AUTH_WHITELIST).permitAll()
                 // 我的反馈
                .antMatchers("/nav1/sub1").hasRole("我的台账")
                .antMatchers("/nav1/sub2").hasRole("工资清单")
                 // 反馈列表
                .antMatchers("/nav2/sub1").hasRole("反馈列表")
                .antMatchers("/nav2/sub2").hasRole("提交反馈")
                 // 反馈审核
                .antMatchers("/nav3/sub1").hasRole("等待审核")
                .antMatchers("/nav3/sub2").hasRole("审核统计")
                 // 工资管理
                .antMatchers("/nav4/sub1").hasRole("工资汇总")
                .antMatchers("/nav4/sub2").hasRole("工资发放")
                 // 系统管理
                .antMatchers("/nav5/sub1").hasRole("用户管理")
                .antMatchers("/nav5/sub2").hasRole("角色管理")
                .antMatchers("/nav5/sub3").hasRole("权限管理")
                .antMatchers("/nav5/sub4").hasRole("日志管理")
                 // 字典管理
                .antMatchers("/nav6/sub1").hasRole("部门管理")
                .antMatchers("/nav6/sub2").hasRole("公告管理")
                .antMatchers("/nav6/sub3").hasRole("变动类型管理")
                .antMatchers("/nav6/sub4").hasRole("变动原因管理")
                 // 所有请求都要权限验证
                .anyRequest().authenticated()
                .and()
                 // 跨域跨站请求处理
                .cors().and().csrf().disable()
                // 将验证码过滤器加在用户名密码验证前
                .addFilterBefore(new CaptchaFilter(), UsernamePasswordAuthenticationFilter.class)

                // 将 token 过滤器加在验证码过滤器之前
                .addFilterBefore(new TokenFilter(), CaptchaFilter.class)

                // 登录处理
                .formLogin().loginProcessingUrl("/login")

                // 登录成功处理
                .successHandler(new LoginSuccessHandlerImpl())
                 // 登录失败处理
                .failureHandler(new LoginFailureHandlerImpl())
                .and()

                 // 注销处理 - 退出登录
                .logout().logoutSuccessHandler(new LogoutHandlerImpl()).permitAll()
                // 无权限处理
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new AccessDeniedHandlerImpl());
                 // 没有登录处理，加上后不再进入登录界面
                 //.authenticationEntryPoint(new AuthenticationEntryPointImpl());

    }

}
