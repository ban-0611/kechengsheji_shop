package com.example.kechengsheji.mapper;

import com.example.kechengsheji.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface RoleMapper {

    @Select("SELECT * FROM t_role WHERE role = #{role}")
    Role findByRole(String role);

    int insert(Role role);
}