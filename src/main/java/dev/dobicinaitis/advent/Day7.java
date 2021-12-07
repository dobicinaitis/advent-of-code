package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class Day7 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<Integer> crabSubmarinePositions = Utils.getFileContentAsIntegerList(filename);

        Map<Integer, Integer> fuelConsumptionToPosition = crabSubmarinePositions.parallelStream()
                .collect(Collectors.toMap(Function.identity(),
                        position -> calculateFuelUsage(position, crabSubmarinePositions),
                        (position, matchingPosition) -> position));

        log.info("fuelConsumptionToPosition = {}", fuelConsumptionToPosition);
        return Collections.min(fuelConsumptionToPosition.values());
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<Integer> crabSubmarinePositions = Utils.getFileContentAsIntegerList(filename);
        List<Integer> allPositions = addInBetweenPositions(crabSubmarinePositions);

        Map<Integer, Integer> fuelConsumptionToPosition = allPositions.parallelStream()
                .collect(Collectors.toMap(Function.identity(),
                        position -> calculateFuelUsageAccountingForBurnRate(position, crabSubmarinePositions),
                        (position, matchingPosition) -> position));

        log.info("fuelConsumptionToPosition = {}", fuelConsumptionToPosition);
        return Collections.min(fuelConsumptionToPosition.values());
    }

    private List<Integer> addInBetweenPositions(List<Integer> crabSubmarinePositions) {
        List<Integer> allPositions = new ArrayList<>(crabSubmarinePositions);
        int min = Collections.min(allPositions);
        int max = Collections.max(allPositions);

        for (int i = min; i < max; i++) {
            if (!allPositions.contains(i)) {
                allPositions.add(i);
            }
        }
        return allPositions;
    }

    private int calculateFuelUsage(Integer destination, List<Integer> coordinates) {
        return coordinates.parallelStream()
                .map(coordinate -> Math.abs(destination - coordinate))
                .reduce(0, Integer::sum);
    }

    private int calculateFuelUsageAccountingForBurnRate(Integer destination, List<Integer> coordinates) {
        return coordinates.parallelStream()
                .map(coordinate -> calculateExtraFuelUsage(Math.abs(destination - coordinate)))
                .reduce(0, Integer::sum);
    }

    private int calculateExtraFuelUsage(int steps) {
        int fuelUsage = 0;
        for (int i = 1; i <= steps; i++) {
            fuelUsage += i;
        }
        return fuelUsage;
    }
}
