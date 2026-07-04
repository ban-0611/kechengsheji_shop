package com.example.kechengsheji.mapper;

import com.example.kechengsheji.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM t_user WHERE username = #{username}")
    User findByUsername(String username);

    @Select("SELECT * FROM t_user WHERE id = #{id}")
    User findById(Integer id);

    @Select("SELECT r.role FROM t_user_role ur JOIN t_role r ON ur.role_id = r.id WHERE ur.user_id = #{userId}")
    List<String> findRolesByUserId(Integer userId);

    int insert(User user);
}