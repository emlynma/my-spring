package com.emlynma.spring.data.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.emlynma.spring.data.entity.enums.AuthTypeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName(autoResultMap = true)
public class Auth {

    private Long id;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private Long uid;

    private AuthTypeEnum type;

    private String identifier;

    private String credential;

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

    }

}
