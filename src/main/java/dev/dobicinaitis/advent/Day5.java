package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day5 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<String> nearbyLineCoordinates = Utils.getFileContent(filename);

        // fill diagram with zeros
        int xAxisSize = getMaxNumber(nearbyLineCoordinates) + 1;
        List<Integer> diagram = new ArrayList<>(Collections.nCopies((int) Math.pow(xAxisSize, 2), 0));

        addHorizontalAndVerticalLinesToDiagram(diagram, xAxisSize, nearbyLineCoordinates);
        drawDiagram(diagram, xAxisSize);

        return (int) diagram.stream().filter(i -> i >= 2).count();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<String> nearbyLineCoordinates = Utils.getFileContent(filename);

        // fill diagram with zeros
        int xAxisSize = getMaxNumber(nearbyLineCoordinates) + 1;
        List<Integer> diagram = new ArrayList<>(Collections.nCopies((int) Math.pow(xAxisSize, 2), 0));

        addHorizontalAndVerticalLinesToDiagram(diagram, xAxisSize, nearbyLineCoordinates);
        addDiagonalLinesToDiagram(diagram, xAxisSize, nearbyLineCoordinates);
        drawDiagram(diagram, xAxisSize);

        return (int) diagram.stream().filter(i -> i >= 2).count();
    }

    static int getMaxNumber(List<String> inputs) {
        int currentNumber = 0, maxNumber = 0;

        for (String input : inputs) {
            for (int i = 0; i < input.length(); i++) {
                // If a numeric value comes, start converting it into an integer till there are consecutive numeric digits
                if (Character.isDigit(input.charAt(i))) {
                    currentNumber = currentNumber * 10 + (input.charAt(i) - '0');
                } else {
                    // update maximum value
                    maxNumber = Math.max(maxNumber, currentNumber);
                    // reset the number
                    currentNumber = 0;
                }
            }
            currentNumber = 0;
        }
        maxNumber = Math.max(maxNumber, currentNumber);
        log.debug("max number in string list = {}", maxNumber);
        return maxNumber;
    }

    private void addHorizontalAndVerticalLinesToDiagram(List<Integer> diagram, int axisSize, List<String> lineCoordinateList) {
        for (String lineCoordinates : lineCoordinateList) {
            int xStartCoordinate = Integer.parseInt(lineCoordinates.substring(0, lineCoordinates.indexOf(",")));
            int yStartCoordinate = Integer.parseInt(lineCoordinates.substring(lineCoordinates.indexOf(",") + 1, lineCoordinates.indexOf(" ")));
            int xEndCoordinate = Integer.parseInt(lineCoordinates.substring(lineCoordinates.lastIndexOf(" ") + 1, lineCoordinates.lastIndexOf(",")));
            int yEndCoordinate = Integer.parseInt(lineCoordinates.substring(lineCoordinates.lastIndexOf(",") + 1));
            //log.debug("{} {} {} {}", xStartCoordinate, yStartCoordinate, xEndCoordinate, yEndCoordinate);

            // horizontal line
            if (yStartCoordinate == yEndCoordinate) {
                if (xStartCoordinate > xEndCoordinate) {
                    int temp = xStartCoordinate;
                    xStartCoordinate = xEndCoordinate;
                    xEndCoordinate = temp;
                }

                int lineLength = xEndCoordinate - xStartCoordinate + 1;

                for (int i = 0; i < lineLength; i++) {
                    int position = axisSize * yStartCoordinate + xStartCoordinate + i;
                    //log.info("{} = {}", position, diagram.get(position) + 1);
                    diagram.set(position, diagram.get(position) + 1);
                }
            }
            // vertical line
            else if (xStartCoordinate == xEndCoordinate) {
                if (yStartCoordinate > yEndCoordinate) {
                    int temp = yStartCoordinate;
                    yStartCoordinate = yEndCoordinate;
                    yEndCoordinate = temp;
                }

                int lineLength = yEndCoordinate - yStartCoordinate + 1;

                for (int i = 0; i < lineLength; i++) {
                    int position = (yStartCoordinate + i) * axisSize + xStartCoordinate;
                    //log.info("{} = {}", position, diagram.get(position) + 1);
                    diagram.set(position, diagram.get(position) + 1);
                }
            }
        }
    }

    private void addDiagonalLinesToDiagram(List<Integer> diagram, int axisSize, List<String> lineCoordinateList) {
        for (String lineCoordinates : lineCoordinateList) {
            int xStartCoordinate = Integer.parseInt(lineCoordinates.substring(0, lineCoordinates.indexOf(",")));
            int yStartCoordinate = Integer.parseInt(lineCoordinates.substring(lineCoordinates.indexOf(",") + 1, lineCoordinates.indexOf(" ")));
            int xEndCoordinate = Integer.parseInt(lineCoordinates.substring(lineCoordinates.lastIndexOf(" ") + 1, lineCoordinates.lastIndexOf(",")));
            int yEndCoordinate = Integer.parseInt(lineCoordinates.substring(lineCoordinates.lastIndexOf(",") + 1));
            //log.debug("{} {} {} {}", xStartCoordinate, yStartCoordinate, xEndCoordinate, yEndCoordinate);

            // ignore horizontal && vertical lines
            if (yStartCoordinate != yEndCoordinate && xStartCoordinate != xEndCoordinate) {
                int position = -1;
                int startingPoint = yStartCoordinate * axisSize + xStartCoordinate;
                diagram.set(startingPoint, diagram.get(startingPoint) + 1);

                // ↘ e.g. 0,0 -> 8,8
                if (xStartCoordinate < xEndCoordinate && yStartCoordinate < yEndCoordinate) {
                    for (int i = 1; i <= Math.abs(yEndCoordinate - yStartCoordinate); i++) {
                        position = startingPoint + axisSize * i + i;
                        diagram.set(position, diagram.get(position) + 1);
                    }
                }
                // ↖ e.g. 6,4 -> 2,0
                else if (xStartCoordinate > xEndCoordinate && yStartCoordinate > yEndCoordinate) {
                    for (int i = 1; i <= Math.abs(yEndCoordinate - yStartCoordinate); i++) {
                        position = startingPoint - axisSize * i - i;
                        diagram.set(position, diagram.get(position) + 1);
                    }
                }
                // ↙ e.g. 8,0 -> 0,8
                else if (xStartCoordinate > xEndCoordinate && yStartCoordinate < yEndCoordinate) {
                    for (int i = 1; i <= Math.abs(yEndCoordinate - yStartCoordinate); i++) {
                        position = startingPoint + axisSize * i - i;
                        diagram.set(position, diagram.get(position) + 1);
                    }
                }
                // ↗ e.g. 5,5 -> 8,2
                else {
                    for (int i = 1; i <= Math.abs(yEndCoordinate - yStartCoordinate); i++) {
                        position = startingPoint - axisSize * i + i;
                        diagram.set(position, diagram.get(position) + 1);
                    }
                }
            }
        }
    }

    public void drawDiagram(List<Integer> diagram, int axisSize) {
        log.info("diagram:");
        for (int i = 0; i < axisSize; i++) {
            for (int j = 0; j < axisSize; j++) {
                String symbol = (diagram.get(i * axisSize + j) == 0) ? "." : String.valueOf(diagram.get(i * axisSize + j));
                System.out.print(symbol + "\t");
            }
            System.out.println();
        }
    }
}
