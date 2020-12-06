package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day5Test {

    @Test
    public void getUpperHalfTest(){
        assertEquals(64, Day5.getUpperHalf(0,127));
    }

    @Test
    public void getLowerHalfTest(){
        assertEquals(63, Day5.getLowerHalf(0,127));
    }

    @Test
    public void getSeatRowFromBoardingPassTest(){
        assertEquals(44, Day5.getSeatRowFromBoardingPass("FBFBBFFRLR", 0, 127, 7));
    }

    @Test
    public void getSeatColumnFromBoardingPassTest(){
        assertEquals(5, Day5.getSeatColumnFromBoardingPass("FBFBBFFRLR", 0, 7, 3));
    }

    @Test
    public void getSeatIdTest(){
        assertEquals(357, Day5.getSeatId(44,5));
    }

    @Test
    public void getEmptySeatIdTest(){
        int[] sortedSeatIds = new int[]{1,2,3,5};
        assertEquals(4, Day5.getEmptySeatId(sortedSeatIds));
    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(357, Day5.getFirstPuzzleSolution("day5_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(959, Day5.getFirstPuzzleSolution("day5_my_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(527, Day5.getSecondPuzzleSolution("day5_my_input.txt"));
    }
}