package com.travelconcierge.controller;

import com.travelconcierge.dto.TravelPlanResponseDto;
import com.travelconcierge.dto.TravelRequestDto;
import com.travelconcierge.service.TravelPlannerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/travel")
public class TravelPlanController {

    private final TravelPlannerService plannerService;

    public TravelPlanController(TravelPlannerService plannerService) {
        this.plannerService = plannerService;
    }

    @PostMapping("/plan")
    public ResponseEntity<TravelPlanResponseDto> planTrip(@RequestBody TravelRequestDto request) {
        return ResponseEntity.ok(plannerService.planTrip(request));
    }
}
