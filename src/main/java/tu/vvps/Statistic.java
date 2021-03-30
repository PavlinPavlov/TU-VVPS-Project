package tu.vvps;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Statistic {

    public double calculateSD(Long[] numArray) {

        if (numArray.length < 2) return 0.0;

        double sum = Arrays.stream(numArray).reduce(Long::sum).orElse(0L);
        int length = numArray.length;
        double mean = sum / length;

        double standardDeviation = Arrays.stream(numArray)
                .mapToDouble(num -> Math.pow(num - mean, 2))
                .reduce(Double::sum)
                .orElse(0.0);

        return Math.sqrt(standardDeviation / length);
    }

    public long[] findTrend(Long[] numArray) {
        Map<Long, Long> occurrences = new HashMap<>();

        for (long num : numArray) {
            if (!occurrences.containsKey(num)) {
                occurrences.put(num, 0L);
            }
            occurrences.put(num, occurrences.get(num) + 1);
        }

        long maxOccurrence = occurrences
                .values()
                .stream()
                .max(Long::compareTo)
                .orElse(-1L);

        return occurrences
                .entrySet()
                .stream()
                .filter(doubleIntegerEntry -> doubleIntegerEntry.getValue().equals(maxOccurrence))
                .mapToLong(Map.Entry::getKey)
                .toArray();
    }
}
