package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day4 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        int pointsTotal = 0;
        for (Card card : parseInput(input)) {
            final long countOfWiningNumbersInCard = card.ticketNumbers().stream()
                    .filter(card.winningNumbers()::contains)
                    .count();
            if (countOfWiningNumbersInCard == 0) {
                continue;
            }
            int cardPoints = 1;
            for (int i = 1; i < countOfWiningNumbersInCard; i++) {
                cardPoints *= 2;
            }
            pointsTotal += cardPoints;
        }
        return pointsTotal;
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final List<Card> cards = new ArrayList<>(parseInput(input));
        int index = 0;
        while (index < cards.size()) {
            final Card card = cards.get(index);
            index++;
            final long countOfWiningNumbersInCard = card.ticketNumbers().parallelStream()
                    .filter(card.winningNumbers()::contains)
                    .count();
            if (countOfWiningNumbersInCard == 0) {
                continue;
            }
            final List<Card> cardsWonByThisCard = new ArrayList<>(
                    cards.subList(card.cardNumber, card.cardNumber + (int) countOfWiningNumbersInCard));
            cards.addAll(cardsWonByThisCard);
        }
        return cards.size();
    }

    private List<Card> parseInput(List<String> input) {
        return input.stream()
                .map(scratchcard -> {
                    final int cardNumber = Utils.extractFirstNumberFromString(scratchcard);
                    final String winningNumberPart = scratchcard.substring(scratchcard.indexOf(": ") + 2, scratchcard.indexOf(" |"));
                    final String cardNumberPart = scratchcard.substring(scratchcard.indexOf("|") + 2);
                    final List<Integer> winningNumbers = Arrays.stream(winningNumberPart.split(" "))
                            .filter(s -> !s.isEmpty())
                            .map(Integer::parseInt)
                            .toList();
                    final List<Integer> ticketNumbers = Arrays.stream(cardNumberPart.split(" "))
                            .filter(s -> !s.isEmpty())
                            .map(Integer::parseInt)
                            .toList();
                    return new Card(cardNumber, winningNumbers, ticketNumbers);
                })
                .toList();
    }

    record Card(int cardNumber, List<Integer> winningNumbers, List<Integer> ticketNumbers) {
    }
}
