package com.example.kechengsheji.controller;

import com.example.kechengsheji.entity.Category;  // 添加这个导入
import com.example.kechengsheji.entity.Product;
import com.example.kechengsheji.service.ProductService;
import com.example.kechengsheji.service.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public IndexController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String index(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            model.addAttribute("username", auth.getName());
            model.addAttribute("isLoggedIn", true);
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(granted -> granted.getAuthority().equals("ROLE_admin"));
            model.addAttribute("isAdmin", isAdmin);
        } else {
            model.addAttribute("username", "游客");
            model.addAttribute("isLoggedIn", false);
            model.addAttribute("isAdmin", false);
        }
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        List<Product> products = productService.getAllProducts();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        return "shop";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/api/stats")
    @ResponseBody
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        List<Product> products = productService.getAllProducts();
        stats.put("productCount", products != null ? products.size() : 0);
        stats.put("categoryCount", categoryService.getAllCategories() != null ?
                categoryService.getAllCategories().size() : 0);
        return stats;
    }

    @GetMapping("/api/user/info")
    @ResponseBody
    public Map<String, Object> getUserInfo() {
        Map<String, Object> userInfo = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            userInfo.put("username", auth.getName());
            userInfo.put("loggedIn", true);
            boolean isAdmin = auth.getAuthorities().stream()
                    .anyMatch(granted -> granted.getAuthority().equals("ROLE_admin"));
            userInfo.put("isAdmin", isAdmin);
        } else {
            userInfo.put("username", "游客");
            userInfo.put("loggedIn", false);
            userInfo.put("isAdmin", false);
        }
        return userInfo;
    }
}