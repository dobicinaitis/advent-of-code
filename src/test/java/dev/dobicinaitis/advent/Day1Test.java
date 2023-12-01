package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private Puzzle puzzle = new Day1();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(142, puzzle.getFirstPuzzleSolution(puzzle.exampleInputFilename));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(54630, puzzle.getFirstPuzzleSolution(puzzle.myInputFilename));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(281, puzzle.getSecondPuzzleSolution("day1_example_2_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(54770, puzzle.getSecondPuzzleSolution(puzzle.myInputFilename));
    }
}