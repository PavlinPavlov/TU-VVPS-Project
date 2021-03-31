package tu.vvps;

import java.util.List;

public class StatisticReport {

    private final long duration;
    private final List<Long> trends;
    private final double standardDeviation;

    public StatisticReport(long duration, List<Long> trends, double standardDeviation) {
        this.duration = duration;
        this.trends = trends;
        this.standardDeviation = standardDeviation;
    }

    public long getDuration() {
        return duration;
    }

    public List<Long> getTrends() {
        return trends;
    }

    public double getStandardDeviation() {
        return standardDeviation;
    }
}
