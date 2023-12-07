package dev.dobicinaitis.advent;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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

    @SneakyThrows
    public static List<String> getFileContent(final String filename) {
        final Path path = Paths.get("src/main/resources/inputs/" + filename);
        return Files.readAllLines(path);
    }

    public static List<Integer> getFileContentAsIntegerList(final String filename, final String delimiter) {
        return Arrays.stream(Objects.requireNonNull(Utils.getFileContentAsString(filename)).split(delimiter))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .toList();
    }

    public static List<Integer> parseToIntegerList(final String input, final String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .filter(s -> !s.isBlank())
                .map(Integer::parseInt)
                .toList();
    }

    public static List<Long> parseToLongList(final String input, final String delimiter) {
        return Arrays.stream(input.split(delimiter))
                .filter(s -> !s.isBlank())
                .map(Long::parseLong)
                .toList();
    }

    @SneakyThrows
    public static String getFileContentAsString(final String filename) {
        final Path path = Paths.get("src/main/resources/inputs/" + filename);
        return Files.readString(path);
    }

    public static int extractFirstNumberFromString(final String input) {
        final Pattern pattern = Pattern.compile("\\d+");
        final Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }

        throw new RuntimeException("Input [{}] does not contain any numbers");
    }
}
