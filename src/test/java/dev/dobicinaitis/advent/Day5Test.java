package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Day5Test {

    @Test
    public void getUpperHalfTest(){
        assertEquals(Day5.getUpperHalf(0,127), 64);
    }

    @Test
    public void getLowerHalfTest(){
        assertEquals(Day5.getLowerHalf(0,127), 63);
    }

    @Test
    public void getSeatRowFromBoardingPassTest(){
        assertEquals(Day5.getSeatRowFromBoardingPass("FBFBBFFRLR", 0, 127, 7), 44);
    }

    @Test
    public void getSeatColumnFromBoardingPassTest(){
        assertEquals(Day5.getSeatColumnFromBoardingPass("FBFBBFFRLR", 0, 7, 3), 5);
    }

    @Test
    public void getSeatIdTest(){
        assertEquals(Day5.getSeatId(44,5), 357);
    }

    @Test
    public void getEmptySeatIdTest(){
        int[] sortedSeatIds = new int[]{1,2,3,5};
        assertEquals(Day5.getEmptySeatId(sortedSeatIds), 4);
    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(Day5.getFirstPuzzleSolution("day5_example_input.txt"), 357);
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(Day5.getFirstPuzzleSolution("day5_my_input.txt"), 959);
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(Day5.getSecondPuzzleSolution("day5_my_input.txt"), 527);
    }
}