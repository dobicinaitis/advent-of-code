package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day6Test {

    @Test
    public void removeNonLetterSymbolsTest(){
        assertEquals("abc",Day6.removeNonLetterSymbols("a1\nb9c"));
    }

    @Test
    public void getUniqueLetterCountTest(){
        String input1 = "abcd";
        assertEquals(4,Day6.getUniqueLetterCount(input1));
    }

    @Test
    public void getCommonLetterCountTest(){
        assertEquals(3, Day6.getCommonLetterCount(List.of("abc")));
        assertEquals(0, Day6.getCommonLetterCount(List.of("a","b","c")));
        assertEquals(1, Day6.getCommonLetterCount(List.of("ab","ac")));
        assertEquals(1, Day6.getCommonLetterCount(List.of("a","a","a","a")));
        assertEquals(1, Day6.getCommonLetterCount(List.of("b")));
        assertEquals(3, Day6.getCommonLetterCount(List.of("abc","abc","abc")));

    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(11, Day6.getFirstPuzzleSolution("day6_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(6504, Day6.getFirstPuzzleSolution("day6_my_input.txt"));
    }

    @Test
    public void secondPuzzleExampleInputTest(){
        assertEquals(6, Day6.getSecondPuzzleSolution("day6_example_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(3351, Day6.getSecondPuzzleSolution("day6_my_input.txt"));
    }
}