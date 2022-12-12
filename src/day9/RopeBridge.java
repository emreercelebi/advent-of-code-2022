package day9;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RopeBridge {

    List<String> fileLines;
    List<Move> moves;

    public RopeBridge() {
        URL path = RopeBridge.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);
        this.buildMoves();

        System.out.println("Rope Bridge part 1: " + this.part1());
        System.out.println("Rope Bridge part 2: " + this.part2());
    }

    private void buildMoves() {
        this.moves = new ArrayList<>();
        this.fileLines.forEach(s -> {
            String[] parts = s.split(" ");
            this.moves.add(new Move(parts[0].charAt(0), Integer.parseInt(parts[1])));
        });
    }

    private int part1() {
        Map<Integer, Set<Integer>> visited = new HashMap<>();
        visited.put(0, new HashSet<>());
        visited.get(0).add(0);;
        int headX = 0, headY = 0, tailX = 0, tailY = 0;
        for (Move move : this.moves) {
            if (move.direction == Direction.UP) {
                headY += move.distance;
            } else if (move.direction == Direction.DOWN) {
                headY -= move.distance;
            } else if (move.direction == Direction.LEFT) {
                headX -= move.distance;
            } else {
                headX += move.distance;
            }

            if (headX == tailX) {
                while (Math.abs(headY - tailY) > 1) {
                    tailY += headY - tailY > 0 ? 1 : -1;
                    visited.putIfAbsent(tailX, new HashSet<>());
                    visited.get(tailX).add(tailY);
                }
            } else if (headY == tailY) {
                while (Math.abs(headX - tailX) > 1) {
                    tailX += headX - tailX > 0 ? 1 : -1;
                    visited.putIfAbsent(tailX, new HashSet<>());
                    visited.get(tailX).add(tailY);
                }
            } else {
                if (Math.abs(headX - tailX) > 1) {
                    tailY += headY - tailY > 0 ? 1 : -1;
                    while (Math.abs(headX - tailX) > 1) {
                        tailX += headX - tailX > 0 ? 1 : -1;
                        visited.putIfAbsent(tailX, new HashSet<>());
                        visited.get(tailX).add(tailY);
                    }
                } else if (Math.abs(headY - tailY) > 1) {
                    tailX += headX - tailX > 0 ? 1 : -1;
                    while (Math.abs(headY - tailY) > 1) {
                        tailY += headY - tailY > 0 ? 1 : -1;
                        visited.putIfAbsent(tailX, new HashSet<>());
                        visited.get(tailX).add(tailY);
                    }
                }
            }
        }

        int spacesVisited = 0;
        for (Set<Integer> spaces : visited.values()) {
            spacesVisited += spaces.size();
        }

        return spacesVisited;
    }

    private int part2() {
        return -1;
    }

    enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    private class Move {
        Direction direction;
        int distance;

        Move(char dir, int distance) {
            switch (dir) {
                case 'U':
                    this.direction = Direction.UP;
                    break;
                case 'D':
                    this.direction = Direction.DOWN;
                    break;
                case 'L':
                    this.direction = Direction.LEFT;
                    break;
                default:
                    this.direction = Direction.RIGHT;
            }
            this.distance = distance;
        }
    }
}
