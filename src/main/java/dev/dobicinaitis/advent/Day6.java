package dev.dobicinaitis.advent;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Day6 {

    public static int getFirstPuzzleSolution(String filename){
        String input = Utils.getFileContentAsString(filename);
        Integer sumOfYesAnswersForAnyoneInGroup = Arrays.stream(input.split("\n\n"))
                .mapToInt(declaration -> getUniqueLetterCount(removeNonLetterSymbols(declaration)))
                .sum();

        return sumOfYesAnswersForAnyoneInGroup;
    }

    public static int getSecondPuzzleSolution(String filename){
        String input = Utils.getFileContentAsString(filename);
        Integer sumOfYesAnswersForEveryoneInGroup = Arrays.stream(input.split("\n\n"))
                .mapToInt(declarations -> getCommonLetterCount(Arrays.asList(declarations.split("\n"))))
                .sum();

        return sumOfYesAnswersForEveryoneInGroup;
    }

    public static String removeNonLetterSymbols(String input){
        return input.replaceAll("[^a-z]", "");
    }

    public static int getUniqueLetterCount(String input){
        LinkedHashSet<Character> uniqueCharSet = new LinkedHashSet();
        for(int i = 0; i < input.length(); i++){
            uniqueCharSet.add(input.charAt(i));
        }
        return uniqueCharSet.size();
    }

    public static int getCommonLetterCount(List<String> inputs){
        String firstEntry = inputs.get(0);
        int commonLetterCount = 0;

        search:
        for(int i = 0; i < firstEntry.length(); i++){
            char letter = firstEntry.charAt(i);
            // check if all other entries contain the same letter
            for (int k = 1; k < inputs.size(); k++){
                if (inputs.get(k).indexOf(letter) == -1){
                    continue search;
                }
            }
            commonLetterCount++;
        }
        return commonLetterCount;
    }
}