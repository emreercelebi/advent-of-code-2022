package day5;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class SupplyStacks {

    List<String> fileLines;
    List<LinkedList<Character>> stacks;
    List<Move> moves;

    public SupplyStacks() {
        URL path = SupplyStacks.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        int stackEndIndex = this.buildStacks();
        this.buildMoves(stackEndIndex);

        // each of these modifies this.stacks, so they can't be run sequentially
//        System.out.println("Supply Stacks part 1: " + this.part1());
        System.out.println("Supply Stacks part 2: " + this.part2());
    }

    private String part1() {
        for (Move move : this.moves) {
            int amount = move.amount;
            int origin = move.origin;
            int destination = move.destination;

            for (int i = 0; i < amount; i++) {
                this.stacks.get(destination).add(this.stacks.get(origin).removeLast());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.stacks.size(); i++) {
            sb.append(this.stacks.get(i).peekLast());
        }

        return sb.toString();
    }

    private String part2() {
        for (Move move : this.moves) {
            int amount = move.amount;
            int origin = move.origin;
            int destination = move.destination;

            Stack<Character> temp = new Stack<>();
            for (int i = 0; i < amount; i++) {
                temp.push(this.stacks.get(origin).removeLast());
            }

            while (!temp.isEmpty()) {
                this.stacks.get(destination).addLast(temp.pop());
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.stacks.size(); i++) {
            sb.append(this.stacks.get(i).peekLast());
        }

        return sb.toString();
    }

    private int buildStacks() {
        this.stacks = new ArrayList<>();
        int numStacks = (this.fileLines.get(0).length() + 1) / 4;

        for (int i = 0; i < numStacks; i++) {
            this.stacks.add(new LinkedList<>());
        }

        int index = 0;
        while (!this.fileLines.get(index).contains(" 1 ")) {
            String line = this.fileLines.get(index);
            for (int i = 1; i < line.length(); i += 4) {
                char c = line.charAt(i);
                if (Character.isUpperCase(c)) {
                    this.stacks.get((i - 1) / 4).addFirst(c);
                }
            }
            index++;
        }

        return index + 2;
    }

    private void buildMoves(int index) {
        this.moves = new ArrayList<>();

        for (int i = index; i < this.fileLines.size(); i++) {
            String line = this.fileLines.get(i);
            line = line.replaceAll("[^0-9]", " ");
            line = line.trim();
            String[] nums = line.split("\\s+");
            this.moves.add(new Move(
                    Integer.parseInt(nums[0]),
                    Integer.parseInt(nums[1]) - 1,
                    Integer.parseInt(nums[2]) - 1));
        }
    }

    private class Move {
        int amount;
        int origin;
        int destination;

        public Move(int amount, int origin, int destination) {
            this.amount = amount;
            this.origin = origin;
            this.destination = destination;
        }
    }
}
