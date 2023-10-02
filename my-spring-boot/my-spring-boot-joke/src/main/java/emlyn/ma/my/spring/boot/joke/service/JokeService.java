package emlyn.ma.my.spring.boot.joke.service;

import emlyn.ma.my.spring.boot.joke.common.client.ApiSpaceClient;
import emlyn.ma.my.spring.boot.joke.domain.Joke;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JokeService {

    private final ApiSpaceClient apiSpaceClient;
    private final RedisTemplate<String, String> redisTemplate;

    public List<String> getJokes(int size) {
        List<Joke> jokes = apiSpaceClient.getJokesRandomly(20);

        redisTemplate.opsForHash().putAll("jokeList", jokes.stream()
                .collect(Collectors.toMap(joke -> String.valueOf(joke.getId()), Joke::getContent)));

        return jokes.stream()
                .map(Joke::getContent)
                .toList().subList(0, size);
    }

}
