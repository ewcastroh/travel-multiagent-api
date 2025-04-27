package com.travel.multiagent.api.util;

import java.util.Arrays;
import java.util.List;

public class ResponseMapperUtil {

    public static List<String> toList(String llmResponse) {
        return Arrays.stream(llmResponse.split("\\n"))
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .toList();
    }

    public static String extractRecommendation(String llmResponse) {
        if (llmResponse.toLowerCase().contains("yes") || llmResponse.toLowerCase().contains("recommend")) {
            return "Yes. " + llmResponse.trim();
        } else if (llmResponse.toLowerCase().contains("no")) {
            return "No. " + llmResponse.trim();
        }
        return llmResponse.trim();
    }
}
