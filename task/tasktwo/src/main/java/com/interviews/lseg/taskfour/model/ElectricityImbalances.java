package com.interviews.lseg.taskfour.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ElectricityImbalances(
        Map<Integer, String> headers, Map<Integer, LocalDateTime> timeSeries, List<List<BigDecimal>> imbalancesData) {

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return timeSeries.keySet().stream()
                .flatMap(t -> headers.keySet().stream()
                        .map(h -> headers.get(h) + "; "
                                        + timeSeries.get(t).format(formatter)
                                        + "; " + imbalancesData.get(t).get(h - 1)))
                .collect(Collectors.joining("\n"));
    }
}
