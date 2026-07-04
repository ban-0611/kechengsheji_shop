package com.example.kechengsheji.service;

import com.example.kechengsheji.entity.Category;
import com.example.kechengsheji.mapper.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取所有分类 - 使用 Spring Cache 缓存
     * 第一次查询 MySQL，后续从缓存读取（不执行 SQL）
     */
    @Cacheable(value = "categories", key = "'all'")
    public List<Category> getAllCategories() {
        System.out.println("❌【缓存未命中】从 MySQL 查询分类列表");
        List<Category> categories = categoryMapper.findAll();
        System.out.println("✅【已写入缓存】分类列表已缓存，共 " + categories.size() + " 条数据");
        return categories;
    }

    @Cacheable(value = "category", key = "#id")
    public Category getCategoryById(Integer id) {
        System.out.println("❌【缓存未命中】从 MySQL 查询分类 ID=" + id);
        Category category = categoryMapper.selectByPrimaryKey(id);
        System.out.println("✅【已写入缓存】分类 ID=" + id + " 已缓存");
        return category;
    }

    @CacheEvict(value = "categories", key = "'all'")
    public void addCategory(Category category) {
        System.out.println("📝【新增分类】清理分类列表缓存");
        categoryMapper.insert(category);
        System.out.println("✅【新增成功】分类: " + category.getName());
    }

    @CachePut(value = "category", key = "#category.id")
    @CacheEvict(value = "categories", key = "'all'")
    public Category updateCategory(Category category) {
        System.out.println("📝【更新分类】更新分类 ID=" + category.getId() + "，清理列表缓存");
        categoryMapper.updateByPrimaryKey(category);
        System.out.println("✅【更新成功】分类: " + category.getName());
        return category;
    }

    @CacheEvict(value = {"category", "categories"}, allEntries = true)
    public void deleteCategory(Integer id) {
        System.out.println("📝【删除分类】清理所有分类缓存");
        categoryMapper.deleteByPrimaryKey(id);
        System.out.println("✅【删除成功】分类 ID=" + id + " 已删除");
    }
}