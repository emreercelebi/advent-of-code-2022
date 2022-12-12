package day8;

import helpers.Helpers;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class TreetopTreeHouse {

    List<String> fileLines;
    List<List<Tree>> treeGrid;

    public TreetopTreeHouse() {
        URL path = TreetopTreeHouse.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        this.buildTreeGrid();
        this.calculateVisibilities();
        this.calculateViewingDistances();

        System.out.println("Treetop Tree House part 1: " + this.part1());
        System.out.println("Treetop Tree House part 2: " + this.part2());
    }

    private void buildTreeGrid() {
        this.treeGrid = new ArrayList<>();
        for (String line : this.fileLines) {
            List<Tree> treeRow = new ArrayList<>();
            for (char c : line.toCharArray()) {
                treeRow.add(new Tree(c - '0'));
            }
            this.treeGrid.add(treeRow);
        }
    }

    private void calculateVisibilities() {
        // view from top
        for (int col = 0; col < this.treeGrid.get(0).size(); col++) {
            int max = -1;
            for (int row = 0; row < this.treeGrid.size(); row++) {
                Tree tree = treeGrid.get(row).get(col);
                if (tree.height > max) {
                    tree.visibleFromTop = true;
                }
                max = Math.max(tree.height, max);
            }
        }

        // view from bottom
        for (int col = 0; col < this.treeGrid.get(0).size(); col++) {
            int max = -1;
            for (int row = this.treeGrid.size() - 1; row >= 0; row--) {
                Tree tree = treeGrid.get(row).get(col);
                if (tree.height > max) {
                    tree.visibleFromBottom = true;
                }
                max = Math.max(tree.height, max);
            }
        }

        // view from left
        for (int row = 0; row < this.treeGrid.size(); row++) {
            int max = -1;
            for (int col = 0; col < this.treeGrid.get(0).size(); col++) {
                Tree tree = treeGrid.get(row).get(col);
                if (tree.height > max) {
                    tree.visibleFromLeft = true;
                }
                max = Math.max(tree.height, max);
            }
        }

        // view from right
        for (int row = 0; row < this.treeGrid.size(); row++) {
            int max = -1;
            for (int col = this.treeGrid.get(0).size() - 1; col >= 0; col--) {
                Tree tree = treeGrid.get(row).get(col);
                if (tree.height > max) {
                    tree.visibleFromRight = true;
                }
                max = Math.max(tree.height, max);
            }
        }
    }

    private void calculateViewingDistances() {
        int[][] directions = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        for (int row = 0; row < this.treeGrid.size(); row++) {
            for (int col = 0; col < this.treeGrid.get(0).size(); col++) {
                Tree tree = this.treeGrid.get(row).get(col);
                int maxHeight = tree.height;

                for (int dirIndex = 0; dirIndex < directions.length; dirIndex++) {
                    int viewingDistance = 0;
                    int r = row, c = col;
                    int[] dir = directions[dirIndex];
                    r += dir[0];
                    c += dir[1];
                    while (r >= 0 && r < this.treeGrid.size() && c >= 0 && c < this.treeGrid.get(0).size()) {
                        int height = this.treeGrid.get(r).get(c).height;
                        viewingDistance++;
                        if (height >= maxHeight) {
                            break;
                        }
                        r += dir[0];
                        c += dir[1];
                    }
                    tree.viewingDistance[dirIndex] = viewingDistance;
                }
            }
        }
    }

    private int part1() {
        int count = 0;
        for (List<Tree> row : this.treeGrid) {
            for (Tree tree : row) {
                if (tree.isVisible()) {
                    count++;
                }
            }
        }
        return count;
    }

    private int part2() {
        int max = -1;
        for (List<Tree> row : this.treeGrid) {
            for (Tree tree : row) {
                max = Math.max(max, tree.scenicScore());
            }
        }
        return max;
    }

    private class Tree {
        int height;
        boolean visibleFromTop;
        boolean visibleFromLeft;
        boolean visibleFromBottom;
        boolean visibleFromRight;
        int[] viewingDistance; // top, left, bottom, right

        Tree(int height) {
            this.height = height;
            this.viewingDistance = new int[4];
        }

        boolean isVisible() {
            return this.visibleFromTop || this.visibleFromLeft || this.visibleFromRight || this.visibleFromBottom;
        }

        int scenicScore() {
            return Arrays.stream(this.viewingDistance).reduce(1, (a, b) -> a * b);
        }
    }
}
