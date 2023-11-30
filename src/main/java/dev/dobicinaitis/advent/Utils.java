package dev.dobicinaitis.advent;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Utils {

    private Utils() {
        throw new RuntimeException("Utility class");
    }

    public static List<String> getFileContent(String filename){
        try {
            Path path = Paths.get("src/main/resources/inputs/" + filename);
            return Files.readAllLines(path);
        } catch (IOException e) {
            log.error("Could not read file: {}", filename);
            e.printStackTrace();
        }
        return null;
    }

    public static List<Integer> getFileContentAsIntegerList(String filename){
        return Arrays.stream(Objects.requireNonNull(Utils.getFileContentAsString(filename)).split(","))
                .map(Integer::parseInt)
                .toList();
    }

    public static String getFileContentAsString(String filename){
        try {
            Path path = Paths.get("src/main/resources/inputs/" + filename);
            return Files.readString(path);
        } catch (IOException e) {
            log.error("Could not read file: {}", filename, e);
            e.printStackTrace();
        }
        return null;
    }

    public static int extractFirstNumberFromString(String input){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(input);

        if(matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        throw new RuntimeException("Input [{}] does not contain any numbers");
    }
}
