package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private final Puzzle puzzle = new Day4();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(13, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(21105, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(30, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(5329815, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}