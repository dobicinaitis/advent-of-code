package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day1 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename){
        List<String> measurements = Utils.getFileContent(filename);
        int timesMeasurementIncreased = 0;

        for (int i = 1; i < measurements.size(); i++){
            if (toInt(measurements.get(i)) > toInt(measurements.get(i - 1))){
                timesMeasurementIncreased++;
            }
        }

        return timesMeasurementIncreased;
    }

    public Integer getSecondPuzzleSolution(String filename){
        List<String> measurements = Utils.getFileContent(filename);
        int timesMeasurementIncreased = 0;

        for (int i = 0; i < measurements.size() - 3; i++){
            int sumOf3Measurements = toInt(measurements.get(i)) + toInt(measurements.get(i + 1)) + toInt(measurements.get(i + 2));
            int sumOf3NextMeasurements = toInt(measurements.get(i + 1)) + toInt(measurements.get(i + 2)) + toInt(measurements.get(i + 3));

            if (sumOf3NextMeasurements > sumOf3Measurements){
                timesMeasurementIncreased++;
            }
        }

        return timesMeasurementIncreased;
    }

    private Integer toInt(String value){
        return Integer.parseInt(value);
    }
}
