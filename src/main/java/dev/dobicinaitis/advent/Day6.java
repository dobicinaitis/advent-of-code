package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class Day6 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<Integer> lanternFishes = Arrays.asList(Utils.getFileContentAsString(filename).split(",")).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        int observationPeriod = 80;

        for (int i = 0; i < observationPeriod; i++) {
            int fishCountOnAGivenDay = lanternFishes.size();
            for (int j = 0; j < fishCountOnAGivenDay; j++) {
                lanternFishes.set(j, lanternFishes.get(j) - 1);
                // give birth to a new fish
                if (lanternFishes.get(j) < 0) {
                    lanternFishes.add(8);
                    lanternFishes.set(j, 6);
                }
            }
        }

        return lanternFishes.size();
    }

    public Long getSecondPuzzleSolution(String filename) {
        List<Long> lanternFishes = Arrays.asList(Utils.getFileContentAsString(filename).split(",")).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());
        int observationPeriod = 256;

        return lanternFishes.parallelStream()
                .map(f -> calculateFamilySize(observationPeriod - (f.intValue() + 1)))
                .reduce(0L, Long::sum);
    }

    private long calculateFamilySize(int observationPeriod) {
        if (observationPeriod < 0) {
            return 1;
        }
        return calculateFamilySize(observationPeriod - 6 - 1) + calculateFamilySize(observationPeriod - 8 - 1);
    }
}
