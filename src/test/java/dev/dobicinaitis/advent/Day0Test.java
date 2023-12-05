package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day0Test {

    private final Puzzle puzzle = new Day0();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(0, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(0, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(0, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(0, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}