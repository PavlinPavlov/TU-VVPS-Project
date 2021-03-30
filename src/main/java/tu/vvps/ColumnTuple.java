package tu.vvps;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColumnTuple {

    private static final String DELIMITER = ",";
    private static final String NUMBER_REGEX = "\\d+";

    private String eventName;
    private String description;

    public static ColumnTuple parse(String line) {
        ColumnTuple entry = new ColumnTuple();

        String[] components = line.split(DELIMITER);

        entry.setEventName(components[4]);
        entry.setDescription(components[5]);

        return entry;
    }

    public static NumbersDTO extractNumbersFromDescription(ColumnTuple tuple) {
        Matcher matcher = Pattern.compile(NUMBER_REGEX).matcher(tuple.description);

        int[] extractedNumbers = new int[3];

        for (int i = 0; i < extractedNumbers.length; i++) {
            if (matcher.find()) {
                extractedNumbers[i] = Integer.parseInt(matcher.group());
            }
        }

        return new NumbersDTO(
                extractedNumbers[0],
                extractedNumbers[1],
                extractedNumbers[2]
        );
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CsvColumnEntry{" +
                "eventName='" + eventName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
