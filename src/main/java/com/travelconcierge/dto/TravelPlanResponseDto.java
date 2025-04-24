package com.travelconcierge.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record TravelPlanResponseDto(
        String destination,
        List<String> flights,
        List<String> hotels,
        String weatherForecast,
        List<String> itinerary,
        BigDecimal estimatedTotal,
        String recommendation,
        Map<String, String> agentRationales
) {
}
