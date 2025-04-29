package com.proffen.dto.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(description = "Event creation/update request payload")
public record EventRequest(
        @Schema(
                description = "Title of the event",
                example = "Skiing in Ohta Park",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        @NotBlank
        @Size(min = 2, max = 64, message = "Title must be 2-64 characters")
        String title,

        @Schema(
                description = "Detailed description of the event",
                example = "Let's do something!",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(max = 5000, message = "Description must be up to 5000 characters")
        String description,

        @Schema(
                description = "Physical or virtual location of the event",
                example = "Vyritsa, Pilnyi prospekt, 10",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @Size(min = 2, max = 128, message = "Location must be 2-128 characters")
        String location,

        @Schema(
                description = "Date and time when the event starts",
                example = "2025-05-01T00:00+03:00",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mmXXX")
        @NotNull
        OffsetDateTime dateTime,

        @Schema(
                description = "List of participant user IDs",
                example = "[1, 2, 3]",
                requiredMode = Schema.RequiredMode.NOT_REQUIRED
        )
        List<@NotNull Long> participants
) {
}
