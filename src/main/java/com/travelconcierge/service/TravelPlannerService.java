package com.travelconcierge.service;

import com.travelconcierge.agents.BudgetCalculatorAgent;
import com.travelconcierge.agents.BudgetEvaluatorAgent;
import com.travelconcierge.agents.FlightFinderAgent;
import com.travelconcierge.agents.HotelBookerAgent;
import com.travelconcierge.agents.IntentClarifierAgent;
import com.travelconcierge.agents.ItineraryBuilderAgent;
import com.travelconcierge.agents.WeatherAdvisorAgent;
import com.travelconcierge.dto.AgentResponse;
import com.travelconcierge.dto.TravelPlanResponseDto;
import com.travelconcierge.dto.TravelRequestDto;
import com.travelconcierge.util.ResponseMapperUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TravelPlannerService {

    private final IntentClarifierAgent intentClarifier;
    private final FlightFinderAgent flightFinder;
    private final HotelBookerAgent hotelBooker;
    private final WeatherAdvisorAgent weatherAdvisor;
    private final BudgetCalculatorAgent budgetCalculator;
    private final BudgetEvaluatorAgent budgetEvaluator;
    private final ItineraryBuilderAgent itineraryBuilder;

    public TravelPlannerService(IntentClarifierAgent intentClarifier,
                                FlightFinderAgent flightFinder,
                                HotelBookerAgent hotelBooker,
                                WeatherAdvisorAgent weatherAdvisor,
                                BudgetCalculatorAgent budgetCalculator,
                                BudgetEvaluatorAgent budgetEvaluator,
                                ItineraryBuilderAgent itineraryBuilder) {
        this.intentClarifier = intentClarifier;
        this.flightFinder = flightFinder;
        this.hotelBooker = hotelBooker;
        this.weatherAdvisor = weatherAdvisor;
        this.budgetCalculator = budgetCalculator;
        this.budgetEvaluator = budgetEvaluator;
        this.itineraryBuilder = itineraryBuilder;
    }

    public TravelPlanResponseDto planTrip(TravelRequestDto request) {
        Map<String, String> rationales = new LinkedHashMap<>();

        AgentResponse clarifiedResponse = intentClarifier.clarify(request.origin(), request.destination(),
                request.startDate().toString(), request.endDate().toString(), String.valueOf(request.budget()),
                request.travelType(), request.peopleCount());
        rationales.put("IntentClarifier", clarifiedResponse.rationale());

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

        AgentResponse calculatedBudgetResponse = budgetCalculator.calculateBudget(request.budget(), request.peopleCount(),
                request.startDate().toString(), request.endDate().toString(), flightsDecision, hotelsDecision);
        BigDecimal estimatedBudgetDecision = new BigDecimal(calculatedBudgetResponse.decision().toString());
        rationales.put("BudgetCalculator", calculatedBudgetResponse.rationale());

        AgentResponse evaluatedBudgetResponse = budgetEvaluator.assessBudget(request.budget(), request.peopleCount(),
                flightsDecision, hotelsDecision);
        String budgetRecommendationDecision = ResponseMapperUtil.extractRecommendation(evaluatedBudgetResponse.decision().toString());
        rationales.put("BudgetEvaluator", evaluatedBudgetResponse.rationale());

        AgentResponse itineraryResponse = itineraryBuilder.planItinerary(request.destination(),
                request.startDate().toString(), request.endDate().toString(), request.travelType(),
                request.peopleCount());
        List<String> itineraryDecision = ResponseMapperUtil.toList(itineraryResponse.decision().toString());
        rationales.put("ItineraryBuilder", itineraryResponse.rationale());

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
