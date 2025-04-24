package com.travelconcierge.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class HotelMockTool {

    @Tool(name = "findHotels")
    public String findHotels(String destination, String startDate, String endDate, String hotelClass) {
        return String.format("""
                Found 3 hotels in %s between %s and %s (Class: %s):
                - Hotel Luna Beach ($140/night)
                - Comfort Suites ($110/night)
                - Riviera Palace ($180/night)
                """, destination, startDate, endDate, hotelClass);
    }
}
