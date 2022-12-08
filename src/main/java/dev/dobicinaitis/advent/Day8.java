package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day8 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final int[][] forrest = getTreeLayout(input);
        //visualizeTheForest(forrest);
        return calculateNumberOfVisibleTrees(forrest);
    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final int[][] forrest = getTreeLayout(input);
        //visualizeTheForest(forrest);
        return calculateMaxScenicScore(forrest);
    }

    private int calculateNumberOfVisibleTrees(int[][] forrest) {
        int visibleTreeCount = 0;
        for (int columnIndex = 0; columnIndex < forrest.length; columnIndex++) {
            for (int rowIndex = 0; rowIndex < forrest[0].length; rowIndex++) {
                //log.debug("column {}, row {} = {}", columnIndex, rowIndex, forrest[rowIndex][columnIndex]);
                if (isTreeVisible(forrest, rowIndex, columnIndex)) {
                    visibleTreeCount++;
                }
            }
        }
        return visibleTreeCount;
    }

    private boolean isTreeVisible(int[][] forrest, int rowIndex, int columnIndex) {
        final int treeHeight = forrest[rowIndex][columnIndex];
        // visible from above
        if (treeHeight > getHeightOfTallestTreeAbove(forrest, rowIndex - 1, columnIndex)) {
            return true;
        }
        // visible from bellow
        if (treeHeight > getHeightOfTallestTreeBelow(forrest, rowIndex + 1, columnIndex)) {
            return true;
        }
        // visible from right
        if (treeHeight > getHeightOfTallestTreeOnTheRight(forrest, rowIndex, columnIndex + 1)) {
            return true;
        }
        // visible from left
        if (treeHeight > getHeightOfTallestTreeOnTheLeft(forrest, rowIndex, columnIndex - 1)) {
            return true;
        }
        return false;
    }

    private int getHeightOfTallestTreeAbove(int[][] forrest, int rowIndex, int columnIndex) {
        int tallestTreeHeight = -1;
        // edge tree
        if (rowIndex < 0) {
            return tallestTreeHeight;
        }
        for (int i = 0; i <= rowIndex; i++) {
            final int treeHeight = forrest[i][columnIndex];
            if (treeHeight > tallestTreeHeight) {
                tallestTreeHeight = treeHeight;
            }
        }
        return tallestTreeHeight;
    }

    private int getHeightOfTallestTreeBelow(int[][] forrest, int rowIndex, int columnIndex) {
        int tallestTreeHeight = -1;
        // edge tree
        if (rowIndex >= forrest.length) {
            return tallestTreeHeight;
        }
        for (int i = rowIndex; i < forrest.length; i++) {
            final int treeHeight = forrest[i][columnIndex];
            if (treeHeight > tallestTreeHeight) {
                tallestTreeHeight = treeHeight;
            }
        }
        return tallestTreeHeight;
    }

    private int getHeightOfTallestTreeOnTheRight(int[][] forrest, int rowIndex, int columnIndex) {
        int tallestTreeHeight = -1;
        // edge tree
        if (columnIndex >= forrest[0].length) {
            return tallestTreeHeight;
        }
        for (int i = columnIndex; i < forrest[0].length; i++) {
            final int treeHeight = forrest[rowIndex][i];
            if (treeHeight > tallestTreeHeight) {
                tallestTreeHeight = treeHeight;
            }
        }
        return tallestTreeHeight;
    }

    private int getHeightOfTallestTreeOnTheLeft(int[][] forrest, int rowIndex, int columnIndex) {
        int tallestTreeHeight = -1;
        // edge tree
        if (columnIndex < 0) {
            return tallestTreeHeight;
        }
        for (int i = 0; i <= columnIndex; i++) {
            final int treeHeight = forrest[rowIndex][i];
            if (treeHeight > tallestTreeHeight) {
                tallestTreeHeight = treeHeight;
            }
        }
        return tallestTreeHeight;
    }

    private Integer calculateMaxScenicScore(int[][] forrest) {
        int maxScenicScore = 0;
        for (int columnIndex = 0; columnIndex < forrest.length; columnIndex++) {
            for (int rowIndex = 0; rowIndex < forrest[0].length; rowIndex++) {
                //log.debug("column {}, row {}, height {}", columnIndex, rowIndex, forrest[rowIndex][columnIndex]);
                final int treeScenicScore = getTreeScenicScore(forrest, rowIndex, columnIndex);
                if (treeScenicScore > maxScenicScore) {
                    maxScenicScore = treeScenicScore;
                }
            }
        }
        return maxScenicScore;
    }

    private int getTreeScenicScore(int[][] forrest, int rowIndex, int columnIndex) {
        final int treeHeight = forrest[rowIndex][columnIndex];
        final int treesVisibleFromAbove = getNumberOfTreesVisibleAbove(forrest, treeHeight, rowIndex - 1, columnIndex);
        final int treesVisibleFromBelow = getNumberOfTreesVisibleBelow(forrest, treeHeight, rowIndex + 1, columnIndex);
        final int treesVisibleFromRight = getNumberOfTreesVisibleToRight(forrest, treeHeight, rowIndex, columnIndex + 1);
        final int treesVisibleFromLeft = getNumberOfTreesVisibleToLeft(forrest, treeHeight, rowIndex, columnIndex - 1);
        final int treeScenicScore = treesVisibleFromAbove * treesVisibleFromBelow * treesVisibleFromRight * treesVisibleFromLeft;
        //log.debug("score: {} * {} * {} * {} = {}", treesVisibleFromAbove, treesVisibleFromLeft, treesVisibleFromBelow, treesVisibleFromRight, treeScenicScore);
        return treeScenicScore;
    }

    private int getNumberOfTreesVisibleAbove(int[][] forrest, int treeHeight, int rowIndex, int columnIndex) {
        int numberOfVisibleTrees = 0;
        // edge tree
        if (rowIndex < 0) {
            return numberOfVisibleTrees;
        }
        for (int i = rowIndex; i >= 0; i--) {
            final int heightOfTreeInSight = forrest[i][columnIndex];
            numberOfVisibleTrees++;
            if (heightOfTreeInSight >= treeHeight) {
                break;
            }
        }
        return numberOfVisibleTrees;
    }

    private int getNumberOfTreesVisibleBelow(int[][] forrest, int treeHeight, int rowIndex, int columnIndex) {
        int numberOfVisibleTrees = 0;
        // edge tree
        if (rowIndex >= forrest.length) {
            return numberOfVisibleTrees;
        }
        for (int i = rowIndex; i < forrest.length; i++) {
            final int heightOfTreeInSight = forrest[i][columnIndex];
            numberOfVisibleTrees++;
            if (heightOfTreeInSight >= treeHeight) {
                break;
            }
        }
        return numberOfVisibleTrees;
    }

    private int getNumberOfTreesVisibleToRight(int[][] forrest, int treeHeight, int rowIndex, int columnIndex) {
        int numberOfVisibleTrees = 0;
        // edge tree
        if (columnIndex >= forrest[0].length) {
            return numberOfVisibleTrees;
        }
        for (int i = columnIndex; i < forrest[0].length; i++) {
            final int heightOfTreeInSight = forrest[rowIndex][i];
            numberOfVisibleTrees++;
            if (heightOfTreeInSight >= treeHeight) {
                break;
            }
        }
        return numberOfVisibleTrees;
    }

    private int getNumberOfTreesVisibleToLeft(int[][] forrest, int treeHeight, int rowIndex, int columnIndex) {
        int numberOfVisibleTrees = 0;
        // edge tree
        if (columnIndex < 0) {
            return numberOfVisibleTrees;
        }
        for (int i = columnIndex; i >= 0; i--) {
            final int heightOfTreeInSight = forrest[rowIndex][i];
            numberOfVisibleTrees++;
            if (heightOfTreeInSight >= treeHeight) {
                break;
            }
        }
        return numberOfVisibleTrees;
    }

    private int[][] getTreeLayout(List<String> input) {
        final int columns = input.get(0).length();
        final int rows = input.size();
        final int[][] forrest = new int[columns][rows];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                forrest[row][column] = Integer.valueOf(input.get(row).substring(column, column + 1));
            }
        }
        return forrest;
    }

    private void visualizeTheForest(int[][] forrest) {
        String output = "";
        for (int[] treeRow : forrest) {
            output += Arrays.toString(treeRow) + "\n";
        }
        log.info("Forrest layout:\n{}", output);
    }
}
