package com.example.kechengsheji.mapper;

import com.example.kechengsheji.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT p.*, c.name as category_name FROM product p " +
            "LEFT JOIN category c ON p.cat_id = c.id " +
            "ORDER BY p.id DESC")
    List<Product> selectAll();

    @Select("SELECT p.*, c.name as category_name FROM product p " +
            "LEFT JOIN category c ON p.cat_id = c.id " +
            "WHERE p.id = #{id}")
    Product selectByPrimaryKey(Integer id);

    List<Product> search(@Param("categoryId") Integer categoryId,
                         @Param("keyword") String keyword,
                         @Param("minPrice") Double minPrice,
                         @Param("maxPrice") Double maxPrice);

    int insert(Product product);

    int updateByPrimaryKey(Product product);

    int deleteByPrimaryKey(Integer id);
}