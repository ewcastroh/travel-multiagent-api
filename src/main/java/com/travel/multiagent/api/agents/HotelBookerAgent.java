package com.travel.multiagent.api.agents;

import com.travel.multiagent.api.dto.AgentResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface HotelBookerAgent {

    @UserMessage("""
            Use the `findHotels` tool and return only a bullet-point list of 2–3 hotel options in {{destination}} from {{startDate}} to {{endDate}} for {{peopleCount}} people.
            - Include Hotel Name, Class (stars), Price per night
            - Format each line as: Hotel – Stars – Price/night per person
            - Do not explain, just return the list.
            - Return only the list of hotels — no other information like flights, weather, or activities.
            Also include a brief explanation of the hotel options.
            """)
    AgentResponse findHotels(@V("destination") String destination, @V("startDate") String startDate,
                             @V("endDate") String endDate, @V("peopleCount") int peopleCount);
}
