package day3;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RucksackReorganization {
    List<String> fileLines;
    List<Character> commonItemsWithinSacks;
    List<Character> commonItemsWithinTriplets;

    public RucksackReorganization() {
        URL path = RucksackReorganization.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        System.out.println("Rucksack Reorganization part 1: " + this.part1());
        System.out.println("Rucksack Reorganization part 2: " + this.part2());
    }

    private int part1() {
        this.buildCommonItemsWithinSacks();
        return this.calculatePriority(this.commonItemsWithinSacks);
    }

    private int part2() {
        this.buildCommonItemsWithinTriplets();
        return this.calculatePriority(this.commonItemsWithinTriplets);
    }

    private int calculatePriority(List<Character> items) {
        int prioritySum = 0;
        for (char c : items) {
            if (Character.isLowerCase(c)) {
                prioritySum += (c - 'a') + 1;
            } else {
                prioritySum += (c - 'A') + 27;
            }
        }
        return prioritySum;
    }

    private void buildCommonItemsWithinSacks() {
        this.commonItemsWithinSacks = new ArrayList<>();
        for (String line : this.fileLines) {
            Set<Character> compartment1 = new HashSet<>();
            Set<Character> compartment2 = new HashSet<>();

            for (int i = 0; i < line.length() / 2; i++) {
                compartment1.add(line.charAt(i));
            }

            for (int i = line.length() / 2; i < line.length(); i++) {
                char c = line.charAt(i);
                if (compartment1.contains(c) && !compartment2.contains(c)) {
                    this.commonItemsWithinSacks.add(c);
                }
                compartment2.add(c);
            }
        }
    }

    private void buildCommonItemsWithinTriplets() {
        this.commonItemsWithinTriplets = new ArrayList<>();
        for (int i = 0; i < this.fileLines.size(); i += 3) {
            Set<Character> sack1 = new HashSet<>();
            Set<Character> sack2 = new HashSet<>();

            for (char c : this.fileLines.get(i).toCharArray()) {
                sack1.add(c);
            }

            for (char c : this.fileLines.get(i + 1).toCharArray()) {
                sack2.add(c);
            }

            for (char c : this.fileLines.get(i + 2).toCharArray()) {
                if (sack1.contains(c) && sack2.contains(c)) {
                    commonItemsWithinTriplets.add(c);
                    break;
                }
            }
        }
    }
}
