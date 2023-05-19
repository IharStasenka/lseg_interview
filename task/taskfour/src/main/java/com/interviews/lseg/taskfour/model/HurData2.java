package com.interviews.lseg.taskfour.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record HurData2(
        String name, Integer year, ZoneData zone, Integer number, Integer numberOfRows,
        List<HurData2Row> data) {

    public enum ZoneData {EP, CP}

    public BigDecimal getMaxSpeed() {
        return this.data.stream().map(HurData2Row::getMaxSpeed).max(BigDecimal::compareTo).get();
    }

    @Override
    public String toString() {
        return "Storm name: " + this.name + ", maximum sustained wind speed: " + this.getMaxSpeed() + " knots.";
    }

}
