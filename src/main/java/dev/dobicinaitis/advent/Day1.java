package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day1 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        reduceToNumbersOnly(input);
        keepFirstAndLastDigitOnly(input);
        return input.stream()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        replaceSpelledNumbersWithDigits(input);
        reduceToNumbersOnly(input);
        keepFirstAndLastDigitOnly(input);
        return input.stream()
                .mapToInt(Integer::parseInt)
                .sum();
    }

    private void reduceToNumbersOnly(final List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            final String entry = input.get(i);
            assert entry != null : "Entry is null";
            final String updatedEntry = entry.replaceAll("[^0-9]", "");
            input.set(i, updatedEntry);
        }
    }

    private void keepFirstAndLastDigitOnly(final List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            final String entry = input.get(i);
            assert entry != null : "Entry is null";
            assert !entry.isEmpty() : "Entry does not contain a single digit";
            final String firstDigit = entry.substring(0, 1);
            final String lastDigit = entry.substring(entry.length() - 1);
            input.set(i, firstDigit + lastDigit);
        }
    }

    private void replaceSpelledNumbersWithDigits(List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            final String entry = input.get(i);
            log.info("Checking entry:  {}", entry);
            assert entry != null : "Entry is null";
            StringBuilder updatedEntry = new StringBuilder();
            StringBuilder unmatchedLetterStack = new StringBuilder();
            // Iterate over the letters in the entry and check if they form a number.
            // The tricky bit is that combinations like "eightwo" should result in 82 instead of 8wo.
            for (int j = 0; j < entry.length(); j++) {
                final String currentLetter = entry.substring(j, j + 1);
                // if it's a number, append it to the updated entry and clear the unmatched letter stack
                if (currentLetter.matches("[0-9]")) {
                    updatedEntry.append(currentLetter);
                    unmatchedLetterStack.setLength(0);
                    continue;
                }
                unmatchedLetterStack.append(currentLetter);
                log.info("Unmatched letter stack: {}", unmatchedLetterStack);
                final String evaluatedSubset = entry.substring(0, j + 1);
                log.info("Checking subset: {}", evaluatedSubset);
                if (evaluatedSubset.endsWith("one")) {
                    updatedEntry.append("1");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("two")) {
                    updatedEntry.append("2");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("three")) {
                    updatedEntry.append("3");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("four")) {
                    updatedEntry.append("4");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("five")) {
                    updatedEntry.append("5");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("six")) {
                    updatedEntry.append("6");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("seven")) {
                    updatedEntry.append("7");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("eight")) {
                    updatedEntry.append("8");
                    unmatchedLetterStack.setLength(0);
                } else if (evaluatedSubset.endsWith("nine")) {
                    updatedEntry.append("9");
                    unmatchedLetterStack.setLength(0);
                }
            }
            // if there are any unmatched letters left, append them to the updated entry
            updatedEntry.append(unmatchedLetterStack);
            log.info("Changes: {} > {}", entry, updatedEntry);
            input.set(i, updatedEntry.toString());
        }
    }

}
