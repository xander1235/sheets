package com.triffy.sheet.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v2/api-docs", "/swagger-ui/**", "/swagger-resources/**", "/webjars/**", "/actuator/**", "/v1**").permitAll()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**") // Ignore all endpoints from Spring Security
                .requestMatchers(request -> {
                    String host = request.getHeader("host");
                    return host != null && (host.equals("https://1978-2405-201-c411-4026-2da7-442a-866f-1cdb.ngrok-free.app") || host.equals("http://1978-2405-201-c411-4026-2da7-442a-866f-1cdb.ngrok-free.app"));
                });
    }

}
