package dev.dobicinaitis.advent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

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

    public static String getFileContentAsString(String filename){
        try {
            Path path = Paths.get("src/main/resources/inputs/" + filename);
            return Files.readString(path);
        } catch (IOException e) {
            log.error("Could not read file: {}", filename);
            e.printStackTrace();
        }
        return null;
    }
}
