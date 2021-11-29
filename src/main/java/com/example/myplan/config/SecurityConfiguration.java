package com.example.myplan.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService customUsersDetailsService;

    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUsersDetailsService = customUserDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUsersDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers(HttpMethod.POST, "/user").permitAll()
                .mvcMatchers(HttpMethod.POST, "/task").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .mvcMatchers(HttpMethod.PATCH, "/user").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .mvcMatchers(HttpMethod.PATCH, "/task").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .mvcMatchers(HttpMethod.GET,"/user").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .mvcMatchers(HttpMethod.GET,"/task").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .mvcMatchers(HttpMethod.PUT,"/user").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .mvcMatchers(HttpMethod.PUT,"/task").access("hasRole('ADMIN') or hasRole('NORMAL')")
                .anyRequest().denyAll() //任何尚未匹配的 URL 都将被拒绝访问
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable(); //默认csrf token 校验开启，针对 POST, PUT, PATCH
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();;
    }
}
