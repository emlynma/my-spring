package com.emlynma.spring.data.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlynma.spring.data.entity.enums.UserSexEnum;
import com.emlynma.spring.data.entity.enums.UserStatusEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName(autoResultMap = true, excludeProperty = "dump")
public class User {

    private Long id;
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long uid;
    private String uname;
    private String phone;
    private String email;
    private String avatar;
    private LocalDate birthday;
    private UserSexEnum sex;
    private UserStatusEnum status;
    @TableField(typeHandler = JacksonTypeHandler.class)
    private ExtraInfo extraInfo;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime updateTime;

    @Data
    public static class ExtraInfo {
        private String address;
    }

}
