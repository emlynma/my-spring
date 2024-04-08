package com.emlynma.spring.data.entity;

import com.emlynma.spring.core.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.annotation.Id;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

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
    private Status status;
    @Column("extra_info")
    private ExtraInfo extraInfo;
    @Column("create_time")
    private LocalDateTime createTime;
    @Column("update_time")
    private LocalDateTime updateTime;

    @Data
    public static class ExtraInfo {
        private Boolean isStudent;
    }

    @Getter
    @AllArgsConstructor
    public enum Status {
        INIT(0)
        ;
        private final int code;
        public static Status valueOf(int code) {
            return Arrays.stream(Status.values()).filter(s -> s.code == code).findAny().orElse(null);
        }
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

    @ReadingConverter
    public static class StatusReadingConverter implements Converter<Integer, Status> {
        @Override
        public Status convert(@NonNull Integer source) {
            return Status.valueOf(source);
        }
    }

    @WritingConverter
    public static class StatusWritingConverter implements Converter<Status, Integer> {
        @Override
        public Integer convert(@NonNull Status source) {
            return source.code;
        }
    }

}
