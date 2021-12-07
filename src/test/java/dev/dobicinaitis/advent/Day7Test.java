package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day7Test {

    private Puzzle puzzle = new Day7();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(37, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(352997, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(168, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(101571302, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}