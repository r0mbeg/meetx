package com.proffen.services;


import com.proffen.dto.responses.EventResponse;
import com.proffen.exceptions.ResourceNotFoundException;
import com.proffen.models.Event;
import com.proffen.models.Member;
import com.proffen.repo.EventRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final MemberService memberService;

    public Event create(String title, String description, String location, String adminName, OffsetDateTime dateTime, List<Long> participantsId) {
        log.info("Try to create event with title: {}", title);
        Member admin = memberService.getByUsername(adminName);
        List<Member> participants = (participantsId != null)
                ? participantsId.stream().map(memberService::getById).toList()
                : new ArrayList<>();
        if (!participants.contains(admin)) {
            participants = new ArrayList<>(participants); // на случай if .toList() дала неизменяемый список
            participants.add(admin);
        }

        Event event = Event.builder()
                .title(title)
                .description(description)
                .location(location)
                .admin(admin)
                .dateTime(dateTime)
                .participants(participants)
                .build();
        log.info("Created event {}", event.getTitle());
        return eventRepo.save(event);
    }

    public List<Event> getAll() {
        log.info("Try to retrieve all events");
        return eventRepo.findAll();
    }

    public List<Event> getAll(String username) {
        log.info("Try to get events for {}", username);
        Member member = memberService.getByUsername(username);
        return eventRepo.findByParticipants_Id(member.getId());
    }

    public Event getById(Long id) {
        log.info("Try to get event with id: {}", id);
        return eventRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found"));
    }
}
