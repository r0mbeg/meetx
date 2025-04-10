package com.proffen.services;


import com.proffen.models.Member;
import com.proffen.exceptions.ResourceNotFoundException;
import com.proffen.repo.MemberRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
}
