package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day1 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        List<String> result = reduceToNumbersOnly(input);
        result = keepFirstAndLastDigitOnly(result);
        return result.stream()
                .map(Integer::parseInt)
                .mapToInt(Integer::intValue).sum();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        return 1;
    }

    private List<String> reduceToNumbersOnly(final List<String> input) {
        return input.stream()
                .map(s -> s.replaceAll("[^0-9]", ""))
                .toList();
    }

    private List<String> keepFirstAndLastDigitOnly(final List<String> input) {
        for (int i = 0; i < input.size(); i++) {
            final String entry = input.get(i);
            if (StringUtils.isBlank(entry)) {
                log.error("Input [{}] is blank", entry);
                throw new RuntimeException("Input is blank");
            }
            if (entry.length() < 2) {
                log.error("Input [{}] is too short", input.get(i));
                throw new RuntimeException("Input is too short");
            }
            final String firstDigit = entry.substring(0, 1);
            final String lastDigit = entry.substring(entry.length() - 1);
            input.set(i, firstDigit + lastDigit);
        }
        return input;
    }

}
