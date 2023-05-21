package com.interviews.lseg.taskfour.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ofPattern;

public record ReactorData(
        BigDecimal output,
        @JsonDeserialize(using = DateDeserializer.class)
        LocalDateTime time) {
    @Override
    public String toString() {
        return "value: " + output + ";time: " + time.format(ofPattern("yyyy-MM-dd HH:mm"));
    }
}
