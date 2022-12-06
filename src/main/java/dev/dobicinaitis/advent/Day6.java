package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;

@Slf4j
@AllArgsConstructor
public class Day6 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final String input = Utils.getFileContentAsString(filename);
        return getMarkerPosition(input, 4);
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final String input = Utils.getFileContentAsString(filename);
        return getMarkerPosition(input, 14);
    }

    private int getMarkerPosition(String input, int markerLength) {
        for (int i = 0; i < input.length() - markerLength; i++) {
            if (isMarker(input.substring(i, i + markerLength))) {
                return i + markerLength;
            }
        }
        throw new RuntimeException("Did not find the marker");
    }

    /**
     * Checks if all characters in a string are unique.
     *
     * @param markerCandidate string to test
     * @return true if all characters in markerCandidate are unique
     */
    private boolean isMarker(String markerCandidate) {
        HashSet uniqueCharacters = new HashSet<>();
        markerCandidate.chars().forEach(character -> uniqueCharacters.add(character));
        return uniqueCharacters.size() == markerCandidate.length();
    }
}
