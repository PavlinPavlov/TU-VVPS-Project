package tu.vvps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String CATEGORY = "Wiki page updated";
    private static final String DEFAULT_FILE = ".\\data\\Logs_Course-A_StudentsActivities.csv";

    public static void main(String[] args) {
        Menu menu = new Menu(new ScannerWrapper(new Scanner(System.in)));

        String filePath = args.length > 0 ? args[0] : DEFAULT_FILE;

        try {
            StatisticReport report = new Main().analyze(menu, filePath);
            logger.info("Duration: {}", report.getDuration());
            logger.info("Trend:    {}", report.getTrends());
            logger.info("SD:       {}", report.getDuration());
        } catch (Exception e) {
            logger.error("File not found: ", e);
        }
    }

    public StatisticReport analyze(Menu menu, String filePath) throws Exception {

        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            String errorMessage = String.format("File: %s was not found!", filePath);
            throw new IllegalArgumentException(errorMessage);
        }

        ID typeOfId = menu.getInputForData();
        Function<NumbersDTO, Long> idExtraction;

        switch (typeOfId) {
            case USER:
                idExtraction = NumbersDTO::getUserId;
                break;
            case PAGE:
                idExtraction = NumbersDTO::getPageId;
                break;
            case COURSE_MODULE:
                idExtraction = NumbersDTO::getCourseModuleId;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + typeOfId);
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)))) {

            long start = System.nanoTime();

            Long[] idArray = br.lines()
                    .skip(1)
                    .map(ColumnTuple::parse)
                    .filter(csvEntry -> csvEntry.getEventName().equals(CATEGORY))
                    .map(ColumnTuple::extractNumbersFromDescription)
                    .map(idExtraction)
                    .toArray(Long[]::new);

            Statistic statistic = new Statistic();

            double standardDeviation = statistic.calculateSD(idArray);
            long[] trends = statistic.findTrend(idArray);

            List<Long> trendList = Arrays.stream(trends).boxed().collect(Collectors.toList());

            long stop = System.nanoTime();
            return new StatisticReport((stop - start), trendList, standardDeviation);

        } catch (IOException e) {
            logger.error("Error in ingesting file:", e);
            return new StatisticReport(0, Collections.emptyList(), 0.0);
        }
    }
}
