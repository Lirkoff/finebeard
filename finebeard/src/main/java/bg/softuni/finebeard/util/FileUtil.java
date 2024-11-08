package bg.softuni.finebeard.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class FileUtil {

    public static String readFileFromClasspath(String relativePath) {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(FileUtil.class.getClassLoader().getResourceAsStream(relativePath), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}