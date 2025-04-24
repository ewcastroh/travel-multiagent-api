package com.travelconcierge.agents;

import com.travelconcierge.dto.AgentResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

import java.math.BigDecimal;
import java.util.List;

@AiService
public interface BudgetCalculatorAgent {

    @UserMessage("""
            You are a budget calculation agent.
            You will be given:
            - The total budget: ${{budget}} (for reference)
            - The number of travelers: {{peopleCount}}
            - A list of selected flight options: {{flightsDecision}}
            - A list of selected hotel options: {{hotelsDecision}}
            
            Instructions:
            1. Extract estimated flight prices from the {{flightsDecision}} list (price per person).
            2. Extract hotel prices from the {{hotelsDecision}} list (price per night per person).
            3. Calculate the amount of days from {{startDate}} to {{endDate}}.
            4. Calculate the estimated total cost as:
            
               estimatedTotal = (flightPrice * peopleCount) + (hotelPrice * calculated amount of days * peopleCount)
            
            Return the estimated total cost as a **number only**, without currency symbols or explanations.
            Example output: `1650`
            """)
    AgentResponse calculateBudget(@V("budget") BigDecimal budget, @V("peopleCount") int peopleCount,
                                  @V("startDate") String startDate, @V("endDate") String endDate,
                                  @V("flightsDecision") List<String> flightsDecision,
                                  @V("hotelsDecision") List<String> hotelsDecision);
}
