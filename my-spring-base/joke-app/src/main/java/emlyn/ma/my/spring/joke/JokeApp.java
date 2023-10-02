package emlyn.ma.my.spring.joke;

import emlyn.ma.my.spring.joke.common.client.JokeApiClient;
import emlyn.ma.my.spring.joke.launcher.JokeLauncher;
import emlyn.ma.my.spring.joke.service.impl.JokeServiceImpl;

public class JokeApp {

    public static void main(String[] args) {
        new JokeLauncher(new JokeServiceImpl(new JokeApiClient())).run();
    }

}
