package com.travel.multiagent.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.multiagent.api.agents.IntentClarifierAgent;
import com.travel.multiagent.api.dto.SimpleTextResponseDto;
import com.travel.multiagent.api.dto.TravelPlanResponseDto;
import com.travel.multiagent.api.dto.TravelRequestDto;
import com.travel.multiagent.api.dto.UserMessageRequestDto;
import com.travel.multiagent.api.service.TravelPlannerService;
import dev.langchain4j.service.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/travel")
public class TravelPlanController {

    private final TravelPlannerService plannerService;
    private final IntentClarifierAgent intentClarifier;
    private final ObjectMapper objectMapper;

    public TravelPlanController(TravelPlannerService plannerService, IntentClarifierAgent intentClarifier, ObjectMapper objectMapper) {
        this.plannerService = plannerService;
        this.intentClarifier = intentClarifier;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/plan")
    public ResponseEntity<TravelPlanResponseDto> planTrip(@RequestBody TravelRequestDto request) {
        return ResponseEntity.ok(plannerService.planTrip(request));
    }

    /**
     * Accepts user messages and tries to extract a complete travel plan.
     */
    @PostMapping("/chat")
    public ResponseEntity<?> handleClarification(@RequestParam String sessionId,
                                                 @RequestBody UserMessageRequestDto userMessage) {
        Result<String> result = intentClarifier.handleUserMessage(sessionId, userMessage.message());
        String content = result.content();

        try {
            TravelRequestDto travelRequest = objectMapper.readValue(content, TravelRequestDto.class);
            TravelPlanResponseDto plan = plannerService.planTrip(travelRequest);
            return ResponseEntity.ok(plan);
        } catch (Exception ex) {
            // Not a valid TravelRequestDto yet — continue the conversation
            return ResponseEntity.ok(new SimpleTextResponseDto(content));
        }
    }
}
