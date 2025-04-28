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
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepo eventRepo;
    private final MemberService memberService;

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
