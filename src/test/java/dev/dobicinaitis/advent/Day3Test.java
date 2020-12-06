package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day3Test {

    @Test
    public void treeWasHitTest(){
        assertTrue(Day3.wasTreeHit('#'));
        assertFalse(Day3.wasTreeHit('.'));
    }

    @Test
    public void extendPatternTest(){
        int targetLength = 10;
        int extendedPatternLength = Day3.getExtendedPattern("a", targetLength).length();
        assertTrue(extendedPatternLength >= targetLength);
    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(7, Day3.getFirstPuzzleSolution("day3_example_input.txt", 3, 1));
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(171, Day3.getFirstPuzzleSolution("day3_my_input.txt", 3, 1));
    }

    @Test
    public void secondPuzzleExampleTest(){
        int treesHitUsingDifferentSlopesMultipliedTogether =
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 1, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 3, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 5, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 7, 1) *
                Day3.getFirstPuzzleSolution("day3_example_input.txt", 1, 2);

        assertEquals(336, treesHitUsingDifferentSlopesMultipliedTogether);
    }

    @Test
    public void secondPuzzleMyInputTest(){
        int treesHitUsingDifferentSlopesMultipliedTogether =
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 1, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 3, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 5, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 7, 1) *
                Day3.getSecondPuzzleSolution("day3_my_input.txt", 1, 2);

        assertEquals(1206576000, treesHitUsingDifferentSlopesMultipliedTogether);
    }
}