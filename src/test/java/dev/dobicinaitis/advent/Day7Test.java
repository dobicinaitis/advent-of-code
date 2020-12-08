package dev.dobicinaitis.advent;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class Day7Test {

    @Test
    public void getBagRulesFromStringTest(){
        String rawBagData = "light red bags contain 1 bright white bag, 2 muted yellow bags.";
        Bag bagRule = Day7.getBagRulesFromString(rawBagData);

        assertEquals("light red", bagRule.getType());
        assertEquals(1, bagRule.getCount());
        assertEquals(2, bagRule.getIncludedBags().size());
        assertEquals("bright white", bagRule.getIncludedBags().get(0).getType());
        assertEquals(1, bagRule.getIncludedBags().get(0).getCount());
        assertEquals("muted yellow", bagRule.getIncludedBags().get(1).getType());
        assertEquals(2, bagRule.getIncludedBags().get(1).getCount());

        String rawBagDataWithoutIncludedBags = "faded blue bags contain no other bags.";
        Bag bagRule2 = Day7.getBagRulesFromString(rawBagDataWithoutIncludedBags);
        assertEquals(0, bagRule2.getIncludedBags().size());

        String rawBagDataWitOneIncludedBags = "wavy magenta bags contain 3 posh brown bags.";
        Bag bagRule3 = Day7.getBagRulesFromString(rawBagDataWitOneIncludedBags);
        assertEquals("wavy magenta", bagRule3.getType());
        assertEquals(1, bagRule3.getCount());
        assertEquals(1, bagRule3.getIncludedBags().size());
        assertEquals("posh brown",bagRule3.getIncludedBags().get(0).getType());
        assertEquals(3,bagRule3.getIncludedBags().get(0).getCount());
    }

    @Test
    public void getMainBagTest(){
        Bag bag1 = Day7.getBagRulesFromString("faded blue bags contain 1 bright white bag, 2 muted yellow bags.");
        Bag bag2 = Day7.getBagRulesFromString("muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.");
        List<Bag> bags = List.of(bag1,bag2);
        Day7.setBags(bags);

        assertEquals("faded blue", Day7.getMainBag(bag2.getIncludedBags().get(1)).getType());
    }

    @Test
    public void getIncludedBagCountTest(){
        Bag bag1 = Day7.getBagRulesFromString("shiny gold bags contain 2 dark red bags.");
        Bag bag2 = Day7.getBagRulesFromString("dark red bags contain 2 dark orange bags.");
        Bag bag3 = Day7.getBagRulesFromString("dark orange bags contain 2 dark yellow bags.");
        Bag bag4 = Day7.getBagRulesFromString("dark yellow bags contain 2 dark green bags.");
        Bag bag5 = Day7.getBagRulesFromString("dark green bags contain 2 dark blue bags.");
        Bag bag6 = Day7.getBagRulesFromString("dark blue bags contain 2 dark violet bags.");
        Bag bag7 = Day7.getBagRulesFromString("dark violet bags contain no other bags.");

        List<Bag> bags = List.of(bag1,bag2,bag3,bag4,bag5,bag6,bag7);
        Day7.setBags(bags);

        assertEquals(126, Day7.getIncludedBagCount(bag1));
    }

    @Test
    public void firstPuzzleExampleTest(){
        assertEquals(4, Day7.getFirstPuzzleSolution("day7_example_input.txt","shiny gold"));
    }

    @Test
    public void firstPuzzleMyInputTest(){
        assertEquals(378, Day7.getFirstPuzzleSolution("day7_my_input.txt","shiny gold"));
    }

    @Test
    public void secondPuzzleExampleInputTest(){
        assertEquals(32, Day7.getSecondPuzzleSolution("day7_example_input.txt","shiny gold"));
    }

    @Test
    public void secondPuzzleMyInputTest(){
        assertEquals(27526, Day7.getSecondPuzzleSolution("day7_my_input.txt","shiny gold"));
    }
}