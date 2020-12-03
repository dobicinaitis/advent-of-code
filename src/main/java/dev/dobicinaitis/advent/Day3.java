package dev.dobicinaitis.advent;

import java.util.ArrayList;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class Day3 {

    public static int getFirstPuzzleSolution(String filename, int stepsRight, int stepsDown){
        ArrayList<String> mapRows = (ArrayList<String>) Utils.getFileContent(filename);
        int currentPositionRight = 0;
        int treesHit = 0;

        for(int i = stepsDown; i < mapRows.size(); i = i + stepsDown){
            currentPositionRight+=stepsRight;
            String currentRow = mapRows.get(i);
            // extend the pattern if it`s too short
            while (currentPositionRight >= currentRow.length()){
                currentRow = currentRow.concat(mapRows.get(i));
            }

            if (currentRow.charAt(currentPositionRight) == '#'){
                treesHit++;
            }
        }
        return treesHit;
    }

    public static int getSecondPuzzleSolution(String filename, int stepsRight, int stepsDown){
        return getFirstPuzzleSolution(filename,stepsRight, stepsDown);
    }
}