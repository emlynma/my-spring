package emlyn.ma.my.spring.joke.service.impl;

import emlyn.ma.my.spring.joke.common.client.JokeApiClient;
import emlyn.ma.my.spring.joke.common.client.domain.GetJokeRequest;
import emlyn.ma.my.spring.joke.common.client.domain.GetJokeResponse;
import emlyn.ma.my.spring.joke.service.JokeService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class JokeServiceImpl implements JokeService {

    private final JokeApiClient jokeApiClient;

    @Override
    public List<String> getJokeList(int count) {
        return queryJoke(count);
    }

    private List<String> queryJoke(int count) {
        GetJokeRequest request = new GetJokeRequest();
        request.setPageSize(count);
        GetJokeResponse response = jokeApiClient.getJoke(request);
        if (response == null) {
            return List.of();
        }
        return response.getResult().stream().map(GetJokeResponse.Joke::getContent).toList();
    }

}
