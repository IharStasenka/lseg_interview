package com.interviews.lseg.taskfour.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

public record ReactorData(
        BigDecimal output,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss '+2'")
        LocalDateTime time) {
    @Override
    public String toString() {
        return "value: " + output + ";time: " + time.format(ofPattern("yyyy-MM-dd HH:mm"));
    }
}
