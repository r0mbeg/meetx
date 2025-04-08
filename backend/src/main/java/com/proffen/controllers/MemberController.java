package com.proffen.controllers;


import com.proffen.dto.responses.MemberResponse;
import com.proffen.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    public List<MemberResponse> getMembers() {
        log.info("Getting all members");
        return memberService.getAll().stream().map(MemberResponse::toResponse).toList();
    }

    @GetMapping("/members/{id}")
    public MemberResponse getMemberById(@PathVariable Long id) {
        log.info("Getting member with id {}", id);
        return MemberResponse.toResponse(memberService.getById(id));
    }
}
