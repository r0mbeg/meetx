package com.proffen.dto.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.proffen.models.Event;
import com.proffen.models.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public record EventResponse (
        @NotNull(message = "Event id must not be null")
        @Positive(message = "Event id must be positive")
        Long id,

        @NotBlank(message = "Event title must not be blank")
        String title,

        String description,

        String location,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmX")
        @NotNull(message = "Event creation date must not be null")
        LocalDateTime createdAt,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmX")
        @NotNull(message = "Event update date must not be null")
        LocalDateTime updatedAt,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmX")
        @NotNull(message = "Event date must not be null")
        LocalDateTime dateTime,

        @NotNull(message = "Event admin must not be null")
        MemberResponse admin,

        @NotEmpty(message = "Participants list must not be empty")
        List<MemberResponse> participants){

    public static EventResponse toResponse(Event event) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }

        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getLocation(),
                event.getCreatedAt(),
                event.getUpdatedAt(),
                event.getDateTime(),
                MemberResponse.toResponse(
                        Objects.requireNonNull(event.getAdmin(), "Event admin must not be null")
                ),
                event.getParticipants().stream()
                        .map(MemberResponse::toResponse)
                        .toList()
        );
    }
}
