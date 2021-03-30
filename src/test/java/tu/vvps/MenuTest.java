package tu.vvps;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MenuTest {

    @ParameterizedTest
    @CsvSource({"0, USER",
                "1, PAGE",
                "-1, COURSE_MODULE"
    })
    void givenUserInput_whenChoosingData_shouldChooseCorrect(int input, ID output) {
        // Set up
        ScannerWrapper scanner = Mockito.mock(ScannerWrapper.class);
        Menu menu = new Menu(scanner);

        when(scanner.readInt()).thenReturn(input);

        ID result = menu.getInputForData();

        assertEquals(output, result);
    }
}
