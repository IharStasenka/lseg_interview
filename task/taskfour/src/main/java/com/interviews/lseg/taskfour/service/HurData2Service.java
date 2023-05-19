package com.interviews.lseg.taskfour.service;

import com.interviews.lseg.taskfour.model.HurData2;
import com.interviews.lseg.taskfour.model.HurData2Mapper;
import com.interviews.lseg.taskfour.model.HurData2Row;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
public class HurData2Service {

    private final HurData2Mapper hurData2Mapper;

    public List<HurData2> processWithFilter(BufferedReader reader, Predicate<HurData2> predicate) throws IOException {
        List<HurData2> validHurData2 = new ArrayList<>();
        String line = reader.readLine();
        while (line != null) {
            HurData2 headerData = hurData2Mapper.parseHeader(line);
            boolean isValid = predicate.test(headerData);
            if (isValid) {
                for (int i = 0; i < headerData.numberOfRows(); i++) {
                    String rowData = reader.readLine();
                    HurData2Row row = hurData2Mapper.parseRow(rowData);
                    headerData.data().add(row);
                }
                validHurData2.add(headerData);
            } else {
                for (int i = 0; i < headerData.numberOfRows(); i++) {
                    reader.readLine();
                }
            }
            line = reader.readLine();
        }
        return validHurData2;
    }
}
