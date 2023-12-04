package dev.dobicinaitis.advent;

import com.diogonunes.jcolor.Attribute;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.diogonunes.jcolor.Ansi.colorize;

@Slf4j
@AllArgsConstructor
public class Day3 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<EnginePart> engineParts = parseInput(input);
        final List<EnginePart> validParts = extractValidParts(engineParts, input);
        return validParts.stream().mapToInt(EnginePart::getPartNumber).sum();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<EnginePart> engineParts = parseInput(input);
        final List<EnginePart> validParts = extractValidParts(engineParts, input);
        return extractGearRatios(validParts, input).stream().mapToInt(Integer::intValue).sum();
    }

    private List<EnginePart> parseInput(List<String> input) {
        final List<EnginePart> engineParts = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            final int rowId = i;
            final String line = input.get(i);
            String digitStash = "";
            int numberStartIndex = -1;
            for (int j = 0; j < line.length(); j++) {
                final String character = String.valueOf(line.charAt(j));
                if (character.matches("\\d")) {
                    digitStash += character;
                    if (numberStartIndex == -1) {
                        numberStartIndex = j;
                    }
                } else if (!digitStash.isEmpty()) {
                    final int partNumber = Integer.parseInt(digitStash);
                    engineParts.add(new EnginePart(partNumber, rowId, numberStartIndex));
                    digitStash = "";
                    numberStartIndex = -1;
                }
                // line ends with a number
                if (j == line.length() - 1 && !digitStash.isEmpty()) {
                    final int partNumber = Integer.parseInt(digitStash);
                    engineParts.add(new EnginePart(partNumber, rowId, numberStartIndex));
                }
            }
        }
        return engineParts;
    }

    /**
     * @return a list of part numbers that are adjacent to a symbol (excluding dots).
     */
    private List<EnginePart> extractValidParts(final List<EnginePart> engineParts, final List<String> schematics) {
        final List<EnginePart> validParts = new ArrayList<>();
        for (final EnginePart enginePart : engineParts) {
            final int rowNumber = enginePart.getRowId();
            final int columnNumber = enginePart.getColumnId();
            final int partDigitCount = ("" + enginePart.getPartNumber()).length();
            final String partLine = schematics.get(rowNumber);
            final boolean checkRowAbove = rowNumber > 0;
            final boolean checkRowBelow = rowNumber < schematics.size() - 1;
            final boolean checkColumnLeft = columnNumber > 0;
            final boolean checkColumnRight = columnNumber + partDigitCount < partLine.length();
            final int startIndex = columnNumber - (checkColumnLeft ? 1 : 0);
            final int endIndex = columnNumber + partDigitCount + (checkColumnRight ? 1 : 0);

            boolean isPartValid = false;

            if (checkRowAbove) {
                final String rowAbove = schematics.get(rowNumber - 1);
                for (int j = startIndex; j < endIndex; j++) {
                    final String character = String.valueOf(rowAbove.charAt(j));
                    if (isSymbol(character)) {
                        isPartValid = true;
                        if (character.equals("*")) {
                            enginePart.addPossibleGear(rowNumber - 1, j);
                        }
                    }
                }
            }
            if (checkRowBelow) {
                final String rowBelow = schematics.get(rowNumber + 1);
                for (int j = startIndex; j < endIndex; j++) {
                    final String character = String.valueOf(rowBelow.charAt(j));
                    if (isSymbol(character)) {
                        isPartValid = true;
                        if (character.equals("*")) {
                            enginePart.addPossibleGear(rowNumber + 1, j);
                        }
                    }
                }
            }
            if (checkColumnLeft) {
                final String character = String.valueOf(partLine.charAt(columnNumber - 1));
                if (isSymbol(character)) {
                    isPartValid = true;
                    if (character.equals("*")) {
                        enginePart.addPossibleGear(rowNumber, columnNumber - 1);
                    }
                }
            }
            if (checkColumnRight) {
                final String character = String.valueOf(partLine.charAt(columnNumber + partDigitCount));
                if (isSymbol(character)) {
                    isPartValid = true;
                    if (character.equals("*")) {
                        enginePart.addPossibleGear(rowNumber, columnNumber + partDigitCount);
                    }
                }
            }
            if (isPartValid) {
                validParts.add(enginePart);
            }
        }
        return validParts;
    }

    /**
     * @return list of gear ratios, which are the multiplication of the part numbers that are adjacent to a gear symbol (*).
     */
    private List<Integer> extractGearRatios(final List<EnginePart> engineParts, final List<String> schematics) {
        final List<EnginePart> partsToCheck = engineParts.stream()
                .filter(enginePart -> !enginePart.getPossibleGears().isEmpty())
                .toList();
        final List<EnginePart> foundGearParts = new ArrayList<>();
        final List<Integer> gearRatios = new ArrayList<>();

        for (EnginePart part : partsToCheck) {
            log.debug("Checking part: {}", part);
            // skip if part is already found to be part of a gear
            if (foundGearParts.stream().anyMatch(gearPart -> gearPart.equals(part))) {
                continue;
            }
            final List<EnginePart> partsOfAGear = new ArrayList<>();
            for (PossibleGear possibleGear : part.getPossibleGears()) {
                partsOfAGear.addAll(partsToCheck.stream()
                        .filter(p -> p.getPossibleGears().stream()
                                .anyMatch(otherPossibleGearPart -> otherPossibleGearPart.getRowId() == possibleGear.getRowId() &&
                                        otherPossibleGearPart.getColumnId() == possibleGear.getColumnId()))
                        .toList());
            }
            // log.debug("Engine parts in a gear: {}", enginePartsInGear);
            if (partsOfAGear.size() == 2) {
                foundGearParts.addAll(partsOfAGear);
                gearRatios.add(partsOfAGear.stream()
                        .mapToInt(EnginePart::getPartNumber)
                        .reduce(1, (a, b) -> a * b));
            }
        }
        // log.debug("Gear ratios: {}", gearRatios);
        final List<EnginePart> partsNotPartOfGears = partsToCheck.stream().filter(part -> !foundGearParts.contains(part)).toList();
        printSchematics(schematics, foundGearParts, partsNotPartOfGears);
        return gearRatios;
    }

    /**
     * When you just can't find the issue, "draw" the thing with fancy colors to debug it your eyes :D
     */
    private void printSchematics(final List<String> schematics, final List<EnginePart> gearParts, final List<EnginePart> partsNotPartOfGears) {
        final StringBuilder printout = new StringBuilder();
        final int rowCountLength = ("" + schematics.size()).length();
        for (int i = 0; i < schematics.size(); i++) {
            final int rowNumber = i;
            printout.append(String.format("%" + rowCountLength + "s ", rowNumber));
            for (int j = 0; j < schematics.get(i).length(); j++) {
                final int columnNumber = j;
                final String character = String.valueOf(schematics.get(i).charAt(j));
                if (character.equals("*")) {
                    printout.append(colorize(character, Attribute.YELLOW_TEXT()));
                } else if (gearParts.stream().anyMatch(part -> part.getRowId() == rowNumber && part.getColumnId() == columnNumber)) {
                    final String partNumber = gearParts.stream()
                            .filter(part -> part.getRowId() == rowNumber && part.getColumnId() == columnNumber)
                            .map(EnginePart::getPartNumber)
                            .map(String::valueOf)
                            .findFirst()
                            .orElseThrow();
                    printout.append(colorize(partNumber, Attribute.GREEN_TEXT()));
                    j += partNumber.length() - 1;
                } else if (partsNotPartOfGears.stream().anyMatch(part -> part.getRowId() == rowNumber && part.getColumnId() == columnNumber)) {
                    final String partNumber = partsNotPartOfGears.stream()
                            .filter(part -> part.getRowId() == rowNumber && part.getColumnId() == columnNumber)
                            .map(EnginePart::getPartNumber)
                            .map(String::valueOf)
                            .findFirst()
                            .orElseThrow();
                    printout.append(colorize(partNumber, Attribute.RED_TEXT()));
                    j += partNumber.length() - 1;
                } else {
                    printout.append(character);
                }
            }
            printout.append("\n");
        }
        System.out.println(printout);
    }

    private boolean isSymbol(@NonNull final String character) {
        return !character.equals(".") && !character.matches("\\d");
    }

    @Data
    @EqualsAndHashCode
    static class EnginePart {
        private final int partNumber;
        private final int rowId;
        private final int columnId;
        private List<PossibleGear> possibleGears = new ArrayList<>();

        public void addPossibleGear(int rowId, int columnId) {
            possibleGears.add(new PossibleGear(rowId, columnId));
        }
    }

    @Data
    @EqualsAndHashCode
    static class PossibleGear {
        private final int rowId;
        private final int columnId;
    }
}
