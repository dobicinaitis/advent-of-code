package dev.dobicinaitis.advent;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Day4 {

    public static long getFirstPuzzleSolution(String filename){
        List<Passport> passports = extractPassportsFromBatchFile(filename);
        return passports.stream().filter(p -> p.isValid()).count();
    }

    public static long getSecondPuzzleSolution(String filename){
        List<Passport> passports = extractPassportsFromBatchFile(filename);
        return passports.stream().filter(p -> p.isValidWithStrictRules()).count();
    }

    public static List<Passport> extractPassportsFromBatchFile(String filename){
        String inputData = Utils.getFileContentAsString(filename);
        List<Passport> passports = Arrays.stream(inputData.split("\n\n"))
                .map(entry -> new Passport(entry))
                .collect(Collectors.toList());
        return passports;
    }
}

@NoArgsConstructor
@Data
@Slf4j
class Passport {
    private Integer birthYear;
    private Integer issueYear;
    private Integer expirationYear;
    private String height;
    private String hairColor;
    private String eyeColor;
    private String passportId;
    private String countryId;

    public Passport(String data){
        data = normalizePassportEntry(data);
        data.lines().forEach(entry ->
                {
                    if (entry.startsWith("byr")) {
                        this.birthYear = Integer.parseInt(extractValue(entry));
                    }
                    else if (entry.startsWith("iyr")) {
                        this.issueYear = Integer.parseInt(extractValue(entry));
                    }
                    else if (entry.startsWith("eyr")) {
                        this.expirationYear = Integer.parseInt(extractValue(entry));
                    }
                    else if (entry.startsWith("hgt")) {
                        this.height = extractValue(entry);
                    }
                    else if (entry.startsWith("hcl")) {
                        this.hairColor = extractValue(entry);
                    }
                    else if (entry.startsWith("ecl")) {
                        this.eyeColor = extractValue(entry);
                    }
                    else if (entry.startsWith("pid")) {
                        this.passportId = extractValue(entry);
                    }
                    else if (entry.startsWith("cid")) {
                        this.countryId = extractValue(entry);
                    }
                }
        );
    }

    public boolean isValid(){
        return (
                birthYear != null &&
                issueYear != null &&
                expirationYear != null &&
                height != null &&
                hairColor != null &&
                eyeColor != null &&
                passportId != null
                //countryId != null -- sneaky hack to validate my North Pole Credentials as a passport
                );
    }

    public boolean isValidWithStrictRules(){
        return (
                isValid() &&
                isNumberWithinRange(birthYear,1920,2002) &&
                isNumberWithinRange(issueYear,2010, 2020) &&
                isNumberWithinRange(expirationYear,2020, 2030) &&
                isHeightValid(height) &&
                isHairColorValid(hairColor) &&
                isEyeColorValid(eyeColor) &&
                isPassportIdValid(passportId)
                //countryId != null -- sneaky hack to validate my North Pole Credentials as a passport
                );
    }

    public boolean isNumberWithinRange(int number, int min, int max){
        return (number >= min && number <= max);
    }

    public boolean isHeightValid(String heightWithUnitOfMeasurement){
        int height = Integer.parseInt(heightWithUnitOfMeasurement.replaceAll("[^\\d]", ""));
        String measurementUnit = heightWithUnitOfMeasurement.replace("" + height, "");

        if ("cm".equals(measurementUnit)){
            return (height >= 150 && height <= 193);
        }

        if ("in".equals(measurementUnit)){
            return (height >= 59 && height <= 76);
        }

        return false;
    }

    public boolean isHairColorValid(String color){
        return color.matches("^#[a-f0-9]{6}$");
    }

    public boolean isEyeColorValid(String eyeColor){
        return "amb".equals(eyeColor) ||
               "blu".equals(eyeColor) ||
               "brn".equals(eyeColor) ||
               "gry".equals(eyeColor) ||
               "grn".equals(eyeColor) ||
               "hzl".equals(eyeColor) ||
               "oth".equals(eyeColor);
    }

    public boolean isPassportIdValid(String passportId){
        return passportId.matches("^[0-9]{9}$");
    }

    private String extractValue(String entry){
        return entry.substring(entry.indexOf(":") + 1);
    }

    /**
     * Return one data record per row
     * @param rawEntry
     * @return
     */
    public String normalizePassportEntry(String rawEntry){
        return rawEntry.replace(" ","\n");
    }
}