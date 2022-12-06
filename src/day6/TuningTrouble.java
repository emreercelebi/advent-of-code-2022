package day6;

import helpers.Helpers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuningTrouble {

    List<String> fileLines;

    public TuningTrouble() {
        URL path = TuningTrouble.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        System.out.println("Tuning Trouble part 1: " + this.part1());
        System.out.println("Tuning Trouble part 2: " + this.part2());
    }

    private int part1() {
        return firstCharacterWithNDistinct(4);
    }

    private int part2() {
        return firstCharacterWithNDistinct(14);
    }

    private int firstCharacterWithNDistinct(int n) {
        Map<Character, Integer> charFreq = new HashMap<>();
        String line = this.fileLines.get(0);
        for (int i = 0; i < n; i++) {
            char c = line.charAt(i);
            charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
        }

        for (int i = n; i < line.length(); i++) {
            char c = line.charAt(i);
            char prevC = line.charAt(i - n);
            charFreq.put(c, charFreq.getOrDefault(c, 0) + 1);
            charFreq.put(prevC, charFreq.get(prevC) - 1);

            if (charFreq.get(prevC) == 0) {
                charFreq.remove(prevC);
            }

            if (charFreq.size() == n) {
                return i + 1;
            }
        }

        return -1;
    }
}
