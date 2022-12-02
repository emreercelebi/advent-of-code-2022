package day2;

import helpers.Helpers;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RockPaperScissors {
    private static final int LOSS = 0;
    private static final int DRAW = 3;
    private static final int WIN = 6;
    enum Choice {
        ROCK,
        PAPER,
        SCISSORS
    }
    List<String> fileLines;
    Map<Character, Integer> yourCharToPoints;
    Map<Character, Choice> opponentCharToChoice;
    Map<Choice, Integer> choiceToPoints;
    Map<Character, Integer> yourCharToResult;

    public RockPaperScissors() {
        URL path = RockPaperScissors.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        this.buildYourCharToPoints();
        this.buildOpponentCharToChoice();
        this.buildChoiceToPoints();
        this.buildYourCharToResult();

        System.out.println("RockPaperScissors part 1: " + this.part1());
        System.out.println("RockPaperScissors part 2: " + this.part2());
    }

    private int part1() {
        int score = 0;
        for (String line : this.fileLines) {
            char opponentChoice = line.charAt(0);
            char yourChoice = line.charAt(2);

            score += this.rpsRound(opponentChoice, yourChoice) + this.yourCharToPoints.get(yourChoice);
        }
        return score;
    }

    private int part2() {
        int score = 0;
        for (String line : this.fileLines) {
            char opponentChar = line.charAt(0);
            char yourChar = line.charAt(2);
            int result = this.yourCharToResult.get(yourChar);
            score += result + this.choiceToPoints.get(this.yourChoiceGivenResult(this.opponentCharToChoice.get(opponentChar), result));
        }
        return score;
    }

    private void buildYourCharToResult() {
        this.yourCharToResult = new HashMap<>();
        this.yourCharToResult.put('X', LOSS);
        this.yourCharToResult.put('Y', DRAW);
        this.yourCharToResult.put('Z', WIN);
    }

    private void buildChoiceToPoints() {
        this.choiceToPoints = new HashMap<>();
        choiceToPoints.put(Choice.ROCK, 1);
        choiceToPoints.put(Choice.PAPER, 2);
        choiceToPoints.put(Choice.SCISSORS, 3);
    }

    private void buildOpponentCharToChoice() {
        this.opponentCharToChoice = new HashMap<>();
        this.opponentCharToChoice.put('A', Choice.ROCK);
        this.opponentCharToChoice.put('B', Choice.PAPER);
        this.opponentCharToChoice.put('C', Choice.SCISSORS);
    }

    private void buildYourCharToPoints() {
        this.yourCharToPoints = new HashMap<>();
        this.yourCharToPoints.put('X', 1);
        this.yourCharToPoints.put('Y', 2);
        this.yourCharToPoints.put('Z', 3);
    }

    private int rpsRound(char opponentChoice, char yourChoice) {
        switch(yourChoice) {
            case ('X'):
                switch (opponentChoice) {
                    case ('A'):
                        return DRAW;
                    case ('B'):
                        return LOSS;
                    case ('C'):
                        return WIN;
                }
            case ('Y'):
                switch (opponentChoice) {
                    case ('A'):
                        return WIN;
                    case ('B'):
                        return DRAW;
                    case ('C'):
                        return LOSS;
                }
            case ('Z'):
                switch (opponentChoice) {
                    case ('A'):
                        return LOSS;
                    case ('B'):
                        return WIN;
                    case ('C'):
                        return DRAW;
                }
            default:
        }
        return -1;
    }

    private Choice yourChoiceGivenResult(Choice opponentChoice, int result) {
        if (result == DRAW) {
            return opponentChoice;
        }
        if (opponentChoice == Choice.ROCK) {
            switch (result) {
                case (WIN):
                    return Choice.PAPER;
                default:
                    return Choice.SCISSORS;
            }
        } else if (opponentChoice == Choice.PAPER) {
            switch (result) {
                case (WIN):
                    return Choice.SCISSORS;
                default:
                    return Choice.ROCK;
            }
        } else {
            switch (result) {
                case (WIN):
                    return Choice.ROCK;
                default:
                    return Choice.PAPER;
            }
        }
    }
}
