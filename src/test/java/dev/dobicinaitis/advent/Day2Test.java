package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private Puzzle puzzle = new Day2();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(15, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(9651, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(12, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(10560, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}