package tu.vvps;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ColumnTupleTest {
    private static final long USER_ID = 3439;
    private static final long PAGE_ID = 285;
    private static final long COURSE_ID = 5135;
    private static final String DESCRIPTION = String.format("The user with id '%d' updated the page with id '%d' for the wiki with course module id '%d'.",
            USER_ID, PAGE_ID, COURSE_ID);
    private static final String EVENT_NAME = "Wiki page updated";

    @Test
    void givenLine_shenParsing_shouldUseCorrectValues() {
        String linePlaceholder = "\"19/02/21, 13:08\",Wiki: Избор нa тема за проект (40 т.),Wiki,%s,%s";
        String line = String.format(linePlaceholder, EVENT_NAME, DESCRIPTION);

        ColumnTuple tuple = ColumnTuple.parse(line);

        assertEquals(EVENT_NAME, tuple.getEventName());
        assertEquals(DESCRIPTION, tuple.getDescription());
    }

    @Test
    void givenTuple_shenExtracting_shouldExtractCorrectValues() {
        ColumnTuple columnTuple = new ColumnTuple();
        columnTuple.setEventName(EVENT_NAME);
        columnTuple.setDescription(DESCRIPTION);

        NumbersDTO numbersDTO = ColumnTuple.extractNumbersFromDescription(columnTuple);

        assertEquals(USER_ID, numbersDTO.getUserId());
        assertEquals(PAGE_ID, numbersDTO.getPageId());
        assertEquals(COURSE_ID, numbersDTO.getCourseModuleId());
    }
}
