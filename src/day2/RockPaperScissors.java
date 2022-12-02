package day2;

import helpers.Helpers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RockPaperScissors {
    List<String> fileLines;
    Map<Character, Integer> yourChoiceToPoints;

    public RockPaperScissors() {
        URL path = RockPaperScissors.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        this.buildYourChoiceToPoints();

        System.out.println("RockPaperScissors part 1: " + this.part1());
//        System.out.println("RockPaperScissors part 2: " + this.part2());
    }

    private int part1() {
        int score = 0;
        for (String line : this.fileLines) {
            char opponentChoice = line.charAt(0);
            char yourChoice = line.charAt(2);

            score += this.rpsRound(opponentChoice, yourChoice) + this.yourChoiceToPoints.get(yourChoice);
        }
        return score;
    }

    private void buildYourChoiceToPoints() {
        this.yourChoiceToPoints = new HashMap<>();
        this.yourChoiceToPoints.put('X', 1);
        this.yourChoiceToPoints.put('Y', 2);
        this.yourChoiceToPoints.put('Z', 3);
    }

    private int rpsRound(char opponentChoice, char yourChoice) {
        switch(yourChoice) {
            case ('X'):
                switch (opponentChoice) {
                    case ('A'):
                        return 3;
                    case ('B'):
                        return 0;
                    case ('C'):
                        return 6;
                }
            case ('Y'):
                switch (opponentChoice) {
                    case ('A'):
                        return 6;
                    case ('B'):
                        return 3;
                    case ('C'):
                        return 0;
                }
            case ('Z'):
                switch (opponentChoice) {
                    case ('A'):
                        return 0;
                    case ('B'):
                        return 6;
                    case ('C'):
                        return 3;
                }
            default:
        }
        return -1;
    }
}
