package com.travel.multiagent.api.agents;

import com.travel.multiagent.api.dto.AgentResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface FlightFinderAgent {

    @UserMessage("""
            Use the `findFlights` tool and return only a bullet-point list of 2–3 top flight options from {{origin}} to {{destination}} from {{startDate}} to {{endDate}} for {{peopleCount}} people:
            - Include Airline, Departure Time, Stops, and Price
            - Format each line as: Airline – Departure – Time - Stops – Price
            - No introduction or summary, just the list.
            - Return only the list of flights — no other information like hotels, weather, or activities.
            Also include a brief explanation of the flight options.
            """)
    AgentResponse findFlights(@V("origin") String origin, @V("destination") String destination,
                              @V("startDate") String startDate, @V("endDate") String endDate,
                              @V("peopleCount") int peopleCount);
}
