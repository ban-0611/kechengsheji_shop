package com.example.kechengsheji.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                // 游客可以访问的页面
                .antMatchers("/", "/login", "/register", "/welcome", "/css/**", "/js/**", "/images/**").permitAll()
                // 需要登录才能访问
                .antMatchers("/shop", "/api/**").authenticated()
                // 只有管理员可以访问增删改
                .antMatchers("/product/add", "/product/edit/**", "/product/delete/**").hasRole("admin")
                .antMatchers("/category/add", "/category/edit/**", "/category/delete/**").hasRole("admin")
                // 管理员和普通用户都可以查看列表
                .antMatchers("/product/list", "/category/list").hasAnyRole("admin", "normal")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login?logout=true")
                .permitAll();
    }
}