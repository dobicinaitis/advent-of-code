package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day8Test {

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(5, Day8.getFirstPuzzleSolution("day8_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(1262, Day8.getFirstPuzzleSolution("day8_my_input.txt"));
    }

    @Test
    public void secondPuzzleExampleInputTest(){
        assertEquals(8, Day8.getSecondPuzzleSolution("day8_example_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(1643, Day8.getSecondPuzzleSolution("day8_my_input.txt"));
    }
}