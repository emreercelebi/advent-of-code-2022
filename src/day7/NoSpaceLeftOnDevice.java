package day7;

import helpers.Helpers;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoSpaceLeftOnDevice {
    List<String> fileLines;
    Directory root;

    private static final int TOTAL_DISK_SPACE = 70000000;
    private static final int REQUIRED_DISK_SPACE = 30000000;

    public NoSpaceLeftOnDevice() {
        URL path = NoSpaceLeftOnDevice.class.getResource(Helpers.FILE_NAME);
        this.fileLines = Helpers.getFileLines(path);

        this.root = new Directory("/");
        this.buildFileSystem();
        this.root.calculateTotalSize();

        System.out.println("No Space Left On Device part 1: " + this.part1());
        System.out.println("No Space Left On Device part 2: " + this.part2());
    }

    private void buildFileSystem() {
        Directory currentDir = this.root;
        for (int i = 1; i < this.fileLines.size(); i++) {
            String line = this.fileLines.get(i);
            if (line.contains("$")) { // command
                if (line.contains("cd")) {
                    String dirName = line.split(" ")[2];
                    if (dirName.equals("..")) {
                        currentDir = currentDir.parent;
                    } else {
                        currentDir = currentDir.getSubdirectory(dirName);
                    }
                }
            } else if (line.contains("dir")) { // listing a directory
                String[] dirParts = line.split(" ");
                currentDir.addSubdirectory(dirParts[1]);
            } else { // listing a file
                String[] fileParts = line.split(" ");
                currentDir.addFile(fileParts[1], Integer.parseInt(fileParts[0]));
            }
        }
    }

    private int part1() {
        int maxSize = 100000;
        List<Integer> sizesUnderMax = new ArrayList<>();
        this.root.addToListIfSizeLessThan(sizesUnderMax, maxSize);

        return sizesUnderMax.stream().reduce(0, Integer::sum);
    }

    private int part2() {
        int currentAvailableSpace = TOTAL_DISK_SPACE - this.root.totalSize;
        int minimumNeededToDelete = REQUIRED_DISK_SPACE - currentAvailableSpace;
        List<Integer> sizesGreaterThanMin = new ArrayList<>();
        this.root.addToListIfSizeGreaterThan(sizesGreaterThanMin, minimumNeededToDelete);

        Collections.sort(sizesGreaterThanMin);
        return sizesGreaterThanMin.get(0);
    }

    private class Directory {
        Directory parent;
        Map<String, Directory> subdirectories;
        Map<String, Integer> files;
        String name;
        int totalSize;

        Directory(String name) {
            this.name = name;
            this.subdirectories = new HashMap<>();
            this.files = new HashMap<>();
        }

        Directory(String name, Directory parent) {
            this(name);
            this.parent = parent;
        }

        void addSubdirectory(String name) {
            this.subdirectories.put(name, new Directory(name, this));
        }

        void addFile(String name, int size) {
            this.files.put(name, size);
        }

        Directory getSubdirectory(String name) {
            return this.subdirectories.get(name);
        }

        int calculateTotalSize() {
            int totalSize = 0;
            for (Directory dir : this.subdirectories.values()) {
                totalSize += dir.calculateTotalSize();
            }
            for (int fileSize : this.files.values()) {
                totalSize += fileSize;
            }
            this.totalSize = totalSize;
            return totalSize;
        }

        void addToListIfSizeLessThan(List<Integer> list, int maxSize) {
            if (this.totalSize <= maxSize) {
                list.add(this.totalSize);
            }
            for (Directory dir : this.subdirectories.values()) {
                dir.addToListIfSizeLessThan(list, maxSize);
            }
        }

        void addToListIfSizeGreaterThan(List<Integer> list, int minSize) {
            if (this.totalSize >= minSize) {
                list.add(this.totalSize);
            }
            for (Directory dir : this.subdirectories.values()) {
                dir.addToListIfSizeGreaterThan(list, minSize);
            }
        }
    }
}
