package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private final Puzzle puzzle = new Day1();

    @Test
    void firstPuzzleExampleTest() {
        assertEquals(142, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    void firstPuzzleMyInputTest() {
        assertEquals(54630, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    void secondPuzzleExampleTest() {
        assertEquals(281, puzzle.getSecondPuzzleSolution("day1_example_2_input.txt"));
    }

    @Test
    void secondPuzzleMyInputTest() {
        assertEquals(54770, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}