package com.proffen.controllers;


import com.proffen.dto.responses.EventResponse;
import com.proffen.services.EventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class EventController {

    private final EventService eventService;

    @GetMapping("/events")
    public List<EventResponse> getEvents() {
        log.info("Getting all events");
        return eventService.getAll().stream().map(EventResponse::toResponse).toList();
    }

    @GetMapping("/events/{id}")
    public EventResponse getEventById(@PathVariable Long id) {
        log.info("Getting event with id {}", id);
        return EventResponse.toResponse(eventService.getById(id));
    }
}
