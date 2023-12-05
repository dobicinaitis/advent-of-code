package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private final Puzzle puzzle = new Day2();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(8, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(1931, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(2286, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(83105, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}