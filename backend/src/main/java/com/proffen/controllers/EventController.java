package com.proffen.controllers;


import com.proffen.dto.requests.EventRequest;
import com.proffen.dto.responses.ErrorResponse;
import com.proffen.dto.responses.EventResponse;
import com.proffen.services.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/events")
    @Operation(summary = "Create new event",
            description = "Creates an event",
            operationId = "createEvent")
    @ApiResponse(responseCode = "200",
            description = "Event created successfully",
            content = @Content(schema = @Schema(implementation = EventResponse.class)))
    public EventResponse createEvent(
            @Parameter(description = "Event creation data", required = true)
            @Valid @RequestBody EventRequest request,
            Principal principal) {
        log.info("Received request to create an event with title: {}", request.title());
        return EventResponse.toResponse(eventService.create(
                request.title(),
                request.description(),
                request.location(),
                principal.getName(),
                request.dateTime(),
                request.participants()
        ));
    }


    @GetMapping("/events/{id}")
    @Operation(summary = "Get event by ID",
            description = "Get event details by their unique id",
            operationId = "getEventById")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Event details got successfully",
                    content = @Content(schema = @Schema(implementation = EventResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Event not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public EventResponse getEventById(@PathVariable Long id) {
        log.info("Getting event with id {}", id);
        return EventResponse.toResponse(eventService.getById(id));
    }
}
