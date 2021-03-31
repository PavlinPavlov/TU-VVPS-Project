package tu.vvps.integration;

import tu.vvps.StatisticReport;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileUtils {

    public static StatisticReport loadResult(String resultFilePath) {
        try {
            assertNotNull(resultFilePath);

            List<String> lines = Files.lines(Paths.get(resultFilePath)).collect(Collectors.toList());

            Matcher matcher = Pattern.compile("([0-9]+[.])?[0-9]+").matcher(lines.get(0));

            List<Long> trends = new ArrayList<>();
            while (matcher.find()) {
                trends.add(Long.parseLong(matcher.group()));
            }

            double sd = Double.parseDouble(lines.get(1).split(": ")[1]);

            return new StatisticReport(0, trends, sd);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFullPath(String file) {
        try {
            URI uri = ClassLoader.getSystemResource(file).toURI();
            return Paths.get(uri).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
