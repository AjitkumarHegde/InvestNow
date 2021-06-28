package com.investnow.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.investnow.api.impl.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Value("${jwt.secret}")
    protected String jwtSecret;

    private UserServiceImpl userService;

    @Autowired
    public SecurityConfig(UserServiceImpl userService)
    {
        this.userService = userService;
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
            .addFilterBefore(jwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeRequests()
            .antMatchers("/**/user/***", "/v2/api-docs", "/configuration/ui", "/swagger-resources", "/swagger-ui.html", "/webjars/**")
            .permitAll()
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
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
                                response.getWriter().write("Access denied! Login in as Admin");
                            }
                        }
                );
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
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

    @Bean
    public JwtTokenAuthenticationFilter jwtTokenAuthenticationFilter() {
        return new JwtTokenAuthenticationFilter();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManager() throws Exception
    {
        return super.authenticationManager();
    }
}
