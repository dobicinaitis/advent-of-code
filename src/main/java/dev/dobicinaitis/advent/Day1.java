package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day1 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<String> caloriesLog = Utils.getFileContent(filename);
        int mostCalories = 0;
        int caloriesCarriedByCurrentElf = 0;
        for (int i = 0; i < caloriesLog.size(); i++) {
            if (!caloriesLog.get(i).isBlank()) {
                caloriesCarriedByCurrentElf += Integer.parseInt(caloriesLog.get(i));
            }
            if (caloriesLog.get(i).isBlank() || i == caloriesLog.size() - 1) {
                if (caloriesCarriedByCurrentElf > mostCalories) {
                    mostCalories = caloriesCarriedByCurrentElf;
                }
                caloriesCarriedByCurrentElf = 0;
            }
        }
        return mostCalories;
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<String> caloriesLog = Utils.getFileContent(filename);
        List<Integer> caloriesPerElf = new ArrayList<>();
        int caloriesCarriedByCurrentElf = 0;
        for (int i = 0; i < caloriesLog.size(); i++) {
            if (!caloriesLog.get(i).isBlank()) {
                caloriesCarriedByCurrentElf += Integer.parseInt(caloriesLog.get(i));
            }
            if (caloriesLog.get(i).isBlank() || i == caloriesLog.size() - 1) {
                caloriesPerElf.add(caloriesCarriedByCurrentElf);
                caloriesCarriedByCurrentElf = 0;
            }
        }
        // sum of top 3 results
        return caloriesPerElf.stream()
                .sorted(Comparator.reverseOrder())
                .limit(3)
                .mapToInt(i -> i)
                .sum();
    }
}
