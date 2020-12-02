package dev.dobicinaitis.advent;

import java.util.List;
import java.util.regex.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Day2 {

    public static long getFirstPuzzleSolution(String filename){
        List<String> inputs = Utils.getFileContent(filename);
        return inputs.stream().filter(p -> new PasswordInfoForSledRentalShop(p).isValid()).count();
    }

    public static long getSecondPuzzleSolution(String filename){
        List<String> inputs = Utils.getFileContent(filename);
        return inputs.stream().filter(p -> new PasswordInfoForTobogganRentalShop(p).isValid()).count();
    }
}

@Slf4j
@Getter
class PasswordInfoForSledRentalShop {
    private String mandatoryLetter;
    private int minMandatoryLetterOccurrences;
    private int maxMandatoryLetterOccurrences;
    private String password;

    public PasswordInfoForSledRentalShop(String s){
        minMandatoryLetterOccurrences = Integer.parseInt(s.substring(0,s.indexOf("-")));
        maxMandatoryLetterOccurrences = Integer.parseInt(s.substring(s.indexOf("-") + 1, s.indexOf(" ")));
        mandatoryLetter = s.substring(s.indexOf(" ") + 1, s.indexOf(":"));
        password = s.substring(s.indexOf(": ") + 2);
    }

    public boolean isValid(){
        long count = Pattern.compile("["+ mandatoryLetter +"]")
                .matcher(password)
                .results()
                .count();
        return  (count >= minMandatoryLetterOccurrences && count <= maxMandatoryLetterOccurrences);
    }
}

@Slf4j
@Getter
class PasswordInfoForTobogganRentalShop {
    private String mandatoryLetter;
    private int firstPositionOfMandatoryLetter;
    private int secondPositionOfMandatoryLetter;
    private String password;

    public PasswordInfoForTobogganRentalShop(String s){
        firstPositionOfMandatoryLetter = Integer.parseInt(s.substring(0,s.indexOf("-")));
        secondPositionOfMandatoryLetter = Integer.parseInt(s.substring(s.indexOf("-") + 1, s.indexOf(" ")));
        mandatoryLetter = s.substring(s.indexOf(" ") + 1, s.indexOf(":"));
        password = s.substring(s.indexOf(": ") + 2);
    }

    public boolean isValid(){
        boolean firstCharacterMatches = password.substring(firstPositionOfMandatoryLetter - 1,
                firstPositionOfMandatoryLetter).equals(mandatoryLetter);
        boolean secondCharacterMatches = password.substring(secondPositionOfMandatoryLetter -1,
                secondPositionOfMandatoryLetter).equals(mandatoryLetter);

        if ((firstCharacterMatches && !secondCharacterMatches) || (!firstCharacterMatches && secondCharacterMatches)){
            return true;
        }

        return false;
    }
}