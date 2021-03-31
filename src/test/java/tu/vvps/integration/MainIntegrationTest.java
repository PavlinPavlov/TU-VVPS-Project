package tu.vvps.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;
import tu.vvps.Main;
import tu.vvps.Menu;
import tu.vvps.ScannerWrapper;
import tu.vvps.StatisticReport;

import java.io.File;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class MainIntegrationTest {

    private ScannerWrapper scanner;
    private Menu menu;
    private Main main;

    @BeforeEach
    private void setUp() {
        scanner = Mockito.mock(ScannerWrapper.class);
        menu = new Menu(scanner);
        main = new Main();
    }

    @ParameterizedTest
    @CsvSource({"TC-2-1.csv, 0, TC-2-1-Results.txt, true, true",
            "TC-2-2.csv, 0, TC-2-2-Results.txt, true, true",
            "TC-3-1.csv, 1, TC-3-1-Results.txt, true, false",
            "TC-3-2.csv, 1, TC-3-2-Results.txt, true, false",
            "TC-4-1.csv, -1, TC-4-1-Results.txt, false, true",
            "TC-5-x.csv, 0, TC-5-1-Results.txt, true, true",
            "TC-5-x.csv, 1, TC-5-2-Results.txt, true, true",
            "TC-5-x.csv, -1, TC-5-3-Results.txt, true, true"
    })
    void givenTestFile_whenIngesting_shouldFilterLines(String inputFile,
                                                       int userChoice,
                                                       String resultFile,
                                                       boolean testTrend,
                                                       boolean testSD) throws Exception {

        String testInputFilePath = FileUtils.getFullPath(inputFile);
        String resultFilePath = FileUtils.getFullPath(resultFile);

        assertFilePath(testInputFilePath);
        assertFilePath(resultFilePath);

        when(scanner.readInt()).thenReturn(userChoice);

        StatisticReport receivedReport = main.analyze(menu, testInputFilePath);
        StatisticReport expectedReport = FileUtils.loadResult(resultFilePath);

        assertNotNull(expectedReport);

        if (testTrend) {
            assertEquals(expectedReport.getTrends().stream().sorted().collect(Collectors.toList()),
                    receivedReport.getTrends().stream().sorted().collect(Collectors.toList()));
        }
        if (testSD) {
            assertEquals(expectedReport.getStandardDeviation(), receivedReport.getStandardDeviation());
        }
    }

    private void assertFilePath(String testFilePath) {
        assertNotNull(testFilePath);
        assertTrue(new File(testFilePath).exists());
    }
}
