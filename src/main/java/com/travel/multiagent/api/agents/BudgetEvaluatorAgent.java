package com.travel.multiagent.api.agents;

import com.travel.multiagent.api.dto.AgentResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.math.BigDecimal;
import java.util.List;

@AiService
public interface BudgetEvaluatorAgent {

    @UserMessage("""
            Determine if the travel plan is within the ${{budget}} budget for {{peopleCount}} people.
            Flight cost estimate per person can be taken from the flight decision list {{flightsDecision}}.
            Hotel cost estimate per night can be taken from the hotel decision list {{hotelsDecision}}.
            Total nights: 5
            
            Return one sentence with a clear recommendation like:
            - Plan is within budget.
            - Over budget due to flight or hotel costs.
            """)
    AgentResponse assessBudget(@V("budget") BigDecimal budget, @V("peopleCount") int peopleCount,
                               @V("flightsDecision") List<String> flightsDecision,
                               @V("hotelsDecision") List<String> hotelsDecision);
}
