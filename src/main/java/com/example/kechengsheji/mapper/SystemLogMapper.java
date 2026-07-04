package com.example.kechengsheji.mapper;

import com.example.kechengsheji.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SystemLogMapper {

    void insert(SystemLog log);

    @Select("SELECT * FROM system_log ORDER BY create_time DESC")
    List<SystemLog> findAll();

    @Select("SELECT COUNT(*) FROM system_log")
    int count();
}