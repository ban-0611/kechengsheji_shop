package com.example.kechengsheji.util;

import com.example.kechengsheji.entity.SystemLog;
import com.example.kechengsheji.service.SystemLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogUtil {

    @Autowired
    private SystemLogService systemLogService;

    public void log(String level, String username, String action, String detail) {
        SystemLog log = new SystemLog();
        log.setLogLevel(level);
        log.setUsername(username);
        log.setAction(action);
        log.setDetail(detail);
        systemLogService.saveLog(log);
        System.out.println("📝【系统日志】" + username + " " + action + ": " + detail);
    }

    public void info(String username, String action, String detail) {
        log("info", username, action, detail);
    }

    public void success(String username, String action, String detail) {
        log("success", username, action, detail);
    }

    public void warning(String username, String action, String detail) {
        log("warning", username, action, detail);
    }

    public void error(String username, String action, String detail) {
        log("error", username, action, detail);
    }
}