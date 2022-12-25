package org.example;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class GameController {
    private static final Integer MAX_GENERATIONS = 100;
    private static final Integer TIME_STEP_MILLIS = 1000;
    private final GameBoard gameBoard;
    private final Display display;

    public GameController() throws IOException, URISyntaxException {
        Boolean[][] seedFromFile = fileStream("seed.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        display = new Display();
        this.gameBoard = new GameBoard(seedFromFile);
    }

    /**
     * Main game loop
     * While game is running it will display current generation, compute the next, then sleep for the duration
     */
    public void run() throws InterruptedException {
        int generation = 0;
        while (generation < MAX_GENERATIONS) {
            System.out.println();
            System.out.println("Generation: " + generation);
            display.display(gameBoard);

            gameBoard.step();

            generation++;
            TimeUnit.MILLISECONDS.sleep(TIME_STEP_MILLIS);
        }
    }

    public Stream<String> fileStream(String fileName) throws IOException, URISyntaxException {
        URL res = getClass().getClassLoader().getResource(fileName);
        return Files.lines(Paths.get(res.toURI()));
    }
}