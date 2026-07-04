package com.example.kechengsheji.controller;

import com.example.kechengsheji.entity.Category;
import com.example.kechengsheji.service.CategoryService;
import com.example.kechengsheji.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/category")
public class CategoryController {

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

    @GetMapping("/list")
    public String list(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "categoryList";
    }

    @GetMapping("/add")
    public String addPage(Model model) {
        model.addAttribute("category", new Category());
        return "categoryAdd";
    }

    @PostMapping("/add")
    public String add(Category category) {
        String username = getCurrentUsername();
        categoryService.addCategory(category);
        logUtil.success(username, "新增分类", "添加了分类「" + category.getName() + "」");
        return "redirect:/category/list";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {
        Category category = categoryService.getCategoryById(id);
        model.addAttribute("category", category);
        return "categoryEdit";
    }

    @PostMapping("/edit")
    public String edit(Category category) {
        String username = getCurrentUsername();
        categoryService.updateCategory(category);
        logUtil.success(username, "编辑分类", "修改了分类「" + category.getName() + "」");
        return "redirect:/category/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        String username = getCurrentUsername();
        Category category = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        logUtil.success(username, "删除分类", "删除了分类「" + category.getName() + "」");
        return "redirect:/category/list";
    }
}