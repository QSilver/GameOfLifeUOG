package org.example;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardTest {
    private GameBoard underTest;

    public Stream<String> fileStream(String fileName) throws IOException, URISyntaxException {
        URL res = getClass().getClassLoader().getResource(fileName);
        return Files.lines(Paths.get(res.toURI()));
    }

    @Test
    void testInitializeBoard() throws IOException, URISyntaxException {
        Boolean[][] initial = fileStream("initialize.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        underTest = new GameBoard(initial);

        for (int i = 0; i < initial.length; i++) {
            for (int j = 0; j < initial[i].length; j++) {
                assertEquals(initial[i][j], underTest.getBoard()[i][j]);
            }
        }
    }

    @Test
    void testGetNeighbours() throws IOException, URISyntaxException {
        Boolean[][] initial = fileStream("initialize.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        int[][] neighbours = {{2, 1, 2},
                              {3, 2, 3},
                              {2, 1, 2}};

        underTest = new GameBoard(initial);

        for (int i = 0; i < initial.length; i++) {
            for (int j = 0; j < initial[i].length; j++) {
                assertEquals(neighbours[i][j], underTest.getCurrentNeighbours(initial, i, j));
            }
        }
    }

    @Test
    void testSpawnOrDie() throws IOException, URISyntaxException {
        Boolean[][] initial = fileStream("initialize.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);
        int[][] neighbours = {{2, 1, 2},
                              {3, 2, 3},
                              {2, 1, 2}};
        Boolean[][] expected = fileStream("expected.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        underTest = new GameBoard(initial);

        for (int i = 0; i < initial.length; i++) {
            for (int j = 0; j < initial[i].length; j++) {
                assertEquals(expected[i][j], underTest.spawnOrDie(initial[i][j], neighbours[i][j]));
            }
        }
    }

    @Test
    void testStep() throws IOException, URISyntaxException {
        Boolean[][] initial = fileStream("initialize.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);
        Boolean[][] expected = fileStream("expected.txt")
                .map(line -> line.split(""))
                .map(splitLine -> Arrays.stream(splitLine).map(c -> c.equals("1")).toArray(Boolean[]::new))
                .toArray(Boolean[][]::new);

        underTest = new GameBoard(initial);
        underTest.step();

        for (int i = 0; i < initial.length; i++) {
            for (int j = 0; j < initial[i].length; j++) {
                assertEquals(expected[i][j], underTest.getBoard()[i][j]);
            }
        }

        underTest.step();

        for (int i = 0; i < initial.length; i++) {
            for (int j = 0; j < initial[i].length; j++) {
                assertEquals(initial[i][j], underTest.getBoard()[i][j]);
            }
        }
    }
}