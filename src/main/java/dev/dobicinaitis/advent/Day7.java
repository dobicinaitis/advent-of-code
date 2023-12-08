package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@AllArgsConstructor
public class Day7 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        return calculateTotalWinnings(filename, false);
    }

    public Integer getSecondPuzzleSolution(String filename) {
        return calculateTotalWinnings(filename, true);
    }

    private int calculateTotalWinnings(final String filename, final boolean withJokers) {
        final List<String> input = Utils.getFileContent(filename);
        final List<CamelCardHand> cardHands = parseInput(input, withJokers);
        final List<CamelCardHand> cardHandsSortedByRank = cardHands.stream()
                .sorted()
                //.peek(System.out::println)
                .toList();
        int totalWinnings = 0;
        for (int i = 1; i <= cardHandsSortedByRank.size(); i++) {
            totalWinnings += cardHandsSortedByRank.get(i - 1).getBid() * i;
        }
        return totalWinnings;
    }

    private List<CamelCardHand> parseInput(final List<String> input, final boolean withJokers) {
        return input.stream()
                .map(line -> line.split(" "))
                .map(column -> {
                    if (withJokers) {
                        return new CamelCardHandWithJokers(column[0], Integer.parseInt(column[1]));
                    }
                    return new CamelCardHand(column[0], Integer.parseInt(column[1]));
                })
                .toList();
    }

    @Data
    static class CamelCardHand implements Comparable<CamelCardHand> {
        private final String cardsOnHand;
        private final int bid;
        private boolean fiveOfAKind;
        private boolean fourOfAKind;
        private boolean fullHouse;
        private boolean threeOfAKind;
        private boolean twoPairs;
        private boolean onePair;
        private boolean highCard;

        public CamelCardHand(String cardsOnHand, int bid) {
            this.cardsOnHand = cardsOnHand;
            this.bid = bid;
            setTypes();
        }

        protected void setTypes() {
            this.highCard = true;
            int pairCount = 0;
            String remainingCards = cardsOnHand;

            while (!remainingCards.isEmpty()) {
                final char card = remainingCards.charAt(0);
                int numberOfSameCardsOnHand = StringUtils.countMatches(cardsOnHand, card);
                if (numberOfSameCardsOnHand != 1) {
                    this.highCard = false;
                }
                if (numberOfSameCardsOnHand == 5) {
                    this.fiveOfAKind = true;
                    break;
                } else if (numberOfSameCardsOnHand == 4) {
                    this.fourOfAKind = true;
                    break;
                } else if (numberOfSameCardsOnHand == 3) {
                    this.threeOfAKind = true;
                } else if (numberOfSameCardsOnHand == 2) {
                    pairCount++;
                }
                remainingCards = remainingCards.replace(String.valueOf(card), "");
            }

            if (pairCount == 2) {
                this.twoPairs = true;
            } else if (pairCount == 1) {
                this.onePair = true;
            }

            if (threeOfAKind && onePair) {
                this.resetTypes();
                this.fullHouse = true;
            }
        }

        protected boolean isStronger(char thisCard, char otherCard) {
            final String thisCardAsString = String.valueOf(thisCard);
            final String otherCardAsString = String.valueOf(otherCard);
            // both are numeric
            if (StringUtils.isNumeric(thisCardAsString) && StringUtils.isNumeric(otherCardAsString)) {
                return Integer.parseInt(thisCardAsString) > Integer.parseInt(otherCardAsString);
            }
            // a letter card is stronger than a number card
            if (!StringUtils.isNumeric(thisCardAsString) && StringUtils.isNumeric(otherCardAsString)) {
                return true;
            }
            // both are letters
            if (Objects.equals(thisCard, otherCard)) {
                return false;
            }
            return switch (thisCard) {
                case 'A' -> true;
                case 'K' -> otherCard != 'A';
                case 'Q' -> otherCard != 'A' && otherCard != 'K';
                case 'J' -> otherCard != 'A' && otherCard != 'K' && otherCard != 'Q';
                default -> false;
            };
        }

        public int getHandStrength() {
            if (this.fiveOfAKind) {
                return 7;
            }
            if (this.fourOfAKind) {
                return 6;
            }
            if (this.fullHouse) {
                return 5;
            }
            if (this.threeOfAKind) {
                return 4;
            }
            if (this.twoPairs) {
                return 3;
            }
            if (this.onePair) {
                return 2;
            }
            if (this.highCard) {
                return 1;
            }
            return 0;
        }

        @Override
        public int compareTo(CamelCardHand other) {
            if (this.getHandStrength() > other.getHandStrength()) {
                return 1;
            } else if (this.getHandStrength() < other.getHandStrength()) {
                return -1;
            }
            // same hand strengths, we'll need to compare individual cards (left to right)
            for (int i = 0; i < cardsOnHand.length(); i++) {
                char thisCard = cardsOnHand.charAt(i);
                char otherCard = other.getCardsOnHand().charAt(i);
                if (thisCard == otherCard) {
                    continue;
                }
                return this.isStronger(thisCard, otherCard) ? 1 : -1;
            }
            return 0;
        }

        protected void resetTypes() {
            this.fiveOfAKind = false;
            this.fourOfAKind = false;
            this.fullHouse = false;
            this.threeOfAKind = false;
            this.twoPairs = false;
            this.onePair = false;
            this.highCard = false;
        }
    }

    static class CamelCardHandWithJokers extends CamelCardHand {

        private static final char JOKER = 'J';

        public CamelCardHandWithJokers(String cardsOnHand, int bid) {
            super(cardsOnHand, bid);
            super.resetTypes();
            this.setTypes();
        }

        @Override
        protected void setTypes() {
            int numberOfJokersOnHand = StringUtils.countMatches(super.cardsOnHand, JOKER);
            // no Joker cards, no need to do anything special
            if (numberOfJokersOnHand == 0) {
                super.setTypes();
                return;
            }

            int pairCount = 0;
            final String cardsOnHandWithoutJokers = super.cardsOnHand.replace("" + JOKER, "");

            if (cardsOnHandWithoutJokers.isEmpty()) {
                super.fiveOfAKind = true;
                return;
            }

            String remainingCards = sortByCharacterFrequency(cardsOnHandWithoutJokers); // abacab -> aaabbc

            while (!remainingCards.isEmpty()) {
                final char card = remainingCards.charAt(0);
                int numberOfSameCardsOnHand = StringUtils.countMatches(super.cardsOnHand, card);
                if (numberOfSameCardsOnHand + numberOfJokersOnHand == 5) {
                    super.fiveOfAKind = true;
                    break;
                } else if (numberOfSameCardsOnHand + numberOfJokersOnHand == 4) {
                    super.resetTypes();
                    super.fourOfAKind = true;
                    break;
                } else if (numberOfSameCardsOnHand + numberOfJokersOnHand == 3) {
                    super.threeOfAKind = true;
                    numberOfJokersOnHand = 0;
                } else if (numberOfSameCardsOnHand + numberOfJokersOnHand == 2) {
                    pairCount++;
                    numberOfJokersOnHand = 0;
                }
                remainingCards = remainingCards.replace(String.valueOf(card), "");
            }

            if (pairCount == 2) {
                super.twoPairs = true;
            } else if (pairCount == 1) {
                super.onePair = true;
            }

            if (super.threeOfAKind && super.onePair) {
                super.resetTypes();
                super.fullHouse = true;
            }
        }

        @Override
        protected boolean isStronger(char thisCard, char otherCard) {
            if (thisCard != JOKER && otherCard != JOKER) {
                return super.isStronger(thisCard, otherCard);
            }
            if (thisCard == JOKER && otherCard == JOKER) {
                return false;
            }
            return thisCard != JOKER;
        }

        @Override
        public int compareTo(CamelCardHand other) {
            if (this.getHandStrength() > other.getHandStrength()) {
                return 1;
            } else if (this.getHandStrength() < other.getHandStrength()) {
                return -1;
            }
            // same hand strengths, we'll need to compare individual cards (left to right)
            for (int i = 0; i < super.cardsOnHand.length(); i++) {
                char thisCard = super.cardsOnHand.charAt(i);
                char otherCard = other.getCardsOnHand().charAt(i);
                if (thisCard == otherCard) {
                    continue;
                }
                return this.isStronger(thisCard, otherCard) ? 1 : -1;
            }
            return 0;
        }
    }

    public static String sortByCharacterFrequency(String input) {
        final Map<Character, Integer> charFrequencyMap = new HashMap<>();
        // count character occurrences
        for (char c : input.toCharArray()) {
            charFrequencyMap.put(c, charFrequencyMap.getOrDefault(c, 0) + 1);
        }
        // sort characters by frequency
        final List<Character> sortedChars = new ArrayList<>(charFrequencyMap.keySet());
        sortedChars.sort((c1, c2) -> charFrequencyMap.get(c2) - charFrequencyMap.get(c1));
        // reconstruct the string based on sorted characters
        final StringBuilder sortedString = new StringBuilder();
        for (char c : sortedChars) {
            final int count = charFrequencyMap.get(c);
            sortedString.append(String.valueOf(c).repeat(Math.max(0, count)));
        }
        return sortedString.toString();
    }

}
