package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@AllArgsConstructor
public class Day6 extends Puzzle {

    public Long getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Race> races = parseInput(input);
        log.debug("Races {}", races);
        return races.stream()
                .mapToLong(this::calculateNumberOfWaysToBeatTheRecord)
                .reduce(1, (a, b) -> a * b);
    }

    public Long getSecondPuzzleSolution(String filename) {
        final List<String> kerningAdjustedInput = Utils.getFileContent(filename).stream()
                .map(line -> line.replace(" ", ""))
                .toList();
        final List<Race> races = parseInput(kerningAdjustedInput);
        log.debug("Races {}", races);
        return races.stream()
                .mapToLong(this::calculateNumberOfWaysToBeatTheRecord)
                .reduce(1, (a, b) -> a * b);
    }

    private long calculateNumberOfWaysToBeatTheRecord(final Race race) {
        long winningScenarioCount = 0;
        for (int speed = 1; speed < race.time; speed++) {
            long distanceTraveled = (long) speed * (race.time - speed);
            if (distanceTraveled > race.bestDistance) {
                winningScenarioCount++;
            }
            if (distanceTraveled < race.bestDistance && winningScenarioCount > 0) {
                // we're traveling less than the best distance, so there's no point in continuing
                break;
            }
        }
        return winningScenarioCount;
    }

    private List<Race> parseInput(List<String> input) {
        final String timePart = StringUtils.substringAfter(input.get(0), ":");
        final String distancePart = StringUtils.substringAfter(input.get(1), ":");
        final List<Integer> times = Utils.parseToIntegerList(timePart, " ");
        final List<Long> distances = Utils.parseToLongList(distancePart, " ");
        return IntStream.range(0, times.size())
                .mapToObj(i -> new Race(times.get(i), distances.get(i)))
                .toList();
    }

    record Race(int time, long bestDistance) {
    }
}
