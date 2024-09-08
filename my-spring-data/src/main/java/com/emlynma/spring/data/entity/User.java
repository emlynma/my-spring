package com.emlynma.spring.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlynma.spring.data.entity.enums.SexEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName(autoResultMap = true)
public class User {

    private Long id;
    private Long uid;
    private String name;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;
    private SexEnum sex;
    private Integer status;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ExtraInfo extraInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ExtraInfo {
        private Boolean isStudent;
    }

}
