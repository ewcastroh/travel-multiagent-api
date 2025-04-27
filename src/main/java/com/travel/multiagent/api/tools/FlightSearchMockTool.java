package com.travel.multiagent.api.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightSearchMockTool {

    @Tool(name = "findFlights")
    public List<String> findFlights(String origin, String destination, String startDate, String endDate, int peopleCount) {
        return List.of(
                "AirComfort - Medellin - 10:30 AM - Direct - $620",
                "SkyHigh Airlines - Medellin - 11:00 AM - 1 Stop - $580",
                "JetSetters - Medellin - 12:00 PM - Direct - $600",
                "CloudNine - Medellin - 1:00 PM - 2 Stops - $550",
                "AirWaves - Medellin - 2:00 PM - Direct - $630"
        );
    }
}
