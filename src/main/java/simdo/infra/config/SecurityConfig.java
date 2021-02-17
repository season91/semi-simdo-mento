package simdo.infra.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import simdo.infra.common.auth.CustomOAuth2UserService;
import simdo.module.member.Role;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final DataSource dataSource;
    private final CustomOAuth2UserService customOAuth2UserService; //구글 로그인 세팅

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .mvcMatchers("/", "/login", "/member/sign-up", "/member/check-email-token", "/member/email-login", "/member/login-by-email"
                        , "/member/find-password", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() //구글 로그인을 위한 세팅
                .mvcMatchers(HttpMethod.GET, "/profile/*").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //구글 로그인을 위한 세팅
                .antMatchers("/notice/write").hasRole("ADMIN")//관리자 권한을 위한 허용
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/login")
                .and().oauth2Login().loginPage("/login") //구글 로그인을 위한 세팅(로그인 페이지 기존 페이지로 지정)
                .and().csrf().ignoringAntMatchers("/login/oauth2/code/google") //구글 로그인을 위한 세팅
                .and().csrf().ignoringAntMatchers("/oauth2/authorization/google") //구글 로그인을 위한 세팅
                .and().csrf().ignoringAntMatchers("/movie/translation") // 번역 API 비동기통신을 위해 특정 url만 csrf 비활성화하였다.
                .and().oauth2Login().userInfoEndpoint().userService(customOAuth2UserService); //구글 로그인을 위한 세팅
        httpSecurity.formLogin()
                .loginPage("/login").permitAll();

        httpSecurity.logout()
                .logoutSuccessUrl("/");

        httpSecurity.rememberMe()
                .userDetailsService(userDetailsService)
                .tokenRepository(tokenRepository());
    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }

    @Override
    public void configure(WebSecurity webSecurity) {
        webSecurity.ignoring()
                .mvcMatchers("/node_modules/**")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

}
