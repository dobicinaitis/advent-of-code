package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

@Slf4j
@AllArgsConstructor
public class Day1 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Integer> firstList = new ArrayList<>(extractLocations(input, 0));
        final List<Integer> secondList = new ArrayList<>(extractLocations(input, 1));
        // sort the lists
        Collections.sort(firstList);
        Collections.sort(secondList);
        // compute total distance between locations in both lists
        return IntStream.range(0, firstList.size())
                .parallel()
                .map(index -> Math.abs(firstList.get(index) - secondList.get(index)))
                .sum();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Integer> firstList = extractLocations(input, 0);
        final List<Integer> secondList = extractLocations(input, 1);
        // compute total similarity score
        return IntStream.range(0, firstList.size())
                .parallel()
                .map(index -> firstList.get(index) * Collections.frequency(secondList, firstList.get(index)))
                .sum();
    }

    private List<Integer> extractLocations(final List<String> input, final int columnIndex) {
        return input.stream()
                .map(entry -> entry.split("\\s+")[columnIndex])
                .map(Integer::parseInt)
                .toList();
    }
}
