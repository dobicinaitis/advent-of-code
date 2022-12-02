package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import static dev.dobicinaitis.advent.Day2.Outcome.*;
import static dev.dobicinaitis.advent.Day2.Shape.*;

@Slf4j
@AllArgsConstructor
public class Day2 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        int totalScore = 0;
        for (String roundInput : Utils.getFileContent(filename)) {
            Shape opponentShape = Shape.fromCode(roundInput.substring(0, 1));
            Shape playerShape = getShapeForStrategy1(roundInput.substring(2));
            totalScore += calculateRoundScore(playerShape, opponentShape);
        }
        return totalScore;
    }

    public Integer getSecondPuzzleSolution(String filename) {
        int totalScore = 0;
        for (String roundInput : Utils.getFileContent(filename)) {
            Shape opponentShape = Shape.fromCode(roundInput.substring(0, 1));
            Shape playerShape = getShapeForStrategy2(roundInput.substring(2), opponentShape);
            totalScore += calculateRoundScore(playerShape, opponentShape);
        }
        return totalScore;
    }

    /**
     * Shape points + win/loss/draw points
     */
    private int calculateRoundScore(Shape playerShape, Shape opponentShape) {
        Outcome outcome = getOutcome(playerShape, opponentShape);
        return playerShape.getPoints() + outcome.getPoints();
    }

    /**
     * Outcome of a Rock Paper Scissors match for player
     *
     * @param shape1 player shape
     * @param shape2 opponent shape
     * @return Outcome (win/loss/draw)
     */
    private Outcome getOutcome(Shape shape1, Shape shape2) {
        if ((shape1 == ROCK && shape2 == SCISSORS) || (shape1 == SCISSORS && shape2 == PAPER) || (shape1 == PAPER && shape2 == ROCK)) {
            return WIN;
        }
        return (shape1 == shape2) ? DRAW : LOSS;
    }

    private Shape getShapeForStrategy1(String code) {
        switch (code) {
            case "X":
                return ROCK;
            case "Y":
                return PAPER;
            case "Z":
                return SCISSORS;
            default:
                throw new RuntimeException("Unknown shape code: " + code);
        }
    }

    private Shape getShapeForStrategy2(String code, Shape oponentShape) {
        List<Shape> shapeOptions = List.of(ROCK, PAPER, SCISSORS);
        Outcome desiredOutcome;
        switch (code) {
            case "X": {
                desiredOutcome = LOSS;
                break;
            }
            case "Y": {
                desiredOutcome = DRAW;
                break;
            }
            case "Z": {
                desiredOutcome = WIN;
                break;
            }
            default:
                throw new RuntimeException("Unknown shape code: " + code);
        }
        for (Shape playerShapeOption : shapeOptions) {
            if (desiredOutcome == getOutcome(playerShapeOption, oponentShape)) {
                return playerShapeOption;
            }
        }
        throw new RuntimeException("Could not determine player shape");
    }

    @AllArgsConstructor
    @Getter
    public enum Outcome {
        WIN(6),
        DRAW(3),
        LOSS(0);

        private int points;
    }

    @AllArgsConstructor
    @Getter
    public enum Shape {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        private int points;

        public static Shape fromCode(String shapeCode) {
            if ("A".equals(shapeCode)) {
                return ROCK;
            }
            if ("B".equals(shapeCode)) {
                return PAPER;
            }
            if ("C".equals(shapeCode)) {
                return SCISSORS;
            }
            throw new RuntimeException("Unknown shape code: " + shapeCode);
        }
    }
}
