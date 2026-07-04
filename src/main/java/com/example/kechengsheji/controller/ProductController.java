package com.example.kechengsheji.controller;

import com.example.kechengsheji.entity.Category;
import com.example.kechengsheji.entity.Product;
import com.example.kechengsheji.service.CategoryService;
import com.example.kechengsheji.service.ProductService;
import com.example.kechengsheji.util.LogUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LogUtil logUtil;

    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName();
        }
        return "系统";
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/list")
    public String list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            Model model) {

        PageInfo<Product> pageInfo = productService.searchProducts(
                categoryId, keyword, minPrice, maxPrice, pageNum, pageSize
        );

        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("categories", categories);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("keyword", keyword);
        model.addAttribute("minPrice", minPrice);
        model.addAttribute("maxPrice", maxPrice);

        return "productList";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productAdd";
    }

    @PostMapping("/add")
    public String add(Product product) {
        String username = getCurrentUsername();
        productService.addProduct(product);
        logUtil.success(username, "新增商品", "添加了「" + product.getName() + "」");
        return "redirect:/product/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        Product product = productService.getProductById(id);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "productEdit";
    }

    @PostMapping("/edit")
    public String edit(Product product) {
        String username = getCurrentUsername();
        productService.updateProduct(product);
        logUtil.success(username, "编辑商品", "修改了商品「" + product.getName() + "」");
        return "redirect:/product/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        String username = getCurrentUsername();
        Product product = productService.getProductById(id);
        productService.deleteProduct(id);
        logUtil.success(username, "删除商品", "删除了商品「" + product.getName() + "」");
        return "redirect:/product/list";
    }
}