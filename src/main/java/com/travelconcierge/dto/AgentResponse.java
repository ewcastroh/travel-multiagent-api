package com.travelconcierge.dto;

public record AgentResponse(
        Object decision,     // Could be List<String> or String depending on agent
        String rationale
) {
}
