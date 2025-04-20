package com.proffen.services;


import com.proffen.dto.requests.MemberRegisterRequest;
import com.proffen.models.Member;
import com.proffen.exceptions.ResourceNotFoundException;
import com.proffen.models.enums.Role;
import com.proffen.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepo memberRepo;

    public List<Member> getAll() {
        log.info("Try to retrieve all members");
        return memberRepo.findAll();
    }

    public Member getById(Long id) {
        log.info("Try to retrieve member with id: {}", id);
        return memberRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
    }

    public Member register(MemberRegisterRequest request, PasswordEncoder encoder) {
        Member member = new Member(
                null,
                request.username(),
                encoder.encode(request.password()),
                request.email(),
                request.name(),
                Role.USER
        );
        log.info("Registered user {}", member.getUsername());
        return memberRepo.save(member);

    }

    public Member loadByUsername(String username) {
        return memberRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));
    }

}
