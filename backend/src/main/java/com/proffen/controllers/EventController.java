package com.proffen.controllers;


import com.proffen.dto.responses.EventResponse;
import com.proffen.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@SecurityRequirement(name = "JWT")
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    @Operation(summary = "Get paginated list of events",
            description = "Retrieves events with optional status filtering",
            operationId = "getEvents")
    @ApiResponse(responseCode = "200",
            description = "Events retrieved successfully",
            content = @Content(schema = @Schema(implementation = EventResponse.class)))
    public List<EventResponse> getEvents(
            Principal principal
    ) {
        log.info("Getting events for {}", principal.getName());
        return eventService.getAll(principal.getName()).stream().map(EventResponse::toResponse).toList();
    }

    @GetMapping("/events/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        log.info("Getting event with id {}", id);
        return EventResponse.toResponse(eventService.getById(id));
    }
}
