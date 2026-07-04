package com.example.kechengsheji.service;

import com.example.kechengsheji.entity.Role;
import com.example.kechengsheji.entity.User;
import com.example.kechengsheji.entity.UserRole;
import com.example.kechengsheji.mapper.RoleMapper;
import com.example.kechengsheji.mapper.UserMapper;
import com.example.kechengsheji.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(User user) {
        User existUser = userMapper.findByUsername(user.getUsername());
        if (existUser != null) {
            throw new RuntimeException("用户名已存在，请换一个");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActive(1);

        userMapper.insert(user);

        Role role = roleMapper.findByRole("ROLE_normal");
        if (role == null) {
            role = new Role();
            role.setRole("ROLE_normal");
            roleMapper.insert(role);
        }

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getId());
        userRole.setRoleId(role.getId());
        userRoleMapper.insert(userRole);
    }
}