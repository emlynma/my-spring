package emlyn.ma.my.spring.boot.joke.controller;

import emlyn.ma.my.spring.boot.common.CommonResponse;
import emlyn.ma.my.spring.boot.joke.service.JokeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/joke")
@RequiredArgsConstructor
public class JokeController {

    private final JokeService jokeService;

    @RequestMapping("/jokeList/{size}")
    public CommonResponse<List<String>> jokeList(@PathVariable Integer size) {
        return CommonResponse.success(jokeService.getJokes(size));
    }

}
