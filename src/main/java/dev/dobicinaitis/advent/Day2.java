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
        return inputs.stream().filter(p -> new Password(p).isValid()).count();
    }

    public static long getSecondPuzzleSolution(String filename){
        List<String> inputs = Utils.getFileContent(filename);
        return inputs.stream().filter(p -> new PasswordCorrectPolicy(p).isValid()).count();
    }
}

@Slf4j
@Getter
class Password {
    private int minOccurrence;
    private int maxOccurrence;
    private String needle;
    private String password;
    private String originalInput;

    public Password(String passwordWithPolicy){
        originalInput = passwordWithPolicy;
        minOccurrence = Integer.parseInt(passwordWithPolicy.substring(0,passwordWithPolicy.indexOf("-")));
        maxOccurrence = Integer.parseInt(passwordWithPolicy.substring(passwordWithPolicy.indexOf("-") + 1,
                passwordWithPolicy.indexOf(" ")));
        needle = passwordWithPolicy.substring(passwordWithPolicy.indexOf(" ") + 1, passwordWithPolicy.indexOf(":"));
        password = passwordWithPolicy.substring(passwordWithPolicy.indexOf(": ") + 2);
    }

    public boolean isValid(){
        long count = Pattern.compile("["+ needle +"]")
                .matcher(password)
                .results()
                .count();
        return  (count >= minOccurrence && count <= maxOccurrence);
    }
}

@Slf4j
@Getter
class PasswordCorrectPolicy {
    private int firstPosition;
    private int secondPosition;
    private String needle;
    private String password;
    private String originalInput;

    public PasswordCorrectPolicy(String passwordWithPolicy){
        Password parsedPassword = new Password(passwordWithPolicy);
        originalInput = passwordWithPolicy;
        firstPosition = parsedPassword.getMinOccurrence();
        secondPosition = parsedPassword.getMaxOccurrence();
        needle = parsedPassword.getNeedle();
        password = parsedPassword.getPassword();
    }

    public boolean isValid(){
        boolean firstCharacterMatches = password.substring(firstPosition - 1, firstPosition).equals(needle);
        boolean secondCharacterMatches = password.substring(secondPosition -1, secondPosition).equals(needle);

        if ((firstCharacterMatches && !secondCharacterMatches) || (!firstCharacterMatches && secondCharacterMatches)){
            return true;
        }

        return false;
    }
}