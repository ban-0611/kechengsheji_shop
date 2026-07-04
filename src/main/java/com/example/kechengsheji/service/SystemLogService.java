package com.example.kechengsheji.service;

import com.example.kechengsheji.entity.SystemLog;
import com.example.kechengsheji.mapper.SystemLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemLogService {

    @Autowired
    private SystemLogMapper systemLogMapper;

    public void saveLog(SystemLog log) {
        systemLogMapper.insert(log);
    }

    public List<SystemLog> getAllLogs() {
        return systemLogMapper.findAll();
    }

    public int count() {
        return systemLogMapper.count();
    }
}