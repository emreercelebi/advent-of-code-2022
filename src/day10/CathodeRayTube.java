package day10;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CathodeRayTube {

    List<String> fileLines;
    List<Step> steps;
    List<Integer> cycleEndValues;

    public CathodeRayTube() {
        URL path = CathodeRayTube.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        this.buildSteps();
        this.buildCycleEndValues();

        System.out.println("Cathode Ray Tube part 1: " + this.part1());
        System.out.println("Cathode Ray Tube part 2:");
        this.part2();
    }

    private void buildSteps() {
        this.steps = new ArrayList<>();
        for (String line : this.fileLines) {
            if (line.equals("noop")) {
                this.steps.add(new Step(1, 0));
            } else {
                String[] parts = line.split(" ");
                this.steps.add(new Step(2, Integer.parseInt(parts[1])));
            }
        }
    }

    private void buildCycleEndValues() {
        this.cycleEndValues = new ArrayList<>();
        this.cycleEndValues.add(1);

        int x = 1;

        for (Step step : this.steps) {
            boolean isNoop = step.cycles == 1;
            this.cycleEndValues.add(x);
            if (!isNoop) {
                x += step.v;
                this.cycleEndValues.add(x);
            }
        }
    }

    private int part1() {
        int[] cyclesToTrack = {20, 60, 100, 140, 180, 220};
        int signalStrength = 0;

        for (int cycle : cyclesToTrack) {
            signalStrength += this.cycleEndValues.get(cycle - 1) * cycle;
        }

        return signalStrength;
    }

    private void part2() {
        char[][] screen = new char[6][40];
        for (char[] row : screen) {
            Arrays.fill(row, '.');
        }

        for (int i = 0; i < 240; i++) {
            int row = i / 40;
            int col = i % 40;

            int x = this.cycleEndValues.get(i);

            if (Math.abs(col - x) <= 1) {
                screen[row][col] = '#';
            }
        }

        for (char[] row : screen) {
            for (char c : row) {
                System.out.print(c);
            }
            System.out.println();
        }
    }

    private class Step {
        int cycles;
        int v;

        Step(int cycles, int v) {
            this.cycles = cycles;
            this.v = v;
        }
    }
}
