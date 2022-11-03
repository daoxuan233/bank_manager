package book.manager.config;

import book.manager.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    //继承WebSecurityConfigurerAdapter，之后会进行配置

    @Resource
    UserAuthService service;

    @Resource
    PersistentTokenRepository repository;

    @Bean
    public PersistentTokenRepository jdbcRepository(@Autowired DataSource dataSource){
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();  //使用基于JDBC的实现
        repository.setDataSource(dataSource);   //配置数据源
        return repository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()   //首先需要配置哪些请求会被拦截，哪些请求必须具有什么角色才能访问
                .antMatchers("/static/**")
                .permitAll()    //静态资源，使用permitAll来运行任何人访问（注意一定要放在前面）
                .antMatchers("/**").hasAnyRole("user","admin")     //所有请求必须登陆并且是user角色才可以访问（不包含上面的静态资源）
                .and()
                //登录相关
                .formLogin()
                .usernameParameter("username")
                .passwordParameter("password")
                //配置Form表单登陆
                .loginPage("/login")
                //登陆页面地址（GET）
                .loginProcessingUrl("/doLogin")    //form表单提交地址（POST）
                .defaultSuccessUrl("/index",true)    //登陆成功后跳转的页面，也可以通过Handler实现高度自定义
                .permitAll()    //登陆页面也需要允许所有人访问
                .and()
                //退出相关
                .logout()
                .logoutUrl("/logout")    //退出登陆的请求地址

                .logoutSuccessUrl("/login")   //退出后重定向的地址
                // 关闭CSRF
                /*.and()
                .csrf()
                .disable()*/
                .and()
                .rememberMe()   //开启记住我功能
                .rememberMeParameter("remember")  //登陆请求表单中需要携带的参数，如果携带，那么本次登陆会被记住
                .tokenRepository(repository)  //这里使用的是直接在内存中保存的TokenRepository实现
                //TokenRepository有很多种实现，InMemoryTokenRepositoryImpl直接基于Map实现的，缺点就是占内存、服务器重启后记住我功能将失效
                //后面我们还会讲解如何使用数据库来持久化保存Token信息
                .tokenValiditySeconds(60*2)  //Token的有效时间（秒）默认为14天;单位 秒
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(service)
                .passwordEncoder(new BCryptPasswordEncoder());//这里使用SpringSecurity提供的BCryptPasswordEncoder
    }

}