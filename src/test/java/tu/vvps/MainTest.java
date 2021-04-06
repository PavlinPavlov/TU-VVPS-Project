package tu.vvps;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tu.vvps.Main;
import tu.vvps.Menu;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {

    private Menu menuSpy;
    private Main main;

    @BeforeEach
    private void setUp() {
        menuSpy = Mockito.mock(Menu.class);
        main = new Main();
    }

    @Test
    void givenNonexistentFilePath_whenReading_shouldThrow() {
        String filePath = "nonexistent.txt";

        String errorMessage = String.format("File: %s was not found!", filePath);

        Exception exc = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            main.analyze(menuSpy, filePath);
        });

        assertEquals(errorMessage, exc.getMessage());
    }
}
