package com.example.kechengsheji.controller;

import com.example.kechengsheji.entity.SystemLog;
import com.example.kechengsheji.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SettingsController {

    @Autowired
    private SystemLogService systemLogService;

    @GetMapping("/settings")
    public String settings(Model model) {
        List<SystemLog> logs = systemLogService.getAllLogs();
        model.addAttribute("logs", logs);
        return "settings";
    }
}