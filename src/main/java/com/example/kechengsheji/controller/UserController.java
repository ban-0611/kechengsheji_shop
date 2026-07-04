package com.example.kechengsheji.controller;

import com.example.kechengsheji.entity.User;
import com.example.kechengsheji.service.UserService;
import com.example.kechengsheji.util.LogUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LogUtil logUtil;

    @GetMapping
    public String registerPage() {
        return "register";
    }

    @PostMapping
    public String register(User user, Model model) {
        try {
            userService.registerUser(user);
            logUtil.success(user.getUsername(), "用户注册", "新用户「" + user.getUsername() + "」注册成功");
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}