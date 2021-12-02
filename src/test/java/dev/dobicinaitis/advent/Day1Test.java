package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private Puzzle puzzle = new Day1();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(7, puzzle.getFirstPuzzleSolution("day1_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(1475, puzzle.getFirstPuzzleSolution("day1_my_input.txt"));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(5, puzzle.getSecondPuzzleSolution("day1_example_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(1516, puzzle.getSecondPuzzleSolution("day1_my_input.txt"));
    }
}