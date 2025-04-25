package com.travelconcierge.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeatherMockTool {

    @Tool(name = "getWeatherForecast")
    public String getForecast(String city, String startDate, String endDate) {
        List<String> weathers = List.of(
                "Sunny with light breeze and average temperatures around 30°C. Ideal for relaxation.",
                "Mildly cloudy, some chance of rain. Still suitable for outdoor activities.",
                "Clear skies and moderate weather. Perfect for sightseeing and walking tours.",
                "Mostly sunny with a few clouds. Good for meetings and outdoor lunches."
        );
        String summary = weathers.get((int) (Math.random() * weathers.size()));

        return String.format("""
                Mock forecast for %s between %s and %s:
                %s
                """, city, startDate, endDate, summary);
    }
}
