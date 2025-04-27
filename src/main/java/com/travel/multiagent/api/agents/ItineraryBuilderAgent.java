package com.travel.multiagent.api.agents;

import com.travel.multiagent.api.dto.AgentResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface ItineraryBuilderAgent {

    @UserMessage("""
            Return a bullet-point list of activities in {{destination}} from {{startDate}} to {{endDate}} for {{travelType}} travel for {{peopleCount}} people.
            - Label each line as Day 1, Day 2, etc.
            - Include 2–3 activities per day.
            - Do not include commentary, just the list.
            - Return only the list of activities — no other information like flights, hotels, or weather.
            """)
    AgentResponse planItinerary(@V("destination") String destination, @V("startDate") String startDate,
                                @V("endDate") String endDate, @V("travelType") String travelType,
                                @V("peopleCount") int peopleCount);
}
