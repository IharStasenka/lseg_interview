package com.interviews.lseg.taskfour.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ReactorDataMapper {
    private ObjectMapper mapper;

    public ReactorDataMapper() {
        mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public ReactorData parse(String data) {
        try {
            return mapper.readValue(data, ReactorData.class);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
