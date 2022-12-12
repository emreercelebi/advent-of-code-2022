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

    private int simulateMovements(int knots) {
        Map<Integer, Set<Integer>> visited = new HashMap<>();
        visited.put(0, new HashSet<>());
        visited.get(0).add(0);
        int[][] positions = new int[knots][2];

        for (Move move : this.moves) {
            for (int i = 0; i < move.distance; i++) {
                if (move.direction == Direction.UP) {
                    positions[0][1]++;
                } else if (move.direction == Direction.DOWN) {
                    positions[0][1]--;
                } else if (move.direction == Direction.LEFT) {
                    positions[0][0]--;
                } else {
                    positions[0][0]++;
                }
                runMovement(1, positions, visited);
            }
        }

        int spacesTailVisited = 0;
        for (Set<Integer> spaces : visited.values()) {
            spacesTailVisited += spaces.size();
        }

        return spacesTailVisited;
    }

    private void runMovement(int currentKnot, int[][] positions, Map<Integer, Set<Integer>> visited) {
        int prevX = positions[currentKnot - 1][0],
                prevY = positions[currentKnot - 1][1],
                currX = positions[currentKnot][0],
                currY = positions[currentKnot][1];
        boolean isTail = currentKnot == positions.length - 1;
        if (prevX == currX) {
            while (Math.abs(prevY - currY) > 1) {
                currY += prevY - currY > 0 ? 1 : -1;
                if (isTail) {
                    visited.putIfAbsent(currX, new HashSet<>());
                    visited.get(currX).add(currY);
                }
            }
        } else if (prevY == currY) {
            while (Math.abs(prevX - currX) > 1) {
                currX += prevX - currX > 0 ? 1 : -1;
                if (isTail) {
                    visited.putIfAbsent(currX, new HashSet<>());
                    visited.get(currX).add(currY);
                }
            }
        } else {
            while (Math.abs(prevX - currX) > 1 && Math.abs(prevY - currY) > 1) {
                currX += prevX - currX > 0 ? 1 : -1;
                currY += prevY - currY > 0 ? 1 : -1;
                if (isTail) {
                    visited.putIfAbsent(currX, new HashSet<>());
                    visited.get(currX).add(currY);
                }
            }
            if (Math.abs(prevX - currX) > 1) {
                currY += prevY - currY > 0 ? 1 : -1;
                while (Math.abs(prevX - currX) > 1) {
                    currX += prevX - currX > 0 ? 1 : -1;
                    if (isTail) {
                        visited.putIfAbsent(currX, new HashSet<>());
                        visited.get(currX).add(currY);
                    }
                }
            } else if (Math.abs(prevY - currY) > 1) {
                currX += prevX - currX > 0 ? 1 : -1;
                while (Math.abs(prevY - currY) > 1) {
                    currY += prevY - currY > 0 ? 1 : -1;
                    if (isTail) {
                        visited.putIfAbsent(currX, new HashSet<>());
                        visited.get(currX).add(currY);
                    }
                }
            }
        }

        positions[currentKnot][0] = currX;
        positions[currentKnot][1] = currY;

        if (currentKnot < positions.length - 1) {
            runMovement(currentKnot + 1, positions, visited);
        }
    }

    private int part1() {
        return simulateMovements(2);
    }

    private int part2() {
        return simulateMovements(10);
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
