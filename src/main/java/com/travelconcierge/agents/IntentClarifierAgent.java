package com.travelconcierge.agents;

import com.travelconcierge.dto.AgentResponse;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface IntentClarifierAgent {

    @SystemMessage("""
            You are a helpful travel agent. A user has submitted a request:
            - Origin: {{origin}}
            - Destination: {{destination}}
            - Dates: From {{startDate}} to {{endDate}}
            - Budget: {{budget}}
            - Travel Type: {{travelType}}
            - Travelers: {{peopleCount}}
            
            Clarify and refine this request into a concise travel plan with assumptions if needed.
            Return the clarified intent in one paragraph.
            """)
    AgentResponse clarify(@V("origin") String origin, @V("destination") String destination, @V("startDate") String startDate,
                          @V("endDate") String endDate, @V("budget") String budget, @V("travelType") String travelType,
                          @V("peopleCount") int peopleCount);
}
