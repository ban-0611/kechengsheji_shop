package com.example.kechengsheji.mapper;

import org.apache.ibatis.jdbc.SQL;

public class ProductSqlProvider {

    public String searchProducts(Integer categoryId, String keyword,
                                 Double minPrice, Double maxPrice) {
        return new SQL() {{
            SELECT("p.*, c.name as category_name");
            FROM("product p");
            LEFT_OUTER_JOIN("category c ON p.cat_id = c.id");

            if (categoryId != null && categoryId != 0) {
                WHERE("p.cat_id = #{categoryId}");
            }
            if (keyword != null && !keyword.isEmpty()) {
                WHERE("(p.name LIKE CONCAT('%', #{keyword}, '%') " +
                        "OR p.descp LIKE CONCAT('%', #{keyword}, '%'))");
            }
            if (minPrice != null) {
                WHERE("p.price >= #{minPrice}");
            }
            if (maxPrice != null) {
                WHERE("p.price <= #{maxPrice}");
            }
            ORDER_BY("p.id DESC");
        }}.toString();
    }
}