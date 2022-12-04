package day4;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CampCleanup {
    List<String> fileLines;
    List<List<Integer>> intervalPairs;

    public CampCleanup() {
        URL path = CampCleanup.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        this.buildPairs();

        System.out.println("Camp Cleanup part 1: " + this.part1());
        System.out.println("Camp Cleanup part 2: " + this.part2());
    }

    private int part1() {
        int fullyContainedCount = 0;
        for (List<Integer> intervalPair : intervalPairs) {
            boolean firstIntervalContainsSecond = intervalPair.get(0) <= intervalPair.get(2) &&
                    intervalPair.get(1) >= intervalPair.get(3);
            boolean secondIntervalContainsFirst = intervalPair.get(2) <= intervalPair.get(0) &&
                    intervalPair.get(3) >= intervalPair.get(1);

            if (firstIntervalContainsSecond || secondIntervalContainsFirst) {
                fullyContainedCount++;
            }
        }

        return fullyContainedCount;
    }

    private int part2() {
        int overlapCount = 0;
        for (List<Integer> intervalPair : intervalPairs) {
            boolean firstIntervalOverlapsSecond = intervalPair.get(1) >= intervalPair.get(2) &&
                    intervalPair.get(1) <= intervalPair.get(3);
            boolean secondIntervalContainsFirst = intervalPair.get(3) >= intervalPair.get(0) &&
                    intervalPair.get(3) <= intervalPair.get(1);

            if (firstIntervalOverlapsSecond || secondIntervalContainsFirst) {
                overlapCount++;
            }
        }

        return overlapCount;
    }

    /**
     * parse each interval string (formatted "A-B,C-D") into an integer list [A, B, C, D],
     * then add that list to this.intervalPairs
     */
    private void buildPairs() {
        this.intervalPairs = new ArrayList<>();
        for (String line : this.fileLines) {
            String[] intervalValues = line.split("[,|-]");
            List<Integer> intervalValuesList = new ArrayList<>();
            for (String value : intervalValues) {
                intervalValuesList.add(Integer.parseInt(value));
            }
            this.intervalPairs.add(intervalValuesList);
        }
    }
}
