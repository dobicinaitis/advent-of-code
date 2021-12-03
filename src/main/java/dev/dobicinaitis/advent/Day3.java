package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day3 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<String> diagnosticReport = Utils.getFileContent(filename);
        int bitCountInEntry = diagnosticReport.get(0).length();
        String gammaRate = "";
        String epsilonRate = "";

        for (int i = 0; i < bitCountInEntry; i++) {
            Integer mostCommonValue = getMostCommonBit(diagnosticReport, i);

            if (mostCommonValue == null) {
                throw new RuntimeException("Incomplete requirements. There is a equal count of 0 and 1 bits");
            }

            if (mostCommonValue == 1) {
                gammaRate += "1";
                epsilonRate += "0";
            } else {
                gammaRate += "0";
                epsilonRate += "1";
            }
        }

        log.info("gammaRate = {}, epsilonRate = {}", gammaRate, epsilonRate);
        return Integer.parseInt(gammaRate, 2) * Integer.parseInt(epsilonRate, 2);
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<String> diagnosticReport = Utils.getFileContent(filename);
        int bitCountInEntry = diagnosticReport.get(0).length();

        List<String> oxygenGeneratorRatings = Utils.getFileContent(filename);
        List<String> cO2ScrubberRatings = Utils.getFileContent(filename);

        // oxygen
        for (int i = 0; i < bitCountInEntry; i++) {
            Integer mostCommonValue = getMostCommonBit(oxygenGeneratorRatings, i);

            if (mostCommonValue == null || mostCommonValue == 1) {
                reduceListBasedOnBitValue(oxygenGeneratorRatings, i, "0");
            }
            else {
                reduceListBasedOnBitValue(oxygenGeneratorRatings, i, "1");
            }

            if (oxygenGeneratorRatings.size() == 1) break;
        }

        // CO2
        for (int i = 0; i < bitCountInEntry; i++) {
            Integer mostCommonValue = getMostCommonBit(cO2ScrubberRatings, i);

            if (mostCommonValue == null || mostCommonValue == 1) {
                reduceListBasedOnBitValue(cO2ScrubberRatings, i, "1");
            }
            else {
                reduceListBasedOnBitValue(cO2ScrubberRatings, i, "0");
            }

            if (cO2ScrubberRatings.size() == 1) break;
        }

        log.info("oxygenGeneratorRating = {}, cO2ScrubberRatings = {}", oxygenGeneratorRatings, cO2ScrubberRatings);
        return Integer.parseInt(oxygenGeneratorRatings.get(0), 2) * Integer.parseInt(cO2ScrubberRatings.get(0), 2);
    }

    private Integer getMostCommonBit(List<String> list, int position) {
        int sumOfBitValues = list.stream()
                .map(s -> Integer.parseInt(s.substring(position, position + 1)))
                .reduce(0, Integer::sum);

        if (sumOfBitValues > Double.valueOf(list.size()) / 2) {
            return 1;
        } else if (sumOfBitValues < Double.valueOf(list.size()) / 2) {
            return 0;
        }

        // equal amount of 0 and 1
        return null;
    }

    private void reduceListBasedOnBitValue(List<String> list, int position, String value) {
        list.removeIf(s -> s.substring(position, position + 1).equals(value));
    }
}
