package com.example.kechengsheji.entity;

import lombok.Data;
import java.util.Date;

@Data
public class SystemLog {
    private Integer id;
    private String logLevel;
    private String username;
    private String action;
    private String detail;
    private String ip;
    private Date createTime;
}