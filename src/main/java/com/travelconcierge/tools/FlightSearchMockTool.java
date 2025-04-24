package com.travelconcierge.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class FlightSearchMockTool {

    @Tool(name = "findFlights")
    public String findFlights(String origin, String destination, String startDate, String endDate, int peopleCount) {
        return String.format("""
                Found 2 mock flights from %s to %s between %s and %s for %d traveler(s):
                - Airlines: $620, Direct, Departs 10:30 AM
                - AeroTravel: $540, 1 Stop (MIA), Departs 2:15 PM
                """, origin, destination, startDate, endDate, peopleCount);
    }
}
