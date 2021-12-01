package dev.dobicinaitis.advent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day1Test {

    private Day1 day1 = new Day1();

    @Test
    public void firstPuzzleExampleTest() {
        assertEquals(7, day1.getFirstPuzzleSolution("day1_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest() {
        assertEquals(1475, day1.getFirstPuzzleSolution("day1_my_input.txt"));
    }

    @Test
    public void secondPuzzleExampleTest() {
        assertEquals(5, day1.getSecondPuzzleSolution("day1_example_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest() {
        assertEquals(1516, day1.getSecondPuzzleSolution("day1_my_input.txt"));
    }
}