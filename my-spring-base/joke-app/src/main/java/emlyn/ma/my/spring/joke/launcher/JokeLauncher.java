package emlyn.ma.my.spring.joke.launcher;

import emlyn.ma.my.spring.joke.service.JokeService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Scanner;

@RequiredArgsConstructor
public class JokeLauncher {

    private final JokeService jokeService;

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            if ("exit".equals(input)) {
                break;
            }
            int count;
            try {
                count = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please input a number.");
                continue;
            }
            List<String> jokeList = jokeService.getJokeList(count);
            if (jokeList.isEmpty()) {
                System.out.println("No joke.");
            }
            jokeList.forEach(System.out::println);
        }
    }

}
