package dev.dobicinaitis.advent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class Day7 {

    private static List<Bag> bags = new ArrayList<>();

    public static int getFirstPuzzleSolution(String filename, String type){
        loadBagRulesFromFile(filename);
        return (int) bags.stream().filter(bag -> isTypeIncluded(bag, type)).count();
    }

    public static int getSecondPuzzleSolution(String filename, String type){
        loadBagRulesFromFile(filename);
        Bag mainBag = bags.stream().filter(b -> b.getType().equals(type)).findFirst().get();
        return getIncludedBagCount(mainBag);
    }

    public static int getIncludedBagCount(Bag bag){
        int includedBagCount = 0;

        for (Bag includedBag : bag.getIncludedBags()){
            includedBagCount+=includedBag.getCount();
            int subBagCount = 0; // bags included with this included bag 🤯
            if (getMainBag(includedBag) != null){
                Bag mainBag = getMainBag(includedBag);
                subBagCount+=getIncludedBagCount(mainBag);
            }
            includedBagCount+=includedBag.getCount() * subBagCount;
        }
        return includedBagCount;
    }

    public static boolean isTypeIncluded(Bag bag, String type){
        for (Bag includedBag: bag.getIncludedBags()){
            if (includedBag.getType().equals(type)){
                return true;
            }
            if (isTypeIncluded(getMainBag(includedBag), type)){
                return true;
            }
        }
        return false;
    }

    public static Bag getMainBag(Bag bag){
        return Day7.bags.stream().filter(mainBag -> mainBag.getType().equals(bag.getType())).findFirst().orElse(null);
    }

    private static void loadBagRulesFromFile(String filename){
        Day7.bags = Utils.getFileContent(filename).stream()
                .map(bag -> getBagRulesFromString(bag))
                .collect(Collectors.toList());
    }

    public static Bag getBagRulesFromString(String rule){
        String mainBagType = rule.substring(0,rule.indexOf(" bags contain"));
        Bag mainBag = Bag.builder().type(mainBagType).count(1).build();
        List<Bag> subBags = new ArrayList<>();

        //add sub-bags
        if (rule.matches(".*[1-9].*")){
            List<String> rawSubBags = Arrays
                    .asList(rule.substring(rule.indexOf(mainBagType) + 14 + mainBagType.length()).split(","));

            for (String rawSubBag: rawSubBags){
                String subBagType = rawSubBag
                        .replaceAll(" bags","")
                        .replaceAll(" bag","")
                        .replaceAll("[\\d]","")
                        .replaceAll("\\.","")
                        .trim();
                Integer subBagCount = Integer.valueOf(rawSubBag.replaceAll("[^\\d]",""));
                subBags.add(Bag.builder().type(subBagType).count(subBagCount).build());
            }
        }
        mainBag.setIncludedBags(subBags);
        return mainBag;
    }

    public static void setBags(List<Bag> bags){
        Day7.bags = bags;
    }
}

@Data
@Builder
class Bag {
    private String type;
    private Integer count;
    private List<Bag> includedBags;
}