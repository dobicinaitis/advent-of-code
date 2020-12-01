package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Day1 {
    private static final int REQUIRED_SUM = 2020;
    private int[] puzzleInput;

    @SneakyThrows
    public int solvePuzzleOne(){
        for(int i = 0; i< puzzleInput.length; i++){
            for (int k = i + 1; k < puzzleInput.length; k++){
                int sum = puzzleInput[i] + puzzleInput[k];
                log.debug("checking: {} + {} = {}", puzzleInput[i], puzzleInput[k], sum);
                if (sum == REQUIRED_SUM){
                    log.info("Bingo! Found entries {} and {} that sum to {}", puzzleInput[i], puzzleInput[k], REQUIRED_SUM);
                    int result = puzzleInput[i] * puzzleInput[k];
                    log.info("result {}", result);
                    return result;
                }
            }
        }
        log.warn("did not find any entries that sum to {}", REQUIRED_SUM);
        throw new Exception("Nope! debug, code, test, repeat :p");
    }


    @SneakyThrows
    public int solvePuzzleTwo(){
        for(int i = 0; i< puzzleInput.length; i++){
            for (int k = i + 1; k < puzzleInput.length; k++){
                for (int j = k + 1; j < puzzleInput.length; j++){
                    int sum = puzzleInput[i] + puzzleInput[k] + puzzleInput[j];
                    log.debug("checking: {} + {} + {} = {}", puzzleInput[i], puzzleInput[k], puzzleInput[j], sum);
                    if (sum == REQUIRED_SUM){
                        log.info("Bingo! Found entries {}, {} and {} that sum to {}", puzzleInput[i], puzzleInput[k],
                                puzzleInput[j], REQUIRED_SUM);
                        int result = puzzleInput[i] * puzzleInput[k] * puzzleInput[j];
                        log.info("result {}", result);
                        return result;
                    }
                }
            }
        }
        log.warn("did not find any entries that sum to {}", REQUIRED_SUM);
        throw new Exception("Nope! debug, code, test, repeat :p");
    }
}
