package emlyn.ma.my.spring.joke.common.client.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class GetJokeResponse {

    private String statusCode;
    private List<Joke> result;

    @Data
    public static class Joke {
        private Integer id;
        private String content;
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private Date updateTime;
    }

}
