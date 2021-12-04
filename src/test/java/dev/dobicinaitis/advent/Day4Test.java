package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day4Test {

    private Puzzle puzzle = new Day4();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(4512, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(22680, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(1924, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(16168, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}