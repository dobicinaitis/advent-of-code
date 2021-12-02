package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day2 extends Puzzle{

    public Integer getFirstPuzzleSolution(String filename){
        List<String> commands = Utils.getFileContent(filename);
        int horizontalPosition = 0;
        int depth = 0;

        for (String command: commands){
            int value = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
            if (command.startsWith("forward")){
                horizontalPosition += value;
            }
            else if (command.startsWith("up")){
                depth -= value;
            }
            else if (command.startsWith("down")){
                depth += value;
            }
            else {
                log.warn("Incomplete requirements :D");
            }
        }

        return horizontalPosition * depth;
    }

    public Integer getSecondPuzzleSolution(String filename){
        List<String> commands = Utils.getFileContent(filename);
        int horizontalPosition = 0;
        int depth = 0;
        int aim = 0;

        for (String command: commands){
            int value = Integer.parseInt(command.substring(command.indexOf(" ") + 1));
            if (command.startsWith("forward")){
                horizontalPosition += value;
                depth += aim * value;
            }
            else if (command.startsWith("up")){
                aim -= value;
            }
            else if (command.startsWith("down")){
                aim += value;
            }
            else {
                log.warn("Incomplete requirements :D");
            }
        }

        return horizontalPosition * depth;
    }
}
