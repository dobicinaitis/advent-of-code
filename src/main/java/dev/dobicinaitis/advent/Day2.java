package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day2 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Game> games = getGames(input);
        // log.debug("Games: {}", games);
        return games.stream()
                .filter(game -> game.getSets().stream().allMatch(GameSet::isSetValid))
                .mapToInt(Game::getId)
                .sum();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Game> games = getGames(input);
        return calculatePart2Solution(games);
    }

    private List<Game> getGames(List<String> input) {
        return input.stream()
                .map(Game::new)
                .toList();
    }

    /**
     * Sum of multiplication of min number of each color of cubes in each game.
     */
    private int calculatePart2Solution(final List<Game> games) {
        int sumOfGamePowers = 0;
        for (Game game : games) {
            final int minNumberOfGreenCubes = game.getSets().stream()
                    .mapToInt(GameSet::getNumberOfGreenCubes)
                    .max()
                    .orElseThrow();
            final int minNumberOfRedCubes = game.getSets().stream()
                    .mapToInt(GameSet::getNumberOfRedCubes)
                    .max()
                    .orElseThrow();
            final int minNumberOfBlueCubes = game.getSets().stream()
                    .mapToInt(GameSet::getNumberOfBlueCubes)
                    .max()
                    .orElseThrow();
            sumOfGamePowers += minNumberOfGreenCubes * minNumberOfRedCubes * minNumberOfBlueCubes;
        }
        return sumOfGamePowers;
    }

    @Getter
    @ToString
    static class Game {
        private final int id;
        private final List<GameSet> sets;

        public Game(final String gameRecord) {
            this.id = Utils.extractFirstNumberFromString(gameRecord);
            final String gameSetRecord = gameRecord.replace("Game " + this.id + ": ", "");
            this.sets = Arrays.stream(gameSetRecord.split(";"))
                    .map(GameSet::new)
                    .toList();
        }
    }

    @Getter
    @ToString
    static class GameSet {
        private static final int MAX_NUMBER_OF_RED_CUBES = 12;
        private static final int MAX_NUMBER_OF_GREEN_CUBES = 13;
        private static final int MAX_NUMBER_OF_BLUE_CUBES = 14;

        private int numberOfRedCubes;
        private int numberOfGreenCubes;
        private int numberOfBlueCubes;
        private final boolean isSetValid;

        public GameSet(final String gameSetRecord) {
            final String[] cubes = gameSetRecord.split(",");
            for (String cube : cubes) {
                if (cube.endsWith("red")) {
                    this.numberOfRedCubes += Utils.extractFirstNumberFromString(cube);
                } else if (cube.endsWith("green")) {
                    this.numberOfGreenCubes += Utils.extractFirstNumberFromString(cube);
                } else if (cube.endsWith("blue")) {
                    this.numberOfBlueCubes += Utils.extractFirstNumberFromString(cube);
                }
            }
            this.isSetValid = this.numberOfRedCubes <= MAX_NUMBER_OF_RED_CUBES
                    && this.numberOfGreenCubes <= MAX_NUMBER_OF_GREEN_CUBES
                    && this.numberOfBlueCubes <= MAX_NUMBER_OF_BLUE_CUBES;
        }
    }
}
