package com.emlynma.spring.data.entity;

import com.emlynma.spring.core.util.JsonUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Table("user")
public class User {

    @Id
    private Long id;
    private Long uid;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthday;
    private Integer sex;
    private Integer status;
    @Column("extra_info")
    private ExtraInfo extraInfo;
    @Column("create_time")
    private LocalDateTime createTime;
    @Column("update_time")
    private LocalDateTime updateTime;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ExtraInfo {
        private Boolean isStudent;
    }

    @ReadingConverter
    public static class ExtraInfoReadingConverter implements Converter<String, ExtraInfo> {
        @Override
        public ExtraInfo convert(@NonNull String source) {
            return JsonUtils.toObject(source, ExtraInfo.class);
        }
    }

    @WritingConverter
    public static class ExtraInfoWritingConverter implements Converter<ExtraInfo, String> {
        @Override
        public String convert(@NonNull ExtraInfo source) {
            return JsonUtils.toJson(source);
        }
    }

}
