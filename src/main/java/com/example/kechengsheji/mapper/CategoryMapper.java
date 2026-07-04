package com.example.kechengsheji.mapper;

import com.example.kechengsheji.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {

    @Select("SELECT * FROM category ORDER BY id")
    List<Category> findAll();

    @Select("SELECT * FROM category WHERE id = #{id}")
    Category selectByPrimaryKey(Integer id);

    int insert(Category category);

    int updateByPrimaryKey(Category category);

    int deleteByPrimaryKey(Integer id);
}