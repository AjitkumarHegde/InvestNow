package com.investnow.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    private UserAuthService userAuthService;

    private ObjectMapper objectMapper;

    @Autowired
    public SecurityConfig(UserAuthService userAuthService, ObjectMapper objectMapper)
    {
        this.userAuthService = userAuthService;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        web
        .ignoring()
        .mvcMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/swagger/**",
                "/swagger-ui/**",
                "/webjars/**"
            );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
            .csrf()
            .disable()
            .addFilter(getJWTAuthenticationFilter())
            .addFilterAfter(new JwtTokenValidator(), JWTAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/user/login", "/user/signup", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-ui.html", "/webjars/**")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(
                        new AccessDeniedHandler()
                        {
                            @Override
                            public void handle(
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    AccessDeniedException accessDeniedException
                            )
                                    throws IOException                            {
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.getWriter().write("Access denied!");
                            }
                        }
                );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    public DaoAuthenticationProvider daoAuthenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userAuthService);
        return provider;
    }

    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JWTAuthenticationFilter getJWTAuthenticationFilter() throws Exception {
        final JWTAuthenticationFilter filter = new JWTAuthenticationFilter(authenticationManagerBean(), objectMapper);
        filter.setFilterProcessesUrl("/user/login");
        return filter;
    }

    @Bean
    public FilterRegistrationBean<Filter> webCorsFilter()
    {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean<Filter> bean = new FilterRegistrationBean<Filter>(new CorsFilter(source));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
