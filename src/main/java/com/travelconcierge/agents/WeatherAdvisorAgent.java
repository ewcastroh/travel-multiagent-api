package com.travelconcierge.agents;

import com.travelconcierge.dto.AgentResponse;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.spring.AiService;

@AiService
public interface WeatherAdvisorAgent {

    @UserMessage("""
            Use the `getWeatherForecast` tool and provide a concise summary of the forecast for {{city}} from {{startDate}} to {{endDate}}.
            Include average temperature and weather condition.
            Return as a single sentence.
            - Format: "The weather in {{city}} from {{startDate}} to {{endDate}} is expected to be 'average temperature' with 'weather condition'."
            Return only the weather information — no other information like flights, hotels, or activities.
            Also include a brief explanation of the forecast.
            """)
    AgentResponse forecast(@V("city") String city, @V("startDate") String startDate, @V("endDate") String endDate);
}
