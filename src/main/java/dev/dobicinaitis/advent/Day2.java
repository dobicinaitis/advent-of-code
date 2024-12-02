package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day2 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> reports = Utils.getFileContent(filename);
        int safeReports = 0;

        for (String report : reports) {
            final List<Integer> levels = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            if (isReportSafe(levels)) {
                safeReports++;
            }
        }

        return safeReports;
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> reports = Utils.getFileContent(filename);
        int safeReports = 0;

        for (String report : reports) {
            final List<Integer> levels = Arrays.stream(report.split(" ")).map(Integer::parseInt).toList();
            log.debug("Checking report: {}", levels);
            if (isReportSafe(levels) || canBeMadeSafeByRemovingOneLevel(levels)) {
                safeReports++;
            }
        }

        return safeReports;
    }

    private boolean canBeMadeSafeByRemovingOneLevel(List<Integer> levels) {
        for (int i = 0; i < levels.size(); i++) {
            final List<Integer> levelsToCheck = new ArrayList<>(levels);
            levelsToCheck.remove(i);
            log.debug("Checking subset of levels: {}", levelsToCheck);
            if (isReportSafe(levelsToCheck)) {
                log.debug("Report can be made safe by removing level {}", levels.get(i));
                return true;
            }
        }
        log.debug("Report is not safe even after removing one level");
        return false;
    }

    private boolean isReportSafe(List<Integer> levels) {
        final int minLevelDiff = 1;
        final int maxLevelDiff = 3;
        boolean isIncreasing = levels.get(0) <= levels.get(1);

        for (int i = 0; i < levels.size() - 1; i++) {
            final int levelDiff = isIncreasing ? levels.get(i + 1) - levels.get(i) : levels.get(i) - levels.get(i + 1);

            if (levelDiff < minLevelDiff || levelDiff > maxLevelDiff) {
                log.debug("Unsafe! Diff between levels {} and {} is {}", levels.get(i), levels.get(i + 1), levelDiff);
                return false;
            }
        }

        return true;
    }
}
