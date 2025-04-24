package com.travelconcierge.agents;

import com.travelconcierge.dto.AgentResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface FlightFinderAgent {

    @SystemMessage("""
            Return a bullet-point list of 2–3 top flight options from {{origin}} to {{destination}} from {{startDate}} to {{endDate}} for {{peopleCount}} people:
            - Include Airline, Departure Time, Stops, and Price
            - Format each line as: Airline – Departure – Stops – Price
            - No introduction or summary, just the list.
            Also include a brief explanation of the flight options.
            """)
    AgentResponse findFlights(@V("origin") String origin, @V("destination") String destination, @V("startDate") String startDate,
                              @V("endDate") String endDate, @V("peopleCount") int peopleCount);
}
