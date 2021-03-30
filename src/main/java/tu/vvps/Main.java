package tu.vvps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Function;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String CATEGORY = "Wiki page updated";
    private static final String FILE = ".\\data\\Logs_Course-A_StudentsActivities.csv";

    public static void main(String[] args) {
        Menu menu = new Menu(new ScannerWrapper(new Scanner(System.in)));
        String filePath = args.length > 0 ? args[0] : FILE;
        new Main().start(menu, filePath);
    }

    public void start(Menu menu, String filePath) {

        File inputFile = new File(filePath);

        if (!inputFile.exists()) {
            String errorMessage = String.format("File: %s was not found!", filePath);
            Exception exc = new IllegalArgumentException(errorMessage);
            logger.error(errorMessage, exc);
            System.exit(1);
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

            long stop = System.nanoTime();

            Statistic statistic = new Statistic();

            logger.info("Fin: " + (stop - start));
            logger.info("Trend: " + Arrays.toString(statistic.findTrend(idArray)));
            logger.info("SD:    " + statistic.calculateSD(idArray));

        } catch (IOException e) {
            logger.error("Error in ingesting file:", e);
        }
    }
}
