package tu.vvps;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class StatisticTest {

    private static final long TREND_1 = 500L;
    private static final long TREND_2 = 5555L;
    private static final Long[] numbers = {1000L, 2000L, 3000L, 4000L, 5000L, 6000L, 7000L};

    private static Statistic statistic;

    @BeforeEach
    private void setUp() {
        statistic = new Statistic();
    }

    @Test
    void givenArrayOfNumbersWithOneTrend_whenFindingTrend_shouldReturnOne() {
        List<Long> newNumbers = Arrays.stream(numbers).collect(Collectors.toList());

        assertFalse(newNumbers.contains(TREND_1));

        newNumbers.add(TREND_1);
        newNumbers.add(TREND_1);

        long[] trend = statistic.findTrend(newNumbers.toArray(new Long[0]));

        assertEquals(1, trend.length);
        assertEquals(TREND_1, trend[0]);
    }

    @Test
    void givenArrayOfNumbersWithTwoTrends_whenFindingTrend_shouldReturnTwo() {
        List<Long> newNumbers = Arrays.stream(numbers).collect(Collectors.toList());

        assertFalse(newNumbers.contains(TREND_1));
        assertFalse(newNumbers.contains(TREND_2));

        newNumbers.add(TREND_1);
        newNumbers.add(TREND_1);
        newNumbers.add(TREND_2);
        newNumbers.add(TREND_2);

        long[] trend = statistic.findTrend(newNumbers.toArray(new Long[0]));

        long[] expected = {TREND_1, TREND_2};

        Arrays.sort(trend);
        Arrays.sort(expected);

        assertEquals(expected.length, trend.length);
        assertArrayEquals(expected, trend);
    }

    @Test
    void givenArrayOfNumbers_whenFindingSD_shouldCalculate() {
        double sd = statistic.calculateSD(numbers);

        assertEquals(2000.0, sd);
    }
}
