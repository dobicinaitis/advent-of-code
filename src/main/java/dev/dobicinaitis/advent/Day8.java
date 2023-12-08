package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.numbers.core.ArithmeticUtils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day8 extends Puzzle {

    public static final String STARTING_POSITION = "AAA";
    public static final String DESTINATION = "ZZZ";

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Integer> instructions = parseInstructions(input);
        log.debug("Instructions {}", instructions);
        final LinkedHashMap<String, List<String>> network = parseNetwork(input);
        log.debug("Network {}", network);
        int steps = 0;
        String currentLocation = STARTING_POSITION;
        while (true) {
            for (int direction : instructions) {
                steps++;
                currentLocation = network.get(currentLocation).get(direction);
                // log.info("Step {}, moved {} to {}", steps, direction == 1 ? "➡️" : "⬅️", currentLocation);
                if (currentLocation.equals(DESTINATION)) {
                    return steps;
                }
            }
        }
    }

    public Long getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Integer> instructions = parseInstructions(input);
        final LinkedHashMap<String, List<String>> network = parseNetwork(input);
        final List<String> startingLocations = network.keySet().stream()
                .filter(key -> key.endsWith("A"))
                .toList();
        final List<Long> stepsToDestinationPerLocation = new ArrayList<>();
        startingLocationLoop:
        for (String startingLocation : startingLocations) {
            String currentLocation = startingLocation;
            int steps = 0;
            while (true) {
                for (int direction : instructions) {
                    steps++;
                    currentLocation = network.get(currentLocation).get(direction);
                    if (currentLocation.endsWith("Z")) {
                        stepsToDestinationPerLocation.add((long) steps);
                        continue startingLocationLoop;
                    }
                }
            }
        }
        // We have identified how many steps will it take per location to reach the destination.
        // Now we need to find the least common multiple of those steps.
        return stepsToDestinationPerLocation.stream().reduce(1L, ArithmeticUtils::lcm);
    }

    private List<Integer> parseInstructions(final List<String> input) {
        return input.getFirst()
                .replace("R", "1")
                .replace("L", "0")
                .chars()
                .mapToObj(instruction -> instruction - 48)
                .toList();
    }

    private LinkedHashMap<String, List<String>> parseNetwork(List<String> input) {
        final LinkedHashMap<String, List<String>> network = new LinkedHashMap<>();
        input.stream()
                .skip(2)
                .forEach(entry -> {
                    final String key = entry.substring(0, 3);
                    final String left = entry.substring(7, 10);
                    final String right = entry.substring(12, 15);
                    network.put(key, List.of(left, right));
                });
        return network;
    }
}