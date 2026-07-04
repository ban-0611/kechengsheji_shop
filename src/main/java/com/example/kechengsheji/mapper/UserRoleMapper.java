package com.example.kechengsheji.mapper;

import com.example.kechengsheji.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRoleMapper {

    int insert(UserRole userRole);
}