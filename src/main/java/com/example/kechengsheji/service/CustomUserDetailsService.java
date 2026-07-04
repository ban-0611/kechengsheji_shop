package com.example.kechengsheji.service;

import com.example.kechengsheji.entity.User;
import com.example.kechengsheji.mapper.UserMapper;
import com.example.kechengsheji.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LogUtil logUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            logUtil.warning("系统", "登录失败", "用户「" + username + "」不存在");
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        List<String> roles = userMapper.findRolesByUserId(user.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (roles != null) {
            for (String role : roles) {
                authorities.add(new SimpleGrantedAuthority(role));
            }
        }

        // 记录登录成功日志
        logUtil.success(username, "用户登录", "用户「" + username + "」登录成功");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getActive() == 1,
                true, true, true,
                authorities
        );
    }
}