package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day2Test {

    @Test
    public void passwordCheckTest(){
        // wrong policy
        PasswordInfoForSledRentalShop passwordInfo = new PasswordInfoForSledRentalShop("1-3 a: abcde");
        assertEquals(1, passwordInfo.getMinMandatoryLetterOccurrences());
        assertEquals(3,passwordInfo.getMaxMandatoryLetterOccurrences());
        assertEquals("a",passwordInfo.getMandatoryLetter());
        assertEquals("abcde", passwordInfo.getPassword());
        assertTrue(passwordInfo.isValid());

        PasswordInfoForSledRentalShop passwordInfo2 = new PasswordInfoForSledRentalShop("10-16 r: nrrrrkrjtxwrrrwx");
        assertFalse(passwordInfo2.isValid());

        // correct policy
        PasswordInfoForTobogganRentalShop passwordInfo3 = new PasswordInfoForTobogganRentalShop("1-3 a: abcde");
        assertEquals(1, passwordInfo3.getFirstPositionOfMandatoryLetter());
        assertEquals(3, passwordInfo3.getSecondPositionOfMandatoryLetter());
        assertEquals("a", passwordInfo3.getMandatoryLetter());
        assertEquals("abcde", passwordInfo3.getPassword());
        assertTrue(passwordInfo3.isValid());

        PasswordInfoForTobogganRentalShop passwordInfo4 = new PasswordInfoForTobogganRentalShop("2-9 c: ccccccccc");
        assertFalse(passwordInfo4.isValid());
    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(2, Day2.getFirstPuzzleSolution("day2_example_input.txt"));
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(424, Day2.getFirstPuzzleSolution("day2_my_input.txt"));
    }


    @Test
    public void secondPuzzleExampleTest(){
        assertEquals(1, Day2.getSecondPuzzleSolution("day2_example_input.txt"));
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(747, Day2.getSecondPuzzleSolution("day2_my_input.txt"));
    }
}