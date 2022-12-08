package dev.dobicinaitis.advent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class Day7 extends Puzzle {

    private static List<Integer> allDirectorySizes = new ArrayList<>();

    public Integer getFirstPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final Directory filesystemLayout = getFilesystemLayout(input);
        //log.debug("Filesystem: \n{}", visualizeDirectory(filesystem, 0));
        allDirectorySizes.clear();
        calculateAllDirectorySizes(filesystemLayout);
        return allDirectorySizes.stream()
                .filter(size -> size <= 100000)
                .mapToInt(Integer::intValue)
                .sum();

    }

    public Integer getSecondPuzzleSolution(String filename) {
        final List<String> input = Utils.getFileContent(filename);
        final Directory filesystemLayout = getFilesystemLayout(input);
        allDirectorySizes.clear();
        calculateAllDirectorySizes(filesystemLayout);
        int spaceRequiredToRunUpdate = (70000000 - getTotalUsedSpace() - 30000000) * -1;
        return findSizeOfSmallestDirectoryToDelete(spaceRequiredToRunUpdate);
    }

    private Directory getFilesystemLayout(List<String> terminalHistory) {
        final Directory rootDirectory = Directory.builder().name("/").path("/").build();
        Directory currentDirectory = rootDirectory;

        for (String line : terminalHistory) {
            if (isCommand(line)) {
                final String[] commandComponents = line.split(" "); // $ cd /
                final String command = commandComponents[1];
                switch (command) {
                    case "cd": {
                        final String argument = commandComponents[2];
                        if ("/".equals(argument)) {
                            currentDirectory = rootDirectory;
                        } else if ("..".equals(argument)) {
                            // cd back to parent directory
                            final String parentPath = currentDirectory.getPath().substring(0, currentDirectory.getPath().lastIndexOf("/"));
                            currentDirectory = getDirectory(rootDirectory, parentPath);
                        } else {
                            // cd into subdirectory
                            currentDirectory = getDirectory(currentDirectory, argument);
                        }
                        break;
                    }
                    case "ls":
                        // will process command output when we get to it
                        break;
                    default:
                        throw new RuntimeException("Unknown command: " + command);
                }
            }
            // ls output for a file or dir
            else {
                if (isDirectory(line)) {
                    final String directoryName = line.substring(4);
                    final String pathSeparator = "/".equals(currentDirectory.path) ? "" : "/";
                    final Directory newDirectory = Directory.builder()
                            .name(directoryName)
                            .path(currentDirectory.path + pathSeparator + directoryName)
                            .build();
                    currentDirectory.subDirectories.add(newDirectory);
                } else {
                    // let's assume that outputs contain only files and directories, so this one is a file
                    final String[] fileParts = line.split(" ");
                    final String filename = fileParts[1];
                    final int size = Integer.valueOf(fileParts[0]);
                    currentDirectory.files.add(new File(filename, size));
                }
            }
        }
        return rootDirectory;
    }

    private Directory getDirectory(Directory rootDirectory, String path) {
        Directory targetDirectory = rootDirectory;
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (!path.isEmpty()) {
            for (String directoryName : path.split("/")) {
                targetDirectory = targetDirectory.subDirectories.stream()
                        .filter(dir -> directoryName.equals(dir.name))
                        .findFirst()
                        .get();
            }
        }
        return targetDirectory;
    }

    private int calculateAllDirectorySizes(Directory rootDirectory) {
        int size = 0;
        // files in this directory
        for (File file : rootDirectory.getFiles()) {
            size += file.size();
        }
        // files in subdirectories
        for (Directory subDirectory : rootDirectory.getSubDirectories()) {
            size += calculateAllDirectorySizes(subDirectory);
        }
        allDirectorySizes.add(size);
        return size;
    }

    private Integer findSizeOfSmallestDirectoryToDelete(int spaceRequiredToRunUpdate) {
        Collections.sort(allDirectorySizes);
        for (int directorySize : allDirectorySizes) {
            if (directorySize >= spaceRequiredToRunUpdate) {
                return directorySize;
            }
        }
        throw new RuntimeException("Did not find a directory to delete");
    }

    private int getTotalUsedSpace() {
        return allDirectorySizes.stream()
                .mapToInt(Integer::intValue)
                .max()
                .getAsInt();
    }

    private boolean isDirectory(String line) {
        return line.startsWith("dir ");
    }

    private boolean isCommand(String line) {
        return line.startsWith("$");
    }

    private String visualizeDirectory(Directory directory, int level) {
        final int paddingLengthFile = (level + 1) * 2;
        final int paddingLengthDir = level * 2;
        String output = getPadding(paddingLengthDir) + directory.getName() + " (dir)\n";
        // output dirs
        for (Directory subDirectory : directory.getSubDirectories()) {
            output += visualizeDirectory(subDirectory, level + 1);
        }
        // output files
        for (File file : directory.getFiles()) {
            output += getPadding(paddingLengthFile) + file.name() + " (file, size=" + file.size() + ")\n";
        }
        return output;
    }

    private String getPadding(int level) {
        return String.format("%1$" + (level + 3) + "s", " - ");
    }

    public record File(String name, int size) {
    }

    @Data
    @Builder
    public static class Directory {
        private String name;
        private String path;
        @Builder.Default
        private List<Directory> subDirectories = new ArrayList<>();
        @Builder.Default
        private List<File> files = new ArrayList<>();
    }
}
