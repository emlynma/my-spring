package emlyn.ma.my.spring.boot.joke.common.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import emlyn.ma.my.spring.boot.common.util.JsonUtils;
import emlyn.ma.my.spring.boot.joke.domain.Joke;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class ApiSpaceClient {

    private static final String JOKES_URL = "https://eolink.o.apispace.com/xhdq/common/joke/getJokesByRandom";
    private static final Map<String, String> API_SPACE_HEADERS = Map.of(
            "Authorization-Type", "apikey",
            "X-APISpace-Token", "nqygaah1x6rl2825zqf4655sqvvd47na"
    );
    private static final String SUCCESS_CODE = "000000";

    private final RestTemplate restTemplate;

    public List<Joke> getJokesRandomly(Integer size) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAll(API_SPACE_HEADERS);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("pageSize", size);
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        try {
            JokesResponse jokesResponse = restTemplate.postForObject(JOKES_URL, requestEntity, JokesResponse.class);
            if (jokesResponse != null && SUCCESS_CODE.equals(jokesResponse.getStatusCode())) {
                return jokesResponse.getResult();
            }
            log.error("getJokesRandomly failed, httpResponse:{}", JsonUtils.toJson(jokesResponse));
        } catch (Exception e) {
            log.error("getJokesRandomly failed", e);
        }
        return List.of();
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class JokesResponse {
        private String statusCode;
        private List<Joke> result;
    }

}
