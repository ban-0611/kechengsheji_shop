package com.example.kechengsheji.service;

import com.example.kechengsheji.entity.Product;
import com.example.kechengsheji.mapper.ProductMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 获取所有商品 - 使用 Spring Cache 缓存
     */
    @Cacheable(value = "products", key = "'all'")
    public List<Product> getAllProducts() {
        System.out.println("❌【缓存未命中】从 MySQL 查询商品列表");
        List<Product> products = productMapper.selectAll();
        System.out.println("✅【已写入缓存】商品列表已缓存，共 " + products.size() + " 条数据");
        return products;
    }

    /**
     * 分页查询 - 不缓存（条件多变）
     */
    public PageInfo<Product> searchProducts(Integer categoryId, String keyword,
                                            Double minPrice, Double maxPrice,
                                            int pageNum, int pageSize) {
        System.out.println("🔍【分页查询】从 MySQL 搜索商品");
        PageHelper.startPage(pageNum, pageSize);
        List<Product> list = productMapper.search(categoryId, keyword, minPrice, maxPrice);
        return new PageInfo<>(list);
    }

    @Cacheable(value = "product", key = "#id")
    public Product getProductById(Integer id) {
        System.out.println("❌【缓存未命中】从 MySQL 查询商品 ID=" + id);
        Product product = productMapper.selectByPrimaryKey(id);
        System.out.println("✅【已写入缓存】商品 ID=" + id + " 已缓存");
        return product;
    }

    @CacheEvict(value = "products", key = "'all'")
    public void addProduct(Product product) {
        System.out.println("📝【新增商品】清理商品列表缓存");
        productMapper.insert(product);
        System.out.println("✅【新增成功】商品: " + product.getName());
    }

    @CachePut(value = "product", key = "#product.id")
    @CacheEvict(value = "products", key = "'all'")
    public Product updateProduct(Product product) {
        System.out.println("📝【更新商品】更新商品 ID=" + product.getId() + "，清理列表缓存");
        productMapper.updateByPrimaryKey(product);
        System.out.println("✅【更新成功】商品: " + product.getName());
        return product;
    }

    @CacheEvict(value = {"product", "products"}, allEntries = true)
    public void deleteProduct(Integer id) {
        System.out.println("📝【删除商品】清理所有商品缓存");
        productMapper.deleteByPrimaryKey(id);
        System.out.println("✅【删除成功】商品 ID=" + id + " 已删除");
    }
}