package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day4Test {

    @Test
    public void normalizePassportEntryTest(){
        Passport passport = new Passport();
        String rawEntryInBatchFile = "hcl:#ae17e1 iyr:2013\n"
                + "eyr:2024\n"
                + "ecl:brn pid:760753108 byr:1931\n"
                + "hgt:179cm";
        String expectedResult = "hcl:#ae17e1\n"
                + "iyr:2013\n"
                + "eyr:2024\n"
                + "ecl:brn\n"
                + "pid:760753108\n"
                + "byr:1931\n"
                + "hgt:179cm";
        String actualResult = passport.normalizePassportEntry(rawEntryInBatchFile);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void passportFromStringTest(){
        String rawEntryInBatchFile = "hcl:#ae17e1 iyr:2013\n"
                + "eyr:2024\n"
                + "ecl:brn pid:760753108 byr:1931\n"
                + "hgt:179cm cid:147 pid:xxx";
        Passport passport = new Passport(rawEntryInBatchFile);

        assertEquals(1931, passport.getBirthYear());
        assertEquals(2013, passport.getIssueYear());
        assertEquals(2024, passport.getExpirationYear());
        assertEquals("179cm", passport.getHeight());
        assertEquals("#ae17e1", passport.getHairColor());
        assertEquals("brn", passport.getEyeColor());
        assertEquals("xxx", passport.getPassportId());
        assertEquals("147", passport.getCountryId());
        assertTrue(passport.isValid());
    }

    @Test
    public void passportStrictValidationTest(){
        String validRawPassportData = "hcl:#ae17e1\n"
                               + "iyr:2013\n"
                               + "eyr:2024\n"
                               + "ecl:brn\n"
                               + "pid:760753108\n"
                               + "byr:1931\n"
                               + "hgt:179cm";

        Passport passport = new Passport(validRawPassportData);
        assertTrue(passport.isValidWithStrictRules());

        String invalidRawPassportData = "hcl:#zzzzzz";
        Passport invalidPassport = new Passport(invalidRawPassportData);
        assertFalse(invalidPassport.isValidWithStrictRules());

        assertTrue(passport.isNumberWithinRange(1001,1000,1002));
        assertFalse(passport.isNumberWithinRange(9999,1000,1002));

        assertTrue(passport.isHeightValid("150cm"));
        assertFalse(passport.isHeightValid("10cm"));

        assertTrue(passport.isHeightValid("59in"));
        assertFalse(passport.isHeightValid("10in"));

        assertTrue(passport.isHairColorValid("#a01234"));
        assertFalse(passport.isHairColorValid("123456"));
        assertFalse(passport.isHairColorValid("#z01234"));

        assertTrue(passport.isEyeColorValid("amb"));
        assertTrue(passport.isEyeColorValid("blu"));
        assertTrue(passport.isEyeColorValid("brn"));
        assertTrue(passport.isEyeColorValid("gry"));
        assertTrue(passport.isEyeColorValid("grn"));
        assertTrue(passport.isEyeColorValid("hzl"));
        assertTrue(passport.isEyeColorValid("oth"));
        assertFalse(passport.isEyeColorValid("ABC"));

        assertTrue(passport.isPassportIdValid("012345678"));
        assertFalse(passport.isPassportIdValid("qwerty"));
    }

    @Test
    public void extractPassportsFromBatchFileTest(){
        List<Passport> passports = Day4.extractPassportsFromBatchFile("day4_example_input.txt");
        assertEquals(passports.size(),4);
    }

    @Test
    public void firstPuzzleExampleTest(){
        Long validPassportCount = Day4.getFirstPuzzleSolution("day4_example_input.txt");
        assertEquals(validPassportCount, 2);
    }

    @Test
    public void firstPuzzleMyInputTest(){
        Long validPassportCount = Day4.getFirstPuzzleSolution("day4_my_input.txt");
        assertEquals(validPassportCount, 247);
    }

    @Test
    public void secondPuzzleExampleTest(){
        Long validPassportCount = Day4.getSecondPuzzleSolution("day4_example_input.txt");
        assertEquals(validPassportCount, 2);
    }

    @Test
    public void secondPuzzleMyInputTest(){
        Long validPassportCount = Day4.getSecondPuzzleSolution("day4_my_input.txt");
        assertEquals(validPassportCount, 145);
    }
}