package com.travel.multiagent.api.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelMockTool {

    @Tool(name = "findHotels")
    public List<String> findHotels(String destination, String startDate, String endDate, int peopleCount) {
        return List.of(
                "Hotel Luna Beach – 3 Stars – $140/night",
                "Comfort Suites – 2 Stars – $110/night",
                "Riviera Palace – 3 Stars – $180/night",
                "Ocean View Resort – 4 Stars – $200/night",
                "Budget Stay – 1 Star – $80/night"
        );
    }
}
