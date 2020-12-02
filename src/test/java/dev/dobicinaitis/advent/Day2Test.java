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
        assertEquals(passwordInfo.getMinMandatoryLetterOccurrences(),1);
        assertEquals(passwordInfo.getMaxMandatoryLetterOccurrences(),3);
        assertEquals(passwordInfo.getMandatoryLetter(),"a");
        assertEquals(passwordInfo.getPassword(),"abcde");
        assertTrue(passwordInfo.isValid());

        PasswordInfoForSledRentalShop passwordInfo2 = new PasswordInfoForSledRentalShop("10-16 r: nrrrrkrjtxwrrrwx");
        assertFalse(passwordInfo2.isValid());

        // correct policy
        PasswordInfoForTobogganRentalShop passwordInfo3 = new PasswordInfoForTobogganRentalShop("1-3 a: abcde");
        assertEquals(passwordInfo3.getFirstPositionOfMandatoryLetter(),1);
        assertEquals(passwordInfo3.getSecondPositionOfMandatoryLetter(),3);
        assertEquals(passwordInfo3.getMandatoryLetter(),"a");
        assertEquals(passwordInfo3.getPassword(),"abcde");
        assertTrue(passwordInfo3.isValid());

        PasswordInfoForTobogganRentalShop passwordInfo4 = new PasswordInfoForTobogganRentalShop("2-9 c: ccccccccc");
        assertFalse(passwordInfo4.isValid());
    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(Day2.getFirstPuzzleSolution("day2_example_input.txt"),2);
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(Day2.getFirstPuzzleSolution("day2_my_input.txt"),424);
    }


    @Test
    public void secondPuzzleExampleTest(){
        assertEquals(Day2.getSecondPuzzleSolution("day2_example_input.txt"),1);
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(Day2.getSecondPuzzleSolution("day2_my_input.txt"),747);
    }
}