package com.proffen.controllers;


import com.proffen.dto.responses.MemberResponse;
import com.proffen.dto.responses.ErrorResponse;
import com.proffen.dto.responses.PageResponse;
import com.proffen.services.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api")
@Tag(name = "Member Management",
        description = "Endpoints for getting and managing member information")
@SecurityRequirement(name = "JWT")
@ApiResponses({
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400",
                description = "Invalid input parameters or malformed request",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "401",
                description = "Unauthorized - Invalid or expired JWT token",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "403",
                description = "Forbidden - Insufficient permissions",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                description = "Member not found",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "422",
                description = "Validation error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(responseCode = "500",
                description = "Internal server error",
                content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
})
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all members list",
            description = "Get all members list without search and pagination",
            operationId = "getMembers")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Member list got successfully",
                    content = @Content(schema = @Schema(implementation = MemberResponse.class)))
    })
    public List<MemberResponse> getMembers() {
        log.info("Getting all members");
        return memberService.getAll().stream().map(MemberResponse::toResponse).toList();
    }


    @GetMapping("/members/search")
    @Operation(summary = "Search members with pagination",
            description = "Fuzzy search by username, name or email (case-insensitive), with pagination",
            operationId = "searchMembers")
    public PageResponse<MemberResponse> searchMembers(
            @RequestParam("query")
            @Parameter(description = "Search query (partial match on username, name, or email)",
                    example = "Alex") String query,

            @ParameterObject Pageable pageable) {

        log.info("Searching members with query: {}", query);
        return PageResponse.from(
                memberService.search(query, pageable)
                        .map(MemberResponse::toResponse)
        );
    }



    @GetMapping("/members/{id}")
    @Operation(summary = "Get member by ID",
            description = "Get member details by their unique id",
            operationId = "getMemberById")
    @ApiResponses({
            @ApiResponse(responseCode = "200",
                    description = "Member details got successfully",
                    content = @Content(schema = @Schema(implementation = MemberResponse.class))),
            @ApiResponse(responseCode = "404",
                    description = "Member not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public MemberResponse getMemberById(
            @Parameter(description = "ID of the member to get",
                    required = true,
                    example = "123")
            @PathVariable Long id) {
        log.info("Getting member with id {}", id);
        return MemberResponse.toResponse(memberService.getById(id));
    }
}

