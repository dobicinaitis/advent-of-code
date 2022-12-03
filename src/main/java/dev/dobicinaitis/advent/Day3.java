package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day3 extends Puzzle {

    public Integer getFirstPuzzleSolution(String filename) {
        List<String> input = Utils.getFileContent(filename);
        List<Character> commonItemTypes = new ArrayList<>();
        for (String items: input){
            int itemsPerCompartment = items.length() / 2;
            String itemsInFirstCompartment = items.substring(0, itemsPerCompartment);
            String itemsInSecondCompartment = items.substring(itemsPerCompartment);
            Character commonItem = findCommonItem(itemsInFirstCompartment, itemsInSecondCompartment);
            commonItemTypes.add(commonItem);
        }
        return commonItemTypes.stream()
                .mapToInt(itemType -> getItemTypeValue(itemType))
                .sum();
    }

    public Integer getSecondPuzzleSolution(String filename) {
        List<String> input = Utils.getFileContent(filename);
        List<Character> badges = new ArrayList<>();
        for (int i = 0; i < input.size() ; i+=3) {
            Character badge = null;
            for (Character item : input.get(i).toCharArray()){
                if (input.get(i + 1).contains(item.toString()) && input.get(i + 2).contains(item.toString())){
                    badge = item;
                }
            }
            if (badge == null){
                throw new RuntimeException("Did not find a common item in group starting at line " + i);
            }
            badges.add(badge);
        }
        return badges.stream()
                .mapToInt(itemType -> getItemTypeValue(itemType))
                .sum();
    }

    private Character findCommonItem(String itemsInFirstCompartment, String itemsInSecondCompartment) {
        // lets for now assume that there's only one common item
        for (Character itemType : itemsInFirstCompartment.toCharArray()){
            if (itemsInSecondCompartment.contains(itemType.toString())){
                return itemType;
            }
        }
        throw new RuntimeException("No common items found");
    }

    /**
     * Determines the numeric value of an item type
     * @param itemType character a-z, A-Z
     * @return int - a-z > 1-26, A-Z > 27-52
     */
    private int getItemTypeValue(char itemType){
        char itemTypeLowerCaseValue = Character.toLowerCase(itemType);
        int valueIncrease = Character.isUpperCase(itemType) ? 26 : 0;
        int value = Character.getNumericValue(itemTypeLowerCaseValue) + valueIncrease - 9;
        log.debug("Type: {}, value: {}", itemType, value);
        return value;
    }
}
