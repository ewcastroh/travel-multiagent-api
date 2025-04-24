package com.travelconcierge.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TravelRequestDto(
        String origin,
        String destination,
        LocalDate startDate,
        LocalDate endDate,
        BigDecimal budget,
        String travelType,
        int peopleCount
) {
}
