package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
@AllArgsConstructor
public class Day4 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<Integer> drawNumbers = getDrawNumbers(filename);
        List<List<Integer>> bingoCards = getBingoCards(filename);

        for (int i = 0; i < drawNumbers.size(); i++) {
            List<Integer> currentDraw = drawNumbers.subList(0, i + 1);
            // check if any card has "Bingo"
            for (List<Integer> bingoCard : bingoCards) {
                if (bingo(currentDraw, bingoCard)) {
                    log.info("Bingo!!! Lucky card {}", bingoCard);
                    return calculateScore(currentDraw, bingoCard, drawNumbers.get(i));
                }
            }
        }

        throw new RuntimeException("No Bingo :(");
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<Integer> drawNumbers = getDrawNumbers(filename);
        List<List<Integer>> bingoCards = getBingoCards(filename);
        List<List<Integer>> winningBingoCards = new ArrayList<>();
        Integer scoreOfLastWinningCard = 0;

        for (int i = 0; i < drawNumbers.size(); i++) {
            List<Integer> currentDraw = drawNumbers.subList(0, i + 1);
            // check if any card has "Bingo"
            for (List<Integer> bingoCard : bingoCards) {
                if (!winningBingoCards.contains(bingoCard) && bingo(currentDraw, bingoCard)) {
                    winningBingoCards.add(bingoCard);
                    scoreOfLastWinningCard = calculateScore(currentDraw, bingoCard, drawNumbers.get(i));
                }
            }
        }

        return scoreOfLastWinningCard;
    }

    private List<Integer> getDrawNumbers(String filename) {
        return List.of(Utils.getFileContent(filename).get(0).split(",")).stream()
                .map(Integer::parseInt)
                .collect(toList());
    }

    private List<List<Integer>> getBingoCards(String filename) {
        List<String> puzzleInput = Utils.getFileContent(filename);
        List<List<Integer>> bingoCards = new ArrayList<>();

        for (int i = 2; i < puzzleInput.size(); i = i + 6) {
            List<Integer> bingoCard = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                bingoCard.addAll(
                        List.of(puzzleInput.get(i + j).trim().split("\\s+")).stream()
                                //.peek(s -> log.debug("Bingo card row: {}", s))
                                .map(Integer::parseInt)
                                .collect(toList()));
            }
            bingoCards.add(bingoCard);
        }
        return bingoCards;
    }

    private boolean bingo(List<Integer> drawnNumbers, List<Integer> bingoCard) {
        // bingo cards are 5x5

        // check row
        for (int i = 0; i < bingoCard.size(); i = i + 5) {
            if (drawnNumbers.contains(bingoCard.get(i))
                    && drawnNumbers.contains(bingoCard.get(i + 1))
                    && drawnNumbers.contains(bingoCard.get(i + 2))
                    && drawnNumbers.contains(bingoCard.get(i + 3))
                    && drawnNumbers.contains(bingoCard.get(i + 4))) {
                return true;
            }
        }

        // check column
        for (int i = 0; i < 5; i++) {
            if (drawnNumbers.contains(bingoCard.get(i))
                    && drawnNumbers.contains(bingoCard.get(i + 1 * 5))
                    && drawnNumbers.contains(bingoCard.get(i + 2 * 5))
                    && drawnNumbers.contains(bingoCard.get(i + 3 * 5))
                    && drawnNumbers.contains(bingoCard.get(i + 4 * 5))) {
                return true;
            }
        }

        return false;
    }

    private int calculateScore(List<Integer> drawnNumbers, List<Integer> bingoCard, Integer lastCalledNumber) {
        int sumOfUnmarkedNumbers = bingoCard.stream()
                .filter(i -> !drawnNumbers.contains(i))
                .reduce(0, Integer::sum);
        return sumOfUnmarkedNumbers * lastCalledNumber;
    }
}
