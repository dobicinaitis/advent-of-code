package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day2Test {

    private Puzzle puzzle = new Day2();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(150, puzzle.getFirstPuzzleSolution("day2_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(1480518, puzzle.getFirstPuzzleSolution("day2_my_input.txt"));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(900, puzzle.getSecondPuzzleSolution("day2_example_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(1282809906, puzzle.getSecondPuzzleSolution("day2_my_input.txt"));
    }
}