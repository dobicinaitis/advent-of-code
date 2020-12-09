package dev.dobicinaitis.advent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Day8 {

    public static int getFirstPuzzleSolution(String filename){
        ArrayList<String> instructions = (ArrayList<String>) Utils.getFileContent(filename);
        BigDecimal accumulator = BigDecimal.ZERO;
        BigDecimal nextInstructionNo = BigDecimal.ZERO;
        List<BigDecimal> executedInstructions = new ArrayList<>();

        while (true){
            if (executedInstructions.contains(nextInstructionNo)){
                // we have entered the endless loop
                return accumulator.intValue();
            }

            BigDecimal instructionNo = nextInstructionNo;
            String instruction = instructions.get(instructionNo.intValue());
            String command = instruction.substring(0,3);
            BigDecimal value = new BigDecimal(instruction.substring(4));

            if (command.equals("acc")){
                accumulator = accumulator.add(value);
                nextInstructionNo = nextInstructionNo.add(BigDecimal.ONE);
            }
            else if (command.equals("jmp")){
                nextInstructionNo = nextInstructionNo.add(value);
            }
            else if (command.equals("nop")){
                nextInstructionNo = nextInstructionNo.add(BigDecimal.ONE);
            }

            executedInstructions.add(instructionNo);
        }
    }

    public static int getSecondPuzzleSolution(String filename){
        ArrayList<String> instructions = (ArrayList<String>) Utils.getFileContent(filename);
        Map<String, Integer> corruptedInstructionSuspects = new HashMap<>();

        for (int i = 0; i < instructions.size(); i++){
            if (instructions.get(i).startsWith("jmp") || instructions.get(i).startsWith("nop")){
                corruptedInstructionSuspects.put(instructions.get(i), i);
            }
        }

        fixAttempt:
        for (Map.Entry<String,Integer> entry : corruptedInstructionSuspects.entrySet()) {
            ArrayList<String> modifiedInstructions = new ArrayList(instructions);
            String originalCommand = modifiedInstructions.get(entry.getValue());
            String modifiedCommand;

            if (originalCommand.contains("jmp")){
                modifiedCommand = originalCommand.replace("jmp","nop");
            }
            else {
                modifiedCommand = originalCommand.replace("nop","jmp");
            }

            modifiedInstructions.set(entry.getValue(), modifiedCommand);

            // reset variables before next integration
            BigDecimal accumulator = BigDecimal.ZERO;
            BigDecimal nextInstructionNo = BigDecimal.ZERO;
            List<BigDecimal> executedInstructions = new ArrayList<>();

            while (true){
                if (nextInstructionNo.intValue() == instructions.size()){
                    // bingo!
                    return accumulator.intValue();
                }
                if (executedInstructions.contains(nextInstructionNo)){
                    // we have entered the endless loop
                    continue fixAttempt;
                }

                BigDecimal instructionNo = nextInstructionNo;
                String instruction = modifiedInstructions.get(instructionNo.intValue());
                String command = instruction.substring(0,3);
                BigDecimal value = new BigDecimal(instruction.substring(4));

                if (command.equals("acc")){
                    accumulator = accumulator.add(value);
                    nextInstructionNo = nextInstructionNo.add(BigDecimal.ONE);
                }
                else if (command.equals("jmp")){
                    if (value.intValue() == 0){
                        continue fixAttempt;
                    }
                    nextInstructionNo = nextInstructionNo.add(value);
                }
                else if (command.equals("nop")){
                    nextInstructionNo = nextInstructionNo.add(BigDecimal.ONE);
                }

                executedInstructions.add(instructionNo);
            }
        }
        return 0;
    }
}