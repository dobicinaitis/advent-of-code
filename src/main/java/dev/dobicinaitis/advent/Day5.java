package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class Day5 extends Puzzle {

    public static final Pattern STACK_NUMBER_LINE_PATTERN = Pattern.compile("^\\s*\\d");
    public static final Pattern REARRANGEMENT_INSTRUCTION_PATTERN = Pattern.compile("^move (\\d+) from (\\d+) to (\\d+)$");

    public String getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Stack> stacks = populateStartingStack(input);
        final List<Step> rearrangementSteps = getRearrangementSteps(input);
        rearrangeStacksForCrateMover9000(stacks, rearrangementSteps);
        return stacks.stream()
                .map(stack -> stack.pop().toString())
                .collect(Collectors.joining(""));
    }

    public String getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Stack> stacks = populateStartingStack(input);
        final List<Step> rearrangementSteps = getRearrangementSteps(input);
        rearrangeStacksForCrateMover9001(stacks, rearrangementSteps);
        return stacks.stream()
                .map(stack -> stack.pop().toString())
                .collect(Collectors.joining(""));
    }

    private int getStackCount(List<String> input) {
        for (String line : input) {
            if (STACK_NUMBER_LINE_PATTERN.matcher(line).find()) {
                return Integer.parseInt(line.substring(line.lastIndexOf(" ") + 1));
            }
        }
        throw new RuntimeException("Stack count could not be identified.");
    }

    private List<Stack> populateStartingStack(List<String> input) {
        final int stackCount = getStackCount(input);
        final List<Stack> stacks = new ArrayList<>();
        final List<Stack> reversedStacks = new ArrayList<>();

        // initialize all stacks, so later on we can add items to them using index No.
        for (int i = 0; i < stackCount; i++) {
            stacks.add(new Stack());
            reversedStacks.add(new Stack());
        }

        for (String line : input) {
            // stop when stack count line is reached
            if (STACK_NUMBER_LINE_PATTERN.matcher(line).find()) {
                break;
            }
            // extract individual crate values and add them to the relevant stacks
            for (int stackNo = 0; stackNo < stackCount; stackNo++) {
                // [A] < lines are trimmed from the right side
                // [B] [C]
                if (line.length() < stackNo * 3 + 3) {
                    break;
                }
                String crate = line.substring(stackNo * 4, stackNo * 4 + 3); // ....[X] > [X]
                crate = crate.replaceAll("(\\[|\\])", ""); // [X] > X
                if (!crate.isBlank()) {
                    stacks.get(stackNo).push(crate);
                }
            }
        }

        // reverse the stacks
        for (int i = 0; i < stacks.size(); i++) {
            while (!stacks.get(i).isEmpty()) {
                final String crate = stacks.get(i).pop().toString();
                reversedStacks.get(i).push(crate);
            }
        }

        return reversedStacks;
    }

    private List<Step> getRearrangementSteps(List<String> input) {
        return input.stream()
                .filter(line -> REARRANGEMENT_INSTRUCTION_PATTERN.matcher(line).find())
                .map(instruction -> new Step(instruction))
                .collect(Collectors.toList());
    }

    private void rearrangeStacksForCrateMover9000(List<Stack> stacks, List<Step> rearrangementSteps) {
        //printStacks(stacks);
        for (Step step : rearrangementSteps) {
            //log.debug("Rearrangement step: {}", step);
            for (int i = 0; i < step.getCrateCount(); i++) {
                final String crate = stacks.get(step.getFromStackNo() - 1).pop().toString();
                stacks.get(step.getToStackNo() - 1).push(crate);
            }
            //printStacks(stacks);
        }
    }

    private void rearrangeStacksForCrateMover9001(List<Stack> stacks, List<Step> rearrangementSteps) {
        //printStacks(stacks);
        for (Step step : rearrangementSteps) {
            //log.debug("Rearrangement step: {}", step);
            List<String> movedCrates = new ArrayList<>();
            for (int i = 0; i < step.getCrateCount(); i++) {
                final String crate = stacks.get(step.getFromStackNo() - 1).pop().toString();
                movedCrates.add(crate);
            }
            Collections.reverse(movedCrates);
            for (String crate : movedCrates) {
                stacks.get(step.getToStackNo() - 1).push(crate);
            }
            //printStacks(stacks);
        }
    }

    private void printStacks(List<Stack> stacks) {
        String output = "";
        int maxCargosInStack = 0;

        for (Stack stack : stacks) {
            if (stack.size() > maxCargosInStack) {
                maxCargosInStack = stack.size();
            }
        }

        for (int i = maxCargosInStack; i > 0; i--) {
            for (int stackNo = 0; stackNo < stacks.size(); stackNo++) {
                if (stacks.get(stackNo).size() < i) {
                    output += "    ";
                } else {
                    output += "[" + stacks.get(stackNo).get(i - 1) + "] ";
                }
            }
            output += System.lineSeparator();
        }

        for (int stackNo = 1; stackNo <= stacks.size(); stackNo++) {
            output += " " + stackNo + "  ";
        }
        log.info("Stack:\n{}", output);
    }

    @Data
    public class Step {
        private int crateCount;
        private int fromStackNo;
        private int toStackNo;

        public Step(String instruction) {
            Matcher matcher = REARRANGEMENT_INSTRUCTION_PATTERN.matcher(instruction);
            if (matcher.matches()) {
                this.crateCount = Integer.parseInt(matcher.group(1));
                this.fromStackNo = Integer.parseInt(matcher.group(2));
                this.toStackNo = Integer.parseInt(matcher.group(3));
            } else {
                throw new RuntimeException("Instruction \"" + instruction + "\" did not match pattern: " + REARRANGEMENT_INSTRUCTION_PATTERN);
            }
        }
    }
}
