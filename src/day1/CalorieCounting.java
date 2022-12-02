package day1;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CalorieCounting {
    List<String> fileLines;
    List<Integer> calorieTotals;

    public CalorieCounting() {
        URL path = CalorieCounting.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);
        this.calorieTotals = new ArrayList<>();

        this.computeCalorieTotals();

        System.out.println("CalorieCounting part 1: " + this.part1());
    }

    private void computeCalorieTotals() {
        int currentCalorieTotal = 0;
        for (int i = 0; i < this.fileLines.size(); i++) {
            String line = this.fileLines.get(i);
            if (line.length() == 0 || i == this.fileLines.size() - 1) { // if blank or end of list
                this.calorieTotals.add(currentCalorieTotal);
                currentCalorieTotal = 0;
            } else {
                try {
                    currentCalorieTotal += Integer.parseInt(line);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing file line to Integer");
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    /**
     * @return largest value in calorieTotals
     */
    private int part1() {
        int max = -1;
        for (int calorieTotal : this.calorieTotals) {
            max = Math.max(max, calorieTotal);
        }
        return max;
    }
}
