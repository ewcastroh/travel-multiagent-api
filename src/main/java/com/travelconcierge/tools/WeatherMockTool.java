package com.travelconcierge.tools;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Component;

@Component
public class WeatherMockTool {

    @Tool(name = "getWeatherForecast")
    public String getForecast(String city, String startDate, String endDate, String travelType) {
        // Simulated logic based on destination or travel type (expand as needed)
        String summary = switch (travelType.toLowerCase()) {
            case "relaxing" -> "Sunny with light breeze and average temperatures around 30°C. Ideal for relaxation.";
            case "adventure" -> "Mildly cloudy, some chance of rain. Still suitable for outdoor activities.";
            case "cultural" -> "Clear skies and moderate weather. Perfect for sightseeing and walking tours.";
            case "business" -> "Mostly sunny with a few clouds. Good for meetings and outdoor lunches.";
            default -> "Weather is mostly favorable for general travel.";
        };

        return String.format("""
                Mock forecast for %s between %s and %s:
                %s
                """, city, startDate, endDate, summary);
    }
}
