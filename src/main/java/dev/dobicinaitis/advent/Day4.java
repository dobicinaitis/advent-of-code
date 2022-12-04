package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day4 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<String> input = Utils.getFileContent(filename);
        int numberOfFullyOverlappingRanges = 0;
        for (String assignment : input) {
            int firstSectionStart = getNumberFromAssigment(assignment, 1);
            int firstSectionEnd = getNumberFromAssigment(assignment, 2);
            int secondSectionStart = getNumberFromAssigment(assignment, 3);
            int secondSectionEnd = getNumberFromAssigment(assignment, 4);

            if ((firstSectionStart >= secondSectionStart && firstSectionEnd <= secondSectionEnd) ||
                    (secondSectionStart >= firstSectionStart && secondSectionEnd <= firstSectionEnd)) {
                numberOfFullyOverlappingRanges++;
            }
        }
        return numberOfFullyOverlappingRanges;
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<String> input = Utils.getFileContent(filename);
        int numberOfOverlappingAssigmentParts = 0;
        for (String assignment : input) {
            int firstSectionStart = getNumberFromAssigment(assignment, 1);
            int firstSectionEnd = getNumberFromAssigment(assignment, 2);
            int secondSectionStart = getNumberFromAssigment(assignment, 3);
            int secondSectionEnd = getNumberFromAssigment(assignment, 4);

            for (int i = firstSectionStart; i <= firstSectionEnd; i++) {
                if (i >= secondSectionStart && i <= secondSectionEnd) {
                    numberOfOverlappingAssigmentParts++;
                    break;
                }
            }
        }
        return numberOfOverlappingAssigmentParts;
    }

    /**
     * Parses the assigment string and extracts a number for a given part No.
     * Example
     * Assigment: 2-4,6-8
     * Part No. 1: 2
     * Part No. 2: 4
     * Part No. 3: 6
     * Part No. 4: 8
     */
    private int getNumberFromAssigment(String assignment, int partNo) {
        switch (partNo) {
            case 1:
                return Integer.parseInt(assignment.substring(0, assignment.indexOf("-")));
            case 2:
                return Integer.parseInt(assignment.substring(assignment.indexOf("-") + 1, assignment.indexOf(",")));
            case 3:
                return Integer.parseInt(assignment.substring(assignment.indexOf(",") + 1, assignment.lastIndexOf("-")));
            case 4:
                return Integer.parseInt(assignment.substring(assignment.lastIndexOf("-") + 1));
            default:
                throw new RuntimeException("Wrong part number " + partNo + ". Supported: 1 to 4");
        }
    }
}
