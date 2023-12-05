package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day3Test {

    private final Puzzle puzzle = new Day3();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(4361, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(539433, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(467835, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(75847567, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}