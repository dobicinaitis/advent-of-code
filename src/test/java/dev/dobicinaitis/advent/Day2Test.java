package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class Day2Test {

    @Test
    public void passwordCheckTest(){
        // wrong policy
        Password password = new Password("1-3 a: abcde");
        assertEquals(password.getMinOccurrence(),1);
        assertEquals(password.getMaxOccurrence(),3);
        assertEquals(password.getNeedle(),"a");
        assertEquals(password.getPassword(),"abcde");
        assertTrue(password.isValid());

        Password password2 = new Password("10-16 r: nrrrrkrjtxwrrrwx");
        assertFalse(password2.isValid());

        // correct policy
        PasswordCorrectPolicy password3 = new PasswordCorrectPolicy("1-3 a: abcde");
        assertEquals(password3.getFirstPosition(),1);
        assertEquals(password3.getSecondPosition(),3);
        assertEquals(password3.getNeedle(),"a");
        assertEquals(password3.getPassword(),"abcde");
        assertTrue(password3.isValid());

        PasswordCorrectPolicy password4 = new PasswordCorrectPolicy("2-9 c: ccccccccc");
        assertFalse(password4.isValid());
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