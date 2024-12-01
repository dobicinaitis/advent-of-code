package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private final Puzzle puzzle = new Day1();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(11, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(1189304, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(31, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(24349736, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}