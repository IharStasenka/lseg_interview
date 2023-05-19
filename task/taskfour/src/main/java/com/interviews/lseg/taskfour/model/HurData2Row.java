package com.interviews.lseg.taskfour.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class HurData2Row {

    private LocalDateTime time;

    private RecordIdentifier identifier;

    private StatusOfSystem status;

    private CoordinateData latitude;

    private CoordinateData longitude;

    private BigDecimal maxSpeed;

    private BigDecimal maxPressure;

    private Map<String, List<CoordinateData>> windRose = new HashMap<>();

    public enum HemisphereData {N, S, W, E, NE, SE, NW, SW}

    public enum RecordIdentifier {L, P, I, S, T}

    public enum StatusOfSystem {TD, TS, HU, EX, SD, SS, LO, DB, ET, PT, ST, WV}

    public record CoordinateData(BigDecimal data, HemisphereData position) {
    }

}
