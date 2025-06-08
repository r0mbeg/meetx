package com.proffen.repo;

import com.proffen.models.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepo extends JpaRepository<Member, Long>, JpaSpecificationExecutor<Member> {

    Optional<Member> findByUsername(String username);

    @Query("""
    SELECT m FROM Member m
    WHERE LOWER(m.username) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(m.name) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(m.email) LIKE LOWER(CONCAT('%', :query, '%'))
""")
    Page<Member> fuzzySearch(@Param("query") String query, Pageable pageable);

}
