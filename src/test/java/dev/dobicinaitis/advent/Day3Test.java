package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day3Test {

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(Day3.getFirstPuzzleSolution("day3_example_input.txt", 3, 1),7);
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(Day3.getFirstPuzzleSolution("day3_my_input.txt", 3, 1),171);
    }

    @Test
    public void secondPuzzleExampleTest(){
        int treesHitUsingDifferentSlopesMultipliedTogether =
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 1, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 3, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 5, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 7, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 1, 2);

        assertEquals(treesHitUsingDifferentSlopesMultipliedTogether,336);
    }

    @Test
    public void secondPuzzleMyInputTest(){
        int treesHitUsingDifferentSlopesMultipliedTogether =
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 1, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 3, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 5, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 7, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 1, 2);

        assertEquals(treesHitUsingDifferentSlopesMultipliedTogether,1206576000);
    }
}