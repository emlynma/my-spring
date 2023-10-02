package emlyn.ma.my.spring.joke.common.client;

import emlyn.ma.my.spring.joke.common.client.domain.GetJokeRequest;
import emlyn.ma.my.spring.joke.common.client.domain.GetJokeResponse;
import emlyn.ma.my.spring.joke.common.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class JokeApiClient {

    private static final String API_URL = "https://eolink.o.apispace.com/xhdq/common/joke/getJokesByRandom";

    private static final HttpClient HTTP_CLIENT  = HttpClient.newBuilder()
            .connectTimeout(Duration.ofMillis(500))
            .build();

    public GetJokeResponse getJoke(GetJokeRequest request) {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .POST(HttpRequest.BodyPublishers.ofString(buildQuery(request)))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("X-APISpace-Token", "nqygaah1x6rl2825zqf4655sqvvd47na")
                .header("Authorization-Type", "apikey")
                .build();
        HttpResponse<String> httpResponse = null;
        try {
            httpResponse = HTTP_CLIENT.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return Optional.ofNullable(httpResponse)
                    .filter(response -> Objects.equals(response.statusCode(), 200))
                    .map(HttpResponse::body)
                    .map(body -> JsonUtils.toObject(body, GetJokeResponse.class))
                    .orElseThrow();
        } catch (Exception e) {
            log.warn("getJoke failed, request:{}, response:{}",
                    JsonUtils.toJson(request), Optional.ofNullable(httpResponse).map(HttpResponse::body).orElse(null), e);
            return null;
        }
    }

    private String buildQuery(Object request) {
        Map<String, String> map = JsonUtils.toMap(request, String.class);
        return buildQuery(map);
    }

    private String buildQuery(Map<String, String> params) {
        StringBuilder query = new StringBuilder();
        params.entrySet().stream()
                .filter(entry -> entry.getValue() != null)
                .forEach(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    query.append(key).append("=").append(value).append("&");
                });
        if (!query.isEmpty()) {
            query.deleteCharAt(query.length() - 1);
        }
        return query.toString();
    }

}
