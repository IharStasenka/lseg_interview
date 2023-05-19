package com.interviews.lseg.taskfour.model;

import static com.interviews.lseg.taskfour.model.HurData2Row.*;
import static com.interviews.lseg.taskfour.model.HurData2Row.HemisphereData.*;
import static java.lang.Integer.parseInt;
import static java.util.Arrays.copyOfRange;
import static org.apache.commons.lang3.StringUtils.deleteWhitespace;
import static org.apache.commons.lang3.math.NumberUtils.createBigDecimal;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HurData2Mapper {

    public static final String DELIMETER = ",";

    public HurData2 parseHeader(String line) {
        String[] fields = line.split(DELIMETER);
        String codeData = fields[0];
        return HurData2.builder()
                .zone(HurData2.ZoneData.valueOf(codeData.substring(0, 2)))
                .number(parseInt(codeData.substring(2, 4)))
                .year(parseInt(codeData.substring(4, 8)))
                .name(deleteWhitespace(fields[1]))
                .numberOfRows(parseString(fields[2]))
                .data(new ArrayList<>())
                .build();
    }

    private Integer parseString(String data) {
        return parseInt(deleteWhitespace(data));
    }

    public HurData2Row parseRow(String line) {
        String[] fields = line.split(DELIMETER);
        return builder()
                .time(parseLocalDateTime(fields[0], fields[1]))
                .identifier(parseRecordIdentifier(fields[2]))
                .status(StatusOfSystem.valueOf(deleteWhitespace(fields[3])))
                .latitude(getCoordinateData(fields[4]))
                .longitude(getCoordinateData(fields[5]))
                .maxSpeed(getBigDecimalFromString(fields[6]))
                .maxPressure(getBigDecimalFromString(fields[7]))
                .windRose(getWindRose(copyOfRange(fields, 8, fields.length - 1)))
                .build();
    }

    private static RecordIdentifier parseRecordIdentifier(String data) {
        String preparedData = deleteWhitespace(data);

        return preparedData.isEmpty() ? null : RecordIdentifier.valueOf(preparedData);
    }

    private LocalDateTime parseLocalDateTime(String dateData, String timeData) {

        return LocalDateTime.of(
                parseInt(dateData.substring(0, 4)),
                parseInt(dateData.substring(4, 6)),
                parseInt(dateData.substring(6, 8)),
                parseInt(timeData.substring(1, 3)),
                parseInt(timeData.substring(3, 5)));
    }

    private static CoordinateData getCoordinateData(String data) {
        String preparedData = deleteWhitespace(data);

        return new CoordinateData(
                createBigDecimal(preparedData.substring(0, preparedData.length() - 2)),
                HemisphereData.valueOf(String.valueOf(preparedData.charAt(preparedData.length() - 1))));
    }

    private static BigDecimal getBigDecimalFromString(String data) {

        return createBigDecimal(deleteWhitespace(data));
    }

    private HashMap<String, List<CoordinateData>> getWindRose(String[] data) {
        HashMap<String, List<CoordinateData>> windRoseData = new HashMap<>();
        windRoseData.put("34", getListOfCoordinateForHemisphereQuadrants(copyOfRange(data, 0, 4)));
        windRoseData.put("50", getListOfCoordinateForHemisphereQuadrants(copyOfRange(data, 4, 8)));
        windRoseData.put("64", getListOfCoordinateForHemisphereQuadrants(copyOfRange(data, 8, 12)));

        return windRoseData;
    }

    private List<CoordinateData> getListOfCoordinateForHemisphereQuadrants(String[] data) {
        return List.of(
                new CoordinateData(getBigDecimalFromString(data[0]), NE),
                new CoordinateData(getBigDecimalFromString(data[1]), SE),
                new CoordinateData(getBigDecimalFromString(data[2]), SW),
                new CoordinateData(getBigDecimalFromString(data[3]), NW)
        );
    }
}
