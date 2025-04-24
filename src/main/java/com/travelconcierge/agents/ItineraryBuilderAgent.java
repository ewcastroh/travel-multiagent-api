package com.travelconcierge.agents;

import com.travelconcierge.dto.AgentResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface ItineraryBuilderAgent {

    @SystemMessage("""
            Return a bullet-point list of activities in {{destination}} from {{startDate}} to {{endDate}} for {{travelType}} travel for {{peopleCount}} people.
            - Label each line as Day 1, Day 2, etc.
            - Include 2–3 activities per day.
            - Do not include commentary, just the list.
            """)
    AgentResponse planItinerary(@V("destination") String destination, @V("startDate") String startDate,
                                @V("endDate") String endDate, @V("travelType") String travelType,
                                @V("peopleCount") int peopleCount);
}
