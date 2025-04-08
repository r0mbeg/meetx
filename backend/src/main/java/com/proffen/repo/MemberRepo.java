package com.proffen.repo;

import com.proffen.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MemberRepo extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {
}
