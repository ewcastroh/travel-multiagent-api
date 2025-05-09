package com.travel.multiagent.api.service;

import com.travel.multiagent.api.agents.BudgetCalculatorAgent;
import com.travel.multiagent.api.agents.BudgetEvaluatorAgent;
import com.travel.multiagent.api.agents.FlightFinderAgent;
import com.travel.multiagent.api.agents.HotelBookerAgent;
import com.travel.multiagent.api.agents.ItineraryBuilderAgent;
import com.travel.multiagent.api.agents.WeatherAdvisorAgent;
import com.travel.multiagent.api.dto.AgentResponse;
import com.travel.multiagent.api.dto.TravelPlanResponseDto;
import com.travel.multiagent.api.dto.TravelRequestDto;
import com.travel.multiagent.api.util.ResponseMapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TravelPlannerService {

    private final FlightFinderAgent flightFinder;
    private final HotelBookerAgent hotelBooker;
    private final WeatherAdvisorAgent weatherAdvisor;
    private final BudgetCalculatorAgent budgetCalculator;
    private final BudgetEvaluatorAgent budgetEvaluator;
    private final ItineraryBuilderAgent itineraryBuilder;

    public TravelPlannerService(FlightFinderAgent flightFinder,
                                HotelBookerAgent hotelBooker,
                                WeatherAdvisorAgent weatherAdvisor,
                                BudgetCalculatorAgent budgetCalculator,
                                BudgetEvaluatorAgent budgetEvaluator,
                                ItineraryBuilderAgent itineraryBuilder) {
        this.flightFinder = flightFinder;
        this.hotelBooker = hotelBooker;
        this.weatherAdvisor = weatherAdvisor;
        this.budgetCalculator = budgetCalculator;
        this.budgetEvaluator = budgetEvaluator;
        this.itineraryBuilder = itineraryBuilder;
    }

    public TravelPlanResponseDto planTrip(TravelRequestDto request) {
        Map<String, String> rationales = new LinkedHashMap<>();

        AgentResponse flightsResponse = flightFinder.findFlights(request.origin(), request.destination(),
                request.startDate().toString(), request.endDate().toString(), request.peopleCount());
        List<String> flightsDecision = ResponseMapperUtil.toList(flightsResponse.decision().toString());
        rationales.put("FlightFinder", flightsResponse.rationale());

        AgentResponse hotelsResponse = hotelBooker.findHotels(request.destination(),
                request.startDate().toString(), request.endDate().toString(), request.peopleCount());
        List<String> hotelsDecision = ResponseMapperUtil.toList(hotelsResponse.decision().toString());
        rationales.put("HotelBooker", hotelsResponse.rationale());

        AgentResponse weatherResponse = weatherAdvisor.forecast(request.destination(),
                request.startDate().toString(), request.endDate().toString());
        String weatherForecastDecision = weatherResponse.decision().toString();
        rationales.put("WeatherAdvisor", weatherResponse.rationale());

        AgentResponse itineraryResponse = itineraryBuilder.planItinerary(request.destination(),
                request.startDate().toString(), request.endDate().toString(), request.travelType(),
                request.peopleCount());
        List<String> itineraryDecision = ResponseMapperUtil.toList(itineraryResponse.decision().toString());
        rationales.put("ItineraryBuilder", itineraryResponse.rationale());

        AgentResponse calculatedBudgetResponse = budgetCalculator.calculateBudget(request.budget(), request.peopleCount(),
                request.startDate().toString(), request.endDate().toString(), flightsDecision, hotelsDecision);
        BigDecimal estimatedBudgetDecision = new BigDecimal(calculatedBudgetResponse.decision().toString());
        rationales.put("BudgetCalculator", calculatedBudgetResponse.rationale());

        AgentResponse evaluatedBudgetResponse = budgetEvaluator.assessBudget(request.budget(), request.peopleCount(),
                flightsDecision, hotelsDecision);
        String budgetRecommendationDecision = ResponseMapperUtil.extractRecommendation(evaluatedBudgetResponse.decision().toString());
        rationales.put("BudgetEvaluator", evaluatedBudgetResponse.rationale());

        return new TravelPlanResponseDto(
                request.destination(),
                flightsDecision,
                hotelsDecision,
                weatherForecastDecision,
                itineraryDecision,
                estimatedBudgetDecision,
                budgetRecommendationDecision,
                rationales
        );
    }
}
