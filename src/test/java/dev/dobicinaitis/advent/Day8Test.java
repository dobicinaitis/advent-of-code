package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

    private Puzzle puzzle = new Day8();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(21, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(1779, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(8, puzzle.getSecondPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(172224, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}