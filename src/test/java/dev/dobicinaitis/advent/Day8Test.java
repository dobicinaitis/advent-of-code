package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day8Test {

    private final Puzzle puzzle = new Day8();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(2, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(12737, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(6L, puzzle.getSecondPuzzleSolution("day8_example_input_2.txt"));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(9064949303801L, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}