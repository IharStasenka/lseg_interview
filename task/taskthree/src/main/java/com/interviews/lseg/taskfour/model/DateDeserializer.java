package com.interviews.lseg.taskfour.model;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateDeserializer extends JsonDeserializer<LocalDateTime> {

    public static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss O");

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JacksonException {
        var value = jsonParser.getText().strip().split(" ");
        String standartizedValue = value[0] + " GMT" + value[1];
        return LocalDateTime.parse(standartizedValue, CUSTOM_FORMATTER);
    }
}
